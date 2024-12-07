package project.MoongChee.domain.chat.dto.response;

import java.time.LocalDateTime;
import project.MoongChee.domain.chat.domain.ChatMessage;

public record ChatMessageResponse(
        Long senderId,
        String senderName,
        String content,
        LocalDateTime createdAt
) {
    public static ChatMessageResponse fromEntity(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getSenderId(),
                chatMessage.getSenderName(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }
}
