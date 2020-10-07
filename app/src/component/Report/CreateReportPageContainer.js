import {connect} from "react-redux";
import CreateReportPage from "./CreateReportPage";

const mapStateToProps = (state) => ({
    token: state.auth?.token,
    currentUser: state.auth?.currentUser,
});

export default connect(
    mapStateToProps
)(CreateReportPage);