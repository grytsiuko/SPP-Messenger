package messenger.backend.userChat;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserChatRepository extends JpaRepository<UserChat, UUID> {
    List<UserChat> findAllByUserId(UUID userId);

    Optional<UserChat> findByUserIdAndChatId(UUID userId, UUID chatId);
}
