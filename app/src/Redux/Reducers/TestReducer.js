import {SET_YEAR, FETCH_USERS_EXAMPLE} from '../Actions/TestAction';

export function testReducer(state = {year: ''}, action) {
    switch (action.type) {
        case 'SET_YEAR':
            return {...state, year: action.payload};
        case 'FETCH_USERS_EXAMPLE':
            console.log('FETCH_USERS_EXAMPLE', action.payload);
            return {...state, userList: action.payload};
        default:
            return state
    }
}