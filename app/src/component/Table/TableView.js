import React, {Component} from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import "../../style/Table.scss"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faArrowDown, faArrowUp, faInfoCircle} from '@fortawesome/free-solid-svg-icons'

class TableView extends Component {
    constructor() {
        super();

        this.state = {
            data : [],
            sortType : "desc",
            lastOrdered : "",
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        let newData = nextProps.data;

        if(this.state.lastOrdered !== "") {
            newData =  _.orderBy(newData, this.state.lastOrdered, this.state.sortType)
        }

        this.setState({data : newData});
    }

    onSortDefault(field) {
        const copy = this.state.data.concat();
        const type = (this.state.lastOrdered === field && this.state.sortType === 'asc') ? 'desc' : 'asc';

        this.setState({
            data: _.orderBy(copy, field, type),
            sortType : type,
            lastOrdered : field,
        })
    }

    render () {
        let columns = this.props.columns;
        let data = this.state.data;
        let defaultColWidth = this.props.defaultColWidth;

        let head = (
            <div className="table-row-head">
                {columns.map((col, index) => {
                    if(col.visible !== false) {
                        let width = (col.width !== undefined) ? col.width : defaultColWidth;

                        let onSort = () => {};
                        let iconType;
                        if(col.sortable != false) {
                            onSort = (col.onSort !== undefined) ? col.onSort : this.onSortDefault;

                            if(this.state.lastOrdered === col.prop) {
                                iconType = (this.state.sortType === 'asc') ? faArrowUp : faArrowDown;
                            }
                        }

                        return (
                            <div className="table-cell-head"
                                 style ={{width : width}}
                                 key={index}
                                 onClick={onSort.bind(this, col.prop)}
                            >
                                {(iconType !== undefined)
                                    ? <FontAwesomeIcon className="icon icon-arrow" icon={iconType}/>
                                    : ''
                                }
                                {col.title}
                            </div>
                        )
                    }
                })}
            </div>
        );

        let body = data.map( (row, index) => (
            <div className="table-row" key={index}>
                {columns.map( (col, index) => {
                    if(col.visible !== false) {
                        let clickFunc = (col.clickable !== false) ? this.props.onClickRow.bind(this, row) : () => {};
                        let align = (["left", "center", "right"].includes(col.align)) ? col.align : "center";
                        let width = (col.width !== undefined) ? col.width : defaultColWidth;

                        return (
                            <div className="table-cell"
                                 style={{
                                     width: width,
                                     textAlign: `${align}`
                                 }}
                                 key={index}
                                 onClick={clickFunc}
                            >
                                {row[col.prop]}
                            </div>
                        );
                    }
                })}
            </div>
        ));

        let emptyBody = (
            <div className="table-row-empty" >
                <FontAwesomeIcon
                    className="icon icon-info"
                    icon={faInfoCircle}
                />
                <div>
                    No information found
                </div>
            </div>
        );

        return (
            <div className="table-frame">
                {head}
                {data.length != 0 ? body : emptyBody}
            </div>
        );
    }
};

TableView.propTypes = {
    columns: PropTypes.array,
    data: PropTypes.array,
    defaultColWidth: PropTypes.number,
};

export default TableView;
