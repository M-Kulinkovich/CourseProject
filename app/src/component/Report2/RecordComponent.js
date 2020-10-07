import React, {Component} from 'react';
import "../../style/Record.scss";
import Filter from "../Reports/Filter";
import ToastComponent from "../Toast";
import {Button, Dropdown, DropdownButton} from "react-bootstrap";
import {fetchFeatures, fetchProjects, fetchTasks} from "../Reports/ReportsService";
import {createReportRecord, getFactors, getLocations, updateReportRecord} from "./ReportServices";

class Record extends Component {
    constructor() {
        super();

        this.state = {
            initialized: false,
            mode: undefined,
            token: undefined,

            record: {
                project: undefined,
                feature: undefined,
                task: undefined,

                hour: 8,
                workUnit: 8,

                factor: undefined,
                location: undefined,

                text: "",
            },

            available: {
                projects: undefined,
                features: undefined,
                tasks: undefined,

                factors: undefined,
                locations: undefined,
            },

            toast: {
                type: undefined,
                visible: false,
                title: undefined,
                message: undefined,
            },
        };

        this.callback = () => {
        };

        this.clear = this.clear.bind(this);

        this.updateState = this.updateState.bind(this);
    }

    displayError(message) {
        this.displayToast('warning', 'Record error', message);
    }

    displayToast(type, title, message) {
        this.setState({
            toast: {
                type: type,
                visible: true,
                title: title,
                message: message
            }
        })
    }

    recordIsValid() {
        let record = this.state.record;

        let message;

        if (['Day off', 'Sick leave', 'Vacation'].includes(record.factor.name)) {
            if (record.project !== undefined) message = 'No project at this factor!';
        } else {
            if (record.project === undefined)
                message = 'Project must be selected!';
            else if (record.hour < record.workUnit)
                message = 'At work time must be less or equals whole time';
        }

        if (message !== undefined) {
            this.displayError(message);
            return false;
        }

        return true;
    }

    updateRecord() {
        if (!this.recordIsValid()) return;

        console.log('RECORD: updating', this.state.record);

        this.state.record.text = document.getElementById('text').value;

        updateReportRecord(this.props.token, this.state.record).then(r => {
            if (r.ok) {
                this.clear();

                this.callback();
            }
        });

        console.log('RECORD: updated', this.state.record);
    }

    saveRecord() {
        this.state.record.text = document.getElementById('text').value;

        if (!this.recordIsValid()) return;

        console.log('RECORD: saving', this.state.record);

        createReportRecord(this.props.token, this.state.record).then(r => {
            if (r.ok) {
                this.displayToast('success', 'Success', 'Record is saved');
                //this.clear();

                this.callback();
                //this.initViewMode();
            }
        });

        console.log('RECORD: saved', this.state.record);
    }

    clear() {
        let record = this.state.record;
        let available = this.state.available;

        record.location = available.locations !== undefined
            ? available.locations[0]
            : undefined;
        record.project = undefined;
        record.feature = undefined;
        record.task = undefined;
        record.factor = available.factors !== undefined
            ? available.factors[0]
            : undefined;
        record.hour = 8;
        record.workUnit = 8;

        available.features = undefined;
        available.tasks = undefined;

        document.getElementById('text').value = '';

        this.setState({
            record: record,
            available: available
        })
    }

    select(type, object) {
        let record = this.state.record;

        console.log('object', object);

        if (type === 'project') {
            record.project = object;
            record.feature = undefined;
            record.task = undefined;
        }

        if (type === 'feature') {
            record.feature = object;
            record.task = undefined;
        }

        if (type === 'task') record.task = object;
        if (type === 'factor') record.factor = object;
        if (type === 'location') record.location = object;
        if (type === 'hour') record.hour = parseInt(object);
        if (type === 'workUnit') record.workUnit = parseInt(object);

        this.setState({
            record: record
        });

        this.refreshAvailable(this.props.token);
    }

    initViewMode() {
        console.log();
        console.log('RECORD: initializing VIEW MODE');

        this.setState({initialized: false});

        if (this.props.record !== undefined) {
            this.setState({
                record: record
            })
        }

        this.setState({
            initialized: true,
            mode: 'view'
        });

        console.log('RECORD: initialization finished');
        console.log();
    }

    initCreateMode(props) {
        console.log();
        console.log('RECORD: initializing CREATE MODE');

        this.setState({initialized: false});

        if (props.template !== undefined) {
            this.state.report = props.template;
            console.log('RECORD: with template', this.state.report);
        }

        this.callback = props.callback;

        this.setState({initialized: true});

        console.log('RECORD: initialization finished');
        console.log();
    }

    initEditMode(props) {
        console.log();
        console.log('RECORD: initializing EDIT MODE');

        this.setState({initialized: false});

        this.state.record = props.record;

        document.getElementById('text').value = this.state.record.text;

        console.log('RECORD: with record', props.record);

        this.callback = props.callback;

        this.setState({initialized: true});

        console.log('RECORD: initialization finished');
        console.log();
    }

