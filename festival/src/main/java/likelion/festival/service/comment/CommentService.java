package likelion.festival.service.comment;

import likelion.festival.dto.comment.CommentCreateRequest;
import likelion.festival.dto.comment.CommentDeleteRequest;
import likelion.festival.dto.comment.CommentResponseDto;
import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.comment.Comment;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongCommentId;
import likelion.festival.exception.WrongPassword;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.comment.repository.CommentRepository;
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

    public List<CommentResponseDto> getAll(final Long boothId) {
        boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);
        List<Comment> comments = commentRepository.findByBoothIdAndActiveOrderByCreatedDateTimeDesc(boothId, Boolean.TRUE);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public CommentResponseDto create(final Long boothId, final CommentCreateRequest commentCreateRequest) {
        final Booth booth = boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);

        final Comment comment = new Comment(commentCreateRequest.getWriter(),
                commentCreateRequest.getPassword(),
                commentCreateRequest.getContent(),
                booth);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String delete(Long commentId, CommentDeleteRequest request) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(WrongCommentId::new);
        if (comment.isCorrectPassword(request.getPassword())) {
            throw new WrongPassword();
        }
        comment.delete();
        return "Ok";
    }

    @Transactional
    public String force_delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(WrongBoothId::new);
        comment.delete();
        return "Ok";
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = null;
        ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
