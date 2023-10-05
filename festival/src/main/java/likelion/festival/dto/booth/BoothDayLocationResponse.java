package likelion.festival.dto.booth;

import likelion.festival.domain.image.Image;
import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.booth.BoothType;
import likelion.festival.dto.image.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BoothDayLocationResponse {

    private Long id;

    private BoothType boothType;

    private String title;

    private String introduction;

    private String location;

    private long likeCnt;

    private Boolean isLike;

    private List<ImageResponse> images;

    public BoothDayLocationResponse(final Booth booth, final boolean activeLike) {
        this.id = booth.getId();
        this.title = booth.getTitle();
        this.introduction = booth.getIntroduction();
        this.boothType = booth.getBoothType();
        this.location = booth.getLocation();
        this.likeCnt = booth.getLikeCount();
        this.isLike = activeLike;
    }
}
