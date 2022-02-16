import React from "react";
import {Redirect, RouteComponentProps, withRouter} from "react-router-dom";
import {bindActionCreators} from "redux";
import {IAppState} from "../../reducers";
import {authActions} from "../../reducers/auth/actions";
import {connect} from "react-redux";
import LoaderWrapper from "../../components/LoaderWrapper/LoaderWrapper";
import {ICurrentUser} from "../../api/auth/authModels";
import authService from "../../api/auth/authService";
import Header from "../../components/Header/Header";
import ChatsList from "../../components/ChatsList/ChatsList";
import styles from "./Home.module.sass";
import Chat from "../../components/Chat/Chat";
import {chatsListActions} from "../../reducers/chatsList/actions";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import generalChatService from "../../api/chat/general/generalChatService";
import {IChatCache} from "../../reducers/chatsList/reducer";
import messageService from "../../api/message/messageService";
import {v4 as uuid} from "uuid";
import Icon from "../../components/Icon/Icon";
import Modal from "../../components/Modal/Modal";
import CreatePersonalChat from "../../components/CreatePersonalChat/CreatePersonalChat";
import personalChatService from "../../api/chat/personal/personalChatService";
import groupChatService from "../../api/chat/group/groupChatService";
import CreateGroupChat from "../../components/CreateGroupChat/CreateGroupChat";
import {toastr} from 'react-redux-toastr';

interface IPropsFromDispatch {
    actions: {
        removeCurrentUser: typeof authActions.removeCurrentUser;
        setCurrentUser: typeof authActions.setCurrentUser;
        setChatsList: typeof chatsListActions.setChatsList;
        addChatToList: typeof chatsListActions.addChatToList;
        updateChatInList: typeof chatsListActions.updateChatInList;
        setFirstChatInList: typeof chatsListActions.setFirstChatInList;
        removeChatFromList: typeof chatsListActions.removeChatFromList;
        removeChatsList: typeof chatsListActions.removeChatsList;
        setSelected: typeof chatsListActions.setSelected;
        removeSelected: typeof chatsListActions.removeSelected;
        appendDetailsCached: typeof chatsListActions.appendDetailsCached;
        setChatMessages: typeof chatsListActions.setChatMessages;
        appendLoadingMessage: typeof chatsListActions.appendLoadingMessage;
        setMessageLoaded: typeof chatsListActions.setMessageLoaded;
    };
}

interface IPropsFromState {
    currentUser?: ICurrentUser;
    chatsList?: IChatDetails[];
    selectedChatId?: string;
    chatDetailsCached: IChatCache[];
}

interface IState {
    loading: boolean;
    creating: boolean;
}

class Home extends React.Component<RouteComponentProps & IPropsFromDispatch & IPropsFromState, IState> {

    state = {
        loading: false,
        creating: false,
    } as IState;

    async componentDidMount() {
        if (authService.isLoggedIn()) {
            const currentUser = await authService.me();
            this.props.actions.setCurrentUser(currentUser);
        }
        setTimeout(() => {
            toastr.success('New message', 'You have received a new message');
        }, 2000);
        // init socket
    }

    componentWillUnmount() {
        // remove socket
    }

    logout = async () => {
        this.setState({loading: true});
        await authService.logout();
        this.props.actions.removeCurrentUser();
        this.setState({loading: false});
        this.props.history.push("/auth");
    }

    loadChatsList = async () => {
        this.props.actions.removeChatsList();
        const list = await generalChatService.getChatsList();
        list.sort((a, b) => {
            if (!a.lastMessage) {
                return -1;
            }
            if (!b.lastMessage) {
                return 1;
            }
            return b.lastMessage.createdAt - a.lastMessage.createdAt;
        });
        this.props.actions.setChatsList(list);
    }

    selectChat = (chat: IChatDetails) => {
        this.props.actions.setSelected(chat.id);
    }

    loadChatDetails = async (id: string) => {
        const chat = this.props.chatsList?.find(c => c.id === id);
        if (chat) {
            this.props.actions.appendDetailsCached(chat);
        }
    }

    loadChatMessages = async (chatId: string) => {
        const messages = await messageService.getMessagesByChatId(chatId);
        this.props.actions.setChatMessages(chatId, messages);
    }

    sendMessage = async (text: string) => {
        const {selectedChatId} = this.props;
        const chat = this.props.chatsList?.find(c => c.id === selectedChatId);

        if (selectedChatId) {
            const id = uuid();
            this.props.actions.appendLoadingMessage(selectedChatId, {text, id});
            const message = await messageService.sendMessage(selectedChatId, text);
            this.props.actions.setMessageLoaded(selectedChatId, id, message);
            if (chat) {
                this.props.actions.setFirstChatInList(chat.id);
                this.props.actions.updateChatInList({
                    ...chat,
                    lastMessage: {text, createdAt: message.createdAt},
                });
            }
        }
    }

