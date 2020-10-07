import React, {Component} from 'react';
import TableComponent from "../Table";
import "../../style/SelectBlock.scss";
import SelectBlock from "../Report/SelectBlock";
import "../../style/Table.scss";
import {testAdminToken, testUserToken} from "../Test/TestToken";
import {createReport, deleteReportRecord, getNullReportRecords} from "./ReportServices";
import ToastComponent from "../Toast";
import {SingleDatePicker} from 'react-dates';
import moment from 'moment';
import {Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import Checkbox from "@material-ui/core/Checkbox";
import {faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {DropdownButton, Dropdown} from 'react-bootstrap';

class CreateReportPage extends Component {
    constructor() {
        super();

        moment.locale('ru');

        this.state = {
            error: false,
            errorTitle: undefined,
            errorMessage: undefined,

            token: undefined,

            success: false,

            report: {
                date: moment(),
                user: undefined,
                hour: 0,
                workUnit: 0,
                confirm: false,

                records: []
            }
        };

        this.swapPrivate = this.swapPrivate.bind(this);

        this.save = this.save.bind(this);

        this.addReport = this.addReport.bind(this);
        this.addRecord = this.addRecord.bind(this);

        this.addDelete = this.addDelete.bind(this);
        this.onDelete = this.onDelete.bind(this);
    }

    swapPrivate() {
        this.state.report.confirm = !this.state.report.confirm;
        this.setState({});
    }

    selectDate(date) {
        this.state.report.date = date;
        this.setState();
    }

    onDelete(row) {
      console.log("recievedIdToDelete", row);
      this.deleteRecord(this.state.token, row);
    }

    addDelete(source) {
      let data = source.concat();

      data.map( (row, index) => {
          row["delete"] = (


            <FontAwesomeIcon
              className="icon icon-delete"
              icon={faTrashAlt}
              onClick={this.onDelete.bind(this, row)}
            />
            //<Button onClick =>
              //delete
            //</Button>
          );

          return true;
      });

      return data;
    }

    updateState(props) {
        this.setState({token: props.token});
        this.refreshRecords(props.token);
        // testUserToken().then(token => {
        //     this.setState({token: token});
        //     this.refreshRecords(token);
        // });

        this.setState({});
    }

    componentDidMount() {
        this.updateState(this.props);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.updateState(nextProps);
    }

    refreshRecords(token) {
        console.log('refresh records');


        getNullReportRecords(token, this.props.currentUser).then(r => {
            if (!r.ok) {
                //TODO add error toast
                return;
            }
            console.log('r=', r);
            this.state.report.records = r.json;

            if (this.state.report !== undefined) {
                let records = this.state.report.records;

                console.log("sss");
                console.log(records);

                let hour = 0;
                let workUnit = 0;

                for (let i = 0; i < records.length; i++) {
                    hour += parseInt(records[i]["hour"]);
                    workUnit += parseInt(records[i]["workUnit"]);
                }

                this.state.report.hour = hour;
                this.state.report.workUnit = workUnit;
            }

            if (this.props.user !== undefined) {
                this.state.report.user = this.props.user;
            }

            if (this.props.callback !== undefined) {
                this.callback = this.props.callback;
            }

            this.setState({});
        })
    }

    addRecord() {
        console.log('addRecord', this);
        this.refreshRecords(this.state.token)
    }

    showReportError(msg) {
        this.setState({
            error: true,
            errorTitle: "Report error",
            errorMessage: msg
        });
    }

    deleteRecord(token, record) {
        console.log(token);
        deleteReportRecord(token, record.id)
            .then(r => {
                console.log('r', r);
                if (r.ok) {
                    this.refreshRecords(token)
                }
            })
    }

    save() {
      this.addReport();
    }

    saveReport(token, report) {
        createReport(token, report)
            .then(r => {
                console.log('resp', r);
                if (r.ok) {
                    this.refreshRecords(token);
                    this.setState({success: true});
                }
            })
    }

    addReport() {
        if (this.state.report.hour > 24) {
            this.showReportError("Too much time for one day");
        } else if (this.state.report.hour < this.state.report.workUnit) {
            this.showReportError("At work time must be less or equals whole time");
        } else if (this.state.report.records.length === 0) {
            this.showReportError("Add some records to send the report");
        } else {
            this.saveReport(this.state.token, this.state.report);
        }
    }

    render() {
        console.log("tosend");
        console.log(this.state.report.records);
        console.log("all");
        console.log(this.state.report);
        return (
            <div className="page">
                <div className="report">
                  <div className="report-info">
                    <div className="reportItem">
                        <div className="reportItem-title">
                          Date
                        </div>
                        <div className="reportItem-datepicker">
                          <SingleDatePicker
                              date={this.state.report.date}
                              onDateChange={date => {
                                  this.selectDate(date);
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
                            {this.state.report.user}
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

                    <div className="report-records">
                        <div className="table">
                            <TableComponent
                                caption="Records"

                            columns={[
                                {
                                    prop: "project",
                                    title: "Project",
                                    width: 150,
                                },
                                {
                                    prop: "feature",
                                    title: "Feature",
                                    width: 150,
                                },
                                {
                                    prop: "task",
                                    title: "Task",
                                    width: 150,
                                },
                                {
                                    prop: "factor",
                                    title: "Factor",
                                    width: 120,
                                },
                                {
                                    prop: "hour",
                                    title: "Hours",
                                    width: 90,
                                    searching: {available: false}
                                },
                                {
                                    prop: "workUnit",
                                    title: "Work unit",
                                    width: 90,
                                    searching: {available: false}
                                },
                                {
                                    prop: "location",
                                    title: "Location",
                                    width: 100,
                                },
                                {
                                    prop: "delete",
                                    title: "",
                                    width: 40,
                                    searching: {available: false},
                                    sortable: false
                                }
                            ]}

                            data={this.addDelete(this.state.report.records)}

                                defaultColWidth={300}
                            />
                        </div>

                        <div className="selectBlock">
                            <SelectBlock
                                callback={this.addRecord}
                                token={this.state.token}
                            />
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
                            txt="Report has been sent"
                            delay={2000}
                            closeAction={() => {
                                this.setState({success: false})
                            }}
                        />
                    </div>
                </div>

                <div className="savingBlock">
                  <div className="buttonBlock">
                      <div className="buttonBlock-checkBox">
                        <Checkbox
                            color="primary"
                            checked={this.state.report.confirm}
                            onChange={this.swapPrivate}
                        />
                        <div className="text line">Confirm</div>
                      </div>
                      <div className="buttonBlock-button">
                        <Button onClick={this.save}>
                          Save
                        </Button>
                      </div>
                      <div className="buttonBlock-back">
                        <Link to='/'><Button>Back to reports</Button></Link>
                      </div>
                  </div>
                </div>
            </div>
        );
    }
}


export default CreateReportPage;
