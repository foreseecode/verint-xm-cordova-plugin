//
//  ForeSeeAPI.h
//  ForeSeeCordova
//
//  Created by Burkett, Wayne on 12/13/22.
//

#ifndef ForeSeeAPI_h
#define ForeSeeAPI_h

#import <Cordova/CDV.h>
#import <EXPCore/EXPCore.h>
#import <EXPPredictive/EXPInviteDelegate.h>

@interface ForeSeeAPI : CDVPlugin <EXPInviteDelegate> {
  // Member variables go here.
    NSMutableArray* listeners;
}

// methods exposed to JS as API
- (void)showSurvey:(CDVInvokedUrlCommand *)command;
- (void)showInvite:(CDVInvokedUrlCommand *)command;
- (void)checkEligibility:(CDVInvokedUrlCommand *)command;
- (void)addCPPValue:(CDVInvokedUrlCommand *)command;
- (void)getCPPValue:(CDVInvokedUrlCommand *)command;
- (void)getAllCPPs:(CDVInvokedUrlCommand *)command;
- (void)removeCPPValue:(CDVInvokedUrlCommand *)command;
- (void)incrementPageViews:(CDVInvokedUrlCommand *)command;
- (void)incrementSignificantEvent:(CDVInvokedUrlCommand *)command;
- (void)resetState:(CDVInvokedUrlCommand *)command;
- (void)start:(CDVInvokedUrlCommand *)command;
- (void)startWithConfigurationFile:(CDVInvokedUrlCommand *)command;
- (void)startWithConfigurationJson:(CDVInvokedUrlCommand *)command;
- (void)isDebugLogEnabled:(CDVInvokedUrlCommand *)command;
- (void)getVersion:(CDVInvokedUrlCommand *)command;
- (void)getContactDetails:(CDVInvokedUrlCommand *)command;
- (void)setContactDetails:(CDVInvokedUrlCommand *)command;
- (void)getPreferredContactType:(CDVInvokedUrlCommand *)command;
- (void)setPreferredContactType:(CDVInvokedUrlCommand *)command;
- (void)customInviteDeclined:(CDVInvokedUrlCommand *)command;
- (void)customInviteAccepted:(CDVInvokedUrlCommand *)command;
- (void)setSkipPoolingCheck:(CDVInvokedUrlCommand *)command;
- (void)setDebugLogEnabled:(CDVInvokedUrlCommand *)command;
- (void)setInviteListener:(CDVInvokedUrlCommand *)command;
- (void)removeInviteListener:(CDVInvokedUrlCommand *)command;
- (void)sendInviteListenerResult:(EXPMeasure *)measure eventMessage:(NSString*)msg;
- (void)showFeedback:(CDVInvokedUrlCommand *)command;
- (void)showFeedbackForName:(CDVInvokedUrlCommand *)command;
- (void)checkIfFeedbackEnabledForName:(CDVInvokedUrlCommand *)command;
- (void)getAvailableFeedbackNames:(CDVInvokedUrlCommand *)command;
- (void)checkIfFeedbackEnabled:(CDVInvokedUrlCommand *)command;
- (void)setFeedbackListener:(CDVInvokedUrlCommand *)command;
- (void)sendFeedbackListenerResult:(NSString *)measure eventMessage:(NSString*)msg;

// Util method
- (EXPContactType)contactTypeForString:(NSString *)string;
@end

#endif /* ForeSeeAPI_h */
