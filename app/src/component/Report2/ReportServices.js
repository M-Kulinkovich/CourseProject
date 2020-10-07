import {authDeleteFetch, authFetch, authPostFetch, authPutFetch, BASE_URL} from "../../util/CommonFetch";
import {Comparison, toBase64} from "../../util/base64Converter";

const REPORT_RECORD_URL = BASE_URL + 'reportRecord/';
const REPORT_URL = BASE_URL + 'report/';
const FACTOR_URL = BASE_URL + 'factor/';
const LOCATION_URL = BASE_URL + 'location/';
const PROJECT_URL = BASE_URL + 'project/';
const FEATURE_URL = BASE_URL + 'feature/';
const TASK_URL = BASE_URL + 'task/';

const FACTOR_CONVERTER = {
    'STANDARD': 'Standard',
    'DAY_OFF': 'Day off',
    'OVERTIME': 'Overtime',
    'SICK_LEAVE': 'Sick leave',
    'VACATION': 'Vacation',
    'BUSINESS_TRIP': 'Business trip'
};

function getById(token, entity, id) {
    return authFetch(BASE_URL + entity + '/' + id, token);
}

function getByIdList(token, entity, ids) {
    return Promise.all(ids.map(id => getById(token, entity, id)));
}

function getReportById(token, reportId) {
    return authFetch(REPORT_URL + reportId, token);
}

function deleteNull(s) {
    s.delete(null);
    return s;
}

function getRecordById(token, recordId) {
    return authFetch(REPORT_RECORD_URL + recordId, token).then(r => {
        // console.log('rec', r);
        return Promise.all([
            Promise.resolve(r),
            r.json.projectId !== null ? authFetch(PROJECT_URL + r.json.projectId, token) : Promise.resolve(undefined),
            r.json.featureId !== null ? authFetch(FEATURE_URL + r.json.featureId, token) : Promise.resolve(undefined),
            r.json.taskId !== null ? authFetch(TASK_URL + r.json.taskId, token) : Promise.resolve(undefined),
            r.json.locationId !== null ? authFetch(LOCATION_URL + r.json.locationId, token) : Promise.resolve(undefined)
        ])
    }).then(arr => {
        let d = arr[0];
        d.json.project = arr[1]?.json;
        d.json.feature = arr[2]?.json;
        d.json.task = arr[3]?.json;
        d.json.location = arr[4]?.json;
        d.json.factor = {name: FACTOR_CONVERTER[d.json.factor], id: d.json.factor};
        d.json.hour /= 60;
        d.json.workUnit /= 60;
        return Promise.resolve(d);
    })
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
                e.factor = FACTOR_CONVERTER[e.factor];
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

function getRecordsByReportId(token, reportId) {
    let f = '?filter=' + toBase64('reportId', Comparison.EQ, reportId);
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
        'factor': reportRecord.factor?.id,
        'locationId': reportRecord.location?.id,
        'hour': reportRecord.hour * 60,
        'workUnit': reportRecord.workUnit * 60,
        'reportId': reportRecord.reportId
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

function updateReport(token, report) {
    return authPutFetch(REPORT_URL + report.id, token, getReportBody(report))
}

function getFactors(token) {
    return authFetch(FACTOR_URL, token).then(r => {
        r.json = r.json.map(e => ({'name': FACTOR_CONVERTER[e], 'id': e}));
        return r;
    })
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
    getLocations,
    getRecordById,
    getRecordsByReportId,
    updateReport,
    updateReportRecord,
    getReportById
}
