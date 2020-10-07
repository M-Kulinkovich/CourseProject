import React, {Component} from 'react';
import {Button, Dropdown, DropdownButton} from "react-bootstrap";
import Filter from "../Reports/Filter";
import "../../style/Table.scss";
import "../../style/SelectBlock.scss";
import {fetchFeatures, fetchProjects, fetchTasks} from "../Reports/ReportsService";
import {createReportRecord, getFactors, getLocations} from "./ReportServices";
import ToastComponent from "../Toast";

class SelectBlock extends Component {
    constructor() {
        super();

        this.state = {
            error: false,
            errorTitle: undefined,
            errorMessage: undefined,

            selected: {
                project: undefined,
                feature: undefined,
                task: undefined,

                factor: undefined,

                hour: 8,
                workUnit: 8,

                location: undefined,

                text: undefined,
            },
            success: false,

            available: {
                projects: undefined,
                features: undefined,
                tasks: undefined,

                factors: undefined,

                locations: undefined,
            }
        };

        this.selectProject = this.selectProject.bind(this);
        this.selectFeature = this.selectFeature.bind(this);
        this.selectTask = this.selectTask.bind(this);
        this.selectFactor = this.selectFactor.bind(this);
        this.selectLocation = this.selectLocation.bind(this);

        this.selectHour = this.selectHour.bind(this);
        this.selectWorkUnit = this.selectWorkUnit.bind(this);

        this.clear = this.clear.bind(this);

        this.callback = () => {
        };
        this.onSelect = this.onSelect.bind(this);
    }

    updateState(props) {
        // this.setState({available: props.available});
        console.log('props', props);
        if (props.callback !== undefined) {
            this.callback = props.callback;
        }
    }

    refreshAvailable(token) {
        if (token === undefined) return;
        let a = [];
        a.push(fetchProjects(token));
        a.push(this.state.selected.project !== undefined ? fetchFeatures(token, this.state.selected.project.id) : Promise.resolve({ok: true}));
        a.push(this.state.selected.feature !== undefined ? fetchTasks(token, this.state.selected.feature.id) : Promise.resolve({ok: true}));
        a.push(this.state.available.factors === undefined ? getFactors(token) : Promise.resolve({ok: true, notUpdate: true}));
        a.push(this.state.available.locations === undefined ? getLocations(token) : Promise.resolve({ok: true, notUpdate: true}));

        Promise.all(a).then(a => {
            if (!a[0].ok || !a[1].ok || !a[2].ok || !a[3].ok || !a[4].ok) {
                //TODO add error toast
                return;
            }
            this.state.available.projects = a[0].json;
            this.state.available.features = a[1].json;
            this.state.available.tasks = a[2].json;

            if (a[3].notUpdate === undefined) {
                this.state.available.factors = a[3].json.map(r => ({'name': r}));
                this.state.selected.factor = this.state.available.factors[0];
            }
            if (a[4].notUpdate === undefined) {
                this.state.available.locations = a[4].json;
                this.state.selected.location = this.state.available.locations[0];
            }
            this.setState({});
        });
    }

    changeSelected() {
        this.refreshAvailable(this.token);
    }

    showRecordError(msg) {
        this.setState({
            error: true,
            errorTitle: "Record error",
            errorMessage: msg
        });
    }

    saveReportRecord() {
        if (this.state.selected.factor === undefined) {
            this.showRecordError("Factor must be selected");
        } else if (this.state.selected.location === undefined) {
            this.showRecordError("Location must be selected");
        } else if (this.state.selected.hour < this.state.selected.workUnit) {
            this.showRecordError("At work time must be less or equals whole time");
        } else {
            console.log('selected', this.state.selected);
            createReportRecord(this.token, this.state.selected).then(r => {
                if (r.ok) {
                    this.setState({success: true});
                    console.log('callback', this.callback);
                    // this.callback();
                    this.callback();
                    this.clear();
                }
                else {
                    this.showRecordError("Cannot be saved");
                }
            })
        }
    }

    componentDidMount() {
        this.updateState(this.props);
        if (this.props.callback !== undefined) {
            this.callback = this.props.callback;
        }

        this.token = this.props.token;
        this.refreshAvailable(this.token);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        // this.updateState(nextProps);
        this.token = nextProps.token;
        this.refreshAvailable(this.token);
    }

