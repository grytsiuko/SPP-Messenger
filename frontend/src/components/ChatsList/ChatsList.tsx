import React from "react";
import styles from "./ChatsList.module.sass";
import ChatListElement from "../ChatListElement/ChatListElement";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";

interface IOwnProps {
    loadChatsList: () => Promise<void>;
    chatsList?: IChatDetails[];
    selectChat: (el: IChatDetails) => void;
    selectedChatId?: string;
}

class ChatsList extends React.Component<IOwnProps> {

    async componentDidMount() {
        await this.props.loadChatsList();
    }

    render() {
        const {chatsList, selectChat, selectedChatId} = this.props;

        return (
            <div className={styles.wrapper}>
                <LoaderWrapper loading={!chatsList}>
                    {chatsList?.map(chat => (
                        <ChatListElement
                            key={chat.id}
                            elementData={chat}
                            onClick={() => selectChat(chat)}
                            selected={selectedChatId === chat.id}
                        />
                    ))}
                </LoaderWrapper>
            </div>
        );
    }
}

export default ChatsList;
