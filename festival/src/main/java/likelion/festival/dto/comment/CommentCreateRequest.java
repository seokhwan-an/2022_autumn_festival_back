package likelion.festival.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentCreateRequest {

    private String writer;

    private String password;

    private String content;
}
