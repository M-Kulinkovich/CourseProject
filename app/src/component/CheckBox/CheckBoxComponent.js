import React from "react";
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormGroup from '@material-ui/core/FormGroup';
import '../../style/checkBox.scss';

export default function CheckBox() {
    return (
        <FormGroup className='CheckBox'>
            <FormControlLabel
                control={<Checkbox color="primary"/>}
                label="right"
                labelPlacement="start"
            />
            <FormControlLabel
                control={<Checkbox color="primary"/>}
            />
            <FormControlLabel
                control={<Checkbox color="primary"/>}
                label="left"
                labelPlacement="end"
            />
        </FormGroup>
    );
}