package project.MoongChee.domain.chat.dto.response;

import java.time.LocalDateTime;
import project.MoongChee.domain.chat.domain.ChatMessage;

public record LatestMessageDto(
        String content,
        LocalDateTime createdAt
) {
    public static LatestMessageDto of(ChatMessage message) {
        return new LatestMessageDto(message.getContent(), message.getCreatedAt());
    }
}
