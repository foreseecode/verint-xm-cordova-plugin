var exec = require('cordova/exec');

exports.init = function(arg0, sucess, error){
    exec(sucess, error, "ForeSeeAPI", "init", [arg0]);
};


exports.showSurvey = function(arg0, sucess, error){
    exec(sucess, error, "ForeSeeAPI", "showSurvey", [arg0]);
};


exports.showInvite = function(arg0, sucess, error){
    exec(sucess, error, "ForeSeeAPI", "showInvite", [arg0]);
};