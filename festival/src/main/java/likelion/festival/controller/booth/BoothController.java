package likelion.festival.controller.booth;

import likelion.festival.dto.booth.BoothDayLocationResponse;
import likelion.festival.dto.booth.BoothResponse;
import likelion.festival.dto.booth.BoothRequest;
import likelion.festival.dto.comment.CommentCreateRequest;
import likelion.festival.dto.comment.CommentResponse;
import likelion.festival.dto.like.LikesValueDto;
import likelion.festival.dto.menu.MenuRequest;
import likelion.festival.dto.menu.MenuResponse;
import likelion.festival.domain.booth.Booth;
import likelion.festival.service.*;
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
    public List<BoothResponse> boothFilter(HttpServletRequest request, @RequestParam String filter) {
        return boothService.boothFilterAndSearch(request, filter);
    }

    @GetMapping("/top5")
    public List<BoothResponse> boothTopFive(HttpServletRequest request) {
        return boothService.boothTopFive(request);
    }

    @GetMapping
    public List<BoothDayLocationResponse> boothDayLocation(HttpServletRequest request, @RequestParam String location) {
        return boothService.boothDayLocation(request, location);
    }

    @PostMapping()
    public Integer boothCreate(@RequestPart(value = "imgList", required = false) List<MultipartFile> imgList,
                               @RequestBody BoothRequest request) {
        Booth booth = boothService.create(request);
        if (imgList == null) {
            return HttpStatus.OK.value();
        }
        imageService.saveBoothImage(imgList, booth);
        return HttpStatus.OK.value();
    }

    @GetMapping("{id}")
    public BoothResponse boothRead(HttpServletRequest request, @PathVariable Long id) {
        return boothService.read(request, id);
    }

    @PostMapping("/{id}/likes")
    public LikesValueDto likeCreate(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
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
    public String likeDelete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
        if (boothCookie.isPresent()) {
            Cookie userCookie = boothCookie.get();
            String cookieKey = userCookie.getValue();
            likesService.delete(id, cookieKey);

            Cookie keyCookie = new Cookie(id.toString(), null);
            keyCookie.setMaxAge(0);
            keyCookie.setPath("/");
            response.addCookie(keyCookie);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return "Ok";
    }

    @PostMapping("{id}/comments")
    public CommentResponse createComment(@PathVariable Long id, @RequestBody CommentCreateRequest commentCreateRequest, HttpServletRequest request) {
        return commentService.create(id, commentCreateRequest);
    }

    @GetMapping("{id}/comments")
    public List<CommentResponse> getCommentList(@PathVariable Long id) {
        return commentService.getAll(id);
    }

    @GetMapping("{id}/menus")
    public List<MenuResponse> getMenuList(@PathVariable Long id) {
        return menuService.getAll(id);
    }

    @PostMapping("{id}/menus")
    public MenuResponse createMenu(@PathVariable Long id, @RequestBody MenuRequest menuRequest) {
        return menuService.create(id, menuRequest);
    }
}
