import React, {Component} from 'react';
import PropTypes from 'prop-types';
import "../../style/Table.scss";
import {Dropdown, DropdownButton} from "react-bootstrap";

import "../../style/Reports.scss";

class Filter extends Component {
    constructor() {
        super();

        this.state = {
            filterTitle: "",
            items: [],
            field: "",

            dropdownTitle: "Select",
        }

        this.callback = () => {};
        this.onSelect = this.onSelect.bind(this);
    }

    update(props) {
        this.setState({
            filterTitle: props.filterTitle,
            items: props.items,
            field: props.useField,
        })

        if(props.onSelect !== undefined) {
            this.callback = props.onSelect
        }
    }

    componentDidMount() {
        this.update(this.props);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.update(nextProps);
    }

    onSelect = index => {
        if(index === -1) {
            this.callback(undefined);
        }

        let item = this.state.items[index];
        this.callback(item);
    }

    render () {
        let filterTitle = this.state.filterTitle;
        let dropdownTitle = (this.props.dropdownTitle !== undefined) ? this.props.dropdownTitle : "Select";
        let items = this.state.items;
        let field = this.state.field;

        return (
            <div className="filterItem">
                <div className="filterItem-title">
                    {filterTitle}
                </div>

                <div className="filterItem-dropdown">
                    <DropdownButton onSelect={this.onSelect} title={dropdownTitle}>
                        {items.map((item, index) => {
                            let caption = item[field];

                            return (
                                <Dropdown.Item eventKey={index} key={index}>
                                    {caption}
                                </Dropdown.Item>
                            );
                        })}
                        <hr></hr>
                        <Dropdown.Item eventKey={-1}> Reset </Dropdown.Item>
                    </DropdownButton>
                </div>
            </div>
        );
    }
};

Filter.propTypes = {
    filterTitle: PropTypes.string,
    dropdownTitle: PropTypes.string,
    items: PropTypes.array.isRequired,
    useField: PropTypes.string,
    onSelect: PropTypes.func,
}

Filter.defaultProps = {
    filterTitle: "Filter",
    useField: "name",
}

export default Filter;