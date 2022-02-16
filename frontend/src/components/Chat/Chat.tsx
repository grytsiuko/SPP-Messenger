import React from "react";
import styles from "./Chat.module.sass";
import {IChatCache} from "../../reducers/chatsList/reducer";
import classnames from "classnames";
import ChatHeader from "../ChatHeader/ChatHeader";
import MessagesListWrapper from "../MessagesListWrapper/MessagesListWrapper";
import {ICurrentUser} from "../../api/auth/authModels";
import ChatSender from "../ChatSender/ChatSender";
import Modal from "../Modal/Modal";
import {ChatTypeEnum, IChatDetails} from "../../api/chat/general/generalChatModels";
import PersonalChatDetails from "../PersonalChatDetails/PersonalChatDetails";
import GroupChatDetails from "../GroupChatDetails/GroupChatDetails";

interface IOwnProps {
    chatsDetailsCached: IChatCache[];
    loadChatDetails: (id: string) => Promise<void>;
    loadChatMessages: (id: string) => Promise<void>;
    selectedChatId?: string;
    currentUser?: ICurrentUser;
    sendMessage: (text: string) => Promise<void>;
    deleteChatFromList: (chatId: string) => void;
    updateChatInList: (chat: IChatDetails) => void;
}

interface IState {
    modalShown: boolean;
}

class Chat extends React.Component<IOwnProps, IState> {

    state = {
        modalShown: false,
    } as IState;

    async componentDidUpdate(prevProps: Readonly<IOwnProps>, prevState: Readonly<{}>, snapshot?: any) {
        const {selectedChatId, chatsDetailsCached} = this.props;
        if (
            selectedChatId &&
            prevProps.selectedChatId !== selectedChatId &&
            !chatsDetailsCached.find(c => c.details.id === selectedChatId)
        ) {
            await this.props.loadChatDetails(selectedChatId);
            await this.props.loadChatMessages(selectedChatId);
        }
    }

    deleteChatFromList = async (chatId: string) => {
        this.setState({modalShown: false});
        this.props.deleteChatFromList(chatId);
    }

    render() {
        const {chatsDetailsCached, selectedChatId, currentUser, sendMessage, updateChatInList} = this.props;
        const {modalShown} = this.state;
        const chatInfo = chatsDetailsCached.find(c => c.details.id === selectedChatId);

        if (!selectedChatId) {
            return (
                <div className={classnames(styles.wrapper, styles.flexWide)}>
                    Choose your chat
                </div>
            );
        }

        return (
            <div className={styles.wrapper}>
                {modalShown && (
                    <Modal close={() => this.setState({modalShown: false})}>
                        {chatInfo?.details?.type === ChatTypeEnum.PERSONAL && (
                            <PersonalChatDetails
                                chatDetails={chatInfo.details}
                                deleteChatFromList={this.deleteChatFromList}
                            />
                        )}
                        {chatInfo?.details?.type === ChatTypeEnum.GROUP && (
                            <GroupChatDetails
                                chatDetails={chatInfo.details}
                                deleteChatFromList={this.deleteChatFromList}
                                updateChatInList={updateChatInList}
                            />
                        )}
                    </Modal>
                )}
                <ChatHeader
                    chatDetails={chatInfo?.details}
                    openModal={() => this.setState({modalShown: true})}
                />
                <MessagesListWrapper
                    messages={chatInfo?.messages}
                    currentUser={currentUser}
                />
                <ChatSender sendMessage={sendMessage}/>
            </div>
        );
    }
}

export default Chat;
