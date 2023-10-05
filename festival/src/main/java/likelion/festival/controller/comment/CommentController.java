package likelion.festival.controller.comment;

import likelion.festival.dto.comment.CommentDeleteRequest;
import likelion.festival.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable final Long id, @RequestBody final CommentDeleteRequest request) {
        return commentService.delete(id, request);
    }

    @DeleteMapping("{id}/force")
    public String deleteForceComment(@PathVariable final Long id) {
        return commentService.force_delete(id);
    }
}
