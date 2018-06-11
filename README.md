# foresee-sdk-cordova-plugin

## Support 

* Cordova-android: 6.2.3+
* Cordova-ios: 4.4.0+
* Android: 19+
* iOS: 9.0+
* ForeSee SDK
   * iOS 5.0.2
   * Android 5.0.0

----

* Add `foresee_configuration.json` file in your `www` folder.

* Add the ForeSee plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin
   ```

   > this will automaticly add `compile "com.foresee.sdk:sdk:+"` to your `build.gradle` file
   > additionally, it will copy the foresee_configuration.json file to `platform/ios` and `platform/android` if exist

* Within the `deviceready` event handler initialize the ForeSee SDK by invoking 

    ```
    cordova.plugins.ForeSeeAPI.start(this.onSuccess, this.onFailure);
    ```

* Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code for example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(this.onSuccess, this.onFailure);`
   ```

* For all supported methods please check out the [API Docs] (https://github.com/foreseecode/foresee-sdk-cordova-plugin/blob/master/out/ForeSeeAPI.js.html)
   
   
   
## License 
Apache License, Version 2.0 
https://www.apache.org/licenses/LICENSE-2.0
