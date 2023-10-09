package likelion.festival.service.comment;

import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.booth.BoothType;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.comment.Comment;
import likelion.festival.domain.comment.repository.CommentRepository;
import likelion.festival.dto.comment.CommentCreateRequest;
import likelion.festival.dto.comment.CommentDeleteRequest;
import likelion.festival.dto.comment.CommentResponse;
import likelion.festival.exception.WrongPassword;
import likelion.festival.security.Encrypt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class CommentServiceTest {

    private Booth booth;

    @Autowired
    private BoothRepository boothRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        booth = new Booth("부스",
                "부스 소개",
                "부스 내용",
                "부스 공지사항",
                BoothType.BOOTH,
                "부스 위치",
                LocalDate.of(2023, 10, 7),
                LocalDate.of(2023, 10, 10));
        boothRepository.save(booth);
        commentService = new CommentService(commentRepository, boothRepository);
    }

    @AfterEach
    void clean() {
        boothRepository.delete(booth);
    }

    private void saveAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("부스에 댓글을 추가한다.")
    @Test
    void create_menu() {
        // given
        final CommentCreateRequest commentCreateRequest = new CommentCreateRequest("작성자", "password1", "댓글 내용");

        // when
        final CommentResponse result = commentService.create(booth.getId(), commentCreateRequest);

        // then
        assertThat(result.getWriter()).isEqualTo("작성자");
        assertThat(result.getContent()).isEqualTo("댓글 내용");
    }

    @DisplayName("부스에 모든 댓글을 최신순으로 읽어온다.")
    @Test
    void read_comment() {
        // given
        final Comment comment1 = new Comment("작성자1", "password1", "댓글 내용1", booth);
        final Comment comment2 = new Comment("작성자2", "password2", "댓글 내용2", booth);
        final Comment comment3 = new Comment("작성자3", "password3", "댓글 내용3", booth);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        saveAndClear();

        // when
        final List<CommentResponse> result = commentService.getAll(booth.getId());

        // then
        final List<CommentResponse> expect = List.of(
                new CommentResponse(3L, "작성자3", "댓글 내용3", LocalDateTime.now()),
                new CommentResponse(2L, "작성자2", "댓글 내용2", LocalDateTime.now()),
                new CommentResponse(1L, "작성자1", "댓글 내용1", LocalDateTime.now())
        );
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(expect);
    }

    @Test
    @DisplayName("사용자가 자신의 댓글을 비밀번호를 통해서 삭제한다.")
    void delete_comment_by_password() {
        // given
        final Comment comment = new Comment("작성자", Encrypt.getEncrypt("password1"), "댓글 내용", booth);
        commentRepository.save(comment);

        final CommentDeleteRequest request = new CommentDeleteRequest("password1");

        // when
        commentService.delete(comment.getId(), request);
        final Comment result = commentRepository.findById(comment.getId()).get();

        // then
        assertThat(result.getActive()).isFalse();
    }

    @DisplayName("잘못된 비밀번호로 댓글을 지우려고 하면 예외를 발생시킨다.")
    @Test
    void delete_fail_comment_with_wrong_password() {
        // given
        final Comment comment = new Comment("작성자", "password1", "댓글 내용", booth);
        commentRepository.save(comment);

        final CommentDeleteRequest request = new CommentDeleteRequest("wrongpwd1");

        // when
        // then
        assertThatThrownBy(() -> commentService.delete(comment.getId(), request))
                .isInstanceOf(WrongPassword.class);
    }

    @DisplayName("댓글을 강제로 삭제한다.")
    @Test
    void delete_comment_force() {
        //given
        final Comment comment = new Comment("작성자", "password1", "댓글 내용", booth);
        commentRepository.save(comment);

        //when
        commentService.force_delete(comment.getId());
        final Comment result = commentRepository.findById(comment.getId()).get();

        //then
        assertThat(result.getActive()).isFalse();
    }
}
