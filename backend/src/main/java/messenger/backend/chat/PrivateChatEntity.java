package messenger.backend.chat;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;



@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


@Entity
@Table(name = "PrivateChat")
public class PrivateChatEntity extends ChatSuperclass {

    public static PrivateChatEntity generatePrivateChat() {
        return PrivateChatEntity.builder()
                .build();
    }


    @Lob
    @ToString.Exclude
    @Column(name = "chatPicture")
    private Byte[] chatPicture;
}
