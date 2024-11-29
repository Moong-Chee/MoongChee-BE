package project.MoongChee.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.MoongChee.domain.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
