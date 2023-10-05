package likelion.festival.service.menu;

import likelion.festival.dto.menu.MenuRequest;
import likelion.festival.dto.menu.MenuResponse;
import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.menu.Menu;
import likelion.festival.exception.WrongBoothId;
import likelion.festival.exception.WrongMenuId;
import likelion.festival.domain.booth.repository.BoothRepository;
import likelion.festival.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final BoothRepository boothRepository;

    @Transactional
    public MenuResponse create(final Long boothId, final MenuRequest menuRequest) {
        final Booth booth = boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);

        final Menu menu = new Menu(menuRequest.getName(), menuRequest.getPrice(), booth);
        menuRepository.save(menu);
        return new MenuResponse(menu);
    }

    public List<MenuResponse> getAll(final Long boothId) {
        final Booth booth = boothRepository.findById(boothId)
                .orElseThrow(WrongBoothId::new);
        final List<Menu> menus = booth.getMenus();
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public MenuResponse update(Long id, MenuRequest menuRequest) {
        final Menu menu = menuRepository.findById(id)
                .orElseThrow(WrongMenuId::new);
        menu.update(menuRequest.getName(), menuRequest.getPrice());
        return new MenuResponse(menu);
    }

    @Transactional
    public String delete(Long id) {
        final Menu menu = menuRepository.findById(id)
                .orElseThrow(WrongMenuId::new);
        menuRepository.delete(menu);
        return "Ok";
    }
}
