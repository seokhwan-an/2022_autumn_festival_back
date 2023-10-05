package likelion.festival.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentDeleteRequest {

    private String password;
}
