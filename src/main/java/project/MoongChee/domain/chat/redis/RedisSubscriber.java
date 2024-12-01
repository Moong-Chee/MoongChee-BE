package project.MoongChee.domain.chat.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.chat.dto.PublishMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper obejctMapper;
    private final SimpMessageSendingOperations messageTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 역직렬화 문자열
        String tmpMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

        log.info("구독자 전송 전 message: {}", tmpMessage);
        try {
            // 역직렬화한 문자열을 PublishMessage로 변환
            PublishMessage publishMessage = obejctMapper.readValue(tmpMessage, PublishMessage.class);

            messageTemplate.convertAndSend("/sub/chats/" + publishMessage.getRoomId(), publishMessage);
            log.info("구독자 전송 후 message: {}", publishMessage.getContent());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
