import React, {ChangeEventHandler} from "react";
import styles from "./Textarea.module.sass";
import classnames from "classnames";

interface IOwnProps {
    value: string;
    onChange: ChangeEventHandler<HTMLTextAreaElement>;
    onBlur?: any;
    name: string;
    className?: string;
}

class Textarea extends React.Component<IOwnProps> {
    render() {
        const {value, onChange, onBlur, name, className} = this.props;
        const classes = classnames(styles.textarea, className);

        return (
            <div className={styles.wrapper}>
                <textarea
                    className={classes}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    name={name}
                />
            </div>
        );
    }
}

export default Textarea;
