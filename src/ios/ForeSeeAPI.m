/********* ForeSeeAPI.m Cordova Plugin Implementation *******/

#import "ForeSeeAPI.h"

NSString* const platformNameKey = @"crossPlatformName";
NSString* const platformSDKVersionKey = @"crossPlatformSDKVersion";
NSString* const platformOSVersionKey = @"crossPlatformOSVersion";
NSString* const platformVersionKey = @"crossPlatformVersion";
NSString* const version = @"2.0.0";

@interface ForeSeeAPI ()

@property (nonatomic) CDVInvokedUrlCommand *inviteListenerCommand;
@property (nonatomic) CDVInvokedUrlCommand *feedbackListenerCommand;

@end

@implementation ForeSeeAPI

#pragma mark - Cordova

- (void)pluginInitialize {
    [EXPPredictive setInviteDelegate:self];
    [DigitalComponent setDelegate:self];
    [EXPCore start];
    [self addCrossPlatformCPPs];
}

#pragma mark - Helpers

- (EXPContactType)contactTypeForString:(NSString *)string {
    if ([string isEqualToString:@"Email"]) {
        return kEXPEmail;
    } else if ([string isEqualToString:@"PhoneNumber"]) {
        return kEXPPhoneNumber;
    } else {
        return kEXPUnknown;
    }
}

- (NSDictionary<NSString *, NSString *> *)convertFrom:(NSDictionary<NSNumber *, NSString *> *)fromDictionary {
    NSMutableDictionary<NSString *, NSString *> *toDictionary = [NSMutableDictionary dictionaryWithCapacity:[fromDictionary count]];
    [fromDictionary enumerateKeysAndObjectsUsingBlock:^(NSNumber *key, NSString *obj, BOOL *stop) {
        [toDictionary setObject:obj forKey:[key stringValue]];
    }];
    return toDictionary;
}

#pragma mark - Public interface

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

- (void)getCPP: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No key for getCPP");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];

        if (key != nil && [key length] > 0) {
            NSString* value = [EXPCore CPPValueForKey:key];
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:value];
        } else {
            NSLog(@"Bad key for getCPP");
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getAllCPPs: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSDictionary* allCPPs = [EXPCore allCPPs];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:allCPPs];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

-(void)removeCPP: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = nil;
    NSArray* arguments = command.arguments;

    if(arguments == nil || arguments.count < 1){
        NSLog(@"No surveyId for removeCPP");
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    else{
        NSString* key = [command.arguments objectAtIndex:0];

        if (key != nil && [key length] > 0) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [EXPCore removeCPPValueForKey:key];
        } else {
            NSLog(@"Bad value in removeCPP");
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

- (void)addCrossPlatformCPPs {
  [EXPCore setCPPValue:@"Cordova iOS" forKey:platformNameKey];
  [EXPCore setCPPValue:[CDVDevice cordovaVersion] forKey:platformSDKVersionKey];
  [EXPCore setCPPValue:[[UIDevice currentDevice] systemVersion] forKey:platformOSVersionKey];
  [EXPCore setCPPValue:version forKey:platformVersionKey];
}

- (void)start: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    NSLog(@"The start() API for iOS is not available in Cordova implementations. The SDK will start automatically on app launch");
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startWithConfigurationFile: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    NSLog(@"The startWithConfigurationFile() API for iOS is not available in Cordova implementations. The SDK will start automatically on app launch");
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startWithConfigurationJson: (CDVInvokedUrlCommand *)command
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    NSLog(@"The startWithConfigurationJson() API for iOS is not available in Cordova implementations. The SDK will start automatically on app launch");
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

- (void)getAllContactDetails:(CDVInvokedUrlCommand *)command{
  NSDictionary<NSString *, NSString *> *result = [self convertFrom:[EXPPredictive allContactDetails]];
  if (result) {
      CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  } else {
      [self sendErrorResultForCommand:command];
  }
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

#pragma mark - Invite listener helpers

- (void)setInviteListener:(CDVInvokedUrlCommand *)command {
    self.inviteListenerCommand = command;
}

- (void)removeInviteListener:(CDVInvokedUrlCommand *)command {
    self.inviteListenerCommand = nil;
}

- (void)sendInviteListenerResult:(EXPMeasure *)measure eventMessage:(NSString *)msg {
    if (!self.inviteListenerCommand) {
        return;
    }
    NSDictionary *eventDictionary = @{@"event":msg, @"surveyId": measure.surveyID};
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:eventDictionary];
    [pluginResult setKeepCallback:[NSNumber numberWithBool:YES]];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.inviteListenerCommand.callbackId];
}

#pragma mark - Digital Surveys

- (void)showDigitalSurvey:(CDVInvokedUrlCommand *)command {
    [DigitalComponent showDigitalSurvey];
    [self sendOKResultForCommand:command];
}

- (void)showDigitalSurveyForName:(CDVInvokedUrlCommand *)command {
    if ([self validate:command argCount:1]) {
        NSString *feedbackName = command.arguments[0];
        [DigitalComponent showDigitalSurveyForName:feedbackName];
        [self sendOKResultForCommand:command];
    }
}

- (void)checkIfDigitalSurveyEnabled:(CDVInvokedUrlCommand *)command {
    [DigitalComponent checkIfDigitalSurveyEnabled];
    [self sendNoResultResultForCommand:command];
}

- (void)checkIfDigitalSurveyEnabledForName:(CDVInvokedUrlCommand *)command {
    if ([self validate:command argCount:1]) {
        NSString *feedbackName = command.arguments[0];
        [DigitalComponent checkIfDigitalSurveyEnabledForName:feedbackName];
        [self sendNoResultResultForCommand:command];
    }
}

- (void)getAvailableDigitalSurveyNames:(CDVInvokedUrlCommand *)command {
    NSArray<NSString *> *result = [DigitalComponent availableDigitalSurveyNames];
    if (result) {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:result];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        [self sendErrorResultForCommand:command];
    }
}

