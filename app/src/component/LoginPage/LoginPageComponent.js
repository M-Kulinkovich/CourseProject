import React from "react";
import {Button, TextField} from '@material-ui/core';
import '../../style/loginPage.scss';
import {store} from "../../Redux/store";

class LoginPageComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            error: false,
            helperText: '',
            loading: false,
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this)
    }

    handleChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    updateStore() {
        if (this.props.loggedIn) {
            document.location.href = '/';
        }
    }

    componentDidMount() {
        this.unsubscribeStore = store.subscribe(this.updateStore.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribeStore();
    }


    handleSubmit = event => {
        this.setState({loading: true});

        event.preventDefault();
        this.props.userLoginFetch(this.state);
        if (this.props.error === true) {
            this.setState({helperText: '', error: false});
        } else {
            this.setState({helperText: 'Incorrect email or password', error: true});
        }
        setTimeout(() => {
            this.setState({loading: false});
        }, 1000);
    };


    render() {
        const {loading} = this.state;
        return (
            <form onSubmit={this.handleSubmit}>
                <div className='container'>
                    <div className='loginForm'>
                        <TextField
                            helperText={this.state.helperText}
                            error={this.props.error}
                            value={this.state.email}
                            name="email"
                            label="Email"
                            type="email"
                            fullWidth
                            required
                            onChange={this.handleChange}
                        />
                        <TextField
                            helperText={this.state.helperText}
                            error={this.props.error}
                            value={this.state.password}
                            name="password"
                            label="Password"
                            type="password"
                            fullWidth
                            required
                            onChange={this.handleChange}
                        />
                    </div>
                    <div className='loginButton'>
                        <Button
                            disabled={!this.state.email || !this.state.password}
                            type="submit"
                            variant="outlined"
                            color="primary"
                        >
                            Login
                        </Button>
                        {loading &&
                        <img className='ImgLoading'
                             src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA=="/>
                        }
                    </div>
                </div>
            </form>
        );
    }
}

export default (LoginPageComponent);