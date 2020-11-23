import React, {Component} from "react";
import HeaderContainer from "./Header/HeaderContainer";
import {Route} from "react-router-dom";
import DatePickerComponent from "./DatePicker/DatePickerComponent";
import TableTestComponent from "./Test/TableTestComponent";
import FooterComponent from "./Footer/FooterComponent";
import DateFilterContainer from "./DateFilter/DateFilterContainer";
import ReportsContainer from "./Reports/ReportsContainer";
import ReportContainer from "./Report2/ReportContainer";
import CreateReportPageContainer from "./Report/CreateReportPageContainer";

class MainRoutes extends Component {
    render() {
        return (
            <div className='MainBox'>
                <HeaderContainer className='header'/>
                <div className="body">
                    <Route
                        exact
                        path="/"
                        title="Главная"
                        component={ReportsContainer}
                    />
                    <Route
                        path="/table"
                        title="Таблица"
                        component={TableTestComponent}
                    />
                    <Route
                        path="/filter"
                        title="Фильтр"
                        component={DateFilterContainer}
                    />
                    <Route
                        path="/reports"
                        title="Отчеты"
                        component={ReportsContainer}
                    />
                    <Route
                        path="/oldreport"
                        title="Отчет"
                        component={CreateReportPageContainer}
                    />
                    <Route
                        path="/report"
                        title="Отчет"
                        component={ReportContainer}
                    />
                </div>

                <FooterComponent className='footer'/>
            </div>
        );
    }
}

export default MainRoutes;
