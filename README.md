# ForeSee Cordova Plugin

## Requirements 

* cordova-android: 10.1.2
* cordova-ios: 6.2.0
* Android: 21+
* iOS: 11.0+
* Verint-XM SDK
    * iOS 7.0.0
    * Android 7.0.0

----
## Setting up the plugin

To set up the plugin in your app, follow these instructions

1. Add `exp_configuration.json` file in your `www` folder. For more information please check [Configuration Options](https://developer.foresee.com/docs/configuration-1)

2. Add the ForeSee plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin.git
   ```

   This will automatically add `compile "com.foresee.sdk:sdk:+"` to your `build.gradle` file. 
   It will also copy the exp_configuration.json file to `platform/ios` and `platform/android` if they exist

   If you have a copy of this repo on disk, then you can also add the plugin to your app by pointing directly to it, like this:

   ```
   cordova plugin add <PATH_TO_THE_PLUGIN_REPO> --nofetch
   ```

3. Within the `deviceready` event handler initialize the Verint-XM SDK by invoking 

    ```
    cordova.plugins.ForeSeeAPI.start(this.onSuccess, this.onFailure);
    ```

4. Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code. For example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(this.onSuccess, this.onFailure);
   ```

5. For all supported methods please check the API docs included in this package, or [online here](http://developer.foresee.com/downloads/sdk/mobile/cordova/current/docs/index.html). For general information about the ForeSee SDK, please see the [ForeSee Developer Portal](https://developer.foresee.com/).
   
   
   
## License 
Apache License, Version 2.0 
https://www.apache.org/licenses/LICENSE-2.0
