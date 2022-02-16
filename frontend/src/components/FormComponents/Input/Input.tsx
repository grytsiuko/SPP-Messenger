import React from "react";
import styles from "./Input.module.sass";

interface IOwnProps {
    value: string;
    label?: string;
    onChange: any;
    onBlur?: any;
    name?: string;
    type?: "password" | "number";
    error?: string;
    touched?: boolean;
}

class Input extends React.Component<IOwnProps> {
    render() {
        const {value, onChange, onBlur, label, type, name, error, touched} = this.props;

        return (
            <div className={styles.wrapper}>
                {label && <div className={styles.label}>{label}</div>}
                <input
                    className={styles.input}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    type={type || "text"}
                    name={name}
                />
                {error && touched && <div className={styles.error}>{error}</div> }
            </div>
        );
    }
}

export default Input;
