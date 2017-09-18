# foresee-sdk-cordova-plugin

* Add the ForeSee plugin to your project 

`cordova plugin add https://github.com/foreseecode/foresee-sdk-cordova-plugin`

> this will automaticly add `    compile "com.foresee.sdk:sdk:+"` to your `build.gradle` file 

* Add a new Application class to your project and and AndroidManifest.xml. Same as described in our [Android Getting Started](https://developer.foresee.com/docs/tutorial)

> skip this step if you already have one


* Call the `ForeSee.start(this)` in your Application class within you `onCreate` method

* Add `foresee_configuration.json` file in your `res/raw` folder as described [Android Getting Started](https://developer.foresee.com/docs/tutorial)

* Now you can use `cordova.plugins.ForeSeeAPI` in your JavaScript code

* Currently supported methods:
   * `ForeSeeAPI.checkEligibility(success, failure)`
   * `ForeSeeAPI.showSurvey(surveyId, success, failure)`
   * `ForeSeeAPI.checkEligibility(surveyId, success, failure)`
   

