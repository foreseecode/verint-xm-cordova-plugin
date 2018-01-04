# foresee-sdk-cordova-plugin

## Support 

* cordova-android": "^6.2.3"
* cordova-ios": "^4.4.0"
* minSdkVersion="19"
* minimum iOS version is `9.0`

----

* Add `foresee_configuration.json` file in your `www` folder.

* Add the ForeSee plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin
   ```

   > this will automaticly add `compile "com.foresee.sdk:sdk:+"` to your `build.gradle` file
   > additonally, it will copy the foresee_configuration.json file to `platform/ios` and `platform/android` if exist

* Whitin the `deviceready` event handler initialize the ForeSee SDK by invoking 

    ```
    cordova.plugins.ForeSeeAPI.start(this.onSuccess);
    ```

* Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code for example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(_onSuccess, _onFailure);`
   ```

* For all suported methods please check out the official [ForeSee Developer Portal](https://developer.foresee.com)
   