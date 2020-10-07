const Comparison = {
    EQ: "eq",
    LT: "lt",
    LTE: "lte",
    GT: "gt",
    GTE: "gte",
    ISN: "isn",
    NN: "nn",
    NQ: "nq",
};

Object.freeze(Comparison);

function toBase64(field, comparison, value) {
    return btoa(field + ',' + comparison + ',' + value);
}

export {Comparison, toBase64}