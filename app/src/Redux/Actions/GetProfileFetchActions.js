import {authFetch, BASE_URL} from "../../util/CommonFetch";
import {loginUser, logoutUser, serverError} from "./AuthActions";

export const getProfileFetch = () => {
    return dispatch => {
        const token = localStorage.token;
        authFetch(BASE_URL + 'users/current/', token)
            .then(resp => {
                if (resp.ok) {
                    dispatch(loginUser(resp.json, token))
                } else {
                    if (resp.status === 700) {
                        dispatch(serverError())
                    } else {
                        dispatch(logoutUser())
                    }
                }
            });
    }
};
