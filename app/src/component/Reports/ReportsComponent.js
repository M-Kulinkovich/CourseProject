import React, {Component} from 'react';
import TableComponent from "../Table";
import FilterBlock from "./FilterBlock";
import "../../style/Reports.scss";
import {fetchFeatures, fetchProjects, fetchReports, fetchTasks} from "./ReportsService";
import {Button} from 'react-bootstrap';
import {Link} from 'react-router-dom';
import {faEdit} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

class ReportsComponent extends Component {
    constructor() {
        super();

        this.state = {
            filter: {},
            reports: [],
            availableProjects: undefined,
            availableFeatures: undefined,
            availableTasks: undefined
        };

        this.receiveFilter = this.receiveFilter.bind(this);
        this.prepareTableData = this.prepareTableData.bind(this);
        this.token = undefined
    }

    prepareTableData(reports) {
      let data = reports;

      data.map((row, index) => {
          let editCell = <div></div>

          if(row['confirm'] === 'false')
                  row['edit'] = (
                      <div>
                          <FontAwesomeIcon
                              icon={faEdit}
                              className="icon icon-edit"
                              onClick={ () => {
                                document.location.href = "report?mode=edit&id=" + row.id
                              }}
                          />
                      </div>
                  )
              return true;
          }
      );

      return data;
    }

    refreshData(token, filter) {
        let a = [];
        a.push(fetchReports(token, filter));
        a.push(fetchProjects(token));
        a.push(filter.project !== undefined ? fetchFeatures(token, filter.project['id']) : Promise.resolve({ok: true}));
        a.push(filter.project !== undefined && filter.feature !== undefined ? fetchTasks(token, filter.feature['id']) : Promise.resolve({ok: true}));
        Promise.all(a).then(a => {
                if (!a[0].ok || !a[1].ok || !a[2].ok || !a[3].ok) {
                    //TODO add error toast
                } else {
                    this.setState({
                        reports: a[0].json,
                        availableProjects: a[1].json,
                        availableFeatures: a[2].json,
                        availableTasks: a[3].json
                    })
                }
            }
        );
    }

    componentDidMount() {
        this.token = this.props.token;
        this.refreshData(this.token, this.state.filter);
    }

    receiveFilter(filter) {
        this.setState({
            filter: filter
        });

        this.refreshData(this.token, filter);
    }

    render() {
        return (
            <div className="page">
                <div className="table">
                    <TableComponent
                        caption="Reports"

                        columns={[
                            {
                                prop: "date",
                                title: "Date",
                                width: 90,
                                searching: {available: false}
                            },
                            {
                                prop: "user",
                                title: "User",
                                width: 200,
                            },
                            {
                                prop: "hour",
                                title: "Time",
                                width: 80,
                                searching: {available: false}
                            },
                            {
                                prop: "workUnit",
                                title: "At work",
                                width: 80,
                                searching: {available: false}
                            },
                            {
                                prop: "confirm",
                                title: "confirm",
                                width: 80,
                                searching: {available: false}
                            },
                            {
                                prop: "edit",
                                title: "",
                                width: 40,
                                searching: {available: false},
                                clickable: false
                            }
                        ]}

                        data={this.prepareTableData(this.state.reports)}

                        onClickRow={(row) => {
                            document.location.href = "https://i.ytimg.com/vi/LSLUU6-KQio/maxresdefault.jpg";
                        }}

                        defaultColWidth={300}
                    />

                    <div className="newReportButton">
                         <Link to='/report?mode=create'><Button>New report</Button></Link>
                    </div>
                </div>

                <div className="filterBlock">
                    <FilterBlock
                        projects={this.state.availableProjects}
                        features={this.state.availableFeatures}
                        tasks={this.state.availableTasks}

                        callback={this.receiveFilter}
                    />
                </div>


            </div>
        )
            ;
    }
}

export default ReportsComponent;
