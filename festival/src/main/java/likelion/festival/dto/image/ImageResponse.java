package likelion.festival.dto.image;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageResponse {

    private Long id;

    private String originFileName;

    private String serverFileName;

    private String storedFilePath;

}
