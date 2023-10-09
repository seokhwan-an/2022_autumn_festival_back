package likelion.festival.service.like;

import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.like.Likes;
import likelion.festival.domain.like.repository.LikesRepository;
import likelion.festival.dto.like.LikesValueDto;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongLikesKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoothRepository boothRepository;
    private final UniqueKeyMaker uniqueKeyMaker;

    public LikesValueDto create(final Long id) {
        final Booth booth = boothRepository.findById(id)
                .orElseThrow(WrongBoothId::new);
        String cookieKey = createCookieKey();
        Likes like = new Likes(cookieKey, booth);
        likesRepository.save(like);

        return new LikesValueDto(like);
    }

    public void delete(final Long boothId, final String cookieKey) {
        boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);
        final Likes likes = likesRepository.findByCookieKey(cookieKey)
                .orElseThrow(WrongLikesKey::new);

        likesRepository.deleteById(likes.getId());
    }

    private String createCookieKey() {
        while (true) {
            final String cookieKey = uniqueKeyMaker.generate();
            Optional<Likes> likes = likesRepository.findByCookieKey(cookieKey);
            if (likes.isEmpty()) {
                return cookieKey;
            }
        }
    }

    public Optional<Cookie> findBoothCookie(final Cookie[] cookies, final Long boothId) {
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(String.valueOf(boothId)))
                .findFirst();
    }
}
