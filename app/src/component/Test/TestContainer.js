import TestComponent from "./TestComponent";
import {setYear, getFreeData} from "../../Redux/Actions/TestAction";
import {fetchProducts} from '../../Redux/Actions/UsersActions';
import {connect} from "react-redux";
import React from "react";


const mapStateToProps = (store) => ({
    items: store.user,
    userList: store.page.userList,
    year: store.page.year
});

const mapDispatchToProps = {
    fetchProducts,
    setYear,
    getFreeData
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TestComponent);

