package com.foresee.cordova.plugin;

import android.util.Log;
import android.os.Build;

import com.verint.xm.sdk.Core;
import com.verint.xm.sdk.Predictive;
import com.verint.xm.sdk.Digital;
import com.verint.xm.sdk.ExpDigitalListener;
import com.verint.xm.sdk.predictive.tracker.listeners.BaseInviteListener;
import com.verint.xm.sdk.common.configuration.MeasureConfiguration;
import com.verint.xm.sdk.predictive.tracker.listeners.DefaultInviteListener;
import com.verint.xm.sdk.common.configuration.EligibleMeasureConfigurations;
import com.verint.xm.sdk.common.configuration.ContactType;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private final String version = "2.0.0";

    HashMap<String, ForeSeeMethod> sActions = new HashMap<String, ForeSeeMethod>();
    Set<CallbackContext> mCallbacks = Collections
            .synchronizedSet(new HashSet<CallbackContext>());
    Set<CallbackContext> mDigitalCallbacks = Collections
            .synchronizedSet(new HashSet<CallbackContext>());

    /**
     * Initialization of all supported ForeSee API methods
     */
    {

        //get device state
        sActions.put("getDeviceState", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No args for getDeviceState");
                        return true;
                    } else {
                        String surveyId = args.getString(0);
                        String sigEventKey = args.getString(1);

                        if (surveyId == null || surveyId.isEmpty()) {
                            callback.error("Bad args for getDeviceState");
                        } else {
                            callback.success(Predictive.getDeviceState(surveyId, sigEventKey));
                        }
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getDeviceState failure");
                } finally {
                    return true;
                }
            }
        });

                //get device state
        sActions.put("setInviteMode", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No args for setInviteMode");
                        return true;
                    } else {
                        String inviteMode = args.getString(0);

                        if (inviteMode == null || inviteMode.isEmpty()) {
                            callback.error("Bad args for setInviteMode");
                        } else {
                            Predictive.setInviteMode(inviteMode);
                            callback.success();
                        }
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setInviteMode failure");
                } finally {
                    return true;
                }
            }
        });

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
                            Predictive.showSurveyForSurveyID(id);
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
                            Predictive.showInviteForSurveyID(id);
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
                Predictive.checkIfEligibleForSurvey();
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

                    if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
                        callback.error("Bad key or value for addCPPValue");
                    } else {
                        Core.addCPPValue(key, value);
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

        //getCPPValue
        sActions.put("getCPPValue", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No key for getCPPValue");
                        return true;
                    } else {
                        String key = args.getString(0);
                        
                        if (key == null || key.isEmpty()) {
                            callback.error("Bad key for getCPPValue");
                        } else {
                            callback.success(Core.getCPPValue(key));
                        }
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getCPPValue failure");
                } finally {
                    return true;
                }
            }
        });

        //getAllCPPs
        sActions.put("getAllCPPs", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(new JSONObject(Core.getAllCPPs()));
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getAllCPPs failure");
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
                        callback.error("No key for removeCPPValue");
                        return true;
                    }

                    String key = args.getString(0);

                    if (key == null || key.isEmpty()) {
                        callback.error("Bad key for removeCPPValue");
                    } else {
                        Core.removeCPPValue(key);
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
                Predictive.incrementPageViews();
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
                        Predictive.incrementSignificantEventCountWithKey(key);
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
                    Core.resetState();
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

                Log.i(sTag, "The start() API for ANDROID is not available in Cordova implementations. The SDK will start automatically on app launch");
                callback.success(sTag + "start() is not available");
                return true;
            }
        });

        //startWithConfigurationFile
        sActions.put("startWithConfigurationFile", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(sTag, "The startWithConfigurationFile() API for ANDROID is not available in Cordova implementations. The SDK will start automatically on app launch");
                callback.success(sTag + "start() is not available");
                return true;

            }
        });

        //startWithConfigurationJson
        sActions.put("startWithConfigurationJson", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(sTag, "The startWithConfigurationJson() API for ANDROID is not available in Cordova implementations. The SDK will start automatically on app launch");
                callback.success(sTag + "start() is not available");
                return true;
            }
        });

        //isDebugLogEnabled
        sActions.put("isDebugLogEnabled", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(String.valueOf(Core.isDebugLogEnabled()));
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

                    Core.setDebugLogEnabled(args.getBoolean(0));
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
                    callback.success(Core.getVersion());
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
                    ContactType contactType = contactTypeForString(args.getString(0));
                    callback.success(Predictive.getContactDetails(contactType));
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
                    if (args == null || args.length() < 1 || args.length() > 2) {
                        callback.error("No, or too many details for setContactDetails");
                        return true;
                    }

                    String contact = args.getString(0);

                    if (null == contact || contact.isEmpty()) {
                        callback.error("Bad details for setContactDetails");
                    } else if (args.length() == 2) {
                        ContactType contactType = contactTypeForString(args.getString(1));
                        Predictive.setContactDetails(contactType, contact);
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

        //getPreferredContactType
        sActions.put("getPreferredContactType", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(Predictive.getPreferredContactType().name());
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getPreferredContactType failure");
                } finally {
                    return true;
                }
            }
        });

        //setPreferredContactType
        sActions.put("setPreferredContactType", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() != 1) {
                        callback.error("Bad contact type for setPreferredContactType");
                        return true;
                    }

                    String string = args.getString(0);

                    if (null == string || string.isEmpty()) {
                        callback.error("Bad contact type for setPreferredContactType");
                    } else {
                        ContactType contactType = contactTypeForString(string);
                        Predictive.setPreferredContactType(contactType);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setPreferredContactType failure");
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
                    Predictive.customInviteDeclined();
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
                    Predictive.customInviteAccepted();
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
                    Core.setSkipPoolingCheck(args.getBoolean(0));
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
        /*
            1. Clear current callbacks
            2. Add a new listener
            3. Add a new callback to list
         */
        sActions.put("setInviteListener", new ForeSeeMethod() {

            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    //1.
                    mCallbacks.clear();

                    //2. 
                    Predictive.setInviteListener(new FSCordovaInviteListener());
                    
                    //3.
                    mCallbacks.add(callback);
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setInviteListener failure");
                } finally {
                    return true;
                }
            }
        });

        //removeInviteListener
        /*
            Clears any invite listeners that have been set to avoid memory leaks
         */
        sActions.put("removeInviteListener", new ForeSeeMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Predictive.setInviteListener(null);
                    mCallbacks.clear();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "removeInviteListener failure");
                } finally {
                    return true;
                }
            }
        });

        // Check if a feedback is enabled.
        sActions.put("checkIfDigitalSurveyEnabledForName", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No feedback name for checkIfDigitalSurveyEnabledForName");
                        return true;
                    }

                    String name = args.getString(0);

                    if (null == name || name.isEmpty()) {
                        callback.error("Bad name for checkIfDigitalSurveyEnabledForName");
                    } else {
                        Digital.checkIfDigitalSurveyEnabledForName(name);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "checkIfDigitalSurveyEnabledForName failure");
                } finally {
                    return true;
                }
            }
        });

        // Get all available feedback names defined in the Configuration.
        sActions.put("getAvailableDigitalSurveyNames", new ForeSeeMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Digital.getAvailableDigitalSurveyNames();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "getAvailableDigitalSurveyNames failure");
                } finally {
                    return true;
                }
            }
        });

        // Check if the default feedback is enabled. 
        sActions.put("checkIfDigitalSurveyEnabled", new ForeSeeMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Digital.checkIfDigitalSurveyEnabled();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "checkIfDigitalSurveyEnabled failure");
                } finally {
                    return true;
                }
            }
        });

        // Show the feedback for a given feedback name.
        sActions.put("showDigitalSurveyForName", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No feedback name for showDigitalSurveyForName");
                        return true;
                    }

                    String name = args.getString(0);

                    if (null == name || name.isEmpty()) {
                        callback.error("Bad name for showDigitalSurveyForName");
                    } else {
                        Digital.showDigitalSurveyForName(name);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "showDigitalSurveyForName failure");
                } finally {
                    return true;
                }
            }
        });

        // Show the default feedback.
        sActions.put("showDigitalSurvey", new ForeSeeMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Digital.showDigitalSurvey();
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "showDigitalSurvey failure");
                } finally {
                    return true;
                }
            }
        });

        // set Digital Listener
        /*
            1. Clear current callbacks
            2. Add a new listener
            3. Add a new callback to list
         */
        sActions.put("setDigitalListener", new ForeSeeMethod() {

            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    //1.
                    mDigitalCallbacks.clear();

                    //2. 
                    Digital.setDigitalListener(new FSCordovaDigitalListener());
                    
                    //3.
                    mDigitalCallbacks.add(callback);
                } catch (Exception ex) {
                    Log.e(sTag, ex.getMessage());
                    callback.error(sTag + "setDigitalListener failure");
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

    @Override
    public void onStart() {
        super.onStart();
        if (!Core.isCoreStarted()) {
            Log.d(sTag, "init the ForeSee SDK");
            Core.start(cordova.getActivity().getApplication());
            Core.addCPPValue("crossPlatformName", "Cordova Android");
            Core.addCPPValue("crossPlatformSDKVersion", CordovaWebView.CORDOVA_VERSION);
            Core.addCPPValue("crossPlatformOSVersion", android.os.Build.VERSION.RELEASE);
            Core.addCPPValue("crossPlatformVersion", version);
        }
    }

    // Util methods
    private ContactType contactTypeForString(String string) {
        ContactType result = ContactType.Unknown;
        try {
            result = ContactType.valueOf(string);
        } catch (IllegalArgumentException ex) {
            Log.e(sTag, ex.getMessage());
        }
        return result;
    }

    class FSCordovaInviteListener implements BaseInviteListener, DefaultInviteListener {

        @Override
        public void onInviteCompleteWithAccept(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInviteCompleteWithAccept");
            try {
                onEvent(new JSONObject().put("event", "onInviteCompleteWithAccept"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onInviteCompleteWithAccept event");
            }
        }

        @Override
        public void onInviteCompleteWithDecline(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInviteCompleteWithDecline");
            try {
                onEvent(new JSONObject().put("event", "onInviteCompleteWithDecline"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onInviteCompleteWithDecline event");
            }
        }

        @Override
        public void onInviteNotShownWithNetworkError(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInviteNotShownWithNetworkError");
            try {
                // Here we return a onSurveyCancelledWithNetworkError event instead of a onInviteNotShownWithNetworkError
                // event to align with iOS's implementation.
                JSONObject jsonObject = new JSONObject().put("event", "onSurveyCancelledWithNetworkError");

                // This is intended to enable forwards compatibility; 
                // eligibleMeasures is null in v5.0.0 of the Android SDK, but will be added in future
                if (validChosenMeasure(eligibleMeasures)) {
                    jsonObject.put("surveyId", eligibleMeasures.getChosenEligibleMeasureConfiguration().getSurveyId());
                }
                onEvent(jsonObject);
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onSurveyCancelledWithNetworkError event");
            }
        }

        @Override
        public void onInviteNotShownWithEligibilityFailed(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInviteNotShownWithEligibilityFailed");
            try {
                onEvent(new JSONObject().put("event", "onInviteNotShownWithEligibilityFailed"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onInviteNotShownWithEligibilityFailed event");
            }
        }

        @Override
        public void onInviteNotShownWithSamplingFailed(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInviteNotShownWithSamplingFailed");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onInviteNotShownWithSamplingFailed");
                
                // This is intended to enable forwards compatibility; 
                // the chosen measure is null in v5.0.0 of the Android SDK, but will be added in future
                if (validChosenMeasure(eligibleMeasures)) {
                    jsonObject.put("surveyId", eligibleMeasures.getChosenEligibleMeasureConfiguration().getSurveyId());
                }

                onEvent(jsonObject);
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onInviteNotShownWithSamplingFailed event");
            }
        }

        @Override
        public void onSurveyPresented(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onSurveyPresented");
            try {
                onEvent(new JSONObject().put("event", "onSurveyPresented"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onSurveyPresented event");
            }
        }

        @Override
        public void onSurveyCompleted(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onSurveyCompleted");
            try {
                onEvent(new JSONObject().put("event", "onSurveyCompleted"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onSurveyCompleted event");
            }
        }

        @Override
        public void onSurveyCancelledByUser(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onSurveyCancelledByUser");
            try {
                onEvent(new JSONObject().put("event", "onSurveyCancelledByUser"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onSurveyCancelledByUser event");
            }
        }

        @Override
        public void onSurveyCancelledWithNetworkError(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onSurveyCancelledWithNetworkError");
            try {
                onEvent(new JSONObject().put("event", "onSurveyCancelledWithNetworkError"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onSurveyCancelledWithNetworkError event");
            }
        }

        @Override
        public void onInvitePresented(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInvitePresented");
            try {
                onEvent(new JSONObject().put("event", "onInvitePresented"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onInvitePresented event");
            }
        }

        @Override
        public void onInviteCancelledWithNetworkError(EligibleMeasureConfigurations eligibleMeasures) {
            Log.d(sTag, "onInviteCancelledWithNetworkError");
            try {
                // Here we return a onSurveyCancelledWithNetworkError event instead of a onInviteCancelledWithNetworkError
                // event to align with iOS's implementation.
                onEvent(new JSONObject().put("event", "onSurveyCancelledWithNetworkError"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onSurveyCancelledWithNetworkError event");
            }
        }

        /**
         * Dispatch event results
         *
         * @param eventMsg
         */
        private void onEvent(JSONObject eventMsg) {
            PluginResult result = new PluginResult(PluginResult.Status.OK, eventMsg);
            result.setKeepCallback(true);
            for (CallbackContext c : mCallbacks) {
                if (c != null) {
                    c.sendPluginResult(result);
                }
                else {
                }
            }
        }

        /** 
         * Utility method to check for null response on listener
         */
        private boolean validChosenMeasure(EligibleMeasureConfigurations eligibleMeasures){
            return eligibleMeasures != null && eligibleMeasures.getChosenEligibleMeasureConfiguration() != null;
        }
    }

    class FSCordovaDigitalListener implements ExpDigitalListener {

        @Override
        public void onDigitalSurveyPresented(String feedbackName) {
            Log.d(sTag, "onDigitalSurveyPresented");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveyPresented");
                
                jsonObject.put("feedbackName", feedbackName);
                
                onEvent(new JSONObject().put("event", "onDigitalSurveyPresented"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveyPresented event");
            }
        }

        @Override
        public void onDigitalSurveyNotPresentedWithNetworkError(String feedbackName) {
            Log.d(sTag, "onDigitalSurveyNotPresentedWithNetworkError");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveyNotPresentedWithNetworkError");
                
                jsonObject.put("feedbackName", feedbackName);
                
                onEvent(new JSONObject().put("event", "onDigitalSurveyNotPresentedWithNetworkError"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveyNotPresentedWithNetworkError event");
            }
        }

        @Override
        public void onDigitalSurveyNotPresentedWithDisabled(String feedbackName) {
            Log.d(sTag, "onDigitalSurveyNotPresentedWithDisabled");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveyNotPresentedWithDisabled");
                
                jsonObject.put("feedbackName", feedbackName);
                
                onEvent(new JSONObject().put("event", "onDigitalSurveyNotPresentedWithDisabled"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveyNotPresentedWithDisabled event");
            }
        }

        @Override
        public void onDigitalSurveySubmitted(String feedbackName) {
            Log.d(sTag, "onDigitalSurveySubmitted");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveySubmitted");
                
                jsonObject.put("feedbackName", feedbackName);
                
                onEvent(new JSONObject().put("event", "onDigitalSurveySubmitted"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveySubmitted event");
            }
        }

        @Override
        public void onDigitalSurveyNotSubmittedWithAbort(String feedbackName) {
            Log.d(sTag, "onDigitalSurveyNotSubmittedWithAbort");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveyNotSubmittedWithAbort");
                
                jsonObject.put("feedbackName", feedbackName);
                
                onEvent(new JSONObject().put("event", "onDigitalSurveyNotSubmittedWithAbort"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveyNotSubmittedWithAbort event");
            }
        }

        @Override
        public void onDigitalSurveyNotSubmittedWithNetworkError(String feedbackName) {
            Log.d(sTag, "onDigitalSurveyNotSubmittedWithNetworkError");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveyNotSubmittedWithNetworkError");
                
                jsonObject.put("feedbackName", feedbackName);
                
                onEvent(new JSONObject().put("event", "onDigitalSurveyNotSubmittedWithNetworkError"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveyNotSubmittedWithNetworkError event");
            }
        }

        @Override
        public void onDigitalSurveyStatusRetrieved(String feedbackName, boolean enabled) {
            Log.d(sTag, "onDigitalSurveyStatusRetrieved");
            try {
                JSONObject jsonObject = new JSONObject().put("event", "onDigitalSurveyStatusRetrieved");
                
                jsonObject.put("feedbackName", feedbackName);
                jsonObject.put("enabled", enabled ? "true" : "false");
                
                onEvent(new JSONObject().put("event", "onDigitalSurveyStatusRetrieved"));
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onDigitalSurveyStatusRetrieved event");
            }
        }

        /**
         * Dispatch event results
         *
         * @param eventMsg
         */
        private void onEvent(JSONObject eventMsg) {
            PluginResult result = new PluginResult(PluginResult.Status.OK, eventMsg);
            result.setKeepCallback(true);
            for (CallbackContext c : mDigitalCallbacks) {
                if (c != null) {
                    c.sendPluginResult(result);
                }
                else {
                }
            }
        }
    }
}
