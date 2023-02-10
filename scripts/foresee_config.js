#!/usr/bin/env node
'use strict';

/**
 * This hook copies the ForeSee config file [foresee-sdk-cordova-plugin](https://github.com/foreseecode/foresee-sdk-cordova-plugin)
 */
var fs = require('fs');
var path = require('path');

var config = fs.readFileSync('config.xml').toString();
//application name
var appName = getValue(config, 'name');

var PLATFORM = {
    IOS: {
        dest: 'platforms/ios/' + appName +  '/Resources/exp_configuration.json',
        src: [
            'exp_configuration.json',
            'www/exp_configuration.json'
        ]
    },
    ANDROID: {
        dest: 'platforms/android/app/src/main/res/raw/exp_configuration.json',
        src: [
            'exp_configuration.json',
            'www/exp_configuration.json'
        ]
    }
};

var LOGO = {
    IOS: {
        dest: 'platforms/ios/' + appName +  '/foresee_logo.png',
        src: [
            'foresee_logo.png',
            'www/img/foresee_logo.png'
        ]
    },
    ANDROID: {
        dest: 'platforms/android/app/src/main/res/drawable/foresee_logo.png',
        src: [
            'foresee_logo.png',
            'www/img/foresee_logo.png'
        ]
    }
};

fs.ensureDirSync = function (dir) {
    if (!fs.existsSync(dir)) {
        dir.split(path.sep).reduce(function (currentPath, folder) {
            currentPath += folder + path.sep;
            if (!fs.existsSync(currentPath)) {              
                fs.mkdirSync(currentPath);
            }
            return currentPath;
        }, '');
    }
};

function createConfigFile(platform) {
    for (var i = 0; i < platform.src.length; i++) {
        var file = platform.src[i];
        if (fileExists(file)) {
                try {
                    var contents = fs.readFileSync(file).toString();
                    console.log("Adding " + platform.src[i] + " contents " + contents + '\n' 
                        + " dest " + platform.dest);
                    var folder = platform.dest.substring(0, platform.dest.lastIndexOf('/'));
                    fs.ensureDirSync(folder);
                    fs.writeFileSync(platform.dest, contents);

                } catch (err) {
                    console.log("Error adding " + platform.src[i] + " " +  err);
                }
            break;
        }else{
            console.log("Could not find the config file ./www/exp_configuration.json");
        }
    }
}

function moveLogoToDirectories(platform) {
    for (var i = 0; i < platform.src.length; i++) {
        var file = platform.src[i];
        if (fileExists(file)) {
                try {
                    var fileSource = fs.createReadStream(file);
                    var fileDestination = fs.createWriteStream(platform.dest)
                    fileSource.pipe(fileDestination);
                    console.log("Successfully moved " +platform.src[i] + "to destination: " + platform.dest);
                } catch (err) {
                    console.log("Error moving logo to directory " + platform.src[i] + " " +  err);
                }
            break;
        }else{
            console.log("Could not find the logo file ./www/foresee_logo.png");
        }
    }
}

function getValue(config, name) {
    var value = config.match(new RegExp('<' + name + '>(.*?)</' + name + '>', 'i'));
    if (value && value[1]) {
        return value[1]
    } else {
        return null
    }
}

function fileExists(path) {
    try {
        return fs.statSync(path).isFile();
    } catch (e) {
        return false;
    }
}

function directoryExists(path) {
    try {
        console.log(fs + ' fs ' +  'isDirectory ' + fs.statSync(path).isDirectory());
        return fs.statSync(path).isDirectory();
    } catch (e) {
        return false;
    }
}

module.exports = function(context) {
  //get platform from the context supplied by cordova
 
  console.log("Adding the ForeSee SDK config file");
  let platforms = context.opts.cordova.platforms;
 
  // Copy key files to their platform specific folders
  if (platforms.indexOf('ios') !== -1 && directoryExists("platforms/ios")) {
    console.log('Creating the exp_configuration.json file for iOS');
    moveLogoToDirectories(LOGO.IOS);
    createConfigFile(PLATFORM.IOS);
  }
  if (platforms.indexOf('android') !== -1 && directoryExists("platforms/android")) {
    console.log('Creating the exp_configuration.json file for ANDROID');
    moveLogoToDirectories(LOGO.ANDROID);
    createConfigFile(PLATFORM.ANDROID);
  }
};
