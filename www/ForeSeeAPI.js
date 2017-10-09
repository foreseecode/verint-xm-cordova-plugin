var exec = require('cordova/exec');

const ForeSeeAPI = "ForeSeeAPI";

/**
 * @description
 * Programmatically present the survey for a given survey ID (sid)
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.showSurvey(["app_test_1"], _onSuccess, _onFailure);`
 * 
 * @param  {array} args - Array of strings, we expect a surveyID inside.
 * @param  {callback} success - A Cordova-style success callback object.
 * @param  {callback} error - A Cordova-style error callback object.
 */
exports.showSurvey = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "showSurvey", args);
};


/**
 * @description
 * Programmatically present the invitation for a given survey ID (sid)
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.showInvite(["app_test_1"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of strings, we expect a surveyID inside.
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.showInvite = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "showInvite", args);
};


/**
 * @description
 * Check to see if the user is eligible for a survey.
 * If the user meets trigger criteria *and* are in the sampling pool, the invitation is presented.
 * Implementers must explicitly check for eligibility (the SDK does not do this automatically).
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.checkEligibility(_onSuccess, _onFailure);`
 * 
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.checkEligibility = function (success, error) {
    exec(success, error, ForeSeeAPI, "checkEligibility", []);
};

/**
 * @description
 * Adds a CPP key/value pair. CPPs are transmitted along with surveys upon submission.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.addCPPValue(["hello", "bye"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of strings, we expect a key/value pair inside.
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.addCPPValue = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "addCPPValue", args);
};


/**
 * @description
 * Removes a CPP value
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.removeCPPValue(["hello"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of string, we expect a single inside.
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.removeCPPValue = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "removeCPPValue", args);
};


/**
 * @description
 * Manually increment the number of pages counted by the ForeSee SDK.
 * This can be useful when the user would consider a new page to have been shown.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.incrementPageViews(_onSuccess, _onFailure);`
 * 
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.incrementPageViews = function (success, error) {
    exec(success, error, ForeSeeAPI, "incrementPageViews", []);
};


/**
 * @description
 * Increment the significant event count for a given key
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.incrementSignificantEventCount(["hello"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of string, we expect to get a single string inside
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.incrementSignificantEventCount = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "incrementSignificantEvent", args);
};



/**
 * @description 
 * Reset the state of the tracker and (if applicable) reactivates the Replay module.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.resetState(_onSuccess, _onFailure);`
 * 
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.resetState = function (success, error) {
    exec(success, error, ForeSeeAPI, "resetState", []);
};


/**
 * @description 
 * Starts the Trigger and SessionReplay modules (if enabled).
 * Behavior of the two modules is defined in the foresee_configuration.json file.
 * For SessionReplay, a recording starts once this method is called and ends when the app is put into the background.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.start([json], _onSucess, _onFailure )
 * 
 * @param {array} args - We expect to get a single Json object
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style success callback object.
 */
exports.start = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "start", args);
};

exports.startWithConfigurationFile = function(args, success, error){
    exec(success, error, ForeSeeAPI, "startWithConfigurationFile", args);
}

exports.startWithConfigurationJson = function(args, success, error){
    exec(success, error, ForeSeeAPI, "startWithConfigurationJson", args);
}

exports.isDebugLogEnabled = function(success, error){
    exec(success, error, ForeSeeAPI, "isDebugLogEnabled", []);
}

exports.getVersion = function(success, error){
    exec(success, error, ForeSeeAPI, "getVersion", []);
}

exports.getContactDetails = function(success, error){
    exec(success, error, ForeSeeAPI, "getContactDetails", []);
}

exports.customInviteDeclined = function(success, error){
    exec(success, error, ForeSeeAPI, "customInviteDeclined", []);
}

exports.customInviteAccepted = function(success, error){
    exec(success, error, ForeSeeAPI, "customInviteAccepted", []);
}

exports.setSkipPoolingCheck = function(args, success, error){
    exec(success, error, "setSkipPoolingCheck", args);
}

// onSuccess: Callback that is invoked upon receiving the data about the invoked command 
//The callback takes one paramter, containing the message from a command

// onError: Optional callback that is invoked in the event of an error. 
//The callback takes one error parameter, containing the details of the error.

