import React from "react";
import styles from "./UserManager.module.sass";
import {IUserShortDto} from "../../api/user/userModels";
import Icon from "../Icon/Icon";

interface IOwnProps {
    user: IUserShortDto;
    deletable?: boolean;
    onDelete: () => void;
    upgradable?: boolean;
    upgraded?: boolean;
    onToggleUpgrade: () => void;
}

class UserManager extends React.Component<IOwnProps> {
    render() {
        const {user, deletable, onDelete, upgradable, upgraded, onToggleUpgrade} = this.props;

        return (
            <div className={styles.wrapper}>
                <div className={styles.meta}>
                    <div className={styles.fullName}>{user.fullName}</div>
                    <div className={styles.username}>@{user.username}</div>
                    <div className={styles.role}>{user.permissionLevel}</div>
                </div>
                <div className={styles.icons}>
                    {upgradable && (
                        <Icon
                            iconName={upgraded ? "fas fa-star" : "far fa-star"}
                            className={styles.icon}
                            onClick={onToggleUpgrade}
                        />
                    )}
                    {deletable && (
                        <Icon
                            iconName="fas fa-trash"
                            className={styles.icon}
                            onClick={onDelete}
                        />
                    )}
                </div>
            </div>
        );
    }
}

export default UserManager;
