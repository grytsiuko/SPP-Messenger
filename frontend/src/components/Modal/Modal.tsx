import React from "react";
import styles from "./Modal.module.sass";
import Icon from "../Icon/Icon";

interface IOwnProps {
    close: () => void;
}

class Modal extends React.Component<IOwnProps> {
    render() {
        const {close, children} = this.props;

        return (
            <div className={styles.background}>
                <div className={styles.wrapper}>
                    <div className={styles.buttons}>
                        <Icon
                            iconName="fas fa-times"
                            className={styles.cross}
                            onClick={close}
                        />
                    </div>
                    <div className={styles.content}>
                        {children}
                    </div>
                </div>
            </div>
        );
    }
}

export default Modal;
