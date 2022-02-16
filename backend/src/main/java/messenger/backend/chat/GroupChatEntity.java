package messenger.backend.chat;

import lombok.*;
import lombok.experimental.SuperBuilder;
import messenger.backend.seeds.FakerService;

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
@Table(name = "GroupChat")

public class GroupChatEntity extends ChatSuperclass {

    public static GroupChatEntity generateGroupChat() {

        return GroupChatEntity.builder()
                .groupName(FakerService.faker.funnyName().name())
                .build();
    }

    @Column(name = "GroupName", length = 64)
    private String groupName;

    @Lob
    @Column(name = "GroupPicture")
    private Byte[] groupPicture;

}
