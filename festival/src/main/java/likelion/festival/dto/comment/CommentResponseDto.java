package likelion.festival.dto.comment;

import likelion.festival.domain.comment.Comment;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private Long id;

    private String writer;

    private String content;

    private LocalDateTime createdDateTime;

    public CommentResponseDto(final Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.createdDateTime = comment.getCreatedDateTime();
    }

}
