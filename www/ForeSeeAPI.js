var exec = require('cordova/exec');

exports.showSurvey = function(arg0, sucess, error){
    exec(sucess, error, "ForeSeeAPI", "showSurvey", [arg0]);
};

exports.showInvite = function(arg0, sucess, error){
    exec(sucess, error, "ForeSeeAPI", "showInvite", [arg0]);
};

exports.checkEligibility = function(sucess, error){
    exec(sucess, error, "ForeSeeAPI", "checkEligibility", []);
};