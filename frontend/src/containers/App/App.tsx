import React from 'react';
import {Provider} from 'react-redux';
import {store} from "../../store";
import ReduxToastr from "react-redux-toastr";
import {BrowserRouter} from 'react-router-dom';
import Routing from "../Routing/Routing";
import 'react-redux-toastr/lib/css/react-redux-toastr.min.css';

class App extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <ReduxToastr
                    timeOut={4000}
                    newestOnTop={false}
                    preventDuplicates
                    position="top-right"
                    transitionIn="fadeIn"
                    transitionOut="fadeOut"
                    closeOnToastrClick={true}
                />
                <BrowserRouter>
                    <Routing />
                </BrowserRouter>
            </Provider>
        );
    }
}

export default App;
