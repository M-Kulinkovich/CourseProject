import {authDeleteFetch, authFetch, authPostFetch, authPutFetch, BASE_URL} from "../../util/CommonFetch";
import {Comparison, toBase64} from "../../util/base64Converter";

const REPORT_RECORD_URL = BASE_URL + 'reportRecord/';
const REPORT_URL = BASE_URL + 'report/';
const FACTOR_URL = BASE_URL + 'factor/';
const LOCATION_URL = BASE_URL + 'location/';
const PROJECT_URL = BASE_URL + 'project/';

function getById(token, entity, id) {
    return authFetch(BASE_URL + entity + '/' + id, token);
}

function getByIdList(token, entity, ids) {
    return Promise.all(ids.map(id => getById(token, entity, id)));
}

function deleteNull(s) {
    s.delete(null);
    return s;
}

function getReportRecords(token, url = REPORT_RECORD_URL) {
    return authFetch(url, token).then(d => {
        console.log('ok1');
        let a = [
            Promise.resolve(d),
            getByIdList(token, 'project', Array.from(deleteNull(new Set(d.json.map(r => r.projectId))))),
            getByIdList(token, 'feature', Array.from(deleteNull(new Set(d.json.map(r => r.featureId))))),
            getByIdList(token, 'task', Array.from(deleteNull(new Set(d.json.map(r => r.taskId))))),
            getByIdList(token, 'location', Array.from(deleteNull(new Set(d.json.map(r => r.locationId)))))
        ];
        return Promise.all(a);
    }).then(a => {
        let names = [{}, {}, {}, {}];
        if (!a[0].ok) {
            return a[0];
        }
        for (let i = 1; i < 5; i++) {
            a[i].forEach(e => {
                names[i - 1][e.json.id] = e.json.name
            })
        }

        return {
            ok: true,
            json: a[0].json.map(e => {
                e.project = names[0][e.projectId];
                e.feature = names[1][e.featureId];
                e.task = names[2][e.taskId];
                e.location = names[3][e.locationId];
                e.hour /= 60;
                e.workUnit /= 60;
                return e;
            })
        }
    });
}

function getNullReportRecords(token, currentUser) {
    let f = '?filter=' + toBase64('report', Comparison.ISN, 'null')
        + '&filter=' + toBase64('userId', Comparison.EQ, currentUser?.id);
    return getReportRecords(token, REPORT_RECORD_URL + f);
}

function getReportRecordMap(reportRecord) {
    let d = {
        'projectId': reportRecord.project?.id,
        'featureId': reportRecord.feature?.id,
        'taskId': reportRecord.task?.id,
        'detailedTaskId': reportRecord.detailedTask?.id,
        'text': reportRecord.text,
        'date': (new Date()).toISOString().substring(0, 10),
        'factor': reportRecord.factor?.name,
        'locationId': reportRecord.location?.id,
        'hour': reportRecord.hour * 60,
        'workUnit': reportRecord.workUnit * 60
    };
    console.log('d', d);
    return d;
}

function createReportRecord(token, reportRecord) {
    return authPostFetch(REPORT_RECORD_URL, token, getReportRecordMap(reportRecord));
}

function deleteReportRecord(token, reportId) {
    return authDeleteFetch(REPORT_RECORD_URL + reportId, token);
}

function updateReportRecord(token, reportRecord) {
    return authPutFetch(REPORT_RECORD_URL + reportRecord['id'], token, getReportRecordMap(reportRecord));
}

function getReportBody(report) {
    let d = {
        'date': report.date.toISOString().substring(0, 10),
        'confirm': report.confirm,
        'reportRecordId': report.records.map(r => r.id),
    };
    console.log('d', d);
    return d;
}

function createReport(token, report) {
    return authPostFetch(REPORT_URL, token, getReportBody(report));
}

function getFactors(token) {
    return authFetch(FACTOR_URL, token)
}

function getLocations(token) {
    return authFetch(LOCATION_URL, token)
}

export {
    getFactors,
    getReportRecords,
    getNullReportRecords,
    createReport,
    createReportRecord,
    deleteReportRecord,
    getLocations
}