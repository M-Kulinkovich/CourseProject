import logo from "../../Images/TI-logo-black.png";
import React, {Component} from "react";
import "../../style/ServerError.scss";


class ServerErrorComponent extends Component {
    render() {
        return (
            <div>
                <img src={logo} alt="logo" className={'ServerErrorImage'}/>
                <div className={'ServerError'}>
                    Server error!
                </div>
            </div>
        );
    }
}

export default ServerErrorComponent;