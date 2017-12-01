package com.foresee.cordova.plugin;

import android.util.Log;

import com.foresee.sdk.ForeSee;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import java.util.HashMap;

import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class ForeSeeAPI extends CordovaPlugin {

    /**
     * ForeSee API executor
     */
    abstract class ForeSeeMethod {
        public abstract boolean invoke(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova);
    }

    /* Class tag for logs */
    private final static String sTag = "FORESEE_CORDOVA";

    HashMap<String, ForeSeeMethod> sActions = new HashMap<String, ForeSeeMethod>();

    /**
     * Initialization of all supported ForeSee API methods
     */ {

        //showSurvey
        sActions.put("showSurvey", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, final CallbackContext callbackContext, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callbackContext.error("No Survey ID provided for showInvite");
                        return true;
                    }

                    final String id = args.getString(0);

                    if (null == id || id.isEmpty()) {
                        callbackContext.error("Bad surveyId for showSurvey");
                        return true;
                    }

                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ForeSee.showSurveyForSurveyID(id);
                            callbackContext.success();
                        }
                    });
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callbackContext.error(sTag + " showSurvey failure");
                }

                return true;
            }
        });

        //showInivite
        sActions.put("showInvite", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, final CallbackContext callbackContext, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callbackContext.error("No Survey ID provided for showInvite");
                        return true;
                    }

                    final String id = args.getString(0);

                    if (null == id || id.isEmpty()) {
                        callbackContext.error("Bad surveyId for showInvite");
                        return true;
                    }

                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ForeSee.showInviteForSurveyID(id);
                            callbackContext.success();
                        }
                    });

                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callbackContext.error(sTag + " showInvite failure");
                }

                return true;
            }
        });

        //checkEligibility
        sActions.put("checkEligibility", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
                ForeSee.checkIfEligibleForSurvey();
                callbackContext.success();
                return true;
            }
        });

        //addCPP
        sActions.put("addCPPValue", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 2) {
                        callback.error("No key or value for addCPPValue");
                        return true;
                    }

                    String key = args.getString(0);
                    String value = args.getString(1);

                    if (key == null || key.isEmpty()
                            || value == null || value.isEmpty()) {
                        callback.error("Bad key or value for addCPPValue");
                    } else {
                        ForeSee.addCPPValue(key, value);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + " show addCPPValue failure");
                } finally {
                    return true;
                }
            }
        });

        //removeCPP
        sActions.put("removeCPPValue", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No value for removeCPPValue");
                        return true;
                    }

                    String key = args.getString(0);

                    if (key == null || key.isEmpty()) {
                        callback.error("Bad value for removeCPPValue");
                    } else {
                        ForeSee.removeCPPValue(key);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + " show removeCPPValue failure");
                } finally {
                    return true;
                }
            }
        });

        //increment page views
        sActions.put("incrementPageViews", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callaback, CordovaInterface cordova) {
                ForeSee.incrementPageViews();
                callaback.success();
                return true;
            }
        });

        //increment significant event
        sActions.put("incrementSignificantEvent", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No key for incrementSignificantEvent");
                        return true;
                    }

                    String key = args.getString(0);

                    if (null == key || key.isEmpty()) {
                        callback.error("Bad key for incrementSignificantEvent");
                    } else {
                        ForeSee.incrementSignificantEventCountWithKey(key);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "incrementSignificantEvent failure");
                } finally {
                    return true;
                }
            }
        });


        //resetState
        sActions.put("resetState", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    ForeSee.resetState();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "incrementSignificantEvent failure");
                } finally {
                    return true;
                }
            }
        });


        //start
        sActions.put("start", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(sTag, "start() JS API for ANDROID is not available");
                callback.success(sTag + "start() is not available");
                return true;
            }
        });

        //startWithConfigurationFile
        sActions.put("startWithConfigurationFile", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                
                Log.i(sTag, "startWithConfigurationFile() JS API for ANDROID is not available");
                callback.success(sTag + "start() is not available");
                return true;
               
            }
        });


        //startWithConfigurationJson
        sActions.put("startWithConfigurationJson", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(sTag, "startWithConfigurationJson() JS API for ANDROID is not available");
                callback.success(sTag + "start() is not available");
                return true;
            }
        });

        //isDebugLogEnabled
        sActions.put("isDebugLogEnabled", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(String.valueOf(ForeSee.isDebugLogEnabled()));
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "isDebugLogEnabled failure");
                } finally {
                    return true;
                }
            }
        });


        //setDebugLogEnabled
        sActions.put("setDebugLogEnabled", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No value for setDebugLogEnabled");
                        return true;
                    }
                    
                    ForeSee.setDebugLogEnabled(args.getBoolean(0));
                    callback.success();

                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setDebugLogEnabled failure");
                } finally {
                    return true;
                }
            }
        });


        //getVersion
        sActions.put("getVersion", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(ForeSee.getVersion());
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getVersion failure");
                } finally {
                    return true;
                }
            }
        });

        //getContactDetails
        sActions.put("getContactDetails", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(ForeSee.getContactDetails());
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getContactDetails failure");
                } finally {
                    return true;
                }
            }
        });

        //setContactDetails
        sActions.put("setContactDetails", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No details for setContactDetails");
                        return true;
                    }

                    String contact = args.getString(0);

                    if (null == contact || contact.isEmpty()) {
                        callback.error("Bad details for setContactDetails");
                    } else {
                        ForeSee.setContactDetails(contact);
                        callback.success();
                    }

                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setContactDetails failure");
                } finally {
                    return true;
                }
            }
        });


        //customInviteDeclined
        sActions.put("customInviteDeclined", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    ForeSee.customInviteDeclined();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "customInviteDeclined failure");
                } finally {
                    return true;
                }
            }
        });

        //customInviteAccepted
        sActions.put("customInviteAccepted", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    ForeSee.customInviteAccepted();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "customInviteAccepted failure");
                } finally {
                    return true;
                }
            }
        });

        //setSkipPoolingCheck
        sActions.put("setSkipPoolingCheck", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No value for setSkipPoolingCheck");
                        return true;
                    }
                    ForeSee.setSkipPoolingCheck(args.getBoolean(0));
                    callback.success();

                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setSkipPoolingCheck failure");
                } finally {
                    return true;
                }
            }
        });

        //setInviteListener
        sActions.put("setInviteListener", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    //Stub method will be implemented in https://issuetracking.foresee.com/browse/MOBILSDK-1211
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setInviteListener failure");
                } finally {
                    return true;
                }
            }
        });

        //startRecording
        sActions.put("startRecording", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "startRecording failure");
                } finally {
                    return true;
                }
            }
        });

        //logReplayPageChange
        sActions.put("logReplayPageChange", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No details for logReplayPageChange");
                        return true;
                    }

                    String page = args.getString(0);

                    if (null == page || page.isEmpty()) {
                        callback.error("Bad details for logReplayPageChange");
                    } else {
                        ForeSee.logReplayPageChange(page);
                        callback.success();
                    }

                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "logReplayPageChange failure");
                } finally {
                    return true;
                }
            }
        });

        //setMaskingDebugEnabled
        sActions.put("setMaskingDebugEnabled", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No value for setMaskingDebugEnabled");
                        return true;
                    }
                    ForeSee.setMaskingDebugEnabled(args.getBoolean(0));
                    callback.success();

                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setMaskingDebugEnabled failure");
                } finally {
                    return true;
                }
            }
        });

        //isRecording
        sActions.put("isRecording", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(String.valueOf(ForeSee.isRecording()));
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "isRecording failure");
                } finally {
                    return true;
                }
            }
        });

        //pauseRecording
        sActions.put("pauseRecording", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    ForeSee.pauseRecording();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "pauseRecording failure");
                } finally {
                    return true;
                }
            }
        });

        //resumeRecording
        sActions.put("resumeRecording", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    ForeSee.resumeRecording();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "resumeRecording failure");
                } finally {
                    return true;
                }
            }
        });

    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (sActions.containsKey(action)) {
            Log.d(sTag, "Action " + action);
            return sActions.get(action).invoke(args, callbackContext, cordova);

        } else {
            Log.d(sTag, "This action is not supported");
            callbackContext.error("This action is not supported");
            return false;
        }
    }
}
