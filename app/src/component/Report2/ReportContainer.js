import {connect} from "react-redux";
import ReportComponent from "./ReportComponent";


const mapStateToProps = (state) => ({
    token: state.auth?.token,
    currentUser: state.auth?.currentUser,
});

export default connect(
    mapStateToProps
)(ReportComponent);