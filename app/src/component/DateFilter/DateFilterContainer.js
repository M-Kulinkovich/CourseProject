import DateFilterComponent from "./DateFilterComponent";
import {connect} from "react-redux";
import React from "react";

const mapStateToProps = (state) => ({
    token: state.auth?.token,
    currentUser: state.auth?.currentUser,
});


export default connect(
    mapStateToProps,
)(DateFilterComponent);