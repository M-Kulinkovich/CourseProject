const OperationType = {
    GET_CURRENT_USER: "getCurrentUser",
    GET_AVAILABLE: "getAvailableForRecord",
    GET_REPORT: "getReportById",
    SAVE_REPORT: "saveReportAndHisRecords",
    UPDATE_REPORT: "rewriteReportAndHisRecords",
};

Object.freeze(OperationType);

export {OperationType}