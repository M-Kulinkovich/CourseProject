import React, {Component} from 'react';
import ToastComponent from "../Toast";
import {Button} from 'react-bootstrap';

class ToastTestComponent extends Component {
    constructor() {
        super();

        this.state = {
            infoOnShow : true,
            successOnShow : true,
            warningOnShow : false,
        };

        this.showWarningToast = this.showWarningToast.bind(this);
    }

    hideToast(kind) {
        this.setState(kind);
    }

    showWarningToast() {
        this.setState({
            warningOnShow : true,
        });
    }

    render () {
        return (
            <div>
                <Button onClick={this.showWarningToast}>
                    Show warning
                </Button>
                <ol>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                    <li>test</li>
                </ol>
                <ToastComponent
                    visible={this.state.infoOnShow}
                    delay={3000}
                    closeAction={this.hideToast.bind(this, {"infoOnShow" : false})}
                />
                <ToastComponent
                    type="success"
                    visible={this.state.successOnShow}
                    closeAction={this.hideToast.bind(this, {"successOnShow" : false})}
                />
                <ToastComponent
                    type="warning"
                    visible={this.state.warningOnShow}
                    closeAction={this.hideToast.bind(this, {"warningOnShow" : false})}
                />
            </div>
        );
    }
}

export default ToastTestComponent;