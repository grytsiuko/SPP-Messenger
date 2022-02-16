package messenger.backend.seeds;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import messenger.backend.auth.access_levels.Role;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.chat.personal.PersonalChatRepository;
import messenger.backend.message.MessageEntity;
import messenger.backend.message.MessageRepository;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FakerService {

    private final UserRepository userRepository;
    private final PersonalChatRepository privateChatRepo;
    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;

    public static final Faker faker = new Faker();

    public final int userCount = 20;
    public final int privateChatCount = 100;
    public final int msgsPerChat = 64;

    public void generateRandomData() {
        //creating users
        List<UserEntity> users =
                Stream
                        .generate(() -> UserEntity.generateUser())
                        .limit(userCount - 1)
                        .collect(Collectors.toList());
        users.add(UserEntity.builder()
                .username("user")
                .fullName("userFullName")
                .password("$2y$12$ixe4Lh4uQVncJDzPJWckfeyTXPMkuVZm55miqLdnn/TjH0FoF8HOq") //user (BCryptPasswordEncoder(12))
                .role(Role.USER)
                .build()
        );
        userRepository.saveAll(users);

        users.stream().map(UserEntity::getUsername).forEach(System.out::println);

        //creating private chats
        List<PrivateChatEntity> privateChats =
                Stream
                        .generate(() -> PrivateChatEntity.generatePrivateChat())
                        .limit(privateChatCount)
                        .collect(Collectors.toList());

        privateChatRepo.saveAll(privateChats);

        //generating UserChats for private chats
        List<UserChat> privateUserChats = new ArrayList<>();
        int privateChatIndex = 0;
        for(Tuple<Integer,Integer> pair : getUserPairs(privateChatCount,userCount)) {
            //creating one UserChat for each user
            UserChat firstLink = UserChat.generateUserChat(UserChat.PermissionLevel.OWNER, privateChats.get(privateChatIndex), users.get(pair.x));
            UserChat secondLink = UserChat.generateUserChat(UserChat.PermissionLevel.OWNER, privateChats.get(privateChatIndex), users.get(pair.y));

            userChatRepository.save(firstLink);
            userChatRepository.save(secondLink);
            //adding them to List of userChats
//            privateUserChats.add(firstLink);
//            privateUserChats.add(secondLink);

            //adding UserChats to UserEntity
//            users.get(pair.x).appendUserChat(firstLink);
//            users.get(pair.y).appendUserChat(secondLink);

            //TODO both links? Not sure..
            //adding UserChats to PrivateChatEntity
//            privateChats.get(privateChatIndex).appendUserChat(firstLink);
//            privateChats.get(privateChatIndex).appendUserChat(secondLink);

            List<MessageEntity> firstUserMessages = Stream
                    .generate(() -> MessageEntity.generateMessage(firstLink))
                    .limit(msgsPerChat/2)
                    .collect(Collectors.toList());

            List<MessageEntity> secondUserMessages = Stream
                    .generate(() -> MessageEntity.generateMessage(secondLink))
                    .limit(msgsPerChat/2)
                    .collect(Collectors.toList());

//            firstLink.appendMessages(firstUserMessages);
//            secondLink.appendMessages(secondUserMessages);

            messageRepository.saveAll(firstUserMessages);
            messageRepository.saveAll(secondUserMessages);


            ++privateChatIndex;
        }


    }


    private List<Tuple<Integer, Integer>> getUserPairs(final int pairs,final int usersLength) {
        List<Tuple<Integer, Integer>> result = new ArrayList<>();
        Random random = new Random();
        int first, second;

        for (int i = 0; i < pairs; i++) {
            first = random.nextInt(usersLength);
            do
                second = random.nextInt(usersLength);
            while (first == second);

            result.add(new Tuple<Integer, Integer>(Integer.valueOf(first), Integer.valueOf(second)));
        }

        return result;
    }

}
