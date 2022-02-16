import React from "react";
import styles from "./ChatSender.module.sass";
import Textarea from "../FormComponents/Texarea/Textarea";
import Icon from "../Icon/Icon";

interface IOwnProps {
    sendMessage: (text: string) => Promise<void>;
}

interface IState {
    text: string;
}

class ChatSender extends React.Component<IOwnProps, IState> {
    state = {
        text: ''
    } as IState;

    isValid = () => {
        const {text} = this.state;
        return !!text.trim();
    }

    handleSend = () => {
        const {text} = this.state;
        this.props.sendMessage(text).then();
        this.setState({text: ''});

    }

    render() {
        const {text} = this.state;

        return (
            <div className={styles.wrapper}>
                <div className={styles.textAreaWrapper}>
                    <Textarea
                        value={text}
                        onChange={e => this.setState({text: e.target.value})}
                        name="text"
                        className={styles.textarea}
                    />
                </div>
                <div className={styles.buttonsWrapper}>

                    <Icon iconName={"fas fa-paper-plane fa-2x"}
                          className={this.isValid() ? styles.sendIcon : styles.disabledSend}
                          onClick={this.isValid() ? this.handleSend : undefined} />
                </div>
            </div>
        );
    }
}

export default ChatSender;
