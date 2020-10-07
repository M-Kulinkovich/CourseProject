import React from "react";
import HeaderComponent from "./HeaderComponent";
import {connect} from "react-redux";
import {logoutUser} from "../../Redux/Actions/AuthActions";

const mapStateToProps = state => ({
    currentUser: state.auth?.currentUser
});

const mapDispatchToProps = dispatch => ({
    logoutUser: () => dispatch(logoutUser())
});

export default connect(mapStateToProps, mapDispatchToProps)(HeaderComponent);