    clear() {
        this.state.selected.location = this.state.available.locations !== undefined ? this.state.available.locations[0] : undefined;
        this.state.selected.project = undefined;
        this.state.selected.feature = undefined;
        this.state.selected.task = undefined;
        this.state.selected.factor = this.state.available.factors !== undefined ? this.state.available.factors[0] : undefined;
        this.state.selected.hour = 8;
        this.state.selected.workUnit = 8;

        this.state.available.features = undefined,
            this.state.available.tasks = undefined,


            this.setState({})
        this.changeSelected()
        // this.callback();
    }

    selectProject(project) {
        this.state.selected.project = project;
        this.state.selected.feature = undefined;
        this.state.selected.task = undefined;
        // this.callback(this.state.selected);
        this.setState({});

        this.changeSelected()
    }

    selectFeature(feature) {
        this.state.selected.feature = feature;
        this.state.selected.task = undefined;
        // this.callback(this.state.selected);
        this.setState({})

        this.changeSelected()
    }

    selectTask(task) {
        this.state.selected.task = task;
        this.setState({})

        // this.callback(this.state.selected);
    }

    selectFactor(factor) {
        this.state.selected.factor = factor;
        this.setState({})

        // this.callback(this.state.selected);
    }

    selectLocation(location) {
        this.state.selected.location = location;
        this.setState({})

        // this.callback(this.state.selected);
    }

    selectHour = time => {
        this.state.selected.hour = parseInt(time);
        this.setState({})
    };

    selectWorkUnit = time => {
        this.state.selected.workUnit = parseInt(time);

        this.setState({});
    };

    onSelect() {
        // this.callback(this.state.selected);

        this.saveReportRecord();
    }

    getDropdownTitle(item) {
        return (item !== undefined) ? item.name : "Select";
    }

