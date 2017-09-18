/********* ForeSeeAPI.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface ForeSeeAPI : CDVPlugin {
  // Member variables go here.
}

- (void)checkEligibility:(CDVInvokedUrlCommand*)command;

- (void)showSurvey:(CDVInvokedUrlCommand*)command;

- (void)showInivite:(CDVInvokedUrlCommand*)command;

@end

@implementation ForeSeeAPI

- (void)checkEligibility:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;

    [ForeSee checkIfEligibleForSurvey];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)showSurvey:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* surveyId = [command.arguments objectAtIndex:0];

    if (surveyId != nil && [surveyId length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)showInvite:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* surveyId = [command.arguments objectAtIndex:0];

    if (surveyId != nil && [surveyId length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


@end
