package project.MoongChee.domain.chat.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.MoongChee.domain.chat.domain.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT c.id FROM ChatRoom c WHERE " +
            "(c.user1.id = :user1Id AND c.user2.id = :user2Id) OR " +
            "(c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    Optional<Long> findRoomIdByUserIds(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Query("SELECT c FROM ChatRoom c " +
            "JOIN FETCH c.user1 " +
            "JOIN FETCH c.user2 " +
            "WHERE c.user1.id = :userId OR c.user2.id = :userId")
    Optional<ArrayList<ChatRoom>> findRoomsByUserId(Long userId);
}
