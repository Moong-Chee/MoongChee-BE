package project.MoongChee.domain.chat.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 2082503192322391880L;

    private Long roomId;

    private Long senderId;

    private String content;
}