#pragma mark - <DigitalDelegate>

- (void)digitalSurveyPresented:(NSString *)surveyName {
    [self sendDigitalListenerResult:surveyName eventMessage:@"digitalSurveyPresented"];
}

- (void)digitalSurveySubmitted:(NSString *)surveyName {
    [self sendDigitalListenerResult:surveyName eventMessage:@"digitalSurveySubmitted"];
}

- (void)digitalSurveyStatusRetrieved:(NSString *)surveyName enabled:(BOOL)enabled {
    [self sendDigitalListenerResult:surveyName withStatus:[NSNumber numberWithBool:enabled] eventMessage:@"digitalSurveyStatusRetrieved"];
}

- (void)digitalSurveyNotPresentedWithDisabled:(NSString *)surveyName {
    [self sendDigitalListenerResult:surveyName eventMessage:@"digitalSurveyNotPresentedWithDisabled"];
}

- (void)digitalSurveyNotPresentedWithNetworkError:(NSString *)surveyName {
    [self sendDigitalListenerResult:surveyName eventMessage:@"digitalSurveyNotPresentedWithNetworkError"];
}

- (void)digitalSurveyNotSubmittedWithNetworkError:(NSString *)surveyName {
    [self sendDigitalListenerResult:surveyName eventMessage:@"digitalSurveyNotSubmittedWithNetworkError"];
}

- (void)digitalSurveyNotSubmittedWithAbort:(NSString *)surveyName {
    [self sendDigitalListenerResult:surveyName eventMessage:@"digitalSurveyNotSubmittedWithAbort"];
}

#pragma mark - Digital Survey listener helpers

- (void)setDigitalListener:(CDVInvokedUrlCommand *)command {
    self.feedbackListenerCommand = command;
}

- (void)removeDigitalListener:(CDVInvokedUrlCommand *)command {
    self.feedbackListenerCommand = nil;
}

- (void)sendDigitalListenerResult:(NSString *)surveyName eventMessage:(NSString *)msg {
    [self sendDigitalListenerResult:surveyName withStatus:nil eventMessage:msg];
}

- (void)sendDigitalListenerResult:(NSString *)surveyName withStatus:(NSNumber*)status eventMessage:(NSString *)msg {
    if (!self.feedbackListenerCommand) {
        return;
    }
    NSDictionary *eventDictionary = @{@"event":msg, @"feedbackName":surveyName, @"status":status};
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                                  messageAsDictionary:eventDictionary];
    [pluginResult setKeepCallback:[NSNumber numberWithBool:YES]];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.feedbackListenerCommand.callbackId];
}

#pragma mark - Cordova command validation

- (BOOL)validate:(CDVInvokedUrlCommand *)command argCount:(int)count {
    return [self validate:command argCount:count sendErrorResult:YES];
}

- (BOOL)validate:(CDVInvokedUrlCommand *)command argCount:(int)count sendErrorResult:(BOOL)sendError {
    if (command && command.arguments && command.arguments.count >= count) {
        BOOL foundEmptyArg = NO;
        for (id arg in command.arguments) {
            if ([arg respondsToSelector:@selector(length)] && [arg length] <= 0) {
                foundEmptyArg = YES;
                break;
            }
        }
        if (!foundEmptyArg) {
            return YES;
        }
    }
    if (sendError) {
        [self sendErrorResultForCommand:command message:@"Invalid argument array"];
    }
    return NO;
}

- (void)sendOKResultForCommand:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK]
                                callbackId:command.callbackId];
}

- (void)sendNoResultResultForCommand:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_NO_RESULT]
                                callbackId:command.callbackId];
}

- (void)sendErrorResultForCommand:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR]
                                callbackId:command.callbackId];
}

- (void)sendErrorResultForCommand:(CDVInvokedUrlCommand *)command message:(NSString *)message {
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                                                             messageAsString:message]
                                callbackId:command.callbackId];
}

@end

