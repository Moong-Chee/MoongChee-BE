package project.MoongChee.domain.chat.dto.response;

import project.MoongChee.domain.chat.domain.ChatRoom;
import project.MoongChee.domain.image.domain.Image;

public record ChattingListResponse(
        Long roomId,
        Long user1Id,
        Long user2Id,
        String user1Name,
        String user2Name,
        Image user1ProfileImage,
        Image user2ProfileImage,
        LatestMessageDto latestMessageDto
) {
    public static ChattingListResponse of(ChatRoom chatRoom, LatestMessageDto latestMessageDto) {
        return new ChattingListResponse(
                chatRoom.getId(),
                chatRoom.getUser1().getId(),
                chatRoom.getUser2().getId(),
                chatRoom.getUser1().getName(),
                chatRoom.getUser2().getName(),
                chatRoom.getUser1().getProfileImage(),
                chatRoom.getUser2().getProfileImage(),
                latestMessageDto
        );
    }
}
