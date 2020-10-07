import React, {Component} from 'react';
import {DropdownButton, Dropdown} from 'react-bootstrap';
import {Link} from "react-router-dom";

class DropdownComponent extends Component {

    constructor() {
        super();
        this.state = {
            dropdownTitle: 'Select item'
        }
    }

    handleClick() {
        console.log('Add text to console');
    }

    render() {
        const handleSelect = eventKey => {
            this.setState({dropdownTitle: 'Test' + eventKey})
        };
        return (
            <DropdownButton onSelect={handleSelect} id="dropdown-item-button" title={this.state.dropdownTitle}>
                <Dropdown.Item onClick={this.handleClick} eventKey='1'>Test1</Dropdown.Item>
                <Dropdown.Item onClick={this.handleClick} eventKey='2'>Test2</Dropdown.Item>
                <Dropdown.Item onClick={this.handleClick} eventKey='3'>Test3</Dropdown.Item>
            </DropdownButton>
        );
    }
}

export default DropdownComponent;