package project.MoongChee.domain.chat.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import project.MoongChee.domain.chat.dto.PublishMessage;
import project.MoongChee.global.common.domain.BaseTimeEntity;

@Document
@Getter
@Builder
public class ChatMessage extends BaseTimeEntity {
    @Id
    private Long id;

    private Long roomId;

    private Long senderId;

    private String content;

    public static ChatMessage of(PublishMessage publishMessage) {
        return ChatMessage.builder()
                .roomId(publishMessage.getRoomId())
                .senderId(publishMessage.getSenderId())
                .content(publishMessage.getContent())
                .build();
    }
}
