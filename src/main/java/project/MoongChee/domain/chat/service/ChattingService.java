package project.MoongChee.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.chat.domain.ChatMessage;
import project.MoongChee.domain.chat.domain.ChatRoom;
import project.MoongChee.domain.chat.dto.response.ChatMessageResponseDto;
import project.MoongChee.domain.chat.dto.response.ChattingDto;
import project.MoongChee.domain.chat.dto.response.ChattingListResponseDto;
import project.MoongChee.domain.chat.dto.response.LatestMessageDto;
import project.MoongChee.domain.chat.exception.ChatRoomNotFoundException;
import project.MoongChee.domain.chat.repository.ChatMessageRepository;
import project.MoongChee.domain.chat.repository.ChatRoomRepository;
import project.MoongChee.domain.user.domain.User;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChattingDto getChatRoom(Long roomId, Integer page, Integer size) {
        ChatRoom findRoom = validateChatRoom(roomId);
        User user1 = findRoom.getUser1();
        User user2 = findRoom.getUser2();

        List<ChatMessageResponseDto> chatMessageList = generateChatRoomMessages(roomId, page, size);
        return new ChattingDto(
                user1.getId(), user2.getId(),
                user1.getName(), user2.getName(),
                user1.getProfileImage(), user2.getProfileImage(),
                chatMessageList
        );
    }


    public List<ChattingListResponseDto> getChattingList(Long userId) { // 추후 JWT 파싱으로 받아내기.
        List<ChatRoom> chatRooms = validateChatRommList(userId);

        return chatRooms.stream()
                .map(chatRoom -> {
                    ChatMessage latestMessage = chatMessageRepository.findTopByRoomIdOrderByCreatedAtDesc(
                            chatRoom.getId()).orElse(null);
                    LatestMessageDto latestMessageDto = (latestMessage != null)
                            ? LatestMessageDto.of(latestMessage) : new LatestMessageDto("", null);
                    return ChattingListResponseDto.of(chatRoom, latestMessageDto);
                })
                .collect(Collectors.toList());
    }

    private List<ChatMessageResponseDto> generateChatRoomMessages(Long roomId, Integer page, Integer size) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(
                        roomId, PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .map(ChatMessageResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


    private ChatRoom validateChatRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }

    private List<ChatRoom> validateChatRommList(Long userId) {
        return chatRoomRepository.findRoomsByUserId(userId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }

}
