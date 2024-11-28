package project.MoongChee.domain.image.dto.request;

public record ImageDto(
        String name,
        String url
) {
    public static ImageDto of(String name, String url) {
        return new ImageDto(name, url);
    }
}
