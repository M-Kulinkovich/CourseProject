import React, {Component} from 'react';
import "../../style/app.scss";
import {Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';

class TestComponent extends Component {

    constructor() {
        super();
        this.state = {
            randomValue: ''
        };
        this.onBtnClick = this.onBtnClick.bind(this)
    }

    onBtnClick(e) {
        const year = +e.currentTarget.innerText;
        this.props.setYear(year)
    }

    setRandomValue = () => {
        this.setState({randomValue: Math.random()})
    };

    render() {
        const {userList, year} = this.props;
        return (
            <div>
                <div className='componentBox'>
                    <Button
                        variant="light"
                        onClick={this.setRandomValue}
                    >
                        Click me
                    </Button>
                    <Button
                        variant="outline-primary"
                        onClick={() => {
                            this.setState({randomValue: Math.random()})
                        }}
                    >
                        Click me
                    </Button>
                    <Button
                        variant="light"
                        size="lg"
                        onClick={this.setRandomValue}
                    >
                        Click me
                    </Button>
                    <Button
                        variant="light"
                        size="sm"
                        onClick={this.setRandomValue}
                    >
                        Click me
                    </Button>
                    <Button
                        variant="primary"
                        onClick={this.setRandomValue}
                    >
                        Click me
                    </Button>
                    <Button
                        variant="light"
                        disabled
                        onClick={this.setRandomValue}
                    >
                        Click me
                    </Button>
                </div>
                <p>{this.props.txt}{this.state.randomValue}</p>
                <p>
                    Hello {userList} you Register in {year} and have 0 photos!
                </p>
                <div>
                    <nav>
                        <ul>
                            <li><Link to='/'>Home</Link></li>
                            <li><Link to='/login'>LoginPage</Link></li>
                            <li><Link to='/form'>Forms</Link></li>
                            <li><Link to='/dropDown'>DropDowns</Link></li>
                            <li><Link to='/checkBox'>Checkbox</Link></li>
                            <li><Link to='/filter'>Filter</Link></li>
                            <li><Link to='/datePicker'>DatePicker</Link></li>
                            <li><Link to='/toast'>Toasts</Link></li>
                            <li><Link to='/table'>Table</Link></li>
                            <li><Link to='/reports'>Reports</Link></li>
                            <li><Link to='/report'>Report</Link></li>
                            <li><Link to='/filter'>Filter</Link></li>
                        </ul>
                    </nav>
                </div>
                <button onClick={this.onBtnClick}>2018</button>
                <button onClick={this.onBtnClick}>2022</button>
                <button onClick={this.onBtnClick}>2000</button>
                <br/>
                <button onClick={() => {
                    this.props.fetchProducts();
                }
                }>free
                </button>
            </div>
        );
    }
}

export default TestComponent;