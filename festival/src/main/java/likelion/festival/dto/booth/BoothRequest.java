package likelion.festival.dto.booth;

import lombok.Getter;

@Getter
public class BoothRequest {

    private Long id;

    private String title;

    private String introduction;

    private String boothType;

    private String location;

    private String notice;

    private String content;

    private String startAt;

    private String endAt;
}
