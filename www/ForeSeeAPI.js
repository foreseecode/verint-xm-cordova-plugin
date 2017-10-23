var exec = require('cordova/exec');

const ForeSeeAPI = "ForeSeeAPI";

/**
 * @description
 * Programmatically present the survey for a given survey ID (sid).
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
 * Programmatically present the invitation for a given survey ID (sid).
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
 * `cordova.plugins.ForeSeeAPI.addCPPValue(["key", "value"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of parameters, we expect a key/value pair inside. Required by Cordova's plugin EXEC.
 * @param {callback} success - A Cordova-style success callback object.
 * @param {callback} error - A Cordova-style error callback object.
 */
exports.addCPPValue = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "addCPPValue", args);
};


/**
 * @description
 * Removes a CPP value.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.removeCPPValue(["hello"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of string, we expect a single inside.
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.removeCPPValue = function (args, success, error) {
    exec(success, error, ForeSeeAPI, "removeCPPValue", args);
};


/**
 * @description
 * Manually increment the number of pages counted by the ForeSee SDK.
 * This can be useful when the user expected a new page to have been shown.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.incrementPageViews(_onSuccess, _onFailure);`
 * 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.incrementPageViews = function (success, error) {
    exec(success, error, ForeSeeAPI, "incrementPageViews", []);
};


/**
 * @description
 * Increment the significant event count for a given key.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.incrementSignificantEventCount(["hello"], _onSuccess, _onFailure);`
 * 
 * @param {array} args - Array of string, we expect to get a single string inside.
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
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
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command 
 * The callback takes one parameter, containing the message from a command
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
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
 * `cordova.plugins.ForeSeeAPI.start(_onSucess, _onFailure )
 * 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.start = function (success, error) {
    exec(success, error, ForeSeeAPI, "start", []);
};


/**
 * @description 
 * Starts the Trigger and SessionReplay modules using a custom configuration file in your native project.
 * Behavior of the two modules is defined in the named configuration file in your project.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.startWithConfigurationFile(["my_config.json"], function success(data)`
 * 
 * @param {array} args - array. The name of the configuration file to use when loading modules

 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command 
 * The callback takes one parameter, containing the message from a command
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.startWithConfigurationFile = function(args, success, error){
    exec(success, error, ForeSeeAPI, "startWithConfigurationFile", args);
}

/**
 * @description
 * Starts the Trigger and SessionReplay modules using custom configuration JSON.
 * Behavior of the two modules is defined by the string provided.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.startWithConfigurationJson([jsonConfig], success, error)`
 * 
 * @param {array} args - array. The configuration string in JSON format to use when loading modules.
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.startWithConfigurationJson = function(args, success, error){
    exec(success, error, ForeSeeAPI, "startWithConfigurationJson", args);
}

/**
 * @description
 * Returns whether or not debug logging is enabled.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.isDebugLogEnabled(sucess, error)`
 * 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing Boolean value TRUE or FALSE.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.isDebugLogEnabled = function(success, error){
    exec(success, error, ForeSeeAPI, "isDebugLogEnabled", []);
}

/**
 * @description
 * Gets the version of the SDK
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.getVersion(success, error)
 * 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message with the version of the SDK.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.getVersion = function(success, error){
    exec(success, error, ForeSeeAPI, "getVersion", []);
}


/**
 * @description
 * Retrieves a user’s contact details.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.getContactDetails(success, error);
 * 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command. 
 * The callback takes one parameter, containing the string with a user's contact details (it if was set).
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.getContactDetails = function(success, error){
    exec(success, error, ForeSeeAPI, "getContactDetails", []);
}

/**
 * @description
 * Sets a user’s contact details. This method can be used to provide a user’s contact information, so that they do not need to enter it manually. 
 * When provided, the default invite skips the user input screen. Only applies to CONTACT surveys.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.setContactDetails(["Bob"], success, error); 
 * 
 * @param {array} args 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.setContactDetails = function(args, success, error){
    exec(success, error, ForeSeeAPI, "setContactDetails", args);
}

/**
 * @description
 * Tells the SDK that a custom invitation was declined.
 * You should call this method whenever a user declines a custom invitation that you’ve presented.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.customInviteDeclined(success, error);
 * 
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.customInviteDeclined = function(success, error){
    exec(success, error, ForeSeeAPI, "customInviteDeclined", []);
}

/**
 * @description
 * Tells the SDK that a custom invitation was accepted.
 * You should call this method whenever a user accepts a custom invitation that you’ve presented.
 * 
 * @example
 * `cordova.plugins.ForeSeeAPI.customInviteAccepted(succes, error);`
 *
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command. 
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.customInviteAccepted = function(success, error){
    exec(success, error, ForeSeeAPI, "customInviteAccepted", []);
}

/**
 * @description
 * Disables the pooling check.
 * When debugging your implementation of the ForeSee SDK, it may be useful to disable the pooling check. 
 * This ensures that the invitation will always shows if the loyalty criteria has been fulfilled.
 * 
 * @example
 * cordova.plugins.ForeSeeAPI.setSkipPoolingCheck(["true"], succes, error); 
 * 
 * @param {array} args - array with one element; TRUE of FALSE.
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.setSkipPoolingCheck = function(args, success, error){
    exec(success, error, ForeSeeAPI, "setSkipPoolingCheck", args);
}

/**
 * @description
 * Sets whether or not to enable debug logging. 
 * Debug logging prints useful state information to the console for inspection. By default, debug logging is disabled.
 * 
 * @example
 * cordova.plugins.ForeSeeAPI.setDebugLogEnabled(["true"], success, error);
 * 
 * @param {array} args - array with one element; TRUE of FALSE.
 * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
 * The callback takes one parameter, containing the message from a command.
 * @param {callback} error - Optional callback that is invoked in the event of an error. 
 * The callback takes one error parameter, containing the details of the error.
 */
exports.setDebugLogEnabled = function(args, success, error){
    exec(success, error, ForeSeeAPI, "setDebugLogEnabled", args);
}

