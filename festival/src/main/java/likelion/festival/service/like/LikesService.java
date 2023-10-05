package likelion.festival.service.like;

import likelion.festival.dto.like.LikesValueDto;
import likelion.festival.domain.like.Likes;
import likelion.festival.domain.booth.Booth;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongLikesKey;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.like.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoothRepository boothRepository;

    public LikesValueDto create(final Long id) {
        final Booth booth = boothRepository.findById(id)
                .orElseThrow(WrongBoothId::new);
        String cookieKey = createCookieKey();
        Likes like = new Likes(cookieKey, booth);
        likesRepository.save(like);

        return new LikesValueDto(like);
    }

    public void delete(final Long boothId, final String cookieKey) {
        Optional<Booth> booth = boothRepository.findById(boothId);
        if (booth.isEmpty()) {
            throw new WrongBoothId();
        }
        Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
        if (likes.isEmpty()) {
            throw new WrongLikesKey();
        }
        likesRepository.deleteById(likes.get().getId());
    }

    private String createCookieKey() {
        while (true) {
            final String cookieKey = String.valueOf(UUID.randomUUID());
            Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
            if (likes.isEmpty()) {
                return cookieKey;
            }
        }
    }

    public Optional<Cookie> findBoothCookie(HttpServletRequest request, Long id) {
        Cookie[] userCookies = request.getCookies();
        if (userCookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(userCookies)
                .filter(cookie -> cookie.getName().equals(String.valueOf(id)))
                .findFirst();
    }
}
