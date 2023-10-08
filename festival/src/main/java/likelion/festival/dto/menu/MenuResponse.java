package likelion.festival.dto.menu;

import likelion.festival.domain.menu.Menu;
import lombok.*;

@AllArgsConstructor
@Getter
public class MenuResponse {

    private Long id;

    private String name;

    private int price;

    public MenuResponse(final Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
}
