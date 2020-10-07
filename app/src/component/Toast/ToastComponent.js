import React, {Component} from 'react';
import {Toast} from 'react-bootstrap';
import PropTypes from 'prop-types';
import "../../style/Toast.scss";

class ToastComponent extends Component {
    constructor() {
        super();
        this.state = {visible: true};
        this.hide = this.hide.bind(this);
    }

    hide() {
        this.setState({visible: false});
    }

    componentDidMount() {
        this.setState({visible: this.props.visible})
    }

    componentWillUpdate(nextProps, nextState, nextContext) {
        if (nextProps.visible !== this.state.visible)
            this.setState({visible: nextProps.visible});
    }

    render() {
        if (!this.state.visible) {
            return "";
        }

        let headerStyle = "header-" + this.props.type;
        let title = this.props.title;
        let txt = this.props.txt;
        let delay = this.props.delay;
        let closeAction = (this.props.closeAction !== undefined) ? this.props.closeAction : this.hide;

        let defaultPosition = {
            "info": "tl",
            "success": "tr",
            "warning": "bl"
        }

        let position = "position-" + (
            ["tl", "tr", "bl", "br"].includes(this.props.pos) ? this.props.pos : defaultPosition[this.props.type]
        );

        return (
            <Toast className={`${position} size`}
                   onClose={closeAction}
                   delay={delay} autohide
            >
                <Toast.Header className={headerStyle}>
                    <strong className="mr-auto">{title}</strong>
                </Toast.Header>
                <Toast.Body>
                    <p>{txt}</p>
                </Toast.Body>
            </Toast>
        );
    }
}

ToastComponent.propsTypes = {
    type: PropTypes.string,
    pos: PropTypes.oneOf(["tl", "tr", "bl", "br"]),
    visible: PropTypes.bool,
    title: PropTypes.string,
    txt: PropTypes.string,
    delay: PropTypes.number,
    closeAction: PropTypes.func,
}

ToastComponent.defaultProps = {
    type: "info",
    visible: false,
    title: "No title",
    txt: "No text",
    delay: 1000000,
}

export default ToastComponent;