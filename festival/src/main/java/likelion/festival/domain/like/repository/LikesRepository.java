package likelion.festival.domain.like.repository;

import likelion.festival.domain.like.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByCookieKey(String cookieKey);
}
