export const SET_YEAR = 'SET_YEAR';
export const FETCH_USERS_EXAMPLE = "FETCH_USERS_EXAMPLE";

export function setYear(year) {
    return {
        type: 'SET_YEAR',
        payload: year,
    }
}

export async function getFreeData() {
    try {
        return async (dispatch) => {
            let res = await fetch(`https://jsonplaceholder.typicode.com/users`);
            let userList = await res.json();
            dispatch({
                type: "FETCH_USERS_EXAMPLE",
                payload: userList
            });
            return userList;
        }
    } catch (e) {
        console.log("Error", e);
    }
}
