import React from "react";
import {Link} from "react-router-dom";
import FormWrapper from "../FormComponents/FormWrapper/FormWrapper";
import Input from "../FormComponents/Input/Input";
import Button from "../FormComponents/Button/Button";
import {ILoginRequest} from "../../api/auth/authModels";
import {Form, Formik} from "formik";
import styles from "./LoginForm.module.sass";
import * as Yup from 'yup';
import ErrorMessage from "../FormComponents/ErrorMessage/ErrorMessage";

interface IOwnProps {
    login: (request: ILoginRequest) => Promise<void>;
}

interface IState {
    loading: boolean;
    error?: string;
}

const validationSchema = Yup.object().shape({
    username: Yup.string()
        .min(4, 'Too Short! Need to be 4-16 digits.')
        .max(16, 'Too Long! Need to be 4-16 digits.')
        .required('This field is required'),
    password: Yup.string()
        .min(4, 'Too Short! Need to be 4-16 digits.')
        .max(16, 'Too Long! Need to be 4-16 digits.')
        .required('This field is required'),

});

class LoginForm extends React.Component<IOwnProps, IState> {
    state = {
        loading: false,
    } as IState;

    handleLogin = async (values: any) => {
        this.setState({error: undefined});
        const {login} = this.props;
        const {username, password} = values;
        this.setState({loading: true});

        try {
            await login({username, password});
        } catch (e) {
            this.setState({error: e.message});
        } finally {
            this.setState({loading: false});
        }
    };

    render() {
        const {loading, error} = this.state;

        return (
            <div>
                <Formik
                    onSubmit={this.handleLogin}
                    initialValues={{username: '', password: ''}}
                    validationSchema={validationSchema}
                    render={({
                                 errors,
                                 touched,
                                 handleChange,
                                 handleBlur,
                                 values
                             }) => {
                        const valid = !errors.username && !errors.password;
                        return (
                            <Form>
                                <FormWrapper>
                                    {error && (
                                        <ErrorMessage text={error} />
                                    )}
                                    <Input
                                        label="Username"
                                        value={values.username}
                                        name="username"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        error={errors.username}
                                        touched={touched.username}
                                    />
                                    <Input
                                        label="Password"
                                        value={values.password}
                                        name="password"
                                        type="password"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        error={errors.password}
                                        touched={touched.password}
                                    />
                                    <Button
                                        text="Log in"
                                        loading={loading}
                                        disabled={!valid}
                                        submit
                                    />
                                </FormWrapper>
                            </Form>
                        );
                    }}
                />
                <div className="center">
                    Not registered yet?
                    <br/>
                    <Link className={styles.link} to="/auth/register">Sign up</Link>
                </div>
            </div>
        );
    }
}

export default LoginForm;
