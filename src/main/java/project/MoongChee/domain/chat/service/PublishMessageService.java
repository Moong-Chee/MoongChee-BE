package project.MoongChee.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.chat.domain.ChatMessage;
import project.MoongChee.domain.chat.dto.PublishMessage;
import project.MoongChee.domain.chat.dto.request.MessageDto;
import project.MoongChee.domain.chat.redis.RedisPublisher;
import project.MoongChee.domain.chat.repository.ChatMessageRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishMessageService {
    private final RedisPublisher redisPublisher;
    private final ChatMessageRepository chatMessageRepository;

    // 메시지 발행(Pub)
    public void publishMessage(MessageDto messageDto) {
        PublishMessage publishMessage =
                new PublishMessage(messageDto.getRoomId(), messageDto.getSenderId(), messageDto.getSenderName(),
                        messageDto.getContent());

        redisPublisher.publish(publishMessage);
        chatMessageRepository.save(ChatMessage.of(publishMessage));
    }
}
