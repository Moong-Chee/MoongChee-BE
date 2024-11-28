package project.MoongChee.domain.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.MoongChee.domain.image.dto.request.ImageDto;
import project.MoongChee.domain.user.domain.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String name;

    private String url;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Image from(ImageDto dto, User user) {
        return Image.builder()
                .name(dto.name())
                .url(dto.url())
                .user(user)
                .build();
    }

    public void update(ImageDto dto) {
        this.name = dto.name();
        this.url = dto.url();
    }
}