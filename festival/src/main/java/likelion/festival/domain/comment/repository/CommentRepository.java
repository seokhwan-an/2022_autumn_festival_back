package likelion.festival.domain.comment.repository;

import likelion.festival.domain.comment.Active;
import likelion.festival.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoothIdAndActiveOrderByCreatedDateTimeDesc(Long id, Active active);
}
