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

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public Menu(final String name, final long price, final Booth booth) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.booth = booth;
    }

    public void update(final String name, final long price) {
        this.name = name;
        this.price = price;
    }
}
