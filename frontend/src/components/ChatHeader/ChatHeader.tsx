import React from "react";
import styles from "./ChatHeader.module.sass";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import {IChatDetails} from "../../api/chat/general/generalChatModels";

interface IOwnProps {
    chatDetails?: IChatDetails;
    openModal: () => void;
}

class ChatHeader extends React.Component<IOwnProps> {
    render() {
        const {chatDetails, openModal} = this.props;
        return (
            <div className={styles.wrapper} onClick={openModal}>
                <LoaderWrapper loading={!chatDetails}>
                    {chatDetails?.title || "-"}
                </LoaderWrapper>
            </div>
        );
    }
}

export default ChatHeader;
