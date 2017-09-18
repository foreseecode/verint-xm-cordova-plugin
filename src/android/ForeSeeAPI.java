package com.foresee.cordova.plugin;

import android.util.Log;

import com.foresee.sdk.ForeSee;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class ForeSeeAPI extends CordovaPlugin {

    /* Class tag for logs */
    private final  static String sTag = "FORESEE_CORDOVA";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        Log.d(sTag, "Action " + action);

        if(action.equals("showSurvey")){
            this.showSurvey(args.getString(0), callbackContext);
            return true;
        }else if(action.equals("showInvite")){
            this.showInvite(args.getString(0), callbackContext);
            return true;
        }else if(action.equals("checkEligibility")){
            this.checkEligibility(callbackContext);
            return true;
        }
        return false;
    }

    private void showSurvey(final String id, final  CallbackContext callback){

        if(null == id || id.length() < 1) return;
        
        try {
             cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    ForeSee.showSurveyForSurveyID(id);
                    callback.success();
                }
            });
        }catch (Exception ex){
            Log.d(sTag, ex.getMessage());
            callback.error(sTag + " show survey failure");
        }
    }

    private void showInvite(final String id, final CallbackContext callback){

        if(null == id || id.length() < 1) return;
        
        try {
            cordova.getActivity().runOnUiThread(new Runnable(){
                public void run(){
                    ForeSee.showInviteForSurveyID(id);
                    callback.success();
                }
            });
        }catch (Exception ex){
            Log.d(sTag, ex.getMessage());
            callback.error(sTag + " show invite failure");
        }
    }

    private void checkEligibility(CallbackContext callback){
        ForeSee.checkIfEligibleForSurvey();
        callback.success();
    }
}
