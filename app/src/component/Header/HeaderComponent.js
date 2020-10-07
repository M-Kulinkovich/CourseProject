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
                </div>
                <div className='dropDownBox'>
                    <DropdownButton className='dropDownComponent' title='components'>
                        <Dropdown.Item eventKey='1'>
                            <Link to='/'>
                                All Reports
                            </Link>
                        </Dropdown.Item>
                        {/*<Dropdown.Item eventKey='4'>*/}
                        {/*    <Link to='/reports'>*/}
                        {/*        Reports*/}
                        {/*    </Link>*/}
                        {/*</Dropdown.Item>*/}
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
                        {/*<Dropdown.Item eventKey='5'>*/}
                        {/*    <Link to='/checkBox'>*/}
                        {/*        CheckBox*/}
                        {/*    </Link>*/}
                        {/*</Dropdown.Item>*/}
                    </DropdownButton>
                    <DropdownButton className='dropDownComponent' title='Help'>
                        <Dropdown.Item eventKey='3'>Help</Dropdown.Item>
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
