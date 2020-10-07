import TestComponent from "./TestComponent";
import React, {Fragment, useState} from "react";
import "../../style/app.scss";

function TestValue() {
    const [name, setState] = useState("TEST");
    return (
        <Fragment>
            <div className='boxColor'>{name}</div>
            <TestComponent txt="Random value: "/>
        </Fragment>
    );
}

export default TestValue;