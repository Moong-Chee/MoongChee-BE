package project.MoongChee.domain.chat.dto.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {
    private static final long serialVersionUID = 2082503192322391880L;

    private Long roomId;

    private Long senderId;

    private String senderName;

    private String content;
}
