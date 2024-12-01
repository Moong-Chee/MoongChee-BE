package project.MoongChee.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import project.MoongChee.domain.chat.dto.PublishMessage;

public record PublishMessageResponse(
        Long roomId,
        Long senderId,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createAt
) {
    public PublishMessageResponse(PublishMessage publishMessage) {
        this(publishMessage.getRoomId(), publishMessage.getSenderId(), publishMessage.getContent(),
                LocalDateTime.now());
    }
}
