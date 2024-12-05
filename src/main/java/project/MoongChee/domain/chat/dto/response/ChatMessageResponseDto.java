package project.MoongChee.domain.chat.dto.response;

import java.time.LocalDateTime;
import project.MoongChee.domain.chat.domain.ChatMessage;

public record ChatMessageResponseDto(
        Long senderId,
        String senderName,
        String content,
        LocalDateTime createdAt
) {
    public static ChatMessageResponseDto fromEntity(ChatMessage chatMessage) {
        return new ChatMessageResponseDto(
                chatMessage.getSenderId(),
                chatMessage.getSenderName(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }
}
