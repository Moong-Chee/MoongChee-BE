package project.MoongChee.domain.chat.dto.response;

import java.util.List;
import project.MoongChee.domain.image.domain.Image;

public record ChattingDto(
        Long user1Id,
        Long user2Id,
        String user1Name,
        String user2Name,
        Image user1ProfileImage,
        Image user2ProfileImage,
        List<ChatMessageResponse> chatMessageList
) {
}
