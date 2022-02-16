package messenger.backend.message;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    @Query("SELECT m1 " +
            "FROM MessageEntity m1 " +
            "WHERE m1.userChat.chat.id = :chatId " +
            "ORDER BY m1.createdAt DESC ")
    List<MessageEntity> findLastByChatId(UUID chatId, Pageable pageable);

    List<MessageEntity> findAllMessagesByUserChatChatIdOrderByCreatedAtAsc(UUID chatId);
}
