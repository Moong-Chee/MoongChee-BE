package project.MoongChee.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record GetChatRoomRequest(
        @NotNull Long roomId,
        @NotNull Integer size,
        @NotNull Integer page
) {
}
