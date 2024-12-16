package project.MoongChee.domain.chat.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatMessage")
@Getter
@Builder
public class ChatMessage {
    @Id
    private String id;

    private Long roomId;

    private Long senderId;

    private String senderName;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
