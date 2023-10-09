package likelion.festival.controller.booth;

import likelion.festival.domain.booth.Booth;
import likelion.festival.dto.booth.BoothDayLocationResponse;
import likelion.festival.dto.booth.BoothRequest;
import likelion.festival.dto.booth.BoothResponse;
import likelion.festival.dto.comment.CommentCreateRequest;
import likelion.festival.dto.comment.CommentResponse;
import likelion.festival.dto.like.LikesValueDto;
import likelion.festival.dto.menu.MenuRequest;
import likelion.festival.dto.menu.MenuResponse;
import likelion.festival.service.ImageService;
import likelion.festival.service.booth.BoothService;
import likelion.festival.service.comment.CommentService;
import likelion.festival.service.like.LikesService;
import likelion.festival.service.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = "/api/booths")
@RestController
public class BoothController {

    private final BoothService boothService;
    private final LikesService likesService;
    private final CommentService commentService;
    private final MenuService menuService;
    private final ImageService imageService;

    @GetMapping(params = {"filter"})
    public List<BoothResponse> boothFilter(final HttpServletRequest request, @RequestParam final String filter) {
        final Cookie[] cookies = request.getCookies();
        return boothService.boothFilterAndSearch(cookies, filter);
    }

    @GetMapping("/top5")
    public List<BoothResponse> boothTopFive(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        return boothService.boothTopFive(cookies);
    }

    @GetMapping
    public List<BoothDayLocationResponse> boothDayLocation(final HttpServletRequest request, @RequestParam final String location) {
        final Cookie[] cookies = request.getCookies();
        return boothService.boothDayLocation(cookies, location);
    }

    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "imgList", required = false) final List<MultipartFile> imgList,
                               @RequestBody final BoothRequest request) {
        Booth booth = boothService.create(request);
        if (imgList == null) {
            return HttpStatus.OK.value();
        }
        imageService.saveBoothImage(imgList, booth);
        return HttpStatus.OK.value();
    }

    @GetMapping("{id}")
    public BoothResponse boothRead(final HttpServletRequest request, @PathVariable final Long id) {
        final Cookie[] cookies = request.getCookies();
        return boothService.read(cookies, id);
    }

    @PostMapping("/{id}/likes")
    public LikesValueDto likeCreate(@PathVariable final Long id, final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        Optional<Cookie> boothCookie = likesService.findBoothCookie(cookies, id);
        if (boothCookie.isPresent()) {
            throw new IllegalArgumentException("이미 쿠키 있음");
        }
        LikesValueDto likes = likesService.create(id);
        Cookie keyCokkie = new Cookie(id.toString(), likes.getCookieKey());
        keyCokkie.setMaxAge(7 * 60 * 60 * 24);
        keyCokkie.setPath("/");
        response.addCookie(keyCokkie);
        return likes;
    }

    @DeleteMapping("/{id}/likes")
    public String likeDelete(@PathVariable final Long id, final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        Optional<Cookie> boothCookie = likesService.findBoothCookie(cookies, id);
        if (boothCookie.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 쿠키입니다.");
        }
        Cookie userCookie = boothCookie.get();
        String cookieKey = userCookie.getValue();
        likesService.delete(id, cookieKey);

        Cookie keyCookie = new Cookie(String.valueOf(id), null);
        keyCookie.setMaxAge(0);
        keyCookie.setPath("/");
        response.addCookie(keyCookie);

        return "Ok";
    }

    @PostMapping("{id}/comments")
    public CommentResponse createComment(@PathVariable final Long id, @RequestBody final CommentCreateRequest commentCreateRequest) {
        return commentService.create(id, commentCreateRequest);
    }

    @GetMapping("{id}/comments")
    public List<CommentResponse> getCommentList(@PathVariable final Long id) {
        return commentService.getAll(id);
    }

    @GetMapping("{id}/menus")
    public List<MenuResponse> getMenuList(@PathVariable final Long id) {
        return menuService.getAll(id);
    }

    @PostMapping("{id}/menus")
    public MenuResponse createMenu(@PathVariable final Long id, @RequestBody final MenuRequest menuRequest) {
        return menuService.create(id, menuRequest);
    }
}
