import React from "react";
import styles from "./Header.module.sass";

interface IOwnProps {
    logout: () => Promise<void>;
}

class Header extends React.Component<IOwnProps> {
    render() {
        const {logout} = this.props;

        return (
            <div className={styles.header}>
                <h1>Messenger</h1>
                <div className={styles.links}>
                    <span className={styles.link} onClick={logout}>Logout</span>
                </div>
            </div>
        );
    }
}

export default Header;
