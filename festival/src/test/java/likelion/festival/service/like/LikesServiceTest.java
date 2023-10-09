package likelion.festival.service.like;

import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.booth.BoothType;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.like.Likes;
import likelion.festival.domain.like.repository.LikesRepository;
import likelion.festival.dto.like.LikesValueDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LikesServiceTest {

    private Booth booth;

    @Autowired
    private BoothRepository boothRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private EntityManager entityManager;

    private LikesService likesService;

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
        likesService = new LikesService(likesRepository, boothRepository, new BasicKeyMaker());
    }

    @AfterEach
    void clean() {
        boothRepository.delete(booth);
    }

    @DisplayName("쿠키를 생성한다.")
    @Test
    void create_cookie() {
        // given
        // when
        final LikesValueDto result = likesService.create(booth.getId());

        // then
        final UniqueKeyMaker basicKeyMaker = new BasicKeyMaker();
        assertThat(result.getCookieKey()).isEqualTo(basicKeyMaker.generate());
    }

    @DisplayName("쿠키를 삭제한다.")
    @Test
    void delete_cookie() {
        // given
        final UniqueKeyMaker basicKeyMaker = new BasicKeyMaker();
        final Likes like = new Likes("delete" + basicKeyMaker.generate(), booth);
        likesRepository.save(like);

        // when
        likesService.delete(booth.getId(), "deletetest");
        final Optional<Likes> result = likesRepository.findByCookieKey("deletetest");

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("booth에 알맞은 쿠키를 가져온다.")
    void find_cookie() {
        // given
        final Cookie testCookie1 = new Cookie(String.valueOf(booth.getId()), "test");
        final Cookie testCookie2 = new Cookie(String.valueOf(2), "test");
        final Cookie testCookie3 = new Cookie(String.valueOf(3), "test");
        final Cookie[] cookies = {testCookie1, testCookie2, testCookie3};

        // when
        final Optional<Cookie> result = likesService.findBoothCookie(cookies, booth.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getValue()).isEqualTo(testCookie1.getValue());
    }
}
