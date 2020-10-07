import MyComponent from "./app.component";
import {connect} from "react-redux";
import React from "react";
import {getProfileFetch} from '../Redux/Actions/GetProfileFetchActions';

const mapDispatchToProps = dispatch => ({
    getProfileFetch: () => dispatch(getProfileFetch())
});

const mapStateToProps = (state) => ({
    loggedIn: state.auth?.loggedIn,
    serverError: state.auth?.serverError
});


export default connect(
    mapStateToProps,
    mapDispatchToProps
)(MyComponent);