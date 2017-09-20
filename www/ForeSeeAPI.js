var exec = require('cordova/exec');

const ForeSeeAPI = "ForeSeeAPI";

exports.showSurvey = function(arg0, success, error){
    exec(success, error, ForeSeeAPI, "showSurvey", arg0);
};

exports.showInvite = function(arg0, success, error){
    exec(success, error, ForeSeeAPI, "showInvite", arg0);
};

exports.checkEligibility = function(success, error){
    exec(success, error, ForeSeeAPI, "checkEligibility", []);
};

exports.addCPPValue = function(arg0, success, error){
    exec(success, error, ForeSeeAPI, "addCPPValue", arg0);
};

exports.removeCPPValue = function(arg0, success, error){
    exec(success, error, ForeSeeAPI,  "removeCPPValue", arg0);
};

exports.incrementPageViews = function(success, error){
    exec(success, error, ForeSeeAPI, "incrementPageViews", []);
};

exports.incrementSignificantEventCount = function(arg0, success, error){
    exec(success, error, ForeSeeAPI, "incrementSignificantEvent", arg0);
};

exports.resetState = function(success, error){
    exec(success, error, ForeSeeAPI, "resetState", []);
};
