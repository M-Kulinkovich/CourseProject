import {BASE_URL} from "../../util/CommonFetch";

function testAdminToken() {
    return fetch(BASE_URL + "login", {
        method: "POST",
        body: JSON.stringify(
            {
                email: "g-man@valve.net",
                password: "adminSecret"
            }
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(r => r.json()).then(r => Promise.resolve(r['token']));
}

function testUserToken() {
    return fetch(BASE_URL + "login", {
        method: "POST",
        body: JSON.stringify(
            {
                email: "scorpion@mk.net",
                password: "secret"
            }
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(r => r.json()).then(r => Promise.resolve(r['token']));
}

export {testAdminToken, testUserToken}