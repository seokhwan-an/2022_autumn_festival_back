package likelion.festival.domain.menu;

import likelion.festival.domain.booth.Booth;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "menu")
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public Menu(final String name, final int price, final Booth booth) {
        this.id = null;
        this.name = new Name(name);
        this.price = new Price(price);
        this.booth = booth;
    }

    public void update(final String name, final int price) {
        this.name = new Name(name);
        this.price = new Price(price);
    }

    public String getName() {
        return this.name.getValue();
    }

    public int getPrice() {
        return this.price.getValue();
    }
}
