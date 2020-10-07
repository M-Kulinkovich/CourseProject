import {Comparison, toBase64} from "../../util/base64Converter";
import {authFetch, BASE_URL} from "../../util/CommonFetch";

const REPORT_URL = BASE_URL + "report";
const USER_URL = BASE_URL + "users/";

// function authFetch(url, token) {
//     return fetch(url, {
//         headers: {
//             'Authorization': 'Bearer ' + token
//         }
//     }).then(r => r.json())
//
// }

function fetchUser(userId, token) {
    return authFetch(USER_URL + userId, token)
}

function getName(user) {
    return user['name'] + ' ' + user['surname'];
}

function getFetchReports(token, url = REPORT_URL) {
    return authFetch(url, token).then(r => {
        if (!r.ok) {
            return Promise.all([Promise.resolve(r)]);
        }
        let fetches = [Promise.resolve(r)]
            .concat(Array.from(new Set(r.json.map(r => r['userId'])))
                .map(r => fetchUser(r, token)));
        return Promise.all(fetches);
    }).then(arr => {
        if (!arr[0].ok) {
            return arr[0];
        }
        let reports = arr[0].json;
        let users = {};
        arr.slice(1, arr.length).forEach(e => {
            users[e.json['id']] = getName(e.json)
        });
        console.log('users', arr);
        return Promise.resolve({
            ok: true,
            json: reports.map(e => {
                e['user'] = users[e['userId']];
                e['confirm'] = e['confirm'] ? 'true' : 'false';
                e.hour /= 60;
                e.workUnit /= 60;
                return e;
            })
        });
    })
}


const FILTERS = {
    fromDate: (r => toBase64("date", Comparison.GTE, r.toISOString().substring(0, 10))),
    toDate: (r => toBase64("date", Comparison.LTE, r.toISOString().substring(0, 10))),

    privateOnly: (r => r ? toBase64("confirm", Comparison.EQ, 'false') : undefined),

    project: (r => toBase64("projectId", Comparison.EQ, r['id'])),
    feature: (r => toBase64("featureId", Comparison.EQ, r['id'])),
    task: (r => toBase64("taskId", Comparison.EQ, r['id'])),
};

function fetchReports(token, filter = undefined) {
    if (filter === undefined) {
        return getFetchReports(token);
    }
    let q = '';
    for (let i in filter) {
        if (filter[i] === undefined) continue;
        let f = FILTERS[i](filter[i]);
        if (f === undefined) continue;
        q += (q === '' ? '?' : '&');
        q += 'filter=' + f;
    }
    return getFetchReports(token, REPORT_URL + q);
}

function fetchUsers(token) {
    return authFetch(BASE_URL + "users", token);
}

function fetchProjects(token) {
    return authFetch(BASE_URL + "project", token);
}

function fetchFeatures(token, projectId = undefined) {
    let f = '';
    if (projectId !== undefined) {
        f = '?filter=' + toBase64('projectId', Comparison.EQ, projectId);
    }
    return authFetch(BASE_URL + "feature" + f, token)
}

function fetchTasks(token, featureId = undefined) {
    let f = '';
    if (featureId !== undefined) {
        f = '?filter=' + toBase64('featureId', Comparison.EQ, featureId);
    }
    return authFetch(BASE_URL + "task" + f, token);
}

export {fetchReports, fetchUser, fetchProjects, fetchFeatures, fetchTasks}