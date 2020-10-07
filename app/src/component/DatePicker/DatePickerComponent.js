import React, {Component, Fragment} from 'react';
import 'react-dates/initialize';
import '../../style/_datepicker.scss';
import '../../style/DatePicker.scss';
import moment from 'moment'
import {DateRangePicker, SingleDatePicker} from 'react-dates';

class DatePickerComponent extends Component {

    constructor(props) {
        super(props);
        moment.locale('ru');
        this.state = {
            date: null,
            date2: null,
            startDate: null,
            endDate: null,
            startDate2: null,
            endDate2: null,
        };
    }


    render() {
        return (
            <div>
                <SingleDatePicker
                    id={'singleDatePicker001'}
                    date={this.state.date}
                    onDateChange={date => this.setState({date})}
                    focused={this.state.focused}
                    onFocusChange={({focused}) => this.setState({focused})}
                    readOnly={true}
                    numberOfMonths={1}
                    hideKeyboardShortcutsPanel={true}
                    showClearDate={true}
                />
                <SingleDatePicker
                    id={'singleDatePicker002'}
                    date={this.state.date2}
                    onDateChange={date2 => this.setState({date2})}
                    focused={this.state.focused2}
                    onFocusChange={({focused: focused2}) => this.setState({focused2})}
                    readOnly={true}
                    numberOfMonths={1}
                    hideKeyboardShortcutsPanel={true}
                    showClearDate={true}
                    isOutsideRange={() => false}
                />
                <DateRangePicker
                    startDate={this.state.startDate}
                    endDate={this.state.endDate}
                    onDatesChange={(date) =>
                        this.setState({
                            startDate:date.startDate,
                            endDate:date.endDate
                        })
                    }
                    focusedInput={this.state.focusedInput}
                    onFocusChange={focusedInput => this.setState({ focusedInput })}
                    readOnly={true}
                    hideKeyboardShortcutsPanel={true}
                    showClearDates={true}
                    startDateId="startDate"
                    endDateId="endDate"
                />
                <DateRangePicker
                    startDate={this.state.startDate2}
                    endDate={this.state.endDate2}
                    onDatesChange={(date) =>
                        this.setState({
                            startDate2:date.startDate,
                            endDate2:date.endDate
                        })
                    }
                    focusedInput={this.state.focusedInput2}
                    onFocusChange={focusedInput2 => this.setState({ focusedInput2 })}
                    readOnly={true}
                    hideKeyboardShortcutsPanel={true}
                    showClearDates={true}
                    startDateId="startDate2"
                    endDateId="endDate2"
                    isOutsideRange={() => false}
                />
            </div>

        );
    }
}

export default DatePickerComponent;