    refreshAvailable(token) {
        console.log('     RECORD: refreshing available');

        if (token === undefined) {
            console.log('     RECORD token is undefined');
            return;
        }

        let available = this.state.available;
        let record = this.state.record;

        console.log('refreshing with', record);
        console.log(record.project);

        let a = [];

        a.push(fetchProjects(token));
        a.push(record.project !== undefined ? fetchFeatures(token, record.project.id) : Promise.resolve({
            ok: true,
            json: undefined
        }));
        a.push(record.feature !== undefined ? fetchTasks(token, record.feature.id) : Promise.resolve({
            ok: true,
            json: undefined
        }));
        a.push(available.factors === undefined ? getFactors(token) : Promise.resolve({ok: false, json: undefined}));
        a.push(available.locations === undefined ? getLocations(token) : Promise.resolve({ok: false, json: undefined}));

        Promise.all(a).then(a => {
            if (a[0].ok) available.projects = a[0].json;
            if (a[1].ok) available.features = a[1].json;
            if (a[2].ok) available.tasks = a[2].json;
            if (a[3].ok) {
                available.factors = a[3].json;
                record.factor = available.factors[0];
            }
            if (a[4].ok) {
                available.locations = a[4].json;
                record.location = available.locations[0];
            }

            console.log('     RECORD: new available', available);

            console.log('     RECORD: refreshing finished');

            this.setState({
                record: record,
                available: available,
            });
        });
    }

    updateState(props) {
        //if (this.state.mode !== props.mode) {
        this.state.mode = props.mode;

        if (this.state.mode === 'view') this.initViewMode(props);
        if (this.state.mode === 'create') this.initCreateMode(props);
        if (this.state.mode === 'edit') this.initEditMode(props);
        //}

        if (this.state.mode !== 'view') this.refreshAvailable(props.token);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.updateState(nextProps);
    }

    componentDidMount() {
        this.updateState(this.props);
    }

    getDropdownTitle(item) {
        return (item !== undefined) ? item.name : "Select";
    }

    displayField(title, value, field) {
        if (value === undefined) return <div></div>
        if (field !== undefined) value = value[field];
        let line = (title !== 'Project') ? <hr></hr> : <div></div>

        return (
            <div>
                {line}
                <div className='field'>
                    <div className='field-title'> {title} </div>
                    <div className='field-element'> {value} </div>
                </div>
            </div>
        );
    }

    displayFilter(filterTitle, dropdownTitle, items, selectType) {
        if (items !== undefined) {
            return (
                <div>
                    <Filter
                        filterTitle={filterTitle}
                        dropdownTitle={this.getDropdownTitle(dropdownTitle)}
                        items={items}
                        onSelect={this.select.bind(this, selectType)}
                    />
                    <hr></hr>
                </div>
            );
        }
    }

    displayTime(fieldTitle, dropdownTitle, selectType) {
        let availableTime = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16];

        return (
            <div className='field'>
                <div className='field-title'>
                    {fieldTitle}
                </div>
                <div className='field-element'>
                    <DropdownButton title={dropdownTitle} onSelect={this.select.bind(this, selectType)}>
                        {availableTime.map((amount) => {
                            return (
                                <Dropdown.Item eventKey={amount} key={amount}>
                                    {amount}
                                </Dropdown.Item>
                            );
                        })}
                    </DropdownButton>
                </div>
            </div>
        );
    }

    displaySubmitButton() {
        let buttons = <div></div>

        if (this.state.mode === 'create') {
            buttons = (
                <div className="button-submit">
                    <Button onClick={this.saveRecord.bind(this)}>Add</Button>
                </div>
            );
        }

        if (this.state.mode === 'edit') {
            buttons = (
                <div className="button">
                    <div className="button-submit">
                        <Button onClick={this.updateRecord.bind(this)}>Rewrite</Button>
                    </div>

                    <div className="button-submit">
                        <Button onClick={this.saveRecord.bind(this)}>Create new</Button>
                    </div>
                </div>
            );
        }

        return buttons;
    }

    displayWithViewMode() {
        return <div></div>
    }

    displayWithEditMode() {
        let record = this.state.record;
        let available = this.state.available;

        return (
            <div>
                <div className="button-clear">
                    <Button variant="default" onClick={this.clear}>Clear</Button>
                </div>

                <div className="selectors">
                    {this.displayFilter('Project', record.project, available.projects, 'project')}
                    {this.displayFilter('Feature', record.feature, available.features, 'feature')}
                    {this.displayFilter('Task', record.task, available.tasks, 'task')}
                    {this.displayFilter('Factor', record.factor, available.factors, 'factor')}
                    {this.displayFilter('Location', record.location, available.locations, 'location')}

                    {this.displayTime('All time', this.state.record.hour, 'hour')}
                    <hr></hr>
                    {this.displayTime('At work', this.state.record.workUnit, 'workUnit')}

                    <div className="textarea">
                        <label for="text">Description</label>
                        <textarea className="form-control" id="text" rows="2"></textarea>
                    </div>
                </div>

                {this.displaySubmitButton()}

                <ToastComponent
                    type={this.state.toast.type}
                    visible={this.state.toast.visible}
                    title={this.state.toast.title}
                    txt={this.state.toast.message}
                    delay={3000}
                    closeAction={() => {
                        this.state.toast.visible = false;
                        this.setState({})
                    }}
                />
            </div>
        );
    }

    render() {
        if (!this.state.initialized) return <div>loading...</div>;

        console.log('     current record', this.state.record);

        return (
            <div className='recordBlock'>
                {function () {
                    if (this.state.mode === 'view') return this.displayWithViewMode();
                    if (this.state.mode === 'create') return this.displayWithEditMode();
                    if (this.state.mode === 'edit') return this.displayWithEditMode();
                }.bind(this).call()}
            </div>
        );
    }
}

export default Record;
