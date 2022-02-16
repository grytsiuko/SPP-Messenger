import React from "react";
import styles from "./UserCard.module.sass";
import {IUserShortDto} from "../../api/user/userModels";

interface IOwnProps {
    user: IUserShortDto;
}

class UserCard extends React.Component<IOwnProps> {
    render() {
        const {user} = this.props;

        return (
            <div className={styles.wrapper}>
                <div className={styles.fullName}>{user.fullName}</div>
                <div className={styles.username}>@{user.username}</div>
                <div className={styles.bio}>{user.bio}</div>
            </div>
        );
    }
}

export default UserCard;
