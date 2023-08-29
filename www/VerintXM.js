'use strict';

var exec = require("cordova/exec");
var version = "3.0.0"
const VerintXM = "VerintXM";

function validate(args) {
  if (args && args.constructor !== Array) {
    console.log("WARNING: VerintXM arguments not packed into an array. Method will not behave as expected.");
  }
  return args;
}

module.exports = {

  /**
   * @description
   * Starts the Verint SDK. Accepts an optional config object (which must
   * represent a valid Verint config, including a clientId). If no config
   * is provided, then the native module will look for the config in a file called
   * exp_configuration.json (which must be available to the native modules).
   *
   * @example
   * cordova.plugins.verint.xm.start(config, _onSucess, _onFailure);
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
      exec(success, error, VerintXM, "startWithConfigurationJson", validate(args));
    } else {
      exec(success, error, VerintXM, "start", []);
    }
  },

  /**
   * @description
   * Starts the Verint SDK with the given configuration file in your native module.
   *
   * @example
   * cordova.plugins.verint.xm.startWithConfigurationFile("my_config.json", _onSuccess, _onFailure);
   *
   * @param {String} fileName - The name of the configuration file to use when loading modules.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  startWithConfigurationFile(fileName, success, error) {
    let args = [fileName];
    exec(success, error, VerintXM, "startWithConfigurationFile", validate(args));
  },

  /**
   * @description
   * Starts the Verint SDK with the given configuration JSON string.
   *
   * @example
   * cordova.plugins.verint.xm.startWithConfigurationJson(jsonString, _onSuccess, _onFailure);
   *
   * @param {String} jsonString - The configuration string in JSON format to use when loading modules.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  startWithConfigurationJson(jsonString, success, error) {
    let args = [jsonString];
    exec(success, error, VerintXM, "startWithConfigurationJson", validate(args));
  },

  /**
   * @description
   * Resets the Verint SDK.
   *
   * @example
   * cordova.plugins.verint.xm.resetState(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  resetState(success, error) {
    exec(success, error, VerintXM, "resetState", []);
  },

  /**
   * @description
   * Checks to see if the user is eligible for a survey.
   * If the user meets trigger criteria *and* are in the sampling pool, the invitation is presented.
   * Implementers must explicitly check for eligibility (the SDK does not do this automatically).
   *
   * @example
   * cordova.plugins.verint.xm.checkEligibility(_onSuccess, _onFailure);
   *
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  checkEligibility(success, error) {
    exec(success, error, VerintXM, "checkEligibility", []);
  },

  /**
   * @description
   * Presents the invitation for a given survey ID (sid).
   *
   * @example
   * cordova.plugins.verint.xm.showInvite("app_test_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyId - Given survey ID (sid).
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  showInvite(surveyId, success, error) {
    let args = [surveyId];
    exec(success, error, VerintXM, "showInvite", validate(args));
  },

  /**
   * @description
   * Presents the survey for a given survey ID (sid).
   *
   * @example
   * cordova.plugins.verint.xm.showSurvey("app_test_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyId - Survey ID (sid).
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  showSurvey(surveyId, success, error) {
    let args = [surveyId];
    exec(success, error, VerintXM, "showSurvey", validate(args));
  },

  /**
   * @description
   * Sets a Custom Passed Parameter (CPP) for the given key/value pair.
   * CPPs are transmitted along with surveys upon submission.
   *
   * @example
   * cordova.plugins.verint.xm.setCPP("customParam", "customValue", _onSuccess, _onFailure);
   *
   * @param {String} key - CPP key.
   * @param {String} value - CPP value.
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  setCPP(key, value, success, error) {
    let args = [key, value];
    exec(success, error, VerintXM, "addCPPValue", validate(args));
  },

  /**
   * @description
   * Gets the value of the Custom Passed Parameter (CPP) with the given key.
   *
   * @example
   * cordova.plugins.verint.xm.getCPP("customParam", _onSuccess, _onFailure);
   *
   * @param {String} key - CPP key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getCPP(key, success, error) {
    let args = [key];
    exec(success, error, VerintXM, "getCPP", validate(args));
  },

  /**
   * @description
   * Removes the Custom Passed Parameter (CPP) with the given key.
   *
   * @example
   * cordova.plugins.verint.xm.removeCPP("customParam", _onSuccess, _onFailure);
   *
   * @param {String} key - CPP key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeCPP(key, success, error) {
    let args = [key];
    exec(success, error, VerintXM, "removeCPP", validate(args));
  },

  /**
   * @description
   * Gets an object containing all available Custom Passed Parameters (CPP) key/value pairs.
   *
   * @example
   * cordova.plugins.verint.xm.getAllCPPs(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getAllCPPs(success, error) {
    exec(success, error, VerintXM, "getAllCPPs", []);
  },

  /**
   * @description
   * Manually increments the number of page views criteria counted by the Verint SDK.
   * This can be useful when the user expected a new page to have been shown.
   *
   * @example
   * cordova.plugins.verint.xm.incrementPageViews(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  incrementPageViews(success, error) {
    exec(success, error, VerintXM, "incrementPageViews", []);
  },

  /**
   * @description
   * Increments the count for the significant event with the given key.
   *
   * @example
   * cordova.plugins.verint.xm.incrementSignificantEvent("yourSignificantEventKey", _onSuccess, _onFailure);
   *
   * @param {String} key - Significant event key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  incrementSignificantEvent(key, success, error) {
    let args = [key];
    exec(success, error, VerintXM, "incrementSignificantEvent", validate(args));
  },

  /**
   * @description
   * Sets the significant event count for a given key.
   *
   * @example
   * cordova.plugins.verint.xm.setSignificantEventCount("yourSignificantEventCount", "yourSignificantEventKey", _onSuccess, _onFailure);
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
    exec(success, error, VerintXM, "setSignificantEventCount", validate(args));
  },

  /**
   * @description
   * Resets the significant event count for a given key.
   *
   * @example
   * cordova.plugins.verint.xm.resetSignificantEventCount("yourSignificantEventKey", _onSuccess, _onFailure);
   *
   * @param {String} key - Significant event key.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  resetSignificantEventCount(key, success, error) {
    let args = [key];
    exec(success, error, VerintXM, "resetSignificantEventCount", validate(args));
  },

  /**
   * @description
   * Resets all significant events.
   *
   * @example
   * cordova.plugins.verint.xm.resetSignificantEvents(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  resetSignificantEvents(success, error) {
    exec(success, error, VerintXM, "resetSignificantEvents", []);
  },

  /**
   * @description
   * Cancels any pending invites for the user when the type is EXIT_INVITE or EXIT_SURVEY.
   *
   * @example
   * cordova.plugins.verint.xm.cancelPendingInvites(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  cancelPendingInvites(success, error) {
    exec(success, error, VerintXM, "cancelPendingInvites", []);
  },

  /**
   * @description
   * Sets whether or not to enable debug logging.
   * Debug logging prints useful state information to the console for inspection. By default, debug logging is disabled.
   *
   * @example
   * cordova.plugins.verint.xm.setDebugLogEnabled("true", _onSuccess, _onFailure);
   * cordova.plugins.verint.xm.setDebugLogEnabled(true, _onSuccess, _onFailure);
   *
   * @param {String or boolean} enabled - value indicating should debug log be enabled, "true" or "false", true or false.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setDebugLogEnabled(enabled, success, error) {
    let args = [enabled];
    exec(success, error, VerintXM, "setDebugLogEnabled", validate(args));
  },

  /**
   * @description
   * Returns whether or not debug logging is enabled.
   *
   * @example
   * cordova.plugins.verint.xm.isDebugLogEnabled(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing Boolean value TRUE or FALSE.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  isDebugLogEnabled(success, error) {
    exec(success, error, VerintXM, "isDebugLogEnabled", []);
  },

  /**
   * @description
   * Returns the version of the SDK.
   *
   * @example
   * cordova.plugins.verint.xm.getVersion(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message with the version of the SDK.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getVersion(success, error) {
    exec(success, error, VerintXM, "getVersion", []);
  },

  /**
   * @description
   * Toggles the pooling check.
   * When debugging your implementation of the Verint SDK, it may be useful to disable the pooling check.
   * This ensures that the invitation will always shows if the loyalty criteria has been fulfilled.
   *
   * @example
   * cordova.plugins.verint.xm.setSkipPoolingCheck("true", _onSuccess, _onFailure);
   * cordova.plugins.verint.xm.setSkipPoolingCheck(true, _onSuccess, _onFailure);
   *
   * @param {String or boolean} shouldSkip - Value indicating should pooling check be skipped, "true" or "false", true or false.
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setSkipPoolingCheck(shouldSkip, success, error) {
    let args = [shouldSkip];
    exec(success, error, VerintXM, "setSkipPoolingCheck", validate(args));
  },

  /**
   * @description
   * Notifies the SDK that a custom invite has been accepted.
   * You should call this method whenever a user accepts a custom invitation that you’ve presented.
   *
   * @example
   * cordova.plugins.verint.xm.customInviteAccepted(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  customInviteAccepted(success, error) {
    exec(success, error, VerintXM, "customInviteAccepted", []);
  },

  /**
   * @description
   * Notifies the SDK that a custom invite has been declined.
   * You should call this method whenever a user declines a custom invitation that you’ve presented.
   *
   * @example
   * cordova.plugins.verint.xm.customInviteDeclined(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  customInviteDeclined(success, error) {
    exec(success, error, VerintXM, "customInviteDeclined", []);
  },

  /**
   * @description
   * Returns the user's contact details for the CONTACT invites.
   *
   * @example
   * cordova.plugins.verint.xm.getContactDetails("Email", _onSuccess, _onFailure);
   *
   * @param {String} type - Type of the contact to get, "Email" or "PhoneNumber".
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the string with a user's contact details (it if was set).
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getContactDetails(type, success, error) {
    let args = [type];
    exec(success, error, VerintXM, "getContactDetails", validate(args));
  },

  /**
   * @description
   * Sets the user's contact details for the CONTACT invites. This method can be used to provide a user’s contact information, so that they do not need to enter it manually.
   * When provided, the default invite skips the user input screen.
   *
   * @example
   * cordova.plugins.verint.xm.setContactDetails("somespecified@email.com", "Email", _onSuccess, _onFailure);
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
    exec(success, error, VerintXM, "setContactDetails", validate(args));
  },

  /**
   * @description
   * Sets the preferred contact type for the CONTACT invites.
   *
   * @example
   * cordova.plugins.verint.xm.setPreferredContactType("Email", _onSuccess, _onFailure);
   *
   * @param {String} type - Type of the contact, "Email" or "PhoneNumber".
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setPreferredContactType(type, success, error) {
    let args = [type];
    exec(success, error, VerintXM, "setPreferredContactType", validate(args));
  },

  /**
   * @description
   * Returns the preferred contact type for the CONTACT invites.
   *
   * @example
   * cordova.plugins.verint.xm.getPreferredContactType(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the string with the referred contact type.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getPreferredContactType(success, error) {
    exec(success, error, VerintXM, "getPreferredContactType", []);
  },

  /**
   * @description
   * Returns all key/value pairs of the configured contact details for the CONTACT invites.
   *
   * @example
   * cordova.plugins.verint.xm.getAllContactDetails(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing key/value pairs with all contact details configured for user.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  getAllContactDetails(success, error) {
    exec(success, error, VerintXM, "getAllContactDetails", []);
  },

  /**
   * @description
   * Sets the startup listener. Used to listen for startup lifecycle events.
   *
   * @example
   * cordova.plugins.verint.xm.setStartupListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setStartupListener(success, error) {
    exec(success, error, VerintXM, "setStartupListener", []);
  },

  /**
   * @description
   * Removes the startup listener.
   *
   * @example
   * cordova.plugins.verint.xm.removeInviteListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeStartupListener(success, error) {
    exec(success, error, VerintXM, "removeStartupListener", []);
  },

  /**
   * @description
   * Sets the invite listener. Used to listen for events related to Predictive surveys.
   *
   * @example
   * cordova.plugins.verint.xm.setInviteListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setInviteListener(success, error) {
    exec(success, error, VerintXM, "setInviteListener", []);
  },

  /**
   * @description
   * Removes the invite listener.
   *
   * @example
   * cordova.plugins.verint.xm.removeInviteListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeInviteListener(success, error) {
    exec(success, error, VerintXM, "removeInviteListener", []);
  },

  /**
   * @description
   * Presents the default Digital survey (the first one in the configuration json).
   *
   * @example
   * cordova.plugins.verint.xm.showDigitalSurvey(_onSuccess, _onFailure);
   *
   * @param  {callback} success - A Cordova-style success callback object.
   * @param  {callback} error - A Cordova-style error callback object.
   */
  showDigitalSurvey(success, error) {
    exec(success, error, VerintXM, "showDigitalSurvey", []);
  },

  /**
   * @description
   * Presents the Digital survey for a given name.
   *
   * @example
   * cordova.plugins.verint.xm.showDigitalSurveyForName("digital_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyName - Given survey name.
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  showDigitalSurveyForName(surveyName, success, error) {
    let args = [surveyName];
    exec(success, error, VerintXM, "showDigitalSurveyForName", validate(args));
  },

  /**
   * @description
   * Checks whether the default Digital Survey is enabled.
   *
   * @example
   * cordova.plugins.verint.xm.checkIfDigitalSurveyEnabled(_onSuccess, _onFailure);
   *
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  checkIfDigitalSurveyEnabled(success, error) {
    exec(success, error, VerintXM, "checkIfDigitalSurveyEnabled", []);
  },

  /**
   * @description
   * Checks whether a Digital survey is enabled for a given name.
   *
   * @example
   * cordova.plugins.verint.xm.checkIfDigitalSurveyEnabledForName("digital_1", _onSuccess, _onFailure);
   *
   * @param {String} surveyName - Given survey name.
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  checkIfDigitalSurveyEnabledForName(surveyName, success, error) {
    let args = [surveyName];
    exec(success, error, VerintXM, "checkIfDigitalSurveyEnabledForName", validate(args));
  },

  /**
   * @description
   * Returns all available Digital Survey names defined in the Configuration.
   *
   * @example
   * cordova.plugins.verint.xm.getAvailableDigitalSurveyNames(_onSuccess, _onFailure);
   *
   * @param {callback} success - A Cordova-style success callback object.
   * @param {callback} error - A Cordova-style error callback object.
   */
  getAvailableDigitalSurveyNames(success, error) {
    exec(success, error, VerintXM, "getAvailableDigitalSurveyNames", []);
  },

  /**
   * @description
   * Sets the Digital (ex Feedback) Listener.
   *
   * @example
   * cordova.plugins.verint.xm.setDigitalListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  setDigitalListener(success, error) {
    exec(success, error, VerintXM, "setDigitalListener", []);
  },

  /**
   * @description
   * Removes the Digital (ex Feedback) Listener.
   *
   * @example
   * cordova.plugins.verint.xm.removeDigitalListener(_onSuccess, _onFailure);
   *
   * @param {callback} success - Callback that is invoked upon receiving the data about the invoked command.
   * The callback takes one parameter, containing the message from a command.
   * @param {callback} error - Optional callback that is invoked in the event of an error.
   * The callback takes one error parameter, containing the details of the error.
   */
  removeDigitalListener(success, error) {
    exec(success, error, VerintXM, "removeDigitalListener", []);
  }

};
