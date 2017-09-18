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
        if(action.equals("init")){
            this.init(callbackContext);
            return true;
        }else if(action.equals("showSurvey")){
            this.showSurvey(args.getString(0), callbackContext);
            return true;
        }else if(action.equals("showInvite")){
            this.showInvite(args.getString(0), callbackContext);
            return true;
        }
        return false;
    }

    private void init(CallbackContext callbackContext){
        try {
            Log.d(sTag, "executing the init phase");
            ForeSee.start(cordova.getActivity().getApplication());
            callbackContext.success();
        }catch (Exception ex){
            Log.d(sTag, ex.getMessage());
            callbackContext.error("FORESEE_CORDOVA init phase");
        }
    }

    private void showSurvey(String id, CallbackContext callback){
        try {
            Log.d(sTag, "Show survey " + id);
            ForeSee.showSurveyForSurveyID(id);
            callback.success();
        }catch (Exception ex){
            Log.d(sTag, ex.getMessage());
            callback.error("FORESEE_CORDOVA show survey failure");
        }
    }

    private void showInvite(String id, CallbackContext callback){
        try {
            Log.d(sTag, "Show invite for survey " + id);
            ForeSee.showInviteForSurveyID(id);
            callback.success();
        }catch (Exception ex){
            Log.d(sTag, ex.getMessage());
            callback.error("FORESEE_CORDOVA show invite failure");
        }
    }
}
