
import React, {Component} from 'react';
import {Button, Dropdown, DropdownButton} from 'react-bootstrap';
import 'react-dates/initialize';
import '../../style/_datepicker.scss';
import '../../style/DatePicker.scss';
import '../../style/dateFilter.scss';
import moment from 'moment'
import {DateRangePicker} from 'react-dates';
import {Comparison, toBase64} from "../../util/base64Converter";
import TableComponent from "../Table";

const URL_USERS = "/users";
const BASE_URL = "http://localhost:8080";
const URL_REPORT_FILTER = "/report?filter=";

class DateFilterComponent extends Component {

    constructor(props) {
        super(props);
        moment.locale('ru');
        this.state = {
            startDate: null,
            endDate: null,
            currentUser: null,
            size: null,

            dates: {},
            emptyDates: {},
            userEmail: [],
            dropdownTitle: 'Select user',
            users: [],
            reports: [],
            isPressed: false,
            isAdmin: false,
        }
    }

    componentDidMount() {
        this.props.currentUser.roles.map(r => {
            if (r == "ADMIN")
                this.state.isAdmin = true;
        });
        if (this.state.isAdmin)
            fetch(BASE_URL + URL_USERS,
                {
                    headers: {
                        'Authorization': 'Bearer ' + this.props.token,
                    }
                })
                .then(response => {
                    if (response.ok)
                        response.json().then(res => {
                            this.state.isAdmin = true;
                            this.setState({users: res});
                        });
                });
    }

    setEmptyDates(startDate, interval) {
        if (interval !== 0 && this.state.dates !== null) {
            {
                this.state.days = []
            }
            let isDate = false;
            for (let j = 0; j < this.state.size; j++) {
                if (this.state.dates[j]) {
                    this.state.emptyDates[j] = [];
                    let date = new Date(startDate);
                    for (let i = 0; i <= interval; i++) {
                        this.state.dates[j].map(res => {
                            if (date.toISOString().substring(0, 10) === res) {
                                isDate = true;
                            }
                        });
                        if (!isDate) {
                            this.state.emptyDates[j].push(date.toISOString().substring(0, 10) + "  ");
                        }
                        isDate = false;
                        date.setDate(date.getDate() + 1);
                    }
                }
            }
        }
    }


    fetchReport(startDate, endDate) {
        if (startDate !== null && endDate !== null) {
            this.state.startDate = startDate;
            this.state.endDate = endDate;
            fetch(BASE_URL + URL_REPORT_FILTER + toBase64('date', Comparison.LTE, endDate.toISOString().substring(0, 10)) +
                "&filter=" + toBase64('date', Comparison.GTE, startDate.toISOString().substring(0, 10)),
                {
                    headers: {
                        'Authorization': 'Bearer ' + this.props.token,
                    }
                })
                .then(res => res.json())
                .then(resp => {
                    this.setState({reports: resp});
                });
        }
    }

    addLinks(dates) {
        const returnDates = [];
        dates.map((date,index) => {
            let ref = "/report?mode=create&date=" + date;
            returnDates.push(<a key={index} href={ref}>{date}</a>)
        });
        return returnDates;
    }

    returnDate() {
        const returnDates = [];
        if (this.state.startDate !== null && this.state.endDate !== null
            && this.state.isPressed) {

            if (this.state.isAdmin && this.state.currentUser == null) {
                for (let i = 1; i < this.state.size; i++) {
                    this.state.isPressed = false;
                    let row;
                    if (i === this.props.currentUser.id) {
                        row = {
                            id: this.state.userEmail[i - 1],
                            dates: <div>{this.addLinks(this.state.emptyDates[i])}</div>
                        };
                    } else {
                        row = {
                            id: this.state.userEmail[i - 1],
                            dates: <div>{this.state.emptyDates[i]}</div>
                        };
                    }

                    returnDates.push(row);
                }
            } else if (this.state.isAdmin && this.state.currentUser !== null) {
                let row;
                if (this.state.currentUser.id === this.props.currentUser.id) {
                    row = {
                        id: this.state.currentUser.email,
                        dates: <div>{this.addLinks(this.state.emptyDates[this.state.currentUser.id])}</div>
                    };
                } else {
                    row = {
                        id: this.state.currentUser.email,
                        dates: <div>{this.state.emptyDates[this.state.currentUser.id]}</div>
                    };
                }

                returnDates.push(row);
                this.state.isPressed = false;
            } else {
                let row = {
                    id: 'empty',
                    dates: <div>{this.addLinks(this.state.emptyDates[0])}</div>
                };

                returnDates.push(row);
                this.state.isPressed = false;
            }
            this.state.emptyDates = [];

        }
        return returnDates;
    }


