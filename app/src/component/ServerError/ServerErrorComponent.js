
import Background from "../../Images/error5001.png";
import React, {Component} from "react";
import "../../style/ServerError.scss";


class ServerErrorComponent extends Component {
    render() {
        return (
            <div>
                <img src={Background}  className={'ServerErrorImage'}/>
            </div>
        );
    }
}

export default ServerErrorComponent;