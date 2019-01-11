package com.foresee.cordova.plugin;

import android.util.Log;

import com.foresee.sdk.ForeSee;
import com.foresee.sdk.cxMeasure.tracker.listeners.BaseInviteListener;
import com.foresee.sdk.common.configuration.MeasureConfiguration;
import com.foresee.sdk.cxMeasure.tracker.listeners.DefaultInviteListener;
import com.foresee.sdk.common.configuration.EligibleMeasureConfigurations;
import com.foresee.sdk.common.configuration.ContactType;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.cordova.CallbackContext;

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

    HashMap<String, ForeSeeMethod> sActions = new HashMap<String, ForeSeeMethod>();
    Set<CallbackContext> mCallbacks = Collections
            .synchronizedSet(new HashSet<CallbackContext>());

    /**
     * Initialization of all supported ForeSee API methods
     */
    {

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

                    if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
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
                            callback.success(ForeSee.getCPPValue(key));
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
                    callback.success(new JSONObject(ForeSee.getAllCPPs()));
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
                    if (args == null || args.length() < 1) {
                        callback.success(ForeSee.getContactDetails());
                    } else {
                        ContactType contactType = contactTypeForString(args.getString(0));
                        callback.success(ForeSee.getContactDetails(contactType));
                    }
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
                        ForeSee.setContactDetails(contactType, contact);
                        callback.success();
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

        //getPreferredContactType
        sActions.put("getPreferredContactType", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(ForeSee.getPreferredContactType().name());
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
                        ForeSee.setPreferredContactType(contactType);
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
                    ForeSee.setInviteListener(new FSCordovaInviteListener());
                    
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
                ForeSee.setInviteListener(null);
                mCallbacks.clear();
            } catch (Exception ex) {
                Log.e(sTag, ex.getMessage());
                callback.error(sTag + "removeInviteListener failure");
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
        if (!ForeSee.isForeSeeStarted()) {
            Log.d(sTag, "init the ForeSee SDK");
            ForeSee.start(cordova.getActivity().getApplication());
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
                JSONObject jsonObject = new JSONObject().put("event", "onInviteNotShownWithNetworkError");

                // This is intended to enable forwards compatibility; 
                // eligibleMeasures is null in v5.0.0 of the Android SDK, but will be added in future
                if (validChosenMeasure(eligibleMeasures)) {
                    jsonObject.put("surveyId", eligibleMeasures.getChosenEligibleMeasureConfiguration().getSurveyId());
                }
                onEvent(jsonObject);
            } catch (JSONException e) {
                Log.e(sTag, "Failed to return onInviteNotShownWithNetworkError event");
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
}
