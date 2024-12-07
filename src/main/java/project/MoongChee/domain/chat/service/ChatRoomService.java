package project.MoongChee.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.chat.domain.ChatRoom;
import project.MoongChee.domain.chat.dto.request.CreateChatRoomRequestDto;
import project.MoongChee.domain.chat.dto.response.ChatRoomResponseDto;
import project.MoongChee.domain.chat.exception.ChatRoomNotFoundException;
import project.MoongChee.domain.chat.redis.RedisListener;
import project.MoongChee.domain.chat.repository.ChatRoomRepository;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final RedisListener redisMessageListener;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    // 채팅방 생성
    public ChatRoomResponseDto saveChatRoom(CreateChatRoomRequestDto findChatRoomRequestDto) {
        User user1 = userService.find(findChatRoomRequestDto.user1Id());
        User user2 = userService.find(findChatRoomRequestDto.user2Id());

        ChatRoom savedRoom = chatRoomRepository.save(ChatRoom.of(user1, user2));
        redisMessageListener.adaptMessageListener(savedRoom.getId());
        return new ChatRoomResponseDto(savedRoom.getId());
    }

    // 유저 아이디로 채팅방 조회
    public ChatRoomResponseDto findUser1User2ChatRoom(Long user1Id, Long user2Id) {
        Long result = chatRoomRepository.findRoomIdByUserIds(user1Id, user2Id)
                .orElseThrow(ChatRoomNotFoundException::new);
        return new ChatRoomResponseDto(result);
    }
}
