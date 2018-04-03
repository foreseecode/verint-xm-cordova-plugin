/********* ForeSeeAPI.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <ForeSee/ForeSee.h>
#import <ForeSeeReplay/ForeSeeReplay.h>
#import <ForeSee/FSInviteDelegate.h>

@interface ForeSeeAPI : CDVPlugin <FSInviteDelegate> {
  // Member variables go here.
    NSMutableArray* listeners;
}

// methods exposed to JS as API
- (void)showSurvey: (CDVInvokedUrlCommand*)command;

- (void)showInvite: (CDVInvokedUrlCommand*)command;

- (void)checkEligibility: (CDVInvokedUrlCommand*)command;

- (void)addCPPValue: (CDVInvokedUrlCommand*)command;

- (void)removeCPPValue: (CDVInvokedUrlCommand*)command;

- (void)incrementPageViews: (CDVInvokedUrlCommand*)command;

- (void)incrementSignificantEvent: (CDVInvokedUrlCommand*)command;

- (void)resetState: (CDVInvokedUrlCommand*)command;

- (void)start: (CDVInvokedUrlCommand*)command;

- (void)startWithConfigurationFile: (CDVInvokedUrlCommand*)command;

- (void)startWithConfigurationJson: (CDVInvokedUrlCommand*)command;

- (void)isDebugLogEnabled: (CDVInvokedUrlCommand*)command;

- (void)getVersion: (CDVInvokedUrlCommand*)command;

- (void)getContactDetails: (CDVInvokedUrlCommand*)command;

- (void)setContactDetails: (CDVInvokedUrlCommand*)command;

- (void)customInviteDeclined: (CDVInvokedUrlCommand*)command;

- (void)customInviteAccepted: (CDVInvokedUrlCommand*)command;

- (void)setSkipPoolingCheck: (CDVInvokedUrlCommand*)command;

- (void)setDebugLogEnabled: (CDVInvokedUrlCommand*)command;

- (void)logReplayPageChange: (CDVInvokedUrlCommand*)command;

- (void)pauseRecording: (CDVInvokedUrlCommand*)command;

- (void)resumeRecording: (CDVInvokedUrlCommand*)command;

- (void)setMaskingDebugEnabled: (CDVInvokedUrlCommand*)command;

- (void)isRecording: (CDVInvokedUrlCommand*)command;

- (void)setInviteListener: (CDVInvokedUrlCommand*)command;

- (void)sendInviteListenerResult:(TRMeasure *)measure eventMessage:(NSString*)msg;

@end

@implementation ForeSeeAPI


- (void)pluginInitialize {
    listeners = [[NSMutableArray alloc] init];
}

- (void)checkEligibility: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee checkIfEligibleForSurvey];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)showSurvey: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    
    if(arguments == nil || arguments.count < 1){
        NSLog(@"Bad surveyId for showSurvey");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* surveyId = [command.arguments objectAtIndex:0];
        if (surveyId != nil && [surveyId length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee showSurveyForSurveyID:surveyId];
        } else {
            NSLog(@"Bad surveyId for showSurvey");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)showInvite: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No surveyId for showInvite");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }else{
        NSString* surveyId = [command.arguments objectAtIndex:0];
        
        if (surveyId != nil && [surveyId length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee showInviteForSurveyID:surveyId];
        } else {
            NSLog(@"Bad surveyId for showInvite");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)addCPPValue: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
   
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No surveyId for addCPPValue");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];
        NSString* value = [command.arguments objectAtIndex:1];

        if (key != nil && [key length] > 0 && value != nil && [value length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee setCPPValue:key forKey:key];
        } else {
            NSLog(@"Bad key or value for addCPPValue");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

-(void)removeCPPValue: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
   
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No surveyId for removeCPPValue");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];

        if (key != nil && [key length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee removeCPPValueForKey:key];
        } else {
            NSLog(@"Bad value in removeCPPValue");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

-(void)incrementPageViews: (CDVInvokedUrlCommand*)command{
    
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee incrementPageViews];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

}


-(void)incrementSignificantEvent: (CDVInvokedUrlCommand*)command{

    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
   
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No surveyId for incrementSignificantEvent");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];

        if (key != nil && [key length] > 0 ) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee incrementSignificantEventCountWithKey:key];
        } else {
            NSLog(@"Bad value in incrementSignificantEvent");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

-(void)resetState: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee resetState];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)start: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee start];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startWithConfigurationFile: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No config for startWithConfigurationFile");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* configFile = [command.arguments objectAtIndex:0];
        if (configFile != nil && [configFile length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee startWithConfigurationFile:configFile];
        } else {
            NSLog(@"Bad config for startWithConfigurationFile");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }


    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startWithConfigurationJson: (CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No configJson for startWithConfigurationJson");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* jsonConfig = [command.arguments objectAtIndex:0];
        if (jsonConfig != nil && [jsonConfig length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee startWithConfigurationJson:jsonConfig];
        } else {
            NSLog(@"Bad configJson for startWithConfigurationJson");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)isDebugLogEnabled: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    BOOL result = [ForeSee isDebugLogEnabled];
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getVersion: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    NSString* version = [ForeSee version];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:version];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getContactDetails: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    NSString* result = [ForeSee contactDetails];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setContactDetails: (CDVInvokedUrlCommand*)command{

    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No contact for setContactDetails");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* contact = [command.arguments objectAtIndex:0];
        if (contact != nil && [contact length] > 0) {
            [ForeSee setContactDetails:contact];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        } else {
            NSLog(@"Bad contact for setContactDetails");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)customInviteDeclined: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee customInviteAccepted];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)customInviteAccepted: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee customInviteDeclined];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setSkipPoolingCheck: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No data for setSkipPoolingCheck");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        BOOL skip = [command.arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [ForeSee setSkipPoolingCheck:skip];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)setDebugLogEnabled: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No data for setDebugLogEnabled");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        BOOL enable = [command.arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [ForeSee setDebugLogEnabled:enable];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)logReplayPageChange: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No page name for logReplayPageChange");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* page = [command.arguments objectAtIndex:0];
        if (page != nil && [page length] > 0) {
            [ForeSeeReplay logReplayPageChange:page];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        } else {
            NSLog(@"Bad page name for logReplayPageChange");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setMaskingDebugEnabled: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No data for setMaskingDebugEnabled");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        BOOL enable = [command.arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        if ([ForeSeeReplay respondsToSelector:@selector(setMaskingDebugEnabled:)]) {
            NSLog(@"setMaskingDebugEnabled found");
            [ForeSeeReplay setMaskingDebugEnabled:enable];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        }
        else {
            NSLog(@"setMaskingDebugEnabled not found");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)isRecording: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    BOOL result = [ForeSeeReplay isRecording];
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)pauseRecording: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    NSLog(@"pauseRecording is not available on iOS and will have no effect");
    //[ForeSee pauseRecording]; - there is no Pause in Recording for iOS

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)resumeRecording: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    NSLog(@"resumeRecording is not available on iOS and will have no effect");
    //[ForeSee resumeRecording]; - there is no Resume in Recording for iOS

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setInviteListener: (CDVInvokedUrlCommand*)command{

    if(listeners.count < 1){
        NSLog(@"Initializing the invite listener");
        [ForeSee setInviteDelegate:self];
    }

    [listeners addObject:command];
    NSLog(@"Adding an invite listener");
}

- (void)willNotShowInviteWithEligibilityFailedForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteNotShownWithEligibilityFailed"];
}

- (void)willNotShowInviteWithSamplingFailedForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteNotShownWithSamplingFailed"];
}

- (void)didShowInviteForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInvitePresented"];
}

- (void)didAcceptInviteForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteCompleteWithAccept"];
}

- (void)didDeclineInviteForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteCompleteWithDecline"];
}

- (void)didShowSurveyForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyPresented"];
}

- (void)didCancelSurveyForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyCancelledByUser"];
}

- (void)didCompleteSurveyForMeasure:(TRMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyCompleted"];
}

- (void)didFailForMeasure:(TRMeasure *)measure withNetworkError:(NSError *)error{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyCancelledWithNetworkError"];
}

- (void)sendInviteListenerResult:(TRMeasure *)measure eventMessage:(NSString*)msg{

    CDVPluginResult* pluginResult = nil;

    for(CDVInvokedUrlCommand* command in listeners){

        NSLog(@"Returning callback for %@", msg);
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:msg];

        [pluginResult setKeepCallback: [NSNumber numberWithBool:YES]];

        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

@end
