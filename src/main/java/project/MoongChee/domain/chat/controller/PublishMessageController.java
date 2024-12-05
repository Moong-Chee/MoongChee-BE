package project.MoongChee.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import project.MoongChee.domain.chat.dto.request.MessageDto;
import project.MoongChee.domain.chat.service.PublishMessageService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PublishMessageController {
    private final PublishMessageService publishMessageService;

    // 프론트는 "/pub/chats/messages" 로 전송
    @MessageMapping("/chats/messages")
    public void message(MessageDto messageDto) {
        publishMessageService.publishMessage(messageDto);
    }
}
