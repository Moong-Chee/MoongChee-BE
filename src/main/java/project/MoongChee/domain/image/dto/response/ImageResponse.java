package project.MoongChee.domain.image.dto.response;

import project.MoongChee.domain.image.domain.Image;

public record ImageResponse(
        Long id,
        String name,
        String url
) {
    public static ImageResponse from(Image image) {
        return new ImageResponse(image.getId(), image.getName(), image.getUrl());
    }
}

