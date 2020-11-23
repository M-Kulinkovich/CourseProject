import React, {Component} from "react";
import '../../style/header.scss';
import logo from "../../Images/TI-logo-black.png";
import {DropdownButton, Dropdown} from 'react-bootstrap';
import {Link} from "react-router-dom";

class HeaderComponent extends Component {

    handleClick = event => {
        event.preventDefault();
        this.props.logoutUser()
    };

    render() {

        return (
            <div className='header'>
                <div >
                    <img className='logoBox' src={logo} alt="logo"/>
                    <b className='logoText' > Monitoring employee activity</b>
                </div>
                <div className='dropDownBox'>
                    <DropdownButton className='dropDownComponent' title='Components'>
                        <Dropdown.Item eventKey='1'>
                            <Link to='/'>
                                All Reports
                            </Link>
                        </Dropdown.Item>
                        <Dropdown.Item eventKey='2'>
                            <Link to='/report?mode=create'>
                                Create report
                            </Link>
                        </Dropdown.Item>
                        <Dropdown.Item eventKey='3'>
                            <Link to='/filter'>
                                Date filter
                            </Link>
                        </Dropdown.Item>
                    </DropdownButton>
                    <DropdownButton className='dropDownComponent' title='Documents'>
                        <Dropdown.Item eventKey='3'>
                            Creators
                        </Dropdown.Item>
                    </DropdownButton>
                </div>
                <button onClick={this.handleClick} className='ButtonLogout'>
                    logout
                </button>
            </div>
        );
    }
}

export default HeaderComponent;
