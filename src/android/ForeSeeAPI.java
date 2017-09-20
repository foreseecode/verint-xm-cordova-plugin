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
     * Initializaton of all supported ForeSee API methods
     */ {

        //showSurvey
        sActions.put("showSurvey", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, final CallbackContext callbackContext, CordovaInterface cordova) {

                try {
                    final String id = args.getString(0);

                    if (null == id || id.length() < 1) {
                        callbackContext.error("Wrong value for showSurvey");
                        return true;
                    }

                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ForeSee.showSurveyForSurveyID(id);
                            callbackContext.success();
                        }
                    });
                } catch (Exception ex) {
                    Log.d(sTag, ex.getMessage());
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
                        callbackContext.error("Wrong value for showInvite");
                        return true;
                    }

                    final String id = args.getString(0);

                    if (null == id || id.length() < 1) {
                        callbackContext.error("Wrong value for showInvite");
                        return true;
                    }

                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            ForeSee.showInviteForSurveyID(id);
                            callbackContext.success();
                        }
                    });

                } catch (Exception ex) {
                    Log.d(sTag, ex.getMessage());
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
                    if (args == null || args.length() < 1 || args.getJSONArray(0).length() < 2) {
                        callback.error("Wrong value for addCPP");
                        return true;
                    }

                    ForeSee.addCPPValue(args.getJSONArray(0).getString(0), args.getJSONArray(0).getString(1));
                    callback.success();
                } catch (Exception ex) {
                    Log.d(sTag, ex.getMessage());
                    callback.error(sTag + " show addCPPValue failure");
                }

                return true;
            }
        });

        //removeCPP
        sActions.put("removeCPPValue", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1 || args.getJSONArray(0).length() < 1) {
                        callback.error("Wrong value for removeCPP");
                        return true;
                    }

                    ForeSee.removeCPPValue(args.getString(0));
                    callback.success();
                } catch (Exception ex) {
                    Log.d(sTag, ex.getMessage());
                    callback.error(sTag + " show removeCPPValue failure");
                }

                return true;
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
                        callback.error("Wrongs value for incrementSignificantEvent");
                        return true;
                    }

                    ForeSee.incrementSignificantEventCountWithKey(args.getString(0));
                    callback.success();
                } catch (Exception ex) {
                    Log.d(sTag, ex.getMessage());
                    callback.error(sTag + "incrementSignificantEvent failure");
                }

                return true;
            }
        });


        //increment significant event
        sActions.put("resetState", new ForeSeeMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    ForeSee.resetState();
                    callback.success();
                } catch (Exception ex) {
                    Log.d(sTag, ex.getMessage());
                    callback.error(sTag + "incrementSignificantEvent failure");
                }

                return true;
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
