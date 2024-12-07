package project.MoongChee.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateChatRoomRequest(
        @NotNull Long user1Id,
        @NotNull Long user2Id
) {
}
