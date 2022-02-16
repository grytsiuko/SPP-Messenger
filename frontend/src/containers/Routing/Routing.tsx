import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import Auth from "../Auth/Auth";
import Home from "../Home/Home";

class Routing extends React.Component {
    render() {
        return (
            <Switch>
                 <Route path="/auth" render={() => <Auth/>}/>
                 <Route path="/home" render={() => <Home/>}/>
                 <Route path="/">
                     <Redirect to="/home" />
                 </Route>
            </Switch>
        );
    }
}

export default Routing;
