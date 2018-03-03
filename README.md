# ForeSee Cordova Plugin

## Requirements 

* Cordova-android: 6.2.3+
* Cordova-ios: 4.4.0+
* Android: 19+
* iOS: 9.0+

----
## Setting up the plugin

To set up the plugin in your app, follow these instructions

1. Add `foresee_configuration.json` file in your `www` folder. For more information please check [Configuration Options](https://developer.foresee.com/docs/configuration-1)

2. Add the ForeSee plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin
   ```

   > this will automaticly add `compile "com.foresee.sdk:sdk:+"` to your `build.gradle` file
   > additionally, it will copy the foresee_configuration.json file to `platform/ios` and `platform/android` if exist

3. Within the `deviceready` event handler initialize the ForeSee SDK by invoking 

    ```
    cordova.plugins.ForeSeeAPI.start(this.onSuccess, this.onFailure);
    ```

4. Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code for example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(this.onSuccess, this.onFailure);
   ```

5. For all supported methods please check out the official [ForeSee Developer Portal](https://developer.foresee.com)
   
   
   
## License 
Apache License, Version 2.0 
https://www.apache.org/licenses/LICENSE-2.0
