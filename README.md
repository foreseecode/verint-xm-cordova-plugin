# Verint XM Cordova Plugin

## Requirements 

* cordova-android: 10.1.2
* cordova-ios: 6.2.0
* Android: 21+
* iOS: 11.0+
* Verint-XM SDK
    * iOS 7.0.2
    * Android 7.0.2

----
## Setting up the plugin

To set up the plugin in your app, follow these instructions

1. Add `exp_configuration.json` file in your `www` folder. For more information please check [Configuration Options](https://connect.verint.com/developers/fscxs/w/mobilesdk/24143/configuration-options)

1. Set up the required environment variables

    To install the plugin for Android, you'll need to authenticate with GitHub Packages to download our library. To do so, you'll need a personal key which can be generated from your GitHub account by following the instructions [here](https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token). The token will need the `read:packages` permission.

    Once you have that key, you should set two environment variables on your machine: `GITHUB_USERNAME` for your username, and `GITHUB_PERSONAL_KEY` for your personal key

2. Add the Verint XM Cordova Plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/verint-xm-cordova-plugin.git
   ```

   This will automatically add `compile "com.verint.xm.sdk:*:+"` to your `build.gradle` file. 
   It will also copy the exp_configuration.json file to `platform/ios` and `platform/android` if they exist

   If you have a copy of this repo on disk, then you can also add the plugin to your app by pointing directly to it, like this:

   ```
   cordova plugin add <PATH_TO_THE_PLUGIN_REPO> --nofetch
   ```

3. Within the `deviceready` event handler initialize the Verint-XM SDK by invoking 

    ```
    cordova.plugins.verint.xm.start(this.onSuccess, this.onFailure);
    ```

4. Now you can use `cordova.plugins.verint.xm` in your JavaScript code. For example:

   ```
   cordova.plugins.verint.xm.checkEligibility(this.onSuccess, this.onFailure);
   ```

5. For all supported methods please check the API docs included in this package, or [online here](http://developer.foresee.com/downloads/sdk/mobile/cordova/current/docs/index.html). For general information about the Verint SDK, please see the [Verint Developer Portal](https://connect.verint.com/).

6. Add `exp_logo.png` file in your `www/img/` folder to include a logo for the survey.

7. Add `exp_fcp.json` file in your `www` folder to use FCP configuration on startup. The structure of the `exp_fcp.json` file should include your `appId` as follows: 

   ```
   {
       "appId":"mobilesdkdevstgtest"
   }   
   ```

## Handling local notifications on iOS

The `EXIT_SURVEY` and `EXIT_INVITE` notification modes use local notifications to send surveys to the user. There are two ways to handle notifications in Cordova on iOS:

1. In your app’s native iOS classes
2. Using the `cordova-plugin-local-notification` plugin

### Native classes

#### Import `UserNotifications` and adopt the `UNUserNotificationCenterDelegate` protocol

```
#import <Cordova/CDVViewController.h>
#import <Cordova/CDVAppDelegate.h>
#import <UserNotifications/UserNotifications.h>

@interface AppDelegate : CDVAppDelegate <UNUserNotificationCenterDelegate>
@end
```

#### Register `self` as the `UNUserNotificationCenterDelegate`

```
[UNUserNotificationCenter currentNotificationCenter].delegate = self;
```

#### Handle incoming notifications

```
#pragma mark - UNUserNotificationCenterDelegate

- (void)userNotificationCenter:(UNUserNotificationCenter *)center
didReceiveNotificationResponse:(UNNotificationResponse *)response
         withCompletionHandler:(nonnull void (^)(void))completionHandler
{
  [EXPPredictive showSurveyForNotificationResponse:response];
  completionHandler();
}
```

### Using the `cordova-plugin-local-notification` plugin

Add the plugin to your project:

```
cordova plugin add cordova-plugin-local-notification
```

And then handle notifications in your app’s Javascript:

```
if (device.platform == "iOS") {
    cordova.plugins.notification.local.on("click", function (notification) {
        if (notification.EXPLocalNotificationMeasureKey != null) {
            cordova.plugins.verint.xm.showSurvey([notification.EXPLocalNotificationMeasureKey], this.onSuccess, this.onFailure);
        }
    }, this);
}
```

#### Limitations

There is a documented negative interaction when using this plugin alongside other Cordova plugins that use local notifications (e.g. Firebase Cloud Messaging.) This can cause a crash in apps that use both plugins.

[The issue has been raised](https://github.com/katzer/cordova-plugin-local-notifications/issues/1937) in the `cordova-plugin-local-notification` repository, but has no updates at the time of writing. (This issue seems to appear between the beta.2 and beta.3 versions.)

As of this moment, there is not a known workaround, and we suggest using the `CONTACT` notification method, instead, if you are a user of one of those other plugins.


   
## API Documentation generation

The JSDoc tool is used to generate API documentaion. 
Make sure to update documentation after any API changes.
See: https://github.com/jsdoc/jsdoc


## License 
Apache License, Version 2.0 
https://www.apache.org/licenses/LICENSE-2.0
