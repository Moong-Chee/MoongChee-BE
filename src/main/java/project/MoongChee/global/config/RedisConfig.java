package project.MoongChee.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import project.MoongChee.domain.chat.dto.PublishMessage;
import project.MoongChee.domain.chat.redis.RedisSubscriber;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${REDIS_HOST}")
    private String redisHost;

    @Value("${REDIS_PORT}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 연동 설정 Host 주소와 Post를 주입해준 redisStandaloneConfiguration를
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration("localhost", 6379);
        // LettuceConnectionFactory의 생성자로 다시 넣어준다.
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, PublishMessage> chatRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, PublishMessage> chatRedisTemplate = new RedisTemplate<>();
        chatRedisTemplate.setConnectionFactory(connectionFactory);
        chatRedisTemplate.setKeySerializer(new StringRedisSerializer());
        // Value 직렬화 과정에서 에러 발생할 수 있으니 try catch로 예외 처리
        chatRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));

        return chatRedisTemplate;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("/chatRoom");
    }


    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }

    // 메세지를 구독자에게 보내는 역할
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber redisSubscriber) {
        return new MessageListenerAdapter(redisSubscriber, "onMessage");
    }

}
