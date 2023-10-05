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
public class BoothResponse {

    private Long id;

    private String title;

    private String introduction;

    private BoothType boothType;

    private String location;

    private String notice;

    private String content;

    private String startAt;

    private String endAt;

    private long likeCnt;

    private Boolean isLike;

    private List<ImageResponse> images;

    public BoothResponse(final Booth booth, final boolean activeLike) {
        this.id = booth.getId();
        this.title = booth.getTitle();
        this.introduction = booth.getIntroduction();
        this.boothType = booth.getBoothType();
        this.location = booth.getLocation();
        this.notice = booth.getNotice();
        this.content = booth.getContent();
        this.startAt = String.valueOf(booth.getStartAt());
        this.endAt = String.valueOf(booth.getEndAt());
        this.likeCnt = booth.getLikeCount();
        this.isLike = activeLike;
    }
}
