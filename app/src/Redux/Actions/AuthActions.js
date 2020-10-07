export const LOGIN_USER='LOGIN_USER';
export const LOGOUT_USER='LOGOUT_USER';
export const USER_UNDEFINED='USER_UNDEFINED';

const loginUser = (user, token) => ({
    type: 'LOGIN_USER',
    currentUser: user,
    token: token
});

const logoutUser = () => ({
    type: 'LOGOUT_USER',
});

const serverError = () => ({
    type: 'SERVER_ERROR',
});

const failedUser = (error) => ({
    type: 'USER_UNDEFINED',
    error: error,
});



export {logoutUser, loginUser, failedUser, serverError}