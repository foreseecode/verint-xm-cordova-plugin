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

* Within the `onCreate` method of the `MainActivity.java` class that was generated, initialize the ForeSee SDK by invoking `ForeSee.start(getApplication())`.

``` @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }
        loadUrl(launchUrl);

        ForeSee.start(getApplication());
    }
``` 

* Add `foresee_configuration.json` file in your `res/raw`.

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

* Use 

```
cordova.plugins.ForeSeeAPI.start(success, error)
``` 
to initialize the ForeSee SDK. `deviceready` event is a good place for this.

* Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code for example:

   ```
   cordova.plugins.ForeSeeAPI.checkEligibility(_onSuccess, _onFailure);
   ```

* For all suported methods please check out official portal [ForeSee Developer Portal](https://developer.foresee.com)