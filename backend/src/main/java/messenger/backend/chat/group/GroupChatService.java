package messenger.backend.chat.group;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.chat.exceptions.*;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.chat.group.dto.*;
import messenger.backend.chat.group.exceptions.NotEnoughPermissionLevelException;
import messenger.backend.chat.group.exceptions.UserNotOwnerOfChatException;
import messenger.backend.message.MessageService;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.user.exceptions.UserNotFoundException;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class GroupChatService {

    private final GroupChatRepository groupChatRepository;
    private final UserChatRepository userChatRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public GroupChatResponseDto getById(UUID chatId) {
        var currentUserId = JwtTokenService.getCurrentUserId();
        return groupChatRepository
                .findByIdAndUserId(chatId, currentUserId)
                .map(uc -> GroupChatResponseDto.fromEntity(uc, currentUserId))
                .orElseThrow(ChatNotFoundException::new);
    }

    public GeneralChatResponseDto createGroupChat(CreateGroupChatRequestDto requestDto) {
        UserEntity contextUser = JwtTokenService.getContextUser();

        GroupChatEntity groupChat = GroupChatEntity.builder().groupName(requestDto.getChatName()).build();

        groupChat = groupChatRepository.saveAndFlush(groupChat);

        UserChat contextUserChat = UserChat.builder()
                .user(contextUser)
                .chat(groupChat)
                .permissionLevel(UserChat.PermissionLevel.OWNER)
                .build();

        userChatRepository.saveAndFlush(contextUserChat);

        return GeneralChatResponseDto.fromGroupEntity(
                groupChat,
                messageService.getLastMessageByChatId(groupChat.getId())
        );
    }

    public void deleteGroupChat(DeleteGroupChatRequestDto requestDto) {
        GroupChatEntity groupChatEntity = groupChatRepository.findById(requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();
        boolean isUserOwnerOfChat = groupChatEntity.getUserChats().stream()
                .anyMatch(chat -> chat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER)
                        && chat.getUser().getId().equals(contextUser.getId()));
        if (!isUserOwnerOfChat) throw new UserNotOwnerOfChatException();

        groupChatRepository.delete(groupChatEntity);
    }

    public void addMemberToChat(AddMemberToGroupChatRequestDto requestDto) {
        GroupChatAndUserDto chatAndUsersDto = resolveChatAndUserEntities(requestDto.getChatId(), requestDto.getTargetUserId());
        GroupChatEntity groupChatEntity = chatAndUsersDto.getGroupChatEntity();
        UserEntity targetUser = chatAndUsersDto.getTargetUserEntity();

        UserChat contextUserChat = groupChatEntity.getUserChats().stream()
                .filter(userChat -> userChat.getUser().getId().equals(JwtTokenService.getCurrentUserId()))
                .findAny()
                .orElseThrow(ContextUserNotMemberOfChatException::new);

        if (contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER))
            throw new NotEnoughPermissionLevelException();

        boolean isTargetUserAlreadyAdded = groupChatEntity.getUserChats().stream()
                .anyMatch(userChat -> userChat.getUser().getId().equals(targetUser.getId()));
        if (isTargetUserAlreadyAdded) throw new UserAlreadyMemberOfChatException();

        UserChat targetUserChat = UserChat.builder()
                .user(targetUser)
                .chat(groupChatEntity)
                .permissionLevel(UserChat.PermissionLevel.MEMBER)
                .build();
        userChatRepository.saveAndFlush(targetUserChat);
    }

    public void removeMemberFromChat(RemoveMemberFromGroupChatRequestDto requestDto) {
        UserChatsDto userChatsDto = resolveUserChatEntities(resolveChatAndUserEntities(requestDto.getChatId(), requestDto.getTargetUserId()));
        UserChat contextUserChat = userChatsDto.getContextUserChatEntity();
        UserChat targetUserChat = userChatsDto.getTargetUserChatEntity();

        //todo rewrite with permissions
        boolean allowedMemberDeletion =
                !contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER) &&
                targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER);
        boolean allowedAdminDeletion =
                contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER) &&
                targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.ADMIN);
        if (allowedMemberDeletion || allowedAdminDeletion) {
            userChatRepository.delete(targetUserChat);
        } else {
            throw new NotEnoughPermissionLevelException();
        }
    }

    //just for test todo delete this (or no)
    public List<GroupChatUserInfoDto> getChatUsersList(UUID chatId) {
        GroupChatEntity groupChatEntity = groupChatRepository.findById(chatId)
                .orElseThrow(ChatNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();

        boolean isContextUserInChat = groupChatEntity.getUserChats().stream()
                .anyMatch(userChat -> userChat.getUser().getId().equals(contextUser.getId()));
        if (!isContextUserInChat) throw new UserNotMemberOfChatException();

        return groupChatEntity.getUserChats().stream()
                .map(GroupChatUserInfoDto::from)
                .collect(Collectors.toList());
    }

    public void upgradeToAdmin(UpgradeToAdminRequestDto requestDto) {
        UserChatsDto userChatsDto = resolveUserChatEntities(resolveChatAndUserEntities(requestDto.getChatId(), requestDto.getTargetUserId()));
        UserChat contextUserChat = userChatsDto.getContextUserChatEntity();
        UserChat targetUserChat = userChatsDto.getTargetUserChatEntity();

        if (!contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER))
            throw new NotEnoughPermissionLevelException();

        if (targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER)) {
            targetUserChat.setPermissionLevel(UserChat.PermissionLevel.ADMIN);
        } else {
            throw new InvalidChatOperationException();
        }

        userChatRepository.saveAndFlush(targetUserChat);
    }

    public void downgradeToMember(DowngradeToMemberRequestDto requestDto) {
        UserChatsDto userChatsDto = resolveUserChatEntities(resolveChatAndUserEntities(requestDto.getChatId(), requestDto.getTargetUserId()));
        UserChat contextUserChat = userChatsDto.getContextUserChatEntity();
        UserChat targetUserChat = userChatsDto.getTargetUserChatEntity();

        if (!contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER))
            throw new NotEnoughPermissionLevelException();

        if (targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.ADMIN)) {
            targetUserChat.setPermissionLevel(UserChat.PermissionLevel.MEMBER);
        } else {
            throw new InvalidChatOperationException();
        }

        userChatRepository.saveAndFlush(targetUserChat);
    }

    private GroupChatAndUserDto resolveChatAndUserEntities(UUID chatId, UUID targetUserId) {
        GroupChatEntity groupChatEntity = groupChatRepository.findById(chatId)
                .orElseThrow(ChatNotFoundException::new);

        UserEntity targetUserEntity = userRepository.findById(targetUserId)
                .orElseThrow(UserNotFoundException::new);

        return new GroupChatAndUserDto(groupChatEntity, targetUserEntity);
    }

    private UserChatsDto resolveUserChatEntities(GroupChatEntity groupChatEntity, UUID targetUserId) {
        UserChat contextUserChatEntity = groupChatEntity.getUserChats().stream()
                .filter(userChat -> userChat.getUser().getId().equals(JwtTokenService.getCurrentUserId()))
                .findAny()
                .orElseThrow(ContextUserNotMemberOfChatException::new);

        UserChat targetUserChatEntity = groupChatEntity.getUserChats().stream()
                .filter(groupChat -> groupChat.getUser().getId().equals(targetUserId))
                .findAny()
                .orElseThrow(TargetUserNotMemeberOfChatException::new);

        return UserChatsDto.builder()
                .contextUserChatEntity(contextUserChatEntity)
                .targetUserChatEntity(targetUserChatEntity)
                .build();
    }

    private UserChatsDto resolveUserChatEntities(GroupChatAndUserDto chatAndUsersDto) {
        return resolveUserChatEntities(
                chatAndUsersDto.getGroupChatEntity(),
                chatAndUsersDto.getTargetUserEntity().getId()
        );
    }

    public void changeChatInfo(ChangeGroupChatNameRequestDto requestDto) {
        GroupChatEntity groupChatEntity = groupChatRepository.findById(requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        UserChat contextUserChat = groupChatEntity.getUserChats().stream()
                .filter(userChat -> userChat.getUser().getId().equals(JwtTokenService.getCurrentUserId()))
                .findAny()
                .orElseThrow(ContextUserNotMemberOfChatException::new);

        if (contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER))
            throw new NotEnoughPermissionLevelException();

        groupChatEntity.setGroupName(requestDto.getNewChatName());

        groupChatRepository.saveAndFlush(groupChatEntity);
    }

    //just for test todo delete this
    public List<Map<String, String>> getAllChats() {
        return groupChatRepository.findAll().stream()
                .map(groupChat -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", groupChat.getId().toString());
                    map.put("groupName", groupChat.getGroupName());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
