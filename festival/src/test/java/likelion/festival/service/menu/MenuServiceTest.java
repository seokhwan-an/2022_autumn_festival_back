package likelion.festival.service.menu;

import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.booth.BoothType;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.menu.Menu;
import likelion.festival.domain.menu.repository.MenuRepository;
import likelion.festival.dto.menu.MenuRequest;
import likelion.festival.dto.menu.MenuResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MenuServiceTest {

    private Booth booth;

    @Autowired
    private BoothRepository boothRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private EntityManager entityManager;

    private MenuService menuService;

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
        menuService = new MenuService(menuRepository, boothRepository);
    }

    @AfterEach
    void clean() {
        boothRepository.delete(booth);
    }

    private void saveAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("부스에 메뉴를 추가한다.")
    @Test
    void create_menu() {
        // given
        final MenuRequest menuRequest = new MenuRequest("메뉴", 1000);

        // when
        final MenuResponse result = menuService.create(booth.getId(), menuRequest);

        // then
        assertThat(result.getName()).isEqualTo("메뉴");
        assertThat(result.getPrice()).isEqualTo(1000);
    }

    @DisplayName("부스에 모든 메뉴를 불러온다.")
    @Test
    void read_menu() {
        // given
        final Menu menu1 = new Menu("메뉴1", 1000, booth);
        final Menu menu2 = new Menu("메뉴2", 1500, booth);
        final Menu menu3 = new Menu("메뉴3", 2000, booth);

        menuRepository.save(menu1);
        menuRepository.save(menu2);
        menuRepository.save(menu3);

        saveAndClear();
        // when
        final List<MenuResponse> result = menuService.getAll(booth.getId());

        // then
        final List<MenuResponse> expect = List.of(
                new MenuResponse(1L, "메뉴1", 1000),
                new MenuResponse(2L, "메뉴2", 1500),
                new MenuResponse(3L, "메뉴3", 2000)
        );
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expect);
    }

    @DisplayName("메뉴를 수정한다.")
    @Test
    void update_menu() {
        // given
        final Menu menu = new Menu("메뉴", 1000, booth);
        menuRepository.save(menu);

        final MenuRequest menuUpdateRequest = new MenuRequest("메뉴수정", 1500);

        // when
        final MenuResponse result = menuService.update(menu.getId(), menuUpdateRequest);

        // then
        assertThat(result.getName()).isEqualTo("메뉴수정");
        assertThat(result.getPrice()).isEqualTo(1500);
    }

    @DisplayName("메뉴를 삭제한다.")
    @Test
    void delete_menu() {
        // given
        final Menu menu = new Menu("메뉴", 1000, booth);
        menuRepository.save(menu);

        // when
        menuService.delete(menu.getId());
        final Optional<Menu> result = menuRepository.findById(menu.getId());

        // then
        assertThat(result).isEmpty();
    }
}
