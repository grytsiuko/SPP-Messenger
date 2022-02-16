package messenger.backend.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    @Transactional
    void deleteAllByUserEntityId(UUID userId);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity WHERE id = :tokenId")
    void deleteById(@Param("tokenId") UUID tokenId);
}
