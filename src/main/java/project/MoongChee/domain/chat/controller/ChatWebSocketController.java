package project.MoongChee.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import project.MoongChee.domain.chat.dto.request.MessageDto;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chats/messages") // 클라이언트에서 "/pub/chats/messages"로 발행한 메시지 처리
    public void sendMessage(MessageDto messageDto) {
        Long roomId = messageDto.getRoomId();
        System.out.println("Received WebSocket Message: " + messageDto);

        // 메시지를 특정 채팅방 구독자에게 전달
        messagingTemplate.convertAndSend("/sub/chats/" + roomId, messageDto);
    }
}
