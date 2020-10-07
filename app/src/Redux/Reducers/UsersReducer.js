import {
    FETCH_PRODUCTS_REQUEST,
    FETCH_PRODUCTS_SUCCESS,
    FETCH_PRODUCTS_FAILURE,
} from '../Actions/UsersActions';

const initialState = {
    items: [],
    loading: false,
    error: null
};

export function userReducer(state = initialState, action) {
    switch (action.type) {
        case 'FETCH_PRODUCTS_REQUEST':
            return {
                ...state,
                loading: true,
                error: null
            };

        case 'FETCH_PRODUCTS_SUCCESS':
            return {
                ...state,
                loading: false,
                items: action.payload.users
            };

        case 'FETCH_PRODUCTS_FAILURE':
            return {
                ...state,
                loading: false,
                error: action.payload.error,
                items: []
            };

        default:
            return state;
    }
}