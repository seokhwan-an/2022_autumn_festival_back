package likelion.festival.controller.menu;

import likelion.festival.dto.menu.MenuRequest;
import likelion.festival.dto.menu.MenuResponse;
import likelion.festival.service.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/menu")
@RestController
public class MenuController {

    private final MenuService menuService;

    @PutMapping("/{id}")
    public MenuResponse updateMenu(@PathVariable Long id, @RequestBody MenuRequest request) {
        return menuService.update(id, request);
    }

    @DeleteMapping("{id}")
    public String deleteMenu(@PathVariable Long id) {
        return menuService.delete(id);
    }
}
