# foresee-sdk-cordova-plugin

## Support 

* cordova-android": "^6.2.3"
* cordova-ios": "^4.4.0"

----
## Android

* Add the ForeSee plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin`
   ```

   > this will automaticly add `compile "com.foresee.sdk:sdk:+"` to your `build.gradle` file

* All initial steps are same as described in our  [Android Getting Started](https://developer.foresee.com/docs/tutorial)

* Add a new Application class to your project and AndroidManifest.xml.

   > skip this step if you already have one

* Add `foresee_configuration.json` file in your `res/raw`.

* Call the `ForeSee.start(this)` in your Application class within your `onCreate` method

* Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code for example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(_onSuccess, _onFailure);`
   ```

* For all suported methods please check out official portal [ForeSee Developer Portal](https://developer.foresee.com)
   
----
## iOS

* Add the ForeSee plugin to your project 

   ```
   cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin
   ```

* Add `foresee_configuration.json` file to your iOS project.

* Use `cordova.plugins.ForeSeeAPI.start(success, error)` to initialize the ForeSee SDK.
`deviceready` event is a good place for this.

* Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code for example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(_onSuccess, _onFailure);`
   ```

* For all suported methods please check out official portal [ForeSee Developer Portal](https://developer.foresee.com)