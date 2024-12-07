package project.MoongChee.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChatRoomCheckRequest(
        @NotNull Long user1Id,
        @NotNull Long user2Id
) {
}
