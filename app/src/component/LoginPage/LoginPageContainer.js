import React from "react";
import LoginPageComponent from "./LoginPageComponent"
import {connect} from "react-redux";
import {userLoginFetch} from '../../Redux/Actions/UserLoginFetchAction';
import {getProfileFetch} from "../../Redux/Actions/GetProfileFetchActions";

const mapDispatchToProps = dispatch => ({
    userLoginFetch: (state) => dispatch(userLoginFetch(state)),
    getProfileFetch: () => dispatch(getProfileFetch())
});

const mapStateToProps = (state) => ({
    loggedIn: state?.auth?.auth?.loggedIn,
    error: state.auth.error
});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(LoginPageComponent);