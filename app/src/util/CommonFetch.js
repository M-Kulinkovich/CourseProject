const BASE_URL = 'http://127.0.0.1:8080/';

function commonFetch(url, options) {
    return fetch(url, options).then(response => {
        const contentType = response.headers.get('content-type');
        if (!contentType?.includes('application/json')) {
            return Promise.all([Promise.resolve(response), Promise.resolve(undefined)])
        }
        return Promise.all([Promise.resolve(response), response.json()]);
    }).catch(() => Promise.resolve([{'status': 700, 'ok': false, 'url': url}, undefined]))
        .then(arr => {
            let d = {};
            d['status'] = arr[0].status;
            d['ok'] = arr[0].ok;
            d['url'] = arr[0].url;
            d['json'] = arr[1];
            return Promise.resolve(d);
        })
}

function authFetch(url, token, method = 'GET', mapBody = undefined) {
    let body = undefined;
    try {
        body = JSON.stringify(mapBody);
    } catch (e) {
    }
    return commonFetch(url, {
        method: method,
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: body
    })
}

function authPostFetch(url, token, mapBody = undefined) {
    return authFetch(url, token, 'POST', mapBody)
}

function authDeleteFetch(url, token, mapBody = undefined) {
    return authFetch(url, token, 'DELETE', mapBody);
}

function authPutFetch(url, token, mapBody = undefined) {
    return authFetch(url, token, 'PUT', mapBody)
}

export {commonFetch, authFetch, authDeleteFetch, authPostFetch, authPutFetch, BASE_URL}
