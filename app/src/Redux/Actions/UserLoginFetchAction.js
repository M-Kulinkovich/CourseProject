import {authFetch, BASE_URL, commonFetch} from "../../util/CommonFetch";
import {failedUser, loginUser, serverError} from "./AuthActions";

export const userLoginFetch = (state) => {
    return dispatch => {
        commonFetch(BASE_URL + 'login', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
            body: JSON.stringify({
                email: state.email,
                password: state.password
            })
        }).then(resp => {
            if (resp.ok) {
                return Promise.all([
                    Promise.resolve(resp),
                    authFetch(BASE_URL + 'users/current', resp.json.token)
                ]);
            } else {
                return (
                    Promise.resolve([resp, undefined])
                )
            }
        }).then(arr => {
            if (arr[0].ok && arr[1]?.ok) {
                dispatch(loginUser(arr[1].json, arr[0].json.token))
            } else if (arr[0].status !== 700) {
                dispatch(failedUser())
            }
            else {
                dispatch(serverError())
            }
        })
    }

};

