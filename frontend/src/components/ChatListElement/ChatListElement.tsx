import React from "react";
import styles from "./ChatListElement.module.sass";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import classNames from "classnames";

interface IOwnProps {
    elementData: IChatDetails;
    onClick: () => void;
    selected: boolean;
}

class ChatListElement extends React.Component<IOwnProps> {

    lastMessageMapper = (lastMessage?: string) => {
        if (!lastMessage) {
            return "-";
        }

        const maxMessageLength = 30;
        return lastMessage.length > maxMessageLength
            ? `${lastMessage.substring(0, maxMessageLength - 3)}...`
            : lastMessage;
    }

    render() {
        const {elementData, onClick, selected} = this.props;
        const classes = classNames(styles.wrapper, selected && styles.selected);

        return (
            <div className={classes} onClick={onClick}>
                <div className={styles.header}>
                    {elementData.title} ({elementData.type})
                </div>
                <div className={styles.message}>
                    {this.lastMessageMapper(elementData.lastMessage?.text)}
                </div>
            </div>
        );
    }
}

export default ChatListElement;