    deleteChatFromList = (chatId: string) => {
        this.props.actions.removeSelected();
        this.props.actions.removeChatFromList(chatId);
    }

    updateChatInList = (chat: IChatDetails) => {
        this.props.actions.updateChatInList(chat);
    }

    createPersonalChat = async (targetId: string) => {
        const chat = await personalChatService.create(targetId);
        this.setState({creating: false});
        this.props.actions.addChatToList(chat);
    }

    createGroupChat = async (title: string) => {
        const chat = await groupChatService.create(title);
        this.setState({creating: false});
        this.props.actions.addChatToList(chat);
    }

    render() {
        if (!authService.isLoggedIn()) {
            return <Redirect to="/auth" />;
        }

        const {chatsList, currentUser, selectedChatId, chatDetailsCached} = this.props;
        const {loading, creating} = this.state;

        return (
            <LoaderWrapper loading={!currentUser || loading}>
                {creating && (
                    <Modal close={() => this.setState({creating: false})}>
                        <CreatePersonalChat
                            createPersonalChat={this.createPersonalChat}
                        />
                        <CreateGroupChat
                            createGroupChat={this.createGroupChat}
                        />
                    </Modal>
                )}
                <Header logout={this.logout} />
                <div className={styles.content}>
                    <ChatsList
                        chatsList={chatsList}
                        loadChatsList={this.loadChatsList}
                        selectChat={this.selectChat}
                        selectedChatId={selectedChatId}
                    />
                    <Chat
                        chatsDetailsCached={chatDetailsCached}
                        loadChatDetails={this.loadChatDetails}
                        loadChatMessages={this.loadChatMessages}
                        selectedChatId={selectedChatId}
                        currentUser={currentUser}
                        sendMessage={this.sendMessage}
                        deleteChatFromList={this.deleteChatFromList}
                        updateChatInList={this.updateChatInList}
                    />
                </div>
                <div className={styles.addWrapper}>
                    <Icon
                        iconName="fas fa-plus"
                        onClick={() => this.setState({creating: true})}
                    />
                </div>
            </LoaderWrapper>
        );
    }
}

const mapStateToProps = (state: IAppState) => ({
    currentUser: state.auth.currentUser,
    chatsList: state.chatsList.chatsList,
    selectedChatId: state.chatsList.selectedId,
    chatDetailsCached: state.chatsList.chatsDetailsCached,
});

const mapDispatchToProps = (dispatch: any) => ({
    actions:
        bindActionCreators<any,
            {
                removeCurrentUser: typeof authActions.removeCurrentUser,
                setCurrentUser: typeof authActions.setCurrentUser,
                setChatsList: typeof chatsListActions.setChatsList,
                addChatToList: typeof chatsListActions.addChatToList,
                updateChatInList: typeof chatsListActions.updateChatInList,
                setFirstChatInList: typeof chatsListActions.setFirstChatInList,
                removeChatFromList: typeof chatsListActions.removeChatFromList,
                removeChatsList: typeof chatsListActions.removeChatsList,
                setSelected: typeof chatsListActions.setSelected,
                removeSelected: typeof chatsListActions.removeSelected,
                appendDetailsCached: typeof chatsListActions.appendDetailsCached,
                setChatMessages: typeof chatsListActions.setChatMessages,
                appendLoadingMessage: typeof chatsListActions.appendLoadingMessage,
                setMessageLoaded: typeof chatsListActions.setMessageLoaded,
            }>(
            {
                removeCurrentUser: authActions.removeCurrentUser,
                setCurrentUser: authActions.setCurrentUser,
                setChatsList: chatsListActions.setChatsList,
                addChatToList: chatsListActions.addChatToList,
                removeChatFromList: chatsListActions.removeChatFromList,
                setFirstChatInList: chatsListActions.setFirstChatInList,
                updateChatInList: chatsListActions.updateChatInList,
                removeChatsList: chatsListActions.removeChatsList,
                setSelected: chatsListActions.setSelected,
                removeSelected: chatsListActions.removeSelected,
                appendDetailsCached: chatsListActions.appendDetailsCached,
                setChatMessages: chatsListActions.setChatMessages,
                appendLoadingMessage: chatsListActions.appendLoadingMessage,
                setMessageLoaded: chatsListActions.setMessageLoaded,
            }, dispatch),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Home));
