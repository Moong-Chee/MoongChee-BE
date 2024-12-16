package project.MoongChee.domain.chat.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.chat.domain.ChatRoom;
import project.MoongChee.domain.chat.dto.request.CreateChatRoomRequest;
import project.MoongChee.domain.chat.dto.response.ChatRoomResponse;
import project.MoongChee.domain.chat.exception.ChatRoomAlreadyExists;
import project.MoongChee.domain.chat.exception.ChatRoomNotFoundException;
import project.MoongChee.domain.chat.repository.ChatRoomRepository;
import project.MoongChee.domain.user.domain.User;
import project.MoongChee.domain.user.exception.UserNotFoundException;
import project.MoongChee.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    public ChatRoomResponse createChatRoom(CreateChatRoomRequest request) {
        Optional<Long> roomId = chatRoomRepository.findRoomIdByUserIds(request.user1Id(), request.user2Id());
        if (roomId.isPresent()) {
            throw new ChatRoomAlreadyExists();
        }
        User user1 = userRepository.findById(request.user1Id())
                .orElseThrow(UserNotFoundException::new);
        User user2 = userRepository.findById(request.user2Id())
                .orElseThrow(UserNotFoundException::new);
        ChatRoom newRoom = ChatRoom.of(user1, user2);
        ChatRoom savedRoom = chatRoomRepository.save(newRoom);
        return new ChatRoomResponse(savedRoom.getId());
    }

    // 유저 아이디로 채팅방 조회
    public ChatRoomResponse findChatRoomByUserIds(Long user1Id, Long user2Id) {
        Long result = chatRoomRepository.findRoomIdByUserIds(user1Id, user2Id)
                .orElseThrow(ChatRoomNotFoundException::new);
        return new ChatRoomResponse(result);
    }
}