    sortDate() {
        let existReports = false;
        this.state.size = 0;
        let userId = 0;
        this.state.users.map(user => {
            let userId = user.id;
            this.state.userEmail.push(user.email);
            if (!this.state.dates[userId]) {
                this.state.dates[userId] = [];
                this.state.emptyDates[userId] = [];
            }
            this.state.size = userId + 1;
        });
        if (!this.state.isAdmin)
            this.state.dates[0] = [];
        this.state.reports.map(res => {
                existReports = true;
                userId = res.userId;
                if (this.state.isAdmin)
                    this.state.dates[userId].push(res.date);
                else
                    this.state.dates[0].push(res.date);
            }
        );
        if (this.state.size === 0) {
            if (!existReports)
                this.state.dates[0] = [];
            this.state.size = 1;
        }
        this.setEmptyDates(this.state.startDate, (this.state.endDate - this.state.startDate) / 86400000);
    }

    dropDown() {
        const handleSelect = eventKey => {
            if (eventKey == null) {
                this.state.currentUser = null;
                this.setState({dropdownTitle: "All"});
            } else {
                this.state.currentUser = this.state.users[eventKey];
                this.setState({dropdownTitle: this.state.currentUser.name});
            }
        };
        if (this.state.isAdmin)
            return (
                <DropdownButton onSelect={handleSelect} id="dropdown-item-button" title={this.state.dropdownTitle}>
                    {this.state.users.map((user, index) => (
                        <Dropdown.Item key={user.id} onClick={this.handleClick}
                                       eventKey={index}>{user.name}</Dropdown.Item>
                    ))}
                    <Dropdown.Item key={"all"} eventKey={null}> All </Dropdown.Item>
                </DropdownButton>

            );
    }

    tableDates() {
        let data = this.returnDate();
        if (this.state.isAdmin) {
            return (
                <TableComponent
                    caption=""
                    columns={[
                        {
                            prop: "id",
                            title: "ID",
                            visible: true,
                            align: "center",
                            width: 250
                        },
                        {
                            prop: "dates",
                            title: "DATES",
                            visible: true,
                            width: 900,
                            searching: {available: false}
                        },
                    ]}
                    data={data}
                    width={610}
                />
            );
        } else
            return (
                <TableComponent
                    caption=""
                    columns={[
                        {
                            prop: "dates",
                            title: "DATES",
                            visible: true,
                            width: 1000,
                            searching: {available: false}
                        },
                    ]}
                    data={data}
                    width={610}
                />
            );
    }

    render() {
        const handleSelectDate = (date) => {
            this.setState({
                startDate: date.startDate,
                endDate: date.endDate
            });
            this.fetchReport(date.startDate, date.endDate);
        };

        return (
            <div>
                <div className='content'>
                    <div className={"element"}>{this.dropDown()}</div>
                    <div className={"element"}>
                        <DateRangePicker
                            startDate={this.state.startDate}
                            endDate={this.state.endDate}
                            onDatesChange={(date) =>
                                handleSelectDate(date)
                            }
                            isOutsideRange={() => false}
                            focusedInput={this.state.focusedInput}
                            onFocusChange={focusedInput => this.setState({focusedInput})}
                            readOnly={true}
                            hideKeyboardShortcutsPanel={true}
                            showClearDates={true}
                            startDateId="startDate"
                            endDateId="endDate"
                        />
                    </div>
                    <div className={"element"}>
                        <Button
                            variant="light"
                            onClick={() => {
                                this.sortDate();
                                this.setState({emptyDates: this.state.emptyDates});
                                this.state.isPressed = true;
                            }}
                        >Get dates without reports
                        </Button>
                    </div>
                </div>
                {this.tableDates()}
                {this.returnDate()}
            </div>
        );
    }

}



export default DateFilterComponent
