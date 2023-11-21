package com.verint.xm.cordova.plugin;

import android.util.Log;
import android.os.Build;

import com.verint.xm.sdk.Core;
import com.verint.xm.sdk.Predictive;
import com.verint.xm.sdk.Digital;
import com.verint.xm.sdk.ExpDigitalListener;
import com.verint.xm.sdk.SurveyManagement;
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
import java.util.Map;
import java.io.*;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class VerintXM extends CordovaPlugin {

    /**
     * Verint API executor
     */
    abstract class VerintMethod {
        public abstract boolean invoke(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova);
    }

    // Class tag for logs
    private final static String logTag = "CordovaVerintXM";

    // CPPs
    private final String version = "3.0.1";

    private final String EXP_FCP_JSON_FILE_NAME = "exp_fcp";
    private final String APP_VERSION = "mobsdk";

    HashMap<String, VerintMethod> sActions = new HashMap<String, VerintMethod>();
    Set<CallbackContext> mCallbacks = Collections
            .synchronizedSet(new HashSet<CallbackContext>());
    Set<CallbackContext> mDigitalCallbacks = Collections
            .synchronizedSet(new HashSet<CallbackContext>());

    @Override
    public void onStart() {
        super.onStart();
        if (!Core.isCoreStarted()) {

            Core.setSDKListener(new CustomVerintSDKListener());

            String appId = getAppIdFromJSON();
            Log.d(logTag, "init the Verint SDK");

            if (appId != null) {
                Log.d(logTag, "FCP startup with appId: "+appId);
                Core.startWithAppId(cordova.getActivity().getApplication(), appId, APP_VERSION);
            } else {
                Log.d(logTag, "Regular startup");
                Core.start(cordova.getActivity().getApplication());
            }

            this.addCrossPlatformCPPs();
        }
    }

    void addCrossPlatformCPPs() {
        Core.addCPPValue("crossPlatformName", "Cordova Android");
        Core.addCPPValue("crossPlatformPluginVersion", version);
        Core.addCPPValue("crossPlatformOSVersion", android.os.Build.VERSION.RELEASE);
        Core.addCPPValue("crossPlatformVersion", CordovaWebView.CORDOVA_VERSION);
        Log.d(logTag, "All CPPs (after adding cross platform CPPs): " + Core.getAllCPPs());
    }

    public String getAppIdFromJSON() {
        int identifier = cordova.getActivity().getResources().getIdentifier(EXP_FCP_JSON_FILE_NAME, "raw", cordova.getActivity().getPackageName());
        if (identifier == 0) {
            Log.d(logTag, "exp_fcp.json file does not exist");
            return null;
        }

        String jsonString = getWriter();

        Log.d(logTag, "From json file: "+jsonString);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String appId = jsonObject.getString("appId");
            Log.d(logTag, "appId: "+appId);
            return appId;
        } catch (JSONException e) {
            Log.d(logTag, "JSONException: "+e);
        }
        return null;
    }

    private String getWriter() {
        InputStream inputStream = cordova.getActivity().getResources().openRawResource(cordova.getActivity().getResources().getIdentifier(EXP_FCP_JSON_FILE_NAME, "raw", cordova.getActivity().getPackageName()));
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int number;
            while ((number = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, number);
            }
        } catch(IOException e) {
            Log.d(logTag, "IOException: "+e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                Log.e(logTag, "Exception: "+e);
            }
        }
        return writer.toString();
    }        
    
    /**
     * Initialization of all supported Verint XM API methods
     */
    {

        // Start

        //start
        sActions.put("start", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(logTag, "The start() API for ANDROID is not available in Cordova implementations. The SDK will start automatically on app launch");
                callback.success(logTag + "start() is not available");
                return true;
            }
        });

        //startWithConfigurationFile
        sActions.put("startWithConfigurationFile", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(logTag, "The startWithConfigurationFile() API for ANDROID is not available in Cordova implementations. The SDK will start automatically on app launch");
                callback.success(logTag + "start() is not available");
                return true;

            }
        });

        //startWithConfigurationJson
        sActions.put("startWithConfigurationJson", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                Log.i(logTag, "The startWithConfigurationJson() API for ANDROID is not available in Cordova implementations. The SDK will start automatically on app launch");
                callback.success(logTag + "start() is not available");
                return true;
            }
        });

        // Reset

        //resetState
        sActions.put("resetState", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    Core.resetState();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "incrementSignificantEvent failure");
                } finally {
                    return true;
                }
            }
        });

        // Check eligibility and show invites/surveys
        
        //checkEligibility
        sActions.put("checkEligibility", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
                Predictive.checkIfEligibleForSurvey();
                callbackContext.success();
                return true;
            }
        });
        
        //showInivite
        sActions.put("showInvite", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callbackContext.error(logTag + " showInvite failure");
                }

                return true;
            }
        });
        
        //showSurvey
        sActions.put("showSurvey", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callbackContext.error(logTag + " showSurvey failure");
                }

                return true;
            }
        });

        // CPPs

        //addCPP
        sActions.put("addCPPValue", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + " show addCPPValue failure");
                } finally {
                    return true;
                }
            }
        });

        //getCPP
        sActions.put("getCPP", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No key for getCPP");
                        return true;
                    } else {
                        String key = args.getString(0);
                        
                        if (key == null || key.isEmpty()) {
                            callback.error("Bad key for getCPP");
                        } else {
                            callback.success(Core.getCPPValue(key));
                        }
                    }
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getCPP failure");
                } finally {
                    return true;
                }
            }
        });

        //getAllCPPs
        sActions.put("getAllCPPs", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(new JSONObject(Core.getAllCPPs()));
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getAllCPPs failure");
                } finally {
                    return true;
                }
                
            }
        });

        //removeCPP
        sActions.put("removeCPP", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No key for removeCPP");
                        return true;
                    }

                    String key = args.getString(0);

                    if (key == null || key.isEmpty()) {
                        callback.error("Bad key for removeCPP");
                    } else {
                        Core.removeCPPValue(key);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + " show removeCPP failure");
                } finally {
                    return true;
                }
            }
        });

        // Criteria

        //increment page views
        sActions.put("incrementPageViews", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callaback, CordovaInterface cordova) {
                Predictive.incrementPageViews();
                callaback.success();
                return true;
            }
        });

        //increment significant event
        sActions.put("incrementSignificantEvent", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "incrementSignificantEvent failure");
                } finally {
                    return true;
                }
            }
        });

        sActions.put("setSignificantEventCount", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 2) {
                        callback.error("Not enough args for setSignificantEventCount");
                        return true;
                    }

                    String key = args.getString(0);
                    int count = Integer.parseInt(args.getString(1));

                    if (null == key || key.isEmpty()) {
                        callback.error("Bad key for setSignificantEventCount");
                    } else {
                        Predictive.setSignificantEventCount(key, count);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setSignificantEventCount failure");
                } finally {
                    return true;
                }
            }
        });

        sActions.put("resetSignificantEventCount", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No key arg for resetSignificantEventCount");
                        return true;
                    }

                    String key = args.getString(0);

                    if (null == key || key.isEmpty()) {
                        callback.error("Bad key for resetSignificantEventCount");
                    } else {
                        Predictive.resetSignificantEventCount(key);
                        callback.success();
                    }
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "resetSignificantEventCount failure");
                } finally {
                    return true;
                }
            }
        });

        sActions.put("resetSignificantEvents", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    Predictive.resetSignificantEvents();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "resetSignificantEvents failure");
                } finally {
                    return true;
                }
            }
        });

        sActions.put("cancelPendingInvites", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    //TODO: Update to cancelPendingNotifications when 7.0.3 artefacts are released
                    Predictive.cancelPendingInvites();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "cancelPendingInvites failure");
                } finally {
                    return true;
                }
            }
        });

        // Debugging

        //setDebugLogEnabled
        sActions.put("setDebugLogEnabled", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setDebugLogEnabled failure");
                } finally {
                    return true;
                }
            }
        });
        
        //isDebugLogEnabled
        sActions.put("isDebugLogEnabled", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(String.valueOf(Core.isDebugLogEnabled()));
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "isDebugLogEnabled failure");
                } finally {
                    return true;
                }
            }
        });

        //getVersion
        sActions.put("getVersion", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(Core.getVersion());
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getVersion failure");
                } finally {
                    return true;
                }
            }
        });

        //setSkipPoolingCheck
        sActions.put("setSkipPoolingCheck", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setSkipPoolingCheck failure");
                } finally {
                    return true;
                }
            }
        });        

        // Custom invites

        //customInviteAccepted
        sActions.put("customInviteAccepted", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    Predictive.customInviteAccepted();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "customInviteAccepted failure");
                } finally {
                    return true;
                }
            }
        });
        
        //customInviteDeclined
        sActions.put("customInviteDeclined", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    Predictive.customInviteDeclined();
                    callback.success();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "customInviteDeclined failure");
                } finally {
                    return true;
                }
            }
        });

        // Contact details

        //getContactDetails
        sActions.put("getContactDetails", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    ContactType contactType = contactTypeForString(args.getString(0));
                    callback.success(Predictive.getContactDetails(contactType));
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getContactDetails failure");
                } finally {
                    return true;
                }
            }
        });

        //setContactDetails
        sActions.put("setContactDetails", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setContactDetails failure");
                } finally {
                    return true;
                }
            }
        });

        //setPreferredContactType
        sActions.put("setPreferredContactType", new VerintMethod() {

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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setPreferredContactType failure");
                } finally {
                    return true;
                }
            }
        });        

        //getPreferredContactType
        sActions.put("getPreferredContactType", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(Predictive.getPreferredContactType().name());
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getPreferredContactType failure");
                } finally {
                    return true;
                }
            }
        });

        //getAllContactDetails
        sActions.put("getAllContactDetails", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {
                try {
                    callback.success(new JSONObject(convert(Predictive.getAllContactDetails())));
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getAllContactDetails failure");
                } finally {
                    return true;
                }

            }

        });

        // Invite Listener

        //setInviteListener
        /*
            1. Clear current callbacks
            2. Add a new listener
            3. Add a new callback to list
         */
        sActions.put("setInviteListener", new VerintMethod() {

            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    //1.
                    mCallbacks.clear();

                    //2. 
                    Predictive.setInviteListener(new EXPCordovaInviteListener());
                    
                    //3.
                    mCallbacks.add(callback);
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setInviteListener failure");
                } finally {
                    return true;
                }
            }
        });

        //removeInviteListener
        /*
            Clears any invite listeners that have been set to avoid memory leaks
         */
        sActions.put("removeInviteListener", new VerintMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Predictive.setInviteListener(null);
                    mCallbacks.clear();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "removeInviteListener failure");
                } finally {
                    return true;
                }
            }
        });

        // Digital (ex Feedback) Surveys

        // Show the default survey
        sActions.put("showDigitalSurvey", new VerintMethod() {
    
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Digital.showDigitalSurvey();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "showDigitalSurvey failure");
                } finally {
                    return true;
                }
            }
        });

        // Show the survey for a given name.
        sActions.put("showDigitalSurveyForName", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No survey name for showDigitalSurveyForName");
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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "showDigitalSurveyForName failure");
                } finally {
                    return true;
                }
            }
        });
        
        // Check if the default survey is enabled. 
        sActions.put("checkIfDigitalSurveyEnabled", new VerintMethod() {
    
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Digital.checkIfDigitalSurveyEnabled();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "checkIfDigitalSurveyEnabled failure");
                } finally {
                    return true;
                }
            }
        });
        
        // Check if a survey is enabled.
        sActions.put("checkIfDigitalSurveyEnabledForName", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, CallbackContext callback, CordovaInterface cordova) {

                try {
                    if (args == null || args.length() < 1) {
                        callback.error("No survey name for checkIfDigitalSurveyEnabledForName");
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
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "checkIfDigitalSurveyEnabledForName failure");
                } finally {
                    return true;
                }
            }
        });

        // Get all available survey names defined in the Configuration.
        sActions.put("getAvailableDigitalSurveyNames", new VerintMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    Digital.getAvailableDigitalSurveyNames();
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "getAvailableDigitalSurveyNames failure");
                } finally {
                    return true;
                }
            }
        });

        // Digital (ex Feedback) Listener

        // Set Digital listener
        /*
            1. Clear current callbacks
            2. Add a new listener
            3. Add a new callback to list
         */
        sActions.put("setDigitalListener", new VerintMethod() {

            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    //1.
                    mDigitalCallbacks.clear();

                    //2. 
                    Digital.setDigitalListener(new EXPCordovaDigitalListener());
                    
                    //3.
                    mDigitalCallbacks.add(callback);
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "setDigitalListener failure");
                } finally {
                    return true;
                }
            }
        });

        // Remove Digital listener
        /*
            Clears any Digital listeners that have been set to avoid memory leaks
         */
        sActions.put("removeDigitalListener", new VerintMethod() {
            
            @Override
            public boolean invoke(final JSONArray args, final CallbackContext callback, CordovaInterface cordova) {
                try {
                    mDigitalCallbacks.clear();
                    Digital.setDigitalListener(null);
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callback.error(logTag + "removeDigitalListener failure");
                } finally {
                    return true;
                }
            }
        });

        // Survey Management

        //showInviteForName
        sActions.put("showInviteForName", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, final CallbackContext callbackContext, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callbackContext.error("No name provided for showInviteForName");
                        return true;
                    }

                    final String name = args.getString(0);

                    if (null == name || name.isEmpty()) {
                        callbackContext.error("Bad name provided for showInviteForName");
                        return true;
                    }

                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            SurveyManagement.showInviteForName(name);
                            callbackContext.success();
                        }
                    });

                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callbackContext.error(logTag + " showInviteForName failure");
                }

                return true;
            }
        });
        
        //showSurveyForName
        sActions.put("showSurveyForName", new VerintMethod() {

            @Override
            public boolean invoke(JSONArray args, final CallbackContext callbackContext, CordovaInterface cordova) {
                try {
                    if (args == null || args.length() < 1) {
                        callbackContext.error("No name provided for showSurveyForName");
                        return true;
                    }

                    final String name = args.getString(0);

                    if (null == name || name.isEmpty()) {
                        callbackContext.error("Bad name provided for showSurveyForName");
                        return true;
                    }

                    cordova.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            SurveyManagement.showSurveyForName(name);
                            callbackContext.success();
                        }
                    });
                } catch (Exception ex) {
                    Log.e(logTag, ex.getMessage());
                    callbackContext.error(logTag + " showSurveyForName failure");
                }

                return true;
            }
        });
        
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (sActions.containsKey(action)) {
            Log.d(logTag, "Action " + action);
            return sActions.get(action).invoke(args, callbackContext, cordova);

        } else {
            Log.d(logTag, "This action is not supported");
            callbackContext.error("This action is not supported");
            return false;
        }
    }

    // Contact Type
    
    private ContactType contactTypeForString(String string) {
        ContactType result = ContactType.Unknown;
        try {
            result = ContactType.valueOf(string);
        } catch (IllegalArgumentException ex) {
            Log.e(logTag, ex.getMessage());
        }
        return result;
    }

    private Map<String, String> convert(Map<ContactType, String> from) {
        Map<String, String> to = new HashMap<String, String>();
        for (Map.Entry<ContactType, String> entry : from.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            to.put(key, value);
        }
        return to;
    }
    
    class EXPCordovaInviteListener implements DefaultInviteListener {

        @Override
        public void onInviteNotShownWithEligibilityFailed(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onInviteNotShownWithEligibilityFailed", eligibleMeasureConfigurations);
        }

        @Override
        public void onInviteNotShownWithSamplingFailed(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onInviteNotShownWithSamplingFailed", eligibleMeasureConfigurations);
        }

        @Override
        public void onInvitePresented(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onInvitePresented", eligibleMeasureConfigurations);
        }

        @Override
        public void onInviteCompleteWithAccept(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onInviteCompleteWithAccept", eligibleMeasureConfigurations);
        }

        @Override
        public void onInviteCompleteWithDecline(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onInviteCompleteWithDecline", eligibleMeasureConfigurations);
        }

        @Override
        public void onSurveyPresented(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onSurveyPresented", eligibleMeasureConfigurations);
        }

        @Override
        public void onSurveyCancelledByUser(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onSurveyCancelledByUser", eligibleMeasureConfigurations);
        }

        @Override
        public void onSurveyCompleted(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onSurveyCompleted", eligibleMeasureConfigurations);
        }

        @Override
        public void onSurveyCancelledWithNetworkError(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            sendEvent("onSurveyCancelledWithNetworkError", eligibleMeasureConfigurations);
        }

        @Override
        public void onInviteCancelledWithNetworkError(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            // Have no parity on iOS, so just stub here
        }

        @Override
        public void onInviteNotShownWithNetworkError(EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            // Have no parity on iOS, so just stub here
        }

        private void sendEvent(final String eventName, EligibleMeasureConfigurations eligibleMeasureConfigurations) {
            MeasureConfiguration measureConfiguration = null;
            if (eligibleMeasureConfigurations != null) {
                measureConfiguration = eligibleMeasureConfigurations.getChosenEligibleMeasureConfiguration();
            }
            sendEvent(eventName, measureConfiguration);
        }

        private void sendEvent(final String eventName, MeasureConfiguration measure) {
            Log.d(logTag, "InviteListener::" + eventName);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("event", eventName);
                if (measure != null) {
                    jsonObject.put("surveyId", measure.getSurveyId());
                }
                onEvent(jsonObject);
            } catch (JSONException e) {
                Log.e(logTag, "Failed to create payload for " + eventName + " event");
            }
        }

        /**
         * Dispatch event results
         *
         * @param eventMsg
         */
        private void onEvent(JSONObject eventMsg) {
            if (mCallbacks.size() == 0) {
                Log.e(logTag, "No listeners to send  event");
                return;
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, eventMsg);
            result.setKeepCallback(true);
            for (CallbackContext c : mCallbacks) {
                if (c != null) {
                    c.sendPluginResult(result);
                } else {
                    Log.e(logTag, "Failed to send event .Callback is null");
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

    class EXPCordovaDigitalListener implements ExpDigitalListener {

        @Override
        public void onDigitalSurveyPresented(String surveyName) {
            sendEvent("onDigitalSurveyPresented", surveyName);
        }

        @Override
        public void onDigitalSurveyNotPresentedWithNetworkError(String surveyName) {
            sendEvent("onDigitalSurveyNotPresentedWithNetworkError", surveyName);
        }

        @Override
        public void onDigitalSurveyNotPresentedWithDisabled(String surveyName) {
            sendEvent("onDigitalSurveyNotPresentedWithDisabled", surveyName);
        }

        @Override
        public void onDigitalSurveySubmitted(String surveyName) {
            sendEvent("onDigitalSurveySubmitted", surveyName);
        }

        @Override
        public void onDigitalSurveyNotSubmittedWithNetworkError(String surveyName) {
            sendEvent("onDigitalSurveyNotSubmittedWithNetworkError", surveyName);
        }

        @Override
        public void onDigitalSurveyNotSubmittedWithAbort(String surveyName) {
            sendEvent("onDigitalSurveyNotSubmittedWithAbort", surveyName);
        }

        @Override
        public void onDigitalSurveyStatusRetrieved(String surveyName, boolean enabled) {
            sendEvent("onDigitalSurveyStatusRetrieved", surveyName, enabled);
        }

        private void sendEvent(final String eventName, final String surveyName) {
            sendEvent(eventName, surveyName, null);
        }

        private void sendEvent(final String eventName, final String surveyName, Boolean enabled) {
            Log.d(logTag, "DigitalListener::" + eventName);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("event", eventName);
                jsonObject.put("surveyName", surveyName);
                if (enabled != null) {
                    jsonObject.put("enabled", enabled ? "true" : "false");
                }
                onEvent(jsonObject);
            } catch (JSONException e) {
                Log.e(logTag, "Failed to create payload for " + eventName + " event");
            }
        }

        /**
         * Dispatch event results
         *
         * @param eventMsg
         */
        private void onEvent(JSONObject eventMsg) {
            if (mDigitalCallbacks.size() == 0) {
                Log.e(logTag, "No listeners to send  event");
                return;
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, eventMsg);
            result.setKeepCallback(true);
            for (CallbackContext c : mDigitalCallbacks) {
                if (c != null) {
                    c.sendPluginResult(result);
                } else {
                    Log.e(logTag, "Failed to send event .Callback is null");
                }
            }
        }
    }

    class CustomVerintSDKListener implements Core.VerintSDKListener {
    
        @Override
        public void onSDKStarted() {
            Log.d(logTag, "VerintSDKListener::onSDKStarted");
            // Originally idea was to set cross platforms CPPs only in start SDK functions.
            // But looks like CPPs for Android could be
            // applied only after onSDKStarted.
            // So for now we should keep this. See: VOC-42740
            addCrossPlatformCPPs();
        }
    
        @Override
        public void onSDKStarted(Core.VerintError error, String message) {
            Log.w(logTag, "VerintSDKListener::didStartSDKWithError: " + error + " / " + message);
        }
    
        @Override
        public void onSDKFailedToStart(Core.VerintError error, String message) {
            Log.w(logTag, "VerintSDKListener::didFailToStartSDKWithError: " + error + " / " + message);
        }
        
    }

}
