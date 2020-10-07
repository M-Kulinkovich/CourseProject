import React, {Component} from "react";
import "../style/app.scss"
import LoginPageContainer from "./LoginPage/LoginPageContainer";
import {BrowserRouter, Route} from "react-router-dom";
import MainRoutes from "./MainRoutes";
import ServerErrorComponent from "./ServerError/ServerErrorComponent";


class MyComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loggedIn: undefined,
            serverError: false
        }

    }

    componentDidMount() {
        this.setState({loggedIn: this.props.loggedIn, serverError: this.props.serverError});
        this.props.getProfileFetch();
    };

    componentWillReceiveProps(nextProps, nextContext) {
        if (nextProps.serverError !== this.state.serverError) {
            this.setState({serverError: nextProps.serverError})
        }
        if (nextProps.loggedIn !== this.state.loggedIn) {
            this.setState({loggedIn: nextProps.loggedIn})
        }
    }

    render() {
        return (
            <BrowserRouter>
                {/* {
                    this.state.serverError === true && <div>
                        <ServerErrorComponent/>
                    </div>
                } */}
                {
                    // this.state.serverError === false && this.state.loggedIn === true &&
                    <div className={'MainBox'}>
                        <Route
                            component={MainRoutes}
                        />
                    </div>
                }
                {
                    // this.state.serverError === false && this.state.loggedIn === false &&
                    <div>
                        <Route
                            component={LoginPageContainer}
                        />
                    </div>
                }
            </BrowserRouter>
        );
    }
}

export default MyComponent;
