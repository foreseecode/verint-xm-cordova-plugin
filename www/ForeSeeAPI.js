'use strict';

var exec = require("cordova/exec");
var version = "2.0.0"
const ForeSeeAPI = "ForeSeeAPI";

function validate(args) {
  if (args && args.constructor !== Array) {
    console.log("WARNING: ForeSeeAPI arguments not packed into an array. Method will not behave as expected.");
  }
  return args;
}

module.exports = {

  /**
   * @description
   * Starts the ForeSee SDK. Accepts an optional config object (which must
   * represent a valid ForeSee config, including a clientId). If no config
   * is provided, then the native module will look for the config in a file called
   * foresee_configuration.json (which must be available to the native modules).
   *
   * @example
   * cordova.plugins.ForeSeeAPI.start(config, _onSucess, _onFailure);
   *
   * @param {JSON} config - JSON with a valid config object.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  start(config, success, error) {
    if (config) {
      let args = [JSON.stringify(config)];
      exec(success, error, ForeSeeAPI, "startWithConfigurationJson", validate(args));
    } else {
      exec(success, error, ForeSeeAPI, "start", []);
    }
  },

  /**
   * @description
   * Starts the ForeSee SDK with the given configuration file in your native module.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.startWithConfigurationFile("my_config.json", _onSuccess, _onFailure);
   *
   * @param {String} fileName - The name of the configuration file to use when loading modules.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  startWithConfigurationFile(fileName, success, error) {
    let args = [fileName];
    exec(success, error, ForeSeeAPI, "startWithConfigurationFile", validate(args));
  },

  /**
   * @description
   * Starts the ForeSee SDK with the given configuration JSON string.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.startWithConfigurationJson(jsonString, _onSuccess, _onFailure);
   *
   * @param {String} jsonString - The configuration string in JSON format to use when loading modules.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  startWithConfigurationJson(jsonString, success, error) {
    let args = [jsonString];
    exec(success, error, ForeSeeAPI, "startWithConfigurationJson", validate(args));
  },

  /**
   * @description
   * Resets the ForeSee SDK.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.resetState(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  resetState(success, error) {
    exec(success, error, ForeSeeAPI, "resetState", []);
  },

  /**
   * @description
   * Check to see if the user is eligible for a survey.
   * If the user meets trigger criteria *and* are in the sampling pool, the invitation is presented.
   * Implementers must explicitly check for eligibility (the SDK does not do this automatically).
   *
   * @example
   * cordova.plugins.ForeSeeAPI.checkEligibility(_onSuccess, _onFailure);
   *
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  checkEligibility(success, error) {
    exec(success, error, ForeSeeAPI, "checkEligibility", []);
  },

  /**
   * @description
   * Programmatically present the invitation for a given survey ID (sid).
   *
   * @example
   * cordova.plugins.ForeSeeAPI.showInvite("app_test_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyId - Given survey ID (sid).
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  showInvite(surveyId, success, error) {
    let args = [surveyId];
    exec(success, error, ForeSeeAPI, "showInvite", validate(args));
  },

  /**
   * @description
   * Programmatically present the survey for a given survey ID (sid).
   *
   * @example
   * cordova.plugins.ForeSeeAPI.showSurvey("app_test_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyId - Survey ID (sid).
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  showSurvey(surveyId, success, error) {
    let args = [surveyId];
    exec(success, error, ForeSeeAPI, "showSurvey", validate(args));
  },

  /**
   * @description
   * Sets a Custom Passed Parameter (CPP) for the given key/value pair.
   * CPPs are transmitted along with surveys upon submission.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setCPP("customParam", "customValue", _onSuccess, _onFailure);
   *
   * @param {String} key - CPP key.
   * @param {String} value - CPP value.
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  setCPP(key, value, success, error) {
    let args = [key, value];
    exec(success, error, ForeSeeAPI, "addCPPValue", validate(args));
  },

  /**
   * @description
   * Gets the value of the Custom Passed Parameter (CPP) with the given key.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getCPP("customParam", _onSuccess, _onFailure);
   *
   * @param {String} key - CPP key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getCPP(key, success, error) {
    let args = [key];
    exec(success, error, ForeSeeAPI, "getCPP", validate(args));
  },

  /**
   * @description
   * Removes the Custom Passed Parameter (CPP) with the given key.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.removeCPP("customParam", _onSuccess, _onFailure);
   *
   * @param {String} key - CPP key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeCPP(key, success, error) {
    let args = [key];
    exec(success, error, ForeSeeAPI, "removeCPP", validate(args));
  },

  /**
   * @description
   * Gets an object containing all available Custom Passed Parameters (CPP) key/value pairs.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getAllCPPs(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getAllCPPs(success, error) {
    exec(success, error, ForeSeeAPI, "getAllCPPs", []);
  },

  /**
   * @description
   * Manually increment the number of page views criteria counted by the ForeSee SDK.
   * This can be useful when the user expected a new page to have been shown.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.incrementPageViews(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  incrementPageViews(success, error) {
    exec(success, error, ForeSeeAPI, "incrementPageViews", []);
  },

  /**
   * @description
   * Increments the count for the significant event with the given key.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.incrementSignificantEvent("yourSignificantEventKey", _onSuccess, _onFailure);
   *
   * @param {String} key - Significant event key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  incrementSignificantEvent(key, success, error) {
    let args = [key];
    exec(success, error, ForeSeeAPI, "incrementSignificantEvent", validate(args));
  },

  /**
   * @description
   * Set the significant event count for a given key.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setSignificantEventCount("yourSignificantEventCount", "yourSignificantEventKey", _onSuccess, _onFailure);
   *
   * @param {String} count - Significant event count.
   * @param {String} key - Significant event key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setSignificantEventCount(count, key, success, error) {
    let args = [key, count];
    exec(success, error, ForeSeeAPI, "setSignificantEventCount", validate(args));
  },

  /**
   * @description
   * Reset the significant event count for a given key.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.resetSignificantEventCount("yourSignificantEventKey", _onSuccess, _onFailure);
   *
   * @param {String} key - Significant event key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  resetSignificantEventCount(key, success, error) {
    let args = [key];
    exec(success, error, ForeSeeAPI, "resetSignificantEventCount", validate(args));
  },

  /**
   * @description
   * Resets all significant events.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.resetSignificantEvents(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  resetSignificantEvents(success, error) {
    exec(success, error, ForeSeeAPI, "resetSignificantEvents", []);
  },

  /**
   * @description
   * Programmatically cancel any pending invites for the user when the type is EXIT_INVITE or EXIT_SURVEY.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.cancelPendingInvites(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  cancelPendingInvites(success, error) {
    exec(success, error, ForeSeeAPI, "cancelPendingInvites", []);
  },

  /**
   * @description
   * Sets whether or not to enable debug logging.
   * Debug logging prints useful state information to the console for inspection. By default, debug logging is disabled.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setDebugLogEnabled("true", _onSuccess, _onFailure);
   * cordova.plugins.ForeSeeAPI.setDebugLogEnabled(true, _onSuccess, _onFailure);
   *
   * @param {String or boolean} enabled - value indicating should debug log be enabled, "true" or "false", true or false.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setDebugLogEnabled(enabled, success, error) {
    let args = [enabled];
    exec(success, error, ForeSeeAPI, "setDebugLogEnabled", validate(args));
  },

  /**
   * @description
   * Returns whether or not debug logging is enabled.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.isDebugLogEnabled(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing Boolean value TRUE or FALSE.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  isDebugLogEnabled(success, error) {
    exec(success, error, ForeSeeAPI, "isDebugLogEnabled", []);
  },

  /**
   * @description
   * Returns the version of the SDK.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getVersion(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message with the version of the SDK.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getVersion(success, error) {
    exec(success, error, ForeSeeAPI, "getVersion", []);
  },

  /**
   * @description
   * Toggles the pooling check.
   * When debugging your implementation of the ForeSee SDK, it may be useful to disable the pooling check.
   * This ensures that the invitation will always shows if the loyalty criteria has been fulfilled.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setSkipPoolingCheck("true", _onSuccess, _onFailure);
   * cordova.plugins.ForeSeeAPI.setSkipPoolingCheck(true, _onSuccess, _onFailure);
   *
   * @param {String or boolean} shouldSkip - Value indicating should pooling check be skipped, "true" or "false", true or false.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setSkipPoolingCheck(shouldSkip, success, error) {
    let args = [shouldSkip];
    exec(success, error, ForeSeeAPI, "setSkipPoolingCheck", validate(args));
  },

  /**
   * @description
   * Notifies the SDK that a custom invite has been accepted.
   * You should call this method whenever a user accepts a custom invitation that you’ve presented.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.customInviteAccepted(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  customInviteAccepted(success, error) {
    exec(success, error, ForeSeeAPI, "customInviteAccepted", []);
  },

  /**
   * @description
   * Notifies the SDK that a custom invite has been declined.
   * You should call this method whenever a user declines a custom invitation that you’ve presented.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.customInviteDeclined(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  customInviteDeclined(success, error) {
    exec(success, error, ForeSeeAPI, "customInviteDeclined", []);
  },

  /**
   * @description
   * Returns the user's contact details for the CONTACT invites.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getContactDetails("Email", _onSuccess, _onFailure);
   *
   * @param {String} type - Type of the contact to get, "Email" or "PhoneNumber".
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the string with a user's contact details (it if was set).
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getContactDetails(type, success, error) {
    let args = [type];
    exec(success, error, ForeSeeAPI, "getContactDetails", validate(args));
  },

  /**
   * @description
   * Sets the user's contact details for the CONTACT invites. This method can be used to provide a user’s contact information, so that they do not need to enter it manually.
   * When provided, the default invite skips the user input screen.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setContactDetails("somespecified@email.com", "Email", _onSuccess, _onFailure);
   *
   * @param {String} details - Contact value.
   * @param {String} type - Type of the contact, "Email" or "PhoneNumber".
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setContactDetails(details, type, success, error) {
    let args = [details, type];
    exec(success, error, ForeSeeAPI, "setContactDetails", validate(args));
  },

  /**
   * @description
   * Sets the preferred contact type for the CONTACT invites.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setPreferredContactType("Email", _onSuccess, _onFailure);
   *
   * @param {String} type - Type of the contact, "Email" or "PhoneNumber".
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setPreferredContactType(type, success, error) {
    let args = [type];
    exec(success, error, ForeSeeAPI, "setPreferredContactType", validate(args));
  },

  /**
   * @description
   * Returns the preferred contact type for the CONTACT invites.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getPreferredContactType(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the string with the referred contact type.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getPreferredContactType(success, error) {
    exec(success, error, ForeSeeAPI, "getPreferredContactType", []);
  },

  /**
   * @description
   * Returns all key/value pairs of the configured contact details for the CONTACT invites.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getAllContactDetails(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing key/value pairs with all contact details configured for user.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getAllContactDetails(success, error) {
    exec(success, error, ForeSeeAPI, "getAllContactDetails", []);
  },

  /**
   * @description
   * Set the invite listener.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setInviteListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setInviteListener(success, error) {
    exec(success, error, ForeSeeAPI, "setInviteListener", []);
  },

  /**
   * @description
   * Remove the invite listener.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.removeInviteListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeInviteListener(success, error) {
    exec(success, error, ForeSeeAPI, "removeInviteListener", []);
  },

  /**
   * @description
   * Programmatically present the default Digital survey (the first one in the configuration json).
   *
   * @example
   * cordova.plugins.ForeSeeAPI.showDigitalSurvey(_onSuccess, _onFailure);
   *
   * @param  {callback} success - A Cordova-style success callback object.
   * @param  {callback} error - A Cordova-style error callback object.
   */
  showDigitalSurvey(success, error) {
    exec(success, error, ForeSeeAPI, "showDigitalSurvey", []);
  },

  /**
   * @description
   * Programmatically present the Digital survey for a given name.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.showDigitalSurveyForName("digital_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyName - Given survey name.
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  showDigitalSurveyForName(surveyName, success, error) {
    let args = [surveyName];
    exec(success, error, ForeSeeAPI, "showDigitalSurveyForName", validate(args));
  },

  /**
   * @description
   * Check if the default Digital Survey is enabled.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.checkIfDigitalSurveyEnabled(_onSuccess, _onFailure);
   *
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  checkIfDigitalSurveyEnabled(success, error) {
    exec(success, error, ForeSeeAPI, "checkIfDigitalSurveyEnabled", []);
  },

  /**
   * @description
   * Check if a Digital survey is enabled for a given name.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.checkIfDigitalSurveyEnabledForName("digital_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyName - Given survey name.
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  checkIfDigitalSurveyEnabledForName(surveyName, success, error) {
    let args = [surveyName];
    exec(success, error, ForeSeeAPI, "checkIfDigitalSurveyEnabledForName", validate(args));
  },

  /**
   * @description
   * Returns all available Digital Survey names defined in the Configuration.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.getAvailableDigitalSurveyNames(_onSuccess, _onFailure);
   *
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  getAvailableDigitalSurveyNames(success, error) {
    exec(success, error, ForeSeeAPI, "getAvailableDigitalSurveyNames", []);
  },

  /**
   * @description
   * Set the Digital (ex Feedback) Listener.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.setDigitalListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setDigitalListener(success, error) {
    exec(success, error, ForeSeeAPI, "setDigitalListener", []);
  },

  /**
   * @description
   * Remove the Digital (ex Feedback) Listener.
   *
   * @example
   * cordova.plugins.ForeSeeAPI.removeDigitalListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeDigitalListener(success, error) {
    exec(success, error, ForeSeeAPI, "removeDigitalListener", []);
  }

};
