package project.MoongChee.domain.chat.controller;

import static project.MoongChee.domain.chat.controller.ResponseMessage.CHATROOM_CREATE_SUCCESS;
import static project.MoongChee.domain.chat.controller.ResponseMessage.ROOMID_GET;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.chat.dto.request.CreateChatRoomRequestDto;
import project.MoongChee.domain.chat.dto.response.ChatRoomResponseDto;
import project.MoongChee.domain.chat.service.ChatRoomService;
import project.MoongChee.global.common.response.ApiData;

@Tag(name = "CHATROOM", description = "채팅방 컨트롤러")
@Slf4j
@RestController
@RequestMapping("/api/v1/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    @Operation(summary = "채팅방 생성")
    public ApiData<ChatRoomResponseDto> createChatRoom(
            @RequestBody @Valid CreateChatRoomRequestDto findChatRoomRequestDto) {
        ChatRoomResponseDto response = chatRoomService.saveChatRoom(findChatRoomRequestDto);
        return ApiData.response(CHATROOM_CREATE_SUCCESS.getCode(), CHATROOM_CREATE_SUCCESS.getMessage(), response);
    }

    @GetMapping("/{user1Id}/{user2Id}")
    @Operation(summary = "채팅방 존재 여부 조회")
    public ApiData<ChatRoomResponseDto> existChatRoom(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        ChatRoomResponseDto response = chatRoomService.findUser1User2ChatRoom(user1Id, user2Id);

        return ApiData.response(ROOMID_GET.getCode(), ROOMID_GET.getMessage(), response);
    }
}
