package likelion.festival.domain.comment;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Active {

    @Column(name = "active", nullable = false)
    private Boolean value;

    public Active(final boolean value) {
        this.value = value;
    }

    public void delete() {
        this.value = false;
    }
}
