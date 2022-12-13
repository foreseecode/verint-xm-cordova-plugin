/********* ForeSeeAPI.m Cordova Plugin Implementation *******/

#import "ForeSeeAPI.h"

@implementation ForeSeeAPI


- (void)pluginInitialize {
    listeners = [[NSMutableArray alloc] init];
}

- (EXPContactType)contactTypeForString:(NSString *)string {
    if ([string isEqualToString:@"Email"]) {
        return kEXPEmail;
    } else if ([string isEqualToString:@"PhoneNumber"]) {
        return kEXPPhoneNumber;
    } else {
        return kEXPUnknown;
    }
}

- (void)checkEligibility: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [EXPPredictive checkIfEligibleForSurvey];

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
            [EXPPredictive showSurveyForSurveyID:surveyId];
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
            [EXPPredictive showInviteForSurveyID:surveyId];
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
            [EXPCore setCPPValue:value forKey:key];
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
            NSString* value = [EXPCore CPPValueForKey:key];
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

    NSDictionary* allCPPs = [EXPCore allCPPs];
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
            [EXPCore removeCPPValueForKey:key];
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

    [EXPPredictive incrementPageViews];

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
            [EXPPredictive incrementSignificantEventCountWithKey:key];
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

    [EXPCore resetState];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)start: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [EXPCore start];

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
            [EXPCore startWithConfigurationFile:configFile];
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
            [EXPCore startWithConfigurationJson:jsonConfig];
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
        [EXPCore setDebugLogEnabled:enable];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)isDebugLogEnabled: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    BOOL result = [EXPCore isDebugLogEnabled];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:result];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getVersion: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    NSString* version = [EXPCore version];

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
        EXPContactType contactType = [self contactTypeForString:[command.arguments objectAtIndex:0]];
        result = [EXPPredictive contactDetailsForType:contactType];
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
            EXPContactType contactType = [self contactTypeForString:[arguments objectAtIndex:1]];
            [EXPPredictive setContactDetails:contact forType:contactType];
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
            EXPContactType contactType = [self contactTypeForString:string];
            [EXPPredictive setPreferredContactType:contactType];
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

    [EXPPredictive customInviteAccepted];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)customInviteAccepted: (CDVInvokedUrlCommand *)command{
    CDVPluginResult* pluginResult = nil;

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [EXPPredictive customInviteDeclined];

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
        [EXPPredictive setSkipPoolingCheck:skip];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setInviteListener: (CDVInvokedUrlCommand*)command{
    [listeners removeAllObjects];

    NSLog(@"Initializing the invite listener");
    [EXPPredictive setInviteDelegate:self];

    [listeners addObject:command];
    NSLog(@"Adding an invite listener");
}

- (void)removeInviteListener: (CDVInvokedUrlCommand *)command{
    NSLog(@"Removing the invite listener");
    [EXPPredictive setInviteDelegate:nil];
    [listeners removeAllObjects];
}

- (void)willNotShowInviteWithEligibilityFailedForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteNotShownWithEligibilityFailed"];
}

- (void)willNotShowInviteWithSamplingFailedForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteNotShownWithSamplingFailed"];
}

- (void)didShowInviteForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInvitePresented"];
}

- (void)didAcceptInviteForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteCompleteWithAccept"];
}

- (void)didDeclineInviteForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onInviteCompleteWithDecline"];
}

- (void)didShowSurveyForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyPresented"];
}

- (void)didCancelSurveyForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyCancelledByUser"];
}

- (void)didCompleteSurveyForMeasure:(EXPMeasure *)measure{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyCompleted"];
}

- (void)didFailForMeasure:(EXPMeasure *)measure withNetworkError:(NSError *)error{
    [self sendInviteListenerResult:measure eventMessage:@"onSurveyCancelledWithNetworkError"];
}

- (void)sendInviteListenerResult:(EXPMeasure *)measure eventMessage:(NSString*)msg{

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
