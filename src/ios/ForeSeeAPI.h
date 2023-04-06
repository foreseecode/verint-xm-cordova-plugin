//
//  ForeSeeAPI.h
//  ForeSeeCordova
//
//  Created by Burkett, Wayne on 12/13/22.
//

#ifndef ForeSeeAPI_h
#define ForeSeeAPI_h

#import <Cordova/CDV.h>
#import "CDVDevice.h"
#import <EXPCore/EXPCore.h>
#import <EXPPredictive/EXPInviteDelegate.h>
#import <EXPDigital/EXPDigital-Swift.h>
#import <EXPUtilities/EXPFileUtilities.h>
#import <EXPCore/EXPConfiguration.h>

@interface ForeSeeAPI : CDVPlugin <EXPInviteDelegate, DigitalDelegate>

#pragma mark - Verint-XM interface

- (void)showSurvey:(CDVInvokedUrlCommand *)command;
- (void)showInvite:(CDVInvokedUrlCommand *)command;
- (void)checkEligibility:(CDVInvokedUrlCommand *)command;
- (void)addCPPValue:(CDVInvokedUrlCommand *)command;
- (void)getCPP:(CDVInvokedUrlCommand *)command;
- (void)getAllCPPs:(CDVInvokedUrlCommand *)command;
- (void)removeCPP:(CDVInvokedUrlCommand *)command;
- (void)incrementPageViews:(CDVInvokedUrlCommand *)command;
- (void)incrementSignificantEvent:(CDVInvokedUrlCommand *)command;
- (void)setSignificantEventCount: (CDVInvokedUrlCommand *)command;
- (void)resetSignificantEventCount: (CDVInvokedUrlCommand *)command;
- (void)resetSignificantEvents: (CDVInvokedUrlCommand *)command;
- (void)cancelPendingInvites: (CDVInvokedUrlCommand *)command;
- (void)refreshPendingInvites: (CDVInvokedUrlCommand *)command;
- (void)resetState:(CDVInvokedUrlCommand *)command;
- (void)start:(CDVInvokedUrlCommand *)command;
- (void)startWithConfigurationFile:(CDVInvokedUrlCommand *)command;
- (void)startWithConfigurationJson:(CDVInvokedUrlCommand *)command;
- (void)isDebugLogEnabled:(CDVInvokedUrlCommand *)command;
- (void)getVersion:(CDVInvokedUrlCommand *)command;
- (void)getContactDetails:(CDVInvokedUrlCommand *)command;
- (void)setContactDetails:(CDVInvokedUrlCommand *)command;
- (void)getPreferredContactType:(CDVInvokedUrlCommand *)command;
- (void)getAllContactDetails:(CDVInvokedUrlCommand *)command;
- (void)setPreferredContactType:(CDVInvokedUrlCommand *)command;
- (void)customInviteDeclined:(CDVInvokedUrlCommand *)command;
- (void)customInviteAccepted:(CDVInvokedUrlCommand *)command;
- (void)setSkipPoolingCheck:(CDVInvokedUrlCommand *)command;
- (void)setDebugLogEnabled:(CDVInvokedUrlCommand *)command;
- (void)showDigitalSurvey:(CDVInvokedUrlCommand *)command;
- (void)showDigitalSurveyForName:(CDVInvokedUrlCommand *)command;
- (void)checkIfDigitalSurveyEnabledForName:(CDVInvokedUrlCommand *)command;
- (void)getAvailableDigitalSurveyNames:(CDVInvokedUrlCommand *)command;
- (void)checkIfDigitalSurveyEnabled:(CDVInvokedUrlCommand *)command;

#pragma mark - Plugin-specific public methods

- (void)setInviteListener:(CDVInvokedUrlCommand *)command;
- (void)removeInviteListener:(CDVInvokedUrlCommand *)command;
- (void)setDigitalListener:(CDVInvokedUrlCommand *)command;
- (void)removeDigitalListener:(CDVInvokedUrlCommand *)command;
- (EXPContactType)contactTypeForString:(NSString *)string;
@end

#endif /* ForeSeeAPI_h */


