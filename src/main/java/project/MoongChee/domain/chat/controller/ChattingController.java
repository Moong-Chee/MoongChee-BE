package project.MoongChee.domain.chat.controller;

import static project.MoongChee.domain.chat.controller.ResponseMessage.CHATROOM_GET;
import static project.MoongChee.domain.chat.controller.ResponseMessage.CHATTING_LIST_GET;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.chat.dto.response.ChattingDto;
import project.MoongChee.domain.chat.dto.response.ChattingListResponseDto;
import project.MoongChee.domain.chat.service.ChattingService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "CHAT", description = "채팅 컨트롤러")
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChattingController {
    private final ChattingService chattingService;

    // 채팅방 하나 조회 (대화 내역 반환)
    @GetMapping("/chatting/{roomId}/{page}/{size}")
    @Operation(summary = "단일 채팅방 + 대화 내역 조회")
    public ApiData<ChattingDto> findChatting(@PathVariable Long roomId, @PathVariable Integer page,
                                             @PathVariable Integer size) {
        ChattingDto response = chattingService.getChatRoom(roomId, page, size);
        return ApiData.response(CHATROOM_GET.getCode(), CHATROOM_GET.getMessage(), response);
    }


    @GetMapping("/chattingList/{userId}")
    @Operation(summary = "특정 유저 모든 채팅방 목록 조회")
    public ApiData<List<ChattingListResponseDto>> findChattingList(@PathVariable Long userId) {
        List<ChattingListResponseDto> response = chattingService.getChattingList(userId);
        return ApiData.response(CHATTING_LIST_GET.getCode(), CHATTING_LIST_GET.getMessage(), response);
    }
}
