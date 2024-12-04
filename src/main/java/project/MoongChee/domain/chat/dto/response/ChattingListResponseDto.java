package project.MoongChee.domain.chat.dto.response;

import project.MoongChee.domain.chat.domain.ChatRoom;

public record ChattingListResponseDto(
        Long roomId,
        Long user1Id,
        Long user2Id,
        String user1Name,
        String user2Name,
        LatestMessageDto latestMessageDto
) {
    public static ChattingListResponseDto of(ChatRoom chatRoom, LatestMessageDto latestMessageDto) {
        return new ChattingListResponseDto(
                chatRoom.getId(),
                chatRoom.getUser1().getId(),
                chatRoom.getUser2().getId(),
                chatRoom.getUser1().getCustomId(),
                chatRoom.getUser2().getCustomId(),
                latestMessageDto
        );
    }
}
