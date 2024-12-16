package project.MoongChee.domain.chat.controller;

import static project.MoongChee.domain.chat.controller.ResponseMessage.CHATROOM_GET_SUCCESS;
import static project.MoongChee.domain.chat.controller.ResponseMessage.CHATTING_LIST_GET_SUCCESS;
import static project.MoongChee.domain.chat.controller.ResponseMessage.MESSAGE_SEND_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.chat.dto.request.MessageDto;
import project.MoongChee.domain.chat.dto.response.ChatMessageResponse;
import project.MoongChee.domain.chat.dto.response.ChattingListResponse;
import project.MoongChee.domain.chat.service.ChattingService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "CHAT", description = "채팅 컨트롤러")
@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChattingController {
    private final ChattingService chattingService;

    @GetMapping("/chatting/{roomId}/{page}/{size}")
    @Operation(summary = "단일 채팅방 + 대화 내역 조회")
    public ApiData<List<ChatMessageResponse>> findChatting(@PathVariable Long roomId, @PathVariable Integer page,
                                                           @PathVariable Integer size) {
        List<ChatMessageResponse> response = chattingService.findChatMessages(roomId, page, size);
        return ApiData.response(CHATROOM_GET_SUCCESS.getCode(), CHATROOM_GET_SUCCESS.getMessage(), response);
    }

    @GetMapping("/chattingList/{userId}")
    @Operation(summary = "특정 유저 모든 채팅방 목록 조회")
    public ApiData<List<ChattingListResponse>> findChattingList(@PathVariable Long userId) {
        List<ChattingListResponse> response = chattingService.findUserChatRooms(userId);
        return ApiData.response(CHATTING_LIST_GET_SUCCESS.getCode(), CHATTING_LIST_GET_SUCCESS.getMessage(), response);
    }

    @PostMapping("messages/{roomId}")
    @Operation(summary = "메시지 전송")
    public ApiData<Void> sendMessage(@PathVariable Long roomId, @RequestBody @Valid MessageDto messageDto) {
        chattingService.sendMessage(roomId, messageDto);
        return ApiData.response(MESSAGE_SEND_SUCCESS.getCode(), MESSAGE_SEND_SUCCESS.getMessage());
    }
}
