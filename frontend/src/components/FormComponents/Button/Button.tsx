import React from "react";
import classnames from "classnames";
import styles from "./Button.module.sass";

interface IOwnProps {
    text: string;
    submit?: boolean;
    disabled?: boolean;
    loading?: boolean;
    onClick?: () => void;
}

class Button extends React.Component<IOwnProps> {
    render() {
        const {text, submit, disabled, loading, onClick} = this.props;

        return (
            <button
                type={submit ? "submit" : "button"}
                className={classnames(styles.button, disabled && styles.disabled)}
                onClick={disabled || loading ? undefined : onClick}
            >
                {loading ? "..." : text}
            </button>
        );
    }
}

export default Button;
