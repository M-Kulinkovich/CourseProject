import React from "react";
import TextField from "@material-ui/core/TextField";

export default function FieldExample() {

    return (
        <div>
            <div>
                <TextField type="text" defaultValue="default"/>
            </div>
            <div>
                <TextField type="password" label="Required"/>
            </div>
            <div>
                <TextField type="number" step="any"/>
            </div>
        </div>
    );


}