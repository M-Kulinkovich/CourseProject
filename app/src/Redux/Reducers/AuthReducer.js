import {LOGIN_USER, LOGOUT_USER, USER_UNDEFINED} from "../Actions/AuthActions";

const initialState = {
    currentUser: {},
    loggedIn: undefined,
    token: undefined,
    error: '',
    serverError: false
};

export function auth(state = initialState, action) {
    switch (action.type) {
        case 'LOGIN_USER':
            localStorage.setItem('token', action.token);
            return {
                ...state,
                currentUser: action.currentUser,
                loggedIn: true,
                token: action.token,
                error: false,
                serverError: false
            };
        case 'LOGOUT_USER':
            localStorage.removeItem('token');
            return {...state, currentUser: {}, error: false, loggedIn: false, serverError: false};
        case 'USER_UNDEFINED':
            return {...state, error: true, loggedIn: false, serverError: false};
        case 'SERVER_ERROR':
            return {...state, error: false, serverError: true};
        default:
            return state;
    }
}