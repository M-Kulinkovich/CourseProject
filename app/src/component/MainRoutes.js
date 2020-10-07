import React, {Component} from "react";
import HeaderContainer from "./Header/HeaderContainer";
import {Route} from "react-router-dom";
import TestContainer from "./Test/TestContainer";
import DropdownComponent from "./DropDown/DropdownComponent";
import CheckBox from "./CheckBox/CheckBoxComponent";
import DatePickerComponent from "./DatePicker/DatePickerComponent";
import ToastTestComponent from "./Test/ToastTestComponent";
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
                        path="/dropDown"
                        title="Выпадающее меню"
                        component={DropdownComponent}
                    />
                    <Route
                        path="/checkBox"
                        title="чекбокс"
                        component={CheckBox}
                    />
                    <Route
                        path="/datePicker"
                        title="Календарь"
                        component={DatePickerComponent}
                    />
                    <Route
                        path="/toast"
                        title="Тосты"
                        component={ToastTestComponent}
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
                        exact
                        path="/components"
                        title="Главная"
                        component={TestContainer}
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
