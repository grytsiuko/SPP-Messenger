package messenger.backend.message;


import lombok.*;
import messenger.backend.seeds.FakerService;
import messenger.backend.userChat.UserChat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Message")
public class MessageEntity {
    public static MessageEntity generateMessage(UserChat userChat) {

        return MessageEntity.builder()
                .userChat(userChat)
                .messageType(MessageType.TEXT)
                .createdAt(FakerService.faker.date().between(new Date(2020,4,1),new Date(2021,3,21)))
                .messageBody(FakerService.faker.elderScrolls().quote())
                .build();
    }

    public enum MessageType {
        TEXT,
        IMAGE,
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "MessageId")
    private UUID id;

    @CreatedDate
    @Column(name = "SentTime")
    private Date createdAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "MessageType")
    private MessageType messageType;

    //TODO FK to messageId / Null value
//    @Column(name = "ResponseTo")
//    private UUID responseTo;

    @Column(name = "MessageBody",length = 1024)
    private String messageBody;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="UserChat")
    private UserChat userChat;
}
