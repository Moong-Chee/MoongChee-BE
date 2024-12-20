package project.MoongChee.domain.chat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.MoongChee.domain.chat.domain.ChatMessage;
import project.MoongChee.domain.chat.domain.ChatRoom;
import project.MoongChee.domain.chat.dto.request.MessageDto;
import project.MoongChee.domain.chat.dto.response.ChatMessageResponse;
import project.MoongChee.domain.chat.dto.response.ChattingListResponse;
import project.MoongChee.domain.chat.dto.response.LatestMessageDto;
import project.MoongChee.domain.chat.exception.ChatRoomNotFoundException;
import project.MoongChee.domain.chat.repository.ChatMessageRepository;
import project.MoongChee.domain.chat.repository.ChatRoomRepository;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 내역 조회
    public List<ChatMessageResponse> findChatMessages(Long roomId, Integer page, Integer size) {
        validateChatRoom(roomId);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        List<ChatMessageResponse> messages = chatMessageRepository
                .findByRoomIdOrderByCreatedAtDesc(roomId, pageRequest)
                .getContent() // Page 객체에서 메시지 리스트 반환
                .stream()
                .map(ChatMessageResponse::fromEntity) // ChatMessage를 DTO로 변환
                .collect(Collectors.toList());
        return messages;
    }

    // 채팅방 리스트 조회
    public List<ChattingListResponse> findUserChatRooms(Long userId) {
        List<ChatRoom> rooms = chatRoomRepository.findRoomsByUserId(userId);
        if (rooms.isEmpty()) {
            throw new ChatRoomNotFoundException(); // 빈 리스트일 경우 예외 처리
        }
        return rooms.stream()
                .map(room -> {
                    // 각 채팅방에 대한 최신 메시지 조회
                    LatestMessageDto latestMessageDto = chatMessageRepository
                            .findTopByRoomIdOrderByCreatedAtDesc(room.getId())
                            .map(LatestMessageDto::of)
                            .orElse(null); // 최신 메시지가 없을 경우 null

                    return ChattingListResponse.of(room, latestMessageDto);
                })
                .collect(Collectors.toList());
    }

    // 채팅방 조회 예외처리
    private ChatRoom validateChatRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }

    public void sendMessage(Long roomId, MessageDto messageDto) {
        ChatMessage message = ChatMessage.builder()
                .roomId(roomId)
                .senderId(messageDto.getSenderId())
                .senderName(messageDto.getSenderName())
                .content(messageDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        chatMessageRepository.save(message);

        // ChatRoom의 lastMessage 업데이트
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);
        chatRoom.updateLastMessage(message.getContent());

        chatMessageRepository.save(message);
    }
}
