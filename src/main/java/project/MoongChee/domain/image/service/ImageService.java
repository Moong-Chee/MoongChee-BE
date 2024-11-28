package project.MoongChee.domain.image.service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.MoongChee.domain.image.domain.Image;
import project.MoongChee.domain.image.dto.request.ImageDto;
import project.MoongChee.domain.image.repository.ImageRepository;
import project.MoongChee.domain.user.domain.User;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3ImageService s3ImageService;
    private final ImageRepository imageRepository;

    @Transactional
    public Image save(MultipartFile image, User user) throws IOException {
        ImageDto profileImageDto = s3ImageService.uploadImage(image);
        return imageRepository.save(Image.from(profileImageDto, user));
    }

    public ImageDto getImage(MultipartFile image) throws IOException {
        return s3ImageService.uploadImage(image);
    }

    @Transactional
    public void delete(Image profileImage) {
        imageRepository.delete(profileImage);
    }
}
