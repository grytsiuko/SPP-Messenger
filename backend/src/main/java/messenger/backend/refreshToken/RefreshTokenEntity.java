package messenger.backend.refreshToken;

import lombok.*;
import messenger.backend.user.UserEntity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "refreshToken")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshTokenEntity {

    public static RefreshTokenEntity fromUserEntity(UserEntity userEntity) {
        return RefreshTokenEntity.builder()
                .userEntity(userEntity)
                .build();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "createdAt")
    @CreatedDate
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
