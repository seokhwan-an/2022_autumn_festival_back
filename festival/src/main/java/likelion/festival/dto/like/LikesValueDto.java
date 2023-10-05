package likelion.festival.dto.like;

import likelion.festival.domain.like.Likes;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikesValueDto {

    private String cookieKey;

    public LikesValueDto (final Likes like) {
        this.cookieKey = like.getCookieKey();
    }
}
