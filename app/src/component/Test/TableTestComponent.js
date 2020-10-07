import React, {Component} from 'react';
import TableComponent from "../Table";

class TableTestComponent extends Component {
    constructor() {
        super();

        this.state = {
            buttonType : "primary"
        }

        this.flipButtonType = this.flipButtonType.bind(this);
    }

    flipButtonType() {
        this.setState({
            buttonType : (this.state.buttonType === "primary") ? "success" : "primary",
        })
    }

    render () {
        return (
            <TableComponent
                caption="Some caption"
                columns={[
                    {
                        prop : "p1",
                        title : "Title1",
                        visible : true,
                        align : "right"},
                    {
                        prop: "p2",
                        title: "Title2",
                        visible: true,
                        width: 100,
                        searching: {available: false}
                    },

                    {
                        prop: "p3",
                        title: "Title3",
                        visible: true,
                        width: 200,
                        sortable: false,
                        searching: {useRegister : true}
                    },
                ]}
                data={[
                    {p1 : "1", p2 : "a", p3 : "-12.4"},
                    {
                        p1 : "2",
                        p2 :
                            <button onClick = {this.flipButtonType}>
                                button
                            </button>,
                        p3 : "44.2"
                    },
                    {p1 : "2", p2 : "c", p3 : "78"},
                    {p1 : "1", p2 : "ab", p3 : "qwerty"},
                    {p1 : "12", p3 : "QWERTY"},
                    {p1 : "6", p3 : "    "},
                    {p1 : "8",  p3 : "QWERTY"},
                    {p1 : "0",},
                    {p1 : "1", p2 : "ab", p3 : "qwerty"},
                    {p1 : "13",  p3 : ","},
                    {p1 : "23", p3 : "<>"},
                ]}
                width={610}
            />
        );
    }
}

export default TableTestComponent;