import {combineReducers} from 'redux'
import {userReducer} from '../Reducers/UsersReducer';
import {testReducer} from "./TestReducer";
import {auth} from "./AuthReducer";

export const rootReducer = combineReducers({
    user: userReducer,
    page: testReducer,
    auth: auth,
});