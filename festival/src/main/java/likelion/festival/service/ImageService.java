package likelion.festival.service;

import likelion.festival.domain.image.Image;
import likelion.festival.domain.booth.Booth;
import likelion.festival.domain.notification.Notification;
import likelion.festival.domain.image.repository.ImageRepository;
import likelion.festival.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public void saveNotificationImage(final List<MultipartFile> itemImgList, final Notification notification) {
        try {
            for (MultipartFile requestImage : itemImgList) {
                String origFilename = requestImage.getOriginalFilename();
                String servFilename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "/src/main/resources/";

                final Image image = new Image(origFilename, servFilename, savePath, notification);
                imageRepository.save(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveBoothImage(final List<MultipartFile> itemImgList, final Booth booth) {
        try {
            for (MultipartFile requestImage : itemImgList) {
                String origFilename = requestImage.getOriginalFilename();
                String servFilename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "/src/main/resources/";

                final Image image = new Image(origFilename, servFilename, savePath, booth);
                imageRepository.save(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
