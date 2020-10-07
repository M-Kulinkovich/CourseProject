import React, {Component} from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import "../../style/Table.scss";
import TableView from './TableView';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch } from '@fortawesome/free-solid-svg-icons'

class TableComponent extends Component {
    constructor() {
        super();

        this.state = {
            data : [],
            entered : false,
            input : "",
            prevInput : "",
            filtered : false,
            filteredData : [],
        }

        this.onInput = this.onInput.bind(this);
        this.onFilterDefault = this.onFilterDefault.bind(this);
        this.filterIfNeed = this.filterIfNeed.bind(this);
    }

    componentDidMount() {
        this.setState({data : this.props.data});
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            data : nextProps.data,
            entered : false,
            input : "",
            prevInput : "",
            filtered : false,
            filteredData : [],
        });
    }

    onInput() {
        setTimeout(() => {
            let value = document.getElementById("input").value;
            this.setState({
                entered : true,
                input : value,
            });
        }, 1000);
    }

    setupSearching(base) {
        let searchingDefault = {
            available : true,
            useRegister : false,
            suitsOn : "startsWith"
        }

        if(base === undefined) {
            return searchingDefault;
        }

        let searching = base;

        if(searching.available === undefined) searching.available = searchingDefault.available;
        if(searching.useRegister === undefined) searching.useRegister = searchingDefault.useRegister;
        if(searching.suitsOn === undefined) searching.suitsOn = searchingDefault.suitsOn;

        return searching;
    }

    onFilterDefault(row) {
        let columns = this.props.columns;

        for(let i = 0; i < columns.length; i++) {
            let searching = this.setupSearching(columns[i].searching);

            if(searching.available && (columns[i].visible !== false)) {
                let value = row[columns[i].prop];

                if(value === undefined) {
                    continue;
                }

                let input = this.state.input;

                if(!searching.useRegister) {
                    input = input.toLowerCase();
                    value = value.toLowerCase();
                }

                let index =  value.indexOf(input);

                if( (searching.suitsOn === "startsWith" && index == 0) ||
                    (searching.suitsOn === "particular" && index != -1) ) {
                    return true;
                }
            }
        }
    }

    filterIfNeed() {
        let input = this.state.input;

        if(this.state.entered) {
            if(input === "") {
                this.setState({
                    entered : false,
                    filteredData : [],
                })
            }

            let data = this.state.data;
            if(this.state.prevInput !== "") {
                data = input.startsWith(this.state.prevInput) ? this.state.filteredData : this.state.data;
            }

            let onFilter = (this.props.onFilter !== undefined) ? this.props.onFilter.bind(this) : this.onFilterDefault;

            this.setState({
                entered : false,
                prevInput : input,
                filtered : true,
                filteredData : _.filter(data, onFilter),
            })
        }
    }

    render () {
        this.filterIfNeed();

        let onClickRow = (this.props.onClickRow !== undefined) ? this.props.onClickRow : () => {};

        return (
            <div className="component-table">
                <div className="search">
                    <FontAwesomeIcon
                        className="icon icon-search"
                        icon={faSearch}
                    />
                    <input
                        type="text"
                        id="input"
                        onChange={this.onInput}
                        maxLength="50"
                        size="17">
                    </input>
                </div>

                <div className="caption">
                    {this.props.caption}
                </div>

                <TableView
                    columns={this.props.columns}
                    data={this.state.filtered ? this.state.filteredData : this.state.data}
                    defaultColWidth={this.props.defaultColWidth}
                    onClickRow={onClickRow}
                />
            </div>
        );
    }
};

TableComponent.propTypes = {
    caption : PropTypes.string,

    columns: PropTypes.arrayOf(PropTypes.shape({
        prop: PropTypes.string,
        title : PropTypes.string,
        visible: PropTypes.bool,
        width : PropTypes.number,
        align : PropTypes.string,

        searching : PropTypes.shape({
            available : PropTypes.bool,
            useRegister : PropTypes.bool,
            suitsOn: PropTypes.oneOf["particular", "startsWith"],
        }),

        sortable : PropTypes.bool,
        onSort : PropTypes.func,
    })).isRequired,

    data : PropTypes.arrayOf(PropTypes.object).isRequired,

    onFilter : PropTypes.func,
    onClickRow: PropTypes.func,
    defaultColWidth : PropTypes.number,
};

TableComponent.defaultProps = {
    caption : "",
    defaultColWidth: 150,
};

export default TableComponent;