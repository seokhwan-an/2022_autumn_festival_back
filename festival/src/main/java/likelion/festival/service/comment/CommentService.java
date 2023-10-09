package likelion.festival.service.comment;

import likelion.festival.domain.comment.Active;
import likelion.festival.dto.comment.CommentCreateRequest;
import likelion.festival.dto.comment.CommentDeleteRequest;
import likelion.festival.dto.comment.CommentResponse;
import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.comment.Comment;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongCommentId;
import likelion.festival.exception.WrongPassword;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.comment.repository.CommentRepository;
import likelion.festival.security.Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoothRepository boothRepository;

    public List<CommentResponse> getAll(final Long boothId) {
        boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);
        List<Comment> comments = commentRepository.findByBoothIdAndActiveOrderByCreatedDateTimeDesc(boothId, new Active(true));
        return comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public CommentResponse create(final Long boothId, final CommentCreateRequest commentCreateRequest) {
        final Booth booth = boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);

        final Comment comment = new Comment(commentCreateRequest.getWriter(),
                Encrypt.getEncrypt(commentCreateRequest.getPassword()),
                commentCreateRequest.getContent(),
                booth);
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    @Transactional
    public String delete(final Long commentId, final CommentDeleteRequest request) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(WrongCommentId::new);
        if (!comment.isSamePassword(request.getPassword())) {
            throw new WrongPassword();
        }
        comment.delete();
        return "Ok";
    }

    @Transactional
    public String force_delete(final Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(WrongBoothId::new);
        comment.delete();
        return "Ok";
    }
}