    render() {
        return (
            <div>
                <Button variant="default" className="filterItem-clear" onClick={this.clear}>Clear</Button>

                <div className="selections">
                    {(() => {
                        if (this.state.available !== undefined && this.state.available.projects !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Project"
                                        dropdownTitle={this.getDropdownTitle(this.state.selected.project)}
                                        items={this.state.available.projects}
                                        onSelect={this.selectProject}
                                    />
                                </div>
                            );
                        }
                    }).call()}


                    {(() => {
                        if (this.state.available !== undefined && this.state.available.features !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Feature"
                                        dropdownTitle={this.getDropdownTitle(this.state.selected.feature)}
                                        items={this.state.available.features}
                                        onSelect={this.selectFeature}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if (this.state.available !== undefined && this.state.available.tasks !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Task"
                                        dropdownTitle={this.getDropdownTitle(this.state.selected.task)}
                                        items={this.state.available.tasks}
                                        onSelect={this.selectTask}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if (this.state.available !== undefined && this.state.available.factors !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Factor"
                                        dropdownTitle={this.getDropdownTitle(this.state.selected.factor)}
                                        items={this.state.available.factors}
                                        onSelect={this.selectFactor}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    {(() => {
                        if (this.state.available !== undefined && this.state.available.locations !== undefined) {
                            return (
                                <div>
                                    <hr></hr>
                                    <Filter
                                        filterTitle="Location"
                                        dropdownTitle={this.getDropdownTitle(this.state.selected.location)}
                                        items={this.state.available.locations}
                                        onSelect={this.selectLocation}
                                    />
                                </div>
                            );
                        }
                    }).call()}

                    <div>
                        <hr></hr>
                        <div className="selectItem">
                            <div className="selectItem-title">
                                All time
                            </div>
                            <div className="selectItem-dropdown">
                                <DropdownButton title={this.state.selected.hour} onSelect={this.selectHour}>
                                    <Dropdown.Item eventKey={1}>1</Dropdown.Item>
                                    <Dropdown.Item eventKey={2}>2</Dropdown.Item>
                                    <Dropdown.Item eventKey={3}>3</Dropdown.Item>
                                    <Dropdown.Item eventKey={4}>4</Dropdown.Item>
                                    <Dropdown.Item eventKey={5}>5</Dropdown.Item>
                                    <Dropdown.Item eventKey={6}>6</Dropdown.Item>
                                    <Dropdown.Item eventKey={7}>7</Dropdown.Item>
                                    <Dropdown.Item eventKey={8}>8</Dropdown.Item>
                                    <Dropdown.Item eventKey={9}>9</Dropdown.Item>
                                    <Dropdown.Item eventKey={10}>10</Dropdown.Item>
                                    <Dropdown.Item eventKey={11}>11</Dropdown.Item>
                                    <Dropdown.Item eventKey={12}>12</Dropdown.Item>
                                    <Dropdown.Item eventKey={13}>13</Dropdown.Item>
                                    <Dropdown.Item eventKey={14}>14</Dropdown.Item>
                                    <Dropdown.Item eventKey={15}>15</Dropdown.Item>
                                    <Dropdown.Item eventKey={16}>16</Dropdown.Item>
                                    <Dropdown.Item eventKey={17}>17</Dropdown.Item>
                                    <Dropdown.Item eventKey={18}>18</Dropdown.Item>
                                    <Dropdown.Item eventKey={19}>19</Dropdown.Item>
                                    <Dropdown.Item eventKey={20}>20</Dropdown.Item>
                                    <Dropdown.Item eventKey={21}>21</Dropdown.Item>
                                    <Dropdown.Item eventKey={22}>22</Dropdown.Item>
                                    <Dropdown.Item eventKey={23}>23</Dropdown.Item>
                                </DropdownButton>
                            </div>
                        </div>
                    </div>

                    <div>
                        <hr></hr>
                        <div className="selectItem">
                            <div className="selectItem-title">
                                At work
                            </div>
                            <div className="selectItem-dropdown">
                                <DropdownButton title={this.state.selected.workUnit} onSelect={this.selectWorkUnit}>
                                    <Dropdown.Item eventKey={1}>1</Dropdown.Item>
                                    <Dropdown.Item eventKey={2}>2</Dropdown.Item>
                                    <Dropdown.Item eventKey={3}>3</Dropdown.Item>
                                    <Dropdown.Item eventKey={4}>4</Dropdown.Item>
                                    <Dropdown.Item eventKey={5}>5</Dropdown.Item>
                                    <Dropdown.Item eventKey={6}>6</Dropdown.Item>
                                    <Dropdown.Item eventKey={7}>7</Dropdown.Item>
                                    <Dropdown.Item eventKey={8}>8</Dropdown.Item>
                                    <Dropdown.Item eventKey={9}>9</Dropdown.Item>
                                    <Dropdown.Item eventKey={10}>10</Dropdown.Item>
                                    <Dropdown.Item eventKey={11}>11</Dropdown.Item>
                                    <Dropdown.Item eventKey={12}>12</Dropdown.Item>
                                    <Dropdown.Item eventKey={13}>13</Dropdown.Item>
                                    <Dropdown.Item eventKey={14}>14</Dropdown.Item>
                                    <Dropdown.Item eventKey={15}>15</Dropdown.Item>
                                    <Dropdown.Item eventKey={16}>16</Dropdown.Item>
                                    <Dropdown.Item eventKey={17}>17</Dropdown.Item>
                                    <Dropdown.Item eventKey={18}>18</Dropdown.Item>
                                    <Dropdown.Item eventKey={19}>19</Dropdown.Item>
                                    <Dropdown.Item eventKey={20}>20</Dropdown.Item>
                                    <Dropdown.Item eventKey={21}>21</Dropdown.Item>
                                    <Dropdown.Item eventKey={22}>22</Dropdown.Item>
                                    <Dropdown.Item eventKey={23}>23</Dropdown.Item>
                                </DropdownButton>
                            </div>
                        </div>
                    </div>


                    <ToastComponent
                        type="warning"
                        visible={this.state.error}
                        title={this.state.errorTitle}
                        txt={this.state.errorMessage}
                        delay={5000}
                        closeAction={() => {
                            this.setState({error: false})
                        }}
                    />
                    <ToastComponent
                        type="success"
                        visible={this.state.success}
                        title="Success"
                        txt="Report record has been sent"
                        delay={2000}
                        closeAction={() => {
                            this.setState({success: false})
                        }}
                    />
                </div>
                <div className="selectItem-add">
                    <Button onClick={this.onSelect}>
                        Add
                    </Button>
                </div>
            </div>
        );
    }

}

export default SelectBlock;
