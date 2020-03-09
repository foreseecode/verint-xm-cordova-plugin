/** ForeSeeAPI.h 
 *  ForeSee Cordova Plugin Interface
 */

#import <Cordova/CDV.h>
#import <ForeSee/ForeSee.h>
#import <ForeSee/FSInviteDelegate.h>
#import <ForeSeeFeedback/ForeSeeFeedback.h>

@interface ForeSeeAPI : CDVPlugin <FSInviteDelegate, ForeSeeFeedbackDelegate>

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
- (void)sendInviteListenerResult:(TRMeasure *)measure eventMessage:(NSString*)msg;
- (void)showFeedback:(CDVInvokedUrlCommand *)command;
- (void)showFeedbackForName:(CDVInvokedUrlCommand *)command;
- (void)checkIfFeedbackEnabledForName:(CDVInvokedUrlCommand *)command;
- (void)getAvailableFeedbackNames:(CDVInvokedUrlCommand *)command;
- (void)checkIfFeedbackEnabled:(CDVInvokedUrlCommand *)command;
- (void)setFeedbackListener:(CDVInvokedUrlCommand *)command;
- (void)sendFeedbackListenerResult:(NSString *)feedbackName eventMessage:(NSString*)msg;

@end
