export const FETCH_PRODUCTS_REQUEST = 'FETCH_PRODUCTS_REQUEST';
export const FETCH_PRODUCTS_SUCCESS = 'FETCH_PRODUCTS_SUCCESS';
export const FETCH_PRODUCTS_FAILURE = 'FETCH_PRODUCTS_FAILURE';

export const fetchProductsBegin = () => ({
    type: 'FETCH_PRODUCTS_REQUEST'
});

export const fetchProductsSuccess = users => ({
    type: 'FETCH_PRODUCTS_SUCCESS',
    payload: users,
});

export const fetchProductsFailure = error => ({
    type: 'FETCH_PRODUCTS_FAILURE',
    payload: error,
});

export function fetchProducts(users) {
    return dispatch => {
        dispatch(fetchProductsBegin());
        return fetch("http://localhost:8080/users",
            {
                headers: {
                    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnLW1hbkB2YWx2ZS5uZXQiLCJyb2xlcyI6WyJBRE1JTiJdLCJpYXQiOjE1ODM0MzQ5NDYsImV4cCI6MTU4MzQ3MDk0Nn0.tjLEt8fQkud2IoYjzOj5Xjyx8v6ZBtItDtSV3G8u1t8'
                }
            }
        )

            .then(handleErrors)
            .then(res => res.json())
            .then(users => {
                dispatch(fetchProductsSuccess(users));
                return users;
            })
            .catch(error => dispatch(fetchProductsFailure(error)));
    };
}

function handleErrors(response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}