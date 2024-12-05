package project.MoongChee.domain.chat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class RedisListener {

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;

    public void adaptMessageListener(Long roomId) {
        ChannelTopic topic = new ChannelTopic("/sub/chats/" + roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
    }
}
