import React from "react";
import styles from "./FormWrapper.module.sass";

class FormWrapper extends React.Component {
    render() {
        return (
            <div className={styles.form}>
                {this.props.children}
            </div>
        );
    }
}

export default FormWrapper;
