package likelion.festival.service.booth;

import likelion.festival.dto.booth.BoothDayLocationResponse;
import likelion.festival.dto.booth.BoothResponse;
import likelion.festival.dto.booth.BoothRequest;
import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.booth.BoothType;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.service.like.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoothService {

    private final BoothRepository boothRepository;
    private final LikesService likesService;

    public List<BoothResponse> boothFilterAndSearch(final HttpServletRequest request, final String search) {
        List<Booth> booths = boothRepository.findByTitle(search);
        if (booths.isEmpty()) {
            booths = boothRepository.findByLocation(search);
        }
        if (booths.isEmpty()) {
            booths = boothRepository.findByMenusName(search);
        }
        return booths.stream()
                .map(booth -> new BoothResponse(booth, checkIsLike(request, booth.getId())))
                .collect(Collectors.toList());
    }

    public List<BoothResponse> boothTopFive(final HttpServletRequest request) {
        final List<Booth> booths = boothRepository.findAll();
        return booths.stream()
                .filter(booth -> booth.isActive(LocalDate.now()))
                .sorted(Comparator.comparing(Booth::getLikeCount).reversed())
                .limit(5)
                .map(booth -> new BoothResponse(booth, checkIsLike(request, booth.getId())))
                .collect(Collectors.toUnmodifiableList());
    }

    //날짜와 장소로 필터링 하는 기능 ok
    public List<BoothDayLocationResponse> boothDayLocation(HttpServletRequest request, String location) {
        LocalDate today = LocalDate.now();
        List<Booth> booths = boothRepository.findByLocation(location);
        return booths.stream()
                .filter(booth -> booth.isActive(today))
                .map(booth -> new BoothDayLocationResponse(booth, checkIsLike(request, booth.getId())))
                .collect(Collectors.toList());
    }

    //생성 ok
    @Transactional
    public Booth create(BoothRequest request) {
        final Booth booth = new Booth(request.getTitle(),
                request.getIntroduction(),
                request.getContent(),
                request.getNotice(),
                BoothType.valueOf(request.getBoothType()),
                request.getLocation(),
                LocalDate.parse(request.getStartAt(), DateTimeFormatter.ISO_DATE),
                LocalDate.parse(request.getEndAt(), DateTimeFormatter.ISO_DATE));

        return boothRepository.save(booth);
    }

    //읽기 ok
    public BoothResponse read(final HttpServletRequest request, final Long id) {
        final Booth booth = boothRepository.findById(id)
                .orElseThrow(WrongBoothId::new);
        return new BoothResponse(booth, checkIsLike(request, id));
    }

    private Boolean checkIsLike(HttpServletRequest request, Long id) {
        Optional<Cookie> boothCookie = likesService.findBoothCookie(request, id);
        return boothCookie.isPresent();
    }
}
