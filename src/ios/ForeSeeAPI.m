/********* ForeSeeAPI.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <ForeSee/ForeSee.h>
#import <ForeSeeCxMeasure/FSInviteDelegate.h>

@interface ForeSeeAPI : CDVPlugin <FSInviteDelegate> {
  // Member variables go here.
    NSMutableArray* listeners;
}

// methods exposed to JS as API
- (void)showSurvey: (CDVInvokedUrlCommand *)command;

- (void)showInvite: (CDVInvokedUrlCommand *)command;

- (void)checkEligibility: (CDVInvokedUrlCommand *)command;

- (void)addCPPValue: (CDVInvokedUrlCommand *)command;

- (void)getCPPValue: (CDVInvokedUrlCommand *)command;

- (void)getAllCPPs: (CDVInvokedUrlCommand *)command;

- (void)removeCPPValue: (CDVInvokedUrlCommand *)command;

- (void)incrementPageViews: (CDVInvokedUrlCommand *)command;

- (void)incrementSignificantEvent: (CDVInvokedUrlCommand *)command;

- (void)resetState: (CDVInvokedUrlCommand *)command;

- (void)start: (CDVInvokedUrlCommand *)command;

- (void)startWithConfigurationFile: (CDVInvokedUrlCommand *)command;

- (void)startWithConfigurationJson: (CDVInvokedUrlCommand *)command;

- (void)isDebugLogEnabled: (CDVInvokedUrlCommand *)command;

- (void)getVersion: (CDVInvokedUrlCommand *)command;

- (void)getContactDetails: (CDVInvokedUrlCommand *)command;

- (void)setContactDetails: (CDVInvokedUrlCommand *)command;

- (void)getPreferredContactType: (CDVInvokedUrlCommand *)command;

- (void)setPreferredContactType: (CDVInvokedUrlCommand *)command;

- (void)customInviteDeclined: (CDVInvokedUrlCommand *)command;

- (void)customInviteAccepted: (CDVInvokedUrlCommand *)command;

- (void)setSkipPoolingCheck: (CDVInvokedUrlCommand *)command;

- (void)setDebugLogEnabled: (CDVInvokedUrlCommand *)command;

- (void)setInviteListener: (CDVInvokedUrlCommand *)command;

- (void)removeInviteListener: (CDVInvokedUrlCommand *)command;

- (void)sendInviteListenerResult:(TRMeasure *)measure eventMessage:(NSString*)msg;

- (void)showFeedback:(CDVInvokedUrlCommand *)command;

- (void)showFeedbackForName:(CDVInvokedUrlCommand *)command;

- (void)checkIfFeedbackEnabledForName:(CDVInvokedUrlCommand *)command;

- (void)getAvailableFeedbackNames:(CDVInvokedUrlCommand *)command;

- (void)checkIfFeedbackEnabled:(CDVInvokedUrlCommand *)command;

- (void)setFeedbackListener:(CDVInvokedUrlCommand *)command;

- (void)sendFeedbackListenerResult:(TRMeasure *)measure eventMessage:(NSString*)msg;

// Util method
- (FSContactType)contactTypeForString:(NSString *)string;
@end

@implementation ForeSeeAPI


- (void)pluginInitialize {
    listeners = [[NSMutableArray alloc] init];
}

- (FSContactType)contactTypeForString:(NSString *)string {
    if ([string isEqualToString:@"Email"]) {
        return kFSEmail;
    } else if ([string isEqualToString:@"PhoneNumber"]) {
        return kFSPhoneNumber;
    } else {
        return kFSUnknown;
    }
}

- (void)checkEligibility: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSeeCxMeasure checkIfEligibleForSurvey];

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
            [ForeSeeCxMeasure showSurveyForSurveyID:surveyId];
        } else {
            NSLog(@"Bad surveyId for showSurvey");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)showInvite: (CDVInvokedUrlCommand *)command
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
            [ForeSeeCxMeasure showInviteForSurveyID:surveyId];
        } else {
            NSLog(@"Bad surveyId for showInvite");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)addCPPValue: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
   
    if(arguments == nil || arguments.count < 2){
        NSLog(@"No key or value for addCPPValue");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];
        NSString* value = [command.arguments objectAtIndex:1];

        if (key != nil && [key length] > 0 && value != nil && [value length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [ForeSee setCPPValue:value forKey:key];
        } else {
            NSLog(@"Bad key or value for addCPPValue");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getCPPValue: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
   
    if(arguments == nil || arguments.count < 1){
        NSLog(@"No key for getCPPValue");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];
        
        if (key != nil && [key length] > 0) {
            NSString* value = [ForeSee CPPValueForKey:key];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:value];
        } else {
            NSLog(@"Bad key for getCPPValue");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getAllCPPs: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
   
    NSDictionary* allCPPs = [ForeSee allCPPs];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:allCPPs];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

-(void)removeCPPValue: (CDVInvokedUrlCommand *)command
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

-(void)incrementPageViews: (CDVInvokedUrlCommand *)command{
    
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSeeCxMeasure incrementPageViews];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

}


-(void)incrementSignificantEvent: (CDVInvokedUrlCommand *)command{

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
            [ForeSeeCxMeasure incrementSignificantEventCountWithKey:key];
        } else {
            NSLog(@"Bad value in incrementSignificantEvent");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

-(void)resetState: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee resetState];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)start: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSee start];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startWithConfigurationFile: (CDVInvokedUrlCommand *)command
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

- (void)startWithConfigurationJson: (CDVInvokedUrlCommand *)command
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

- (void)setDebugLogEnabled: (CDVInvokedUrlCommand *)command{
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

- (void)isDebugLogEnabled: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    BOOL result = [ForeSee isDebugLogEnabled];
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getVersion: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    NSString* version = [ForeSee version];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:version];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getContactDetails: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    NSString* result = nil;

    if (arguments == nil || arguments.count < 1) {
        NSLog(@"No data for contactType");
    } else {
        FSContactType contactType = [self contactTypeForString:[command.arguments objectAtIndex:0]];
        result = [ForeSeeCxMeasure contactDetailsForType:contactType];
    }

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setContactDetails: (CDVInvokedUrlCommand *)command{

    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    
    if(arguments == nil || arguments.count < 2 || arguments.count > 2) {
        NSLog(@"No, or too many details for setContactDetails");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    } else {
        NSString* contact = [arguments objectAtIndex:0];
        if (contact != nil && [contact length] != 0 && arguments.count == 2) {
            FSContactType contactType = [self contactTypeForString:[arguments objectAtIndex:1]];
            [ForeSeeCxMeasure setContactDetails:contact forType:contactType];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        } else {
            NSLog(@"Bad contact for setContactDetails");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getPreferredContactType: (CDVInvokedUrlCommand *)command{
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setPreferredContactType: (CDVInvokedUrlCommand *)command{

    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;
    
    if(arguments == nil || arguments.count < 1) {
        NSLog(@"Bad contact type for setPreferredContactType");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    } else {
        NSString* string = [arguments objectAtIndex:0];
        if (string != nil && [string length] != 0) {
            FSContactType contactType = [self contactTypeForString:string];
            [ForeSeeCxMeasure setPreferredContactType:contactType];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        } else {
            NSLog(@"Bad contact type for setContactDetails");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)customInviteDeclined: (CDVInvokedUrlCommand*)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSeeCxMeasure customInviteAccepted];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)customInviteAccepted: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [ForeSeeCxMeasure customInviteDeclined];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setSkipPoolingCheck: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No data for setSkipPoolingCheck");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        BOOL skip = [command.arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [ForeSeeCxMeasure setSkipPoolingCheck:skip];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setInviteListener: (CDVInvokedUrlCommand*)command{
    [listeners removeAllObjects];

    NSLog(@"Initializing the invite listener");
    [ForeSeeCxMeasure setInviteDelegate:self];

    [listeners addObject:command];
    NSLog(@"Adding an invite listener");
}

- (void)removeInviteListener: (CDVInvokedUrlCommand *)command{
    NSLog(@"Removing the invite listener");
    [ForeSeeCxMeasure setInviteDelegate:nil];
    [listeners removeAllObjects];
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
        NSDictionary* eventDictionary = @{@"event":msg, @"surveyId": measure.surveyID};
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:eventDictionary];

        [pluginResult setKeepCallback: [NSNumber numberWithBool:YES]];

        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)showFeedback: (CDVInvokedUrlCommand *)command
{
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)showFeedbackForName: (CDVInvokedUrlCommand *)command{
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)checkIfFeedbackEnabledForName: (CDVInvokedUrlCommand *)command{
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getAvailableFeedbackNames: (CDVInvokedUrlCommand *)command {
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)checkIfFeedbackEnabled: (CDVInvokedUrlCommand *)command { 
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setFeedbackListener: (CDVInvokedUrlCommand*)command{
    // Not supported
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Command not supported"];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)feedbackPresented:(NSString *)feedbackName{
    [self sendFeedbackListenerResult:feedbackName eventMessage:@"onFeedbackPresented"];
}

- (void)feedbackSubmitted:(NSString *)feedbackName {
    [self sendFeedbackListenerResult:feedbackName eventMessage:@"feedbackSubmitted"];
}

- (void)feedbackStatusRetrieved:(NSString *)feedbackName enabled:(BOOL)enabled {
    [self sendFeedbackListenerResult:feedbackName withStatus:[NSNumber numberWithBool:enabled] eventMessage:@"feedbackStatusRetrieved"];
}

- (void)feedbackNotPresentedWithDisabled:(NSString *)feedbackName {
    [self sendFeedbackListenerResult:feedbackName eventMessage:@"feedbackNotPresentedWithDisabled"];
}

- (void)feedbackNotPresentedWithNetworkError:(NSString *)feedbackName {
    [self sendFeedbackListenerResult:feedbackName eventMessage:@"feedbackNotPresentedWithNetworkError"];
}

- (void)feedbackNotSubmittedWithNetworkError:(NSString *)feedbackName {
    [self sendFeedbackListenerResult:feedbackName eventMessage:@"feedbackNotSubmittedWithNetworkError"];
}

- (void)feedbackNotSubmittedWithAbort:(NSString *)feedbackName {
    [self sendFeedbackListenerResult:feedbackName eventMessage:@"feedbackNotSubmittedWithAbort"];
}

- (void)sendFeedbackListenerResult:(NSString *)feedbackName eventMessage:(NSString*)msg{
    [self sendFeedbackListenerResult:feedbackName withStatus:nil eventMessage:@"feedbackNotSubmittedWithAbort"];    
}

- (void)sendFeedbackListenerResult:(NSString *)feedbackName withStatus:(NSNumber*)status eventMessage:(NSString*)msg{
    // Not supported
}
@end
