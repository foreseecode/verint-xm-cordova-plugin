/* ================================================================
 * xutil by xdf(xudafeng[at]126.com)
 *
 * first created at : Wed Jun 04 2014 22:44:25 GMT+0800 (CST)
 *
 * ================================================================
 * Copyright 2014 xdf
 *
 * Licensed under the MIT License
 * You may not use this file except in compliance with the License.
 *
 * ================================================================ */

"use strict";

var util = require('util');
var _file = require('./file');
var _string = require('./string');
var Type = require('./typeof');

/**
 * noop
 */

function _noop(){
  var arg = arguments;
  return function(){
    return arg;
  };

};

/**
 * create
 */

function _create(o){
  if (Object.create) {
    return Object.create(o);
  } else {
    var F = function(){};
    F.prototype = o;
    return new F();
  }
};

/**
 * merge
 */

function _merge(a, b){
  if (a && b) {
    for (var key in b) {
      a[key] = b[key];
    }
  }
  return a;
};

/**
 * md5
 */

function _md5(){

};

/**
 * each
 */

function _each(object, fn) {
  if(!object){
    return {};
  }
  for(var i in object){
    if(object.hasOwnProperty(i)){
      fn.call(this,object[i],i);
    }
  }
  return object;
};

/**
 * augment
 */

function _augment(r,s){
  _each(s,function(v,k){
    r.prototype[k]= v;
  });
};

/**
 * slice
 */

var _slice = Array.prototype.slice;

/**
 * remove
 */

function _remove(arr, n) {
  if(n<0){
    return arr;
  }
  arr = arr.slice(0,n).concat(arr.slice(n+1,arr.length));
  return arr;
}

/**
 * indexOf
 */

function _indexOf(arr, val) {
  if (arr.indexOf) {
    return arr.indexOf(val);
  }
  var i, len = arr.length;
  for (i = 0; i < len; i++) {
    if (arr[i] === val) {
      return i;
    }
  }
  return -1;
};

/**
 * decode uri
 */

function _decode(path){
  try {
    return decodeURIComponent(path);
  } catch (err) {
    return -1;
  }
}

/**
 * nocacherequire
 */
function _noCacheRequire(p){
  ;delete require.cache[require.resolve(p)]
  return require(require.resolve(p))
}

/**
 * single partten
 */

var Util = {
  create: _create,
  merge: _merge,
  noop: _noop,
  md5: _md5,
  each: _each,
  augment: _augment,
  slice: _slice,
  remove: _remove,
  indexOf: _indexOf,
  decode: _decode,
  noCacheRequire: _noCacheRequire,
  file: _file,
  string: _string
};

/**
 * merge typeof
 */

_merge(Util, Type);

/**
 * merge origin util
 */

_merge(util, Util);

module.exports = util;



