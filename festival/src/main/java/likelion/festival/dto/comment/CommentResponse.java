package likelion.festival.dto.comment;

import likelion.festival.domain.comment.Comment;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentResponse {

    private Long id;

    private String writer;

    private String content;

    private LocalDateTime createdAt;

    public CommentResponse(final Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedDateTime();
    }

}
