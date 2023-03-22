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


var FILES = [{
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
},
{
    IOS: {
        dest: 'platforms/ios/' + appName +  '/Resources/exp_logo.png',
        src: [
            'exp_logo.png',
            'www/img/exp_logo.png'
        ]
    },
    ANDROID: {
        dest: 'platforms/android/app/src/main/res/drawable/exp_logo.png',
        src: [
            'exp_logo.png',
            'www/img/exp_logo.png'
        ]
    }
},
{
    IOS: {
        dest: 'platforms/ios/' + appName +  '/Resources/exp_fcp.json',
        src: [
            'exp_fcp.json',
            'www/exp_fcp.json'
        ]
    },
    ANDROID: {
        dest: 'platforms/android/app/src/main/res/raw/exp_fcp.json',
        src: [
            'exp_fcp.json',
            'www/exp_fcp.json'
        ]
    }
}]

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

function moveFile(platform) {
    for (var i = 0; i < platform.src.length; i++) {
        var file = platform.src[i];
        if (fileExists(file)) {
                try {
                    var destFolder = platform.dest.substring(0, platform.dest.lastIndexOf("/"));
                    fs.ensureDirSync(destFolder)
                    fs.copyFileSync(platform.src[i], platform.dest)
                    console.log("Successfully moved " +platform.src[i] + " to destination: " + platform.dest);
                    foundFileInSrc = true;
                } catch (err) {
                    console.log("Error moving file to directory " + platform.src[i] + " " +  err);
                }
            break;
        }else{
            console.log(`Could not find the file: ${file} in src ${i}`);
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
    FILES.forEach ((file) => {
        moveFile(file.IOS);
    })
  }
  if (platforms.indexOf('android') !== -1 && directoryExists("platforms/android")) {
    FILES.forEach ((file) => {
        moveFile(file.ANDROID);
    })
  }
};
