package messenger.backend.chat.personal;


import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface PersonalChatRepository extends JpaRepository<PrivateChatEntity, UUID> {

    @Query("SELECT uc1.chat " +
            "FROM UserChat as uc1 " +
            "WHERE uc1.user=:user1 " +
            "AND EXISTS(SELECT uc2 FROM UserChat as uc2 " +
            "WHERE uc2.user=:user2 AND uc1.chat=uc2.chat)")
    Optional<PrivateChatEntity> findByMembers(@Param("user1") UserEntity user1, @Param("user2") UserEntity user2);

    @Query("SELECT pce FROM PrivateChatEntity as pce INNER JOIN FETCH pce.userChats WHERE pce.id=:id")
    Optional<PrivateChatEntity> findByIdWithFetch(@Param("id") UUID id);

    @Query("SELECT c " +
            "FROM PrivateChatEntity as c INNER JOIN c.userChats uc " +
            "WHERE c.id=:chatId AND uc.user.id = :userId")
    Optional<PrivateChatEntity> findByIdAndUserId(UUID chatId, UUID userId);
}
