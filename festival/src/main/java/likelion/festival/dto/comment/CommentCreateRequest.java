package likelion.festival.dto.comment;


import likelion.festival.domain.booth.Booth;
import lombok.*;

@AllArgsConstructor
@Getter
public class CommentCreateRequest {

    private String writer;

    private String password;

    private String ip;

    private Boolean active;

    private String content;

    private Booth booth;
}
