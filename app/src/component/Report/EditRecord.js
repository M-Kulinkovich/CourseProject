import React, {Component} from 'react';
import {OperationType} from "./Operation";
import {Button} from "react-bootstrap";
import Checkbox from "@material-ui/core/Checkbox";
import Filter from "../Reports/Filter";
import {DateRangePicker} from "react-dates";

class EditRecord extends Component {
    constructor() {
        super();

        this.state = {
            template: {
                project: undefined,
                feature: undefined,
                task: undefined,

                factor: undefined,

                hour: undefined,
                workUnit: undefined,

                location: undefined,

                text: undefined,
            },

            available: {
                projects: undefined,
                features: undefined,
                tasks: undefined,

                factors: undefined,

                locations: undefined,
            }
        }

        this.clear = this.clear.bind(this);

        this.callback = () => {}
    }

    updateState(props) {
        this.setState({available: props.available});

        if(this.props.callback !== undefined) {
            this.callback = this.props.callback;
        }
    }

    componentDidMount() {
        this.updateState(this.props);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.updateState(nextProps);
    }

    clear() {
        this.state.selected.location = undefined;
        this.state.selected.project = undefined;
        this.state.selected.feature = undefined;
        this.state.selected.task = undefined;
        this.state.selected.factor = undefined;
        this.state.selected.hour = undefined;
        this.state.selected.workUnit = undefined;
        this.callback();
    }

    selectProject(project) {
        this.state.selected.project = project;
        this.state.selected.feature = undefined;
        this.state.selected.task = undefined;
        this.callback(this.state.selected);
    }

    selectFeature(feature) {
        this.state.selected.feature = feature;
        this.state.selected.task = undefined;
        this.callback(this.state.selected);
    }

    selectTask(task) {
        this.state.selected.task = task;
        this.callback(this.state.selected);
    }

    selectFactor(factor) {
        this.state.selected.factor = factor;
        this.callback(this.state.selected);
    }

    selectLocation(task) {
        this.state.selected.location = location;
        this.callback(this.state.selected);
    }

    getDropdownTitle(item) {
        return (item !== undefined) ? item.name : "Select";
    }

    render() {
        return (
            <div>
                <Button variant="default" className="filterItem-clear"  onClick={this.clear}>Clear</Button>

                <div className="filters">
                    {(() => {
                        if(this.props.available.projects !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Project"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.project)}
                                        items={this.props.available.projects}
                                        onSelect={this.selectProject}
                                    />
                                </div>
                            );
                        }
                    }).call()}


                    {(() => {
                        if(this.props.available.features !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Feature"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.feature)}
                                        items={this.props.available.features}
                                        onSelect={this.selectFeature}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if(this.props.available.tasks !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Task"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.task)}
                                        items={this.props.available.tasks}
                                        onSelect={this.selectTask}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if(this.props.available.factors !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Factors"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.task)}
                                        items={this.props.available.factors}
                                        onSelect={this.selectFactors}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if(this.props.available.locations !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Task"
                                        dropdownTitle={this.getDropdownTitle(this.state.toFilter.task)}
                                        items={this.props.locations}
                                        onSelect={this.selectLocation}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    <div className="filterItem">
                        <div className="filterItem-datePicker">

                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default EditRecord;