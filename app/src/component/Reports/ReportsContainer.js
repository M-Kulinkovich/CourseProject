import ReportsComponent from './ReportsComponent';
import {connect} from "react-redux";

const mapStateToProps = (state) => ({
    token: state.auth?.token,
});

export default connect(
    mapStateToProps
)(ReportsComponent);