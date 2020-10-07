import React, {Component} from 'react';
import moment from 'moment';
import "../../style/Report.scss";
import {
    createReport,
    deleteReportRecord,
    getNullReportRecords,
    getRecordById,
    getRecordsByReportId, updateReport
} from "./ReportServices";
import RecordComponent from "../Report2/RecordComponent";
import TableComponent from "../Table";
import ToastComponent from "../Toast";
import {faEdit, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import Checkbox from "@material-ui/core/Checkbox";
import {SingleDatePicker} from 'react-dates';

class ReportComponent extends Component {
    constructor() {
        super();

        this.state = {
            initialized: false,
            mode: undefined,
            token: undefined,

            recordComponent: {
                mode: undefined,
                record: undefined,
            },

            report: {
                date: undefined,
                user: undefined,
                hour: undefined,
                workUnit: undefined,
                confirm: false,

                records: []
            },

            table: {
                columns: undefined,
                data: undefined,
            },

            toast: {
                type: undefined,
                visible: false,
                title: undefined,
                message: undefined,
            },
        }

        moment.locale('ru');

        this.getCurrentUser = this.getCurrentUser.bind(this);

        this.initTable = this.initTable.bind(this);
        this.prepareTableData = this.prepareTableData.bind(this);

        this.initCreateMode = this.initCreateMode.bind(this);
    }

    swapPrivate() {
        this.state.report.confirm = !this.state.report.confirm;
        this.setState({});
    }

    getCurrentUser() {
        return this.props.currentUser
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

    reportIsValid() {
        let report = this.state.report;
        let message;

        if (report.hour > 24)
            message = 'Too much time for one day';
        else if (report.hour < report.workUnit)
            message = 'At work time must be less or equals whole time';
        else if (report.records.length === 0)
            message = 'Add some records to send the report';

        if (message !== undefined) {
            this.displayError(message);
            return false;
        }

        return true;
    }

    updateReport() {
        if (!this.reportIsValid()) {
            this.refreshRecords(this.state.token);
            return;
        }

        updateReport(this.state.token, this.state.report).then(r => {
            if (r.ok) {
                document.location.href = '/';
            }
        })
    }

    saveReport() {
        if (!this.reportIsValid()) {
            this.refreshRecords(this.state.token);
            return;
        }

        console.log('REPORT: saving', this.state.report);

        createReport(this.state.token, this.state.report).then(r => {
            if (r.ok) {
                this.displayToast('success', 'Success', 'Repord is saved');
                this.clear();
                this.refreshRecords(this.state.token);
            }
        });

        console.log('RECORD: saved', this.state.report);
    }

    edit(record) {
        console.log('REPORT: template picked', record);
        console.log(this.state.report.records);
///
        getRecordById(this.state.token, record.id).then(r => {
            this.setState({
                recordComponent: {
                    mode: 'edit',
                    record: r.json
                }
            })
        });
    }

    delete(record) {
        console.log('REPORT: deleting record', record);
        deleteReportRecord(this.state.token, record.id)
            .then(r => {
                console.log('delete r', r);
                if (r.ok) {
                    console.log('REPORT: record deleted');
                    this.refreshRecords(this.state.token);
                }
            });
    }

    clear() {

    }

    prepareTableData() {
        console.log('REPORT: updating table data');
        console.log('sssssssssssssssssss', this.state.report.records);
        this.state.table.data = this.state.report.records;

        this.state.table.data.map((row, index) => {
                console.log('data map', row);
                // row['factor'] = row['factor'].name;

                if (this.state.mode !== 'view') {
                    row['edit'] = (
                        <div>
                            <FontAwesomeIcon
                                icon={faEdit}
                                className="icon icon-edit"
                                onClick={this.edit.bind(this, row)}
                            />

                            <FontAwesomeIcon
                                icon={faTrashAlt}
                                className="icon icon-delete"
                                onClick={this.delete.bind(this, row)}
                            />
                        </div>
                    )
                }
                return true;
            }
        );


        console.log('REPORT: table data updated');
    }

    initTable(mode) {
        console.log('REPORT: initializing table');

        let columns = [
            {prop: "factor", title: "Factor", width: 120},
            {prop: "project", title: "Project", width: 150},
            {prop: "feature", title: "Feature", width: 150},
            {prop: "task", title: "Task", width: 150},
            {prop: "hour", title: "Hours", width: 75, searching: {available: false}},
            {prop: "workUnit", title: "At work", width: 75, searching: {available: false}},
            {prop: "location", title: "Location", width: 100},
        ];

        if (mode !== 'view') {
            columns.push(
                {prop: "edit", title: "", width: 55, searching: {available: false}, sortable: false}
            );
        }

        this.state.table.columns = columns;

        console.log('REPORT: table initialized');
    }

    initCreateMode(templateId, date) {
        console.log('REPORT: initializing CREATE mode');

        let report = this.state.report;

        if (templateId !== null) {
            //id

            //report = template;
            console.log('REPORT: with template', templateId);
        } else {
            report.date = moment();
            report.hour = 0;
            report.workUnit = 0;
            report.confirm = false;
        }

        if (date !== null) {
            console.log(date);
            report.date = new Date(date);
            console.log('REPORT: with date', report.date);
        }

        this.setState({report: report});

        this.initTable('create');

        this.state.recordComponent.mode = 'create';

        this.setState({initialized: true});

        console.log('REPORT: initialization finished');
        console.log();
    }

    getRefreshFunction(token) {
        if (this.state.mode === 'create') {
            return getNullReportRecords(token, this.props.currentUser)
        } else {
            return getRecordsByReportId(token, this.state.report.id);
        }
    }

    refreshRecords(token) {
        console.log('REPORT: rrrrrrrrrrrrrrrrrefreshing records');
        console.log(token);
        this.getRefreshFunction(token).then(r => {
            if (!r.ok) {
                //TODO add error toast
                return;
            }

            console.log('REPORT: RRRRRRRRRRRRRRRRRRrecieved', r);

            this.state.report.records = r.json;

            if (this.state.mode !== 'view') {
                console.log('REPORT: recounting total time');

                let hour = 0;
                let workUnit = 0;

                for (let i = 0; i < r.json.length; i++) {
                    hour += parseInt(r.json[i]["hour"]);
                    workUnit += parseInt(r.json[i]["workUnit"]);
                }

                this.state.report.hour = hour;
                this.state.report.workUnit = workUnit;
            }

            console.log('REPORT: refreshing finished');

            this.setState({});
        })
    }


    updateState(props) {
        this.state.token = props.token;
        this.refreshRecords(props.token)
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.updateState(nextProps);
    }

    componentDidMount() {
        const params = new URLSearchParams(location.search);

        this.state.mode = params.get('mode');

        this.state.token = this.props.token;

        if (this.state.mode === 'view') {
            //this.initViewMode();
        }

        if (this.state.mode === 'create') {
            let date = params.get('date');
            let templateId = params.get('templateId');
            this.initCreateMode(templateId, date);

        } else {
            this.state.report.id = params.get('id');
            this.setState({initialized: true});
        }

        if (this.state.mode === 'edit') {
            // this.initEditMode();
        }

        this.updateState(this.props);
    }

    render() {
        if (!this.state.initialized) {
            return <div>loading...</div>;
        }

        console.log('current report', this.state.report);

        this.prepareTableData();


        let createModeDisplay = (
            <div>{
                function () {

                }.bind(this).call()
            }

            </div>
        );

        return (
            <div className="report">
                {
                    function () {
                        if (this.state.mode === 'view') return viewModeDisplay;
                        if (this.state.mode === 'create') return createModeDisplay;
                        if (this.state.mode === 'edit') return editModeDisplay;
                    }.bind(this).call()
                }

                <div className="report-info">
                    <div className="reportItem">
                        <div className="reportItem-title">
                            Date
                        </div>
                        <div className="reportItem-datepicker">
                            <SingleDatePicker
                                date={moment(this.state.report.date)}
                                onDateChange={date => {
                                    this.state.report.date = date;
                                    this.setState({});
                                }
                                }
                                focused={this.state.focused}
                                onFocusChange={({focused}) => this.setState({focused})}
                                readOnly={true}
                                numberOfMonths={1}
                                hideKeyboardShortcutsPanel={true}
                                showClearDate={false}
                                isOutsideRange={() => false}
                            />
                        </div>
                    </div>
                    <div className="reportItem">
                        <div className="reportItem-title">
                            User
                        </div>
                        <div className="reportItem-dropdown">
                            {this.props.currentUser.name + ' ' + this.props.currentUser.surname}
                        </div>
                    </div>
                    <div className="reportItem">
                        <div className="reportItem-title">
                            Total time
                        </div>
                        <div className="reportItem-dropdown">
                            {this.state.report.hour}
                        </div>
                    </div>
                    <div className="reportItem">
                        <div className="reportItem-title">
                            At work
                        </div>
                        <div className="reportItem-dropdown">
                            {this.state.report.workUnit}
                        </div>
                    </div>
                </div>

                <RecordComponent
                    mode={this.state.recordComponent.mode}
                    record={this.state.recordComponent.record}
                    callback={
                        (() => {
                            this.refreshRecords(this.props.token);

                            //this.state.recordComponent.mode = 'view';
                            this.state.recordComponent.mode = 'create';
                            this.setState({})
                        }).bind(this)
                    }
                    token={this.state.token}
                />

                <TableComponent
                    caption="Records"
                    columns={this.state.table.columns}
                    data={this.state.table.data}
                />

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

                <div className="confirmBlock">
                    <div className="button-back">
                        <Link to='/reports'><Button>Back to reports</Button></Link>
                    </div>

                    <div className="checkBox">
                        <Checkbox
                            color="primary"
                            checked={this.state.report.confirm}
                            onChange={this.swapPrivate.bind(this)}
                        />
                        Confirm
                    </div>

                    <div className="button-send">
                        <Button onClick={this.saveReport.bind(this)}>
                            Save Report
                        </Button>
                    </div>
                </div>
            </div>
        );
    }
}

export default ReportComponent;
