import React, {Component} from 'react';
import PropTypes from 'prop-types';
import "../../style/Table.scss";
import Filter from "../Reports/Filter";
import Checkbox from "@material-ui/core/Checkbox";
import {DateRangePicker} from "react-dates";
import '../../style/_datepicker.scss';
import '../../style/DatePicker.scss';
import {Button} from 'react-bootstrap';
import moment from 'moment';

class FilterBlock extends Component {
    constructor() {
        super();

        moment.locale('ru');

        this.state = {
            lastUpdatedPartOfTheDate: undefined,


            toFilter: {
                fromDate: undefined,
                toDate: undefined,

                privateOnly: false,

                project: undefined,
                feature: undefined,
                task: undefined,
            },
        }

        this.clear = this.clear.bind(this);

        this.swapPrivate = this.swapPrivate.bind(this);

        this.selectProject = this.selectProject.bind(this);
        this.selectFeature = this.selectFeature.bind(this);
        this.selectTask = this.selectTask.bind(this);

        this.selectDate = this.selectDate.bind(this);

        this.callback = () => {
        }
    }

    clear() {
        this.state.toFilter.fromDate = undefined;
        this.state.toFilter.toDate = undefined;
        this.state.toFilter.privateOnly = false;
        this.state.toFilter.project = undefined;
        this.state.toFilter.feature = undefined;
        this.state.toFilter.task = undefined;

        this.callback(this.state.toFilter);
    }

    selectDate(date) {
        this.state.toFilter.fromDate = date.startDate;
        this.state.toFilter.toDate = date.endDate;

        if (date.endDate !== null) {
            this.callback(this.state.toFilter);
        }

        /*
        this.setState({
            startDate: date.startDate,
            endDate: date.endDate
        })

        if(date.startDate != null) {
            this.state.toFilter.fromDate = date.startDate;
            this.state.dateSet = 1;
        }

        if(date.endDate != null) {
            this.state.toFilter.toDate = date.endDate;
            this.state.dateSet = 2;
        }

        if(this.state.dateSet == 2) {
            this.state.dateSet = 0;
            this.callback(this.state.toFilter);
        }

         */
    }

    swapPrivate() {
        this.state.toFilter.privateOnly = !this.state.toFilter.privateOnly;
        this.callback(this.state.toFilter);
    }

    selectProject(project) {
        this.state.toFilter.project = project;
        this.state.toFilter.feature = undefined;
        this.state.toFilter.task = undefined;
        this.callback(this.state.toFilter);
    }

    selectFeature(feature) {
        this.state.toFilter.feature = feature;
        this.state.toFilter.task = undefined;
        this.callback(this.state.toFilter);
    }

    selectTask(task) {
        this.state.toFilter.task = task;
        this.callback(this.state.toFilter);
    }

    getDropdownTitle(item) {
        return (item !== undefined) ? item.name : "Select";
    }

    componentDidMount() {
        if (this.props.callback !== undefined) {
            this.callback = this.props.callback;
        }
    }

    render() {
        return (
            <div>
                <Button variant="default" className="filterItem-clear" onClick={this.clear}>Clear</Button>

                <div className="filters">
                    <div className="filterItem">
                        <div className="filterItem-title">
                            Private only
                        </div>
                        <div className="filterItem-checkBox">
                            <Checkbox
                                color="primary"
                                checked={this.state.toFilter.privateOnly}
                                onChange={this.swapPrivate}
                            />
                        </div>
                    </div>

                    {(() => {
                        if (this.props.projects !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Project"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.project)}
                                        items={this.props.projects}
                                        onSelect={this.selectProject}
                                    />
                                </div>
                            );
                        }
                    }).call()}


                    {(() => {
                        if (this.props.features !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Feature"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.feature)}
                                        items={this.props.features}
                                        onSelect={this.selectFeature}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if (this.props.tasks !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Task"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.task)}
                                        items={this.props.tasks}
                                        onSelect={this.selectTask}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    <div className="filterItem">
                        <div className="filterItem-datePicker">
                            <DateRangePicker
                                startDate={this.state.toFilter.fromDate}
                                endDate={this.state.toFilter.toDate}
                                onDatesChange={(date) => {
                                    this.selectDate(date)
                                }}
                                focusedInput={this.state.focusedInput}
                                onFocusChange={focusedInput => this.setState({focusedInput})}
                                readOnly={true}
                                hideKeyboardShortcutsPanel={true}
                                showClearDates={true}
                                startDateId="startDate"
                                endDateId="endDate"
                                isOutsideRange={() => false}

                                startDatePlaceholderText="From"
                                endDatePlaceholderText="To"

                                daySize={30}
                            />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
};

FilterBlock.propTypes = {
    projects: PropTypes.array,
    features: PropTypes.array,
    tasks: PropTypes.array,
};

export default FilterBlock;