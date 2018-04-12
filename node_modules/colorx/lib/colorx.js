/* ================================================================
 * colorx by xdf(xudafeng[at]126.com)
 *
 * first created at : Sat Jun 07 2014 11:10:37 GMT+0800 (CST)
 *
 * ================================================================
 * Copyright 2014 xdf
 *
 * Licensed under the MIT License
 * You may not use this file except in compliance with the License.
 *
 * ================================================================ */

'use strict';

//static
var reset = '\u001b[0m';
var colors = {
  red   : '\u001b[31m',
  yellow: '\u001b[33m',
  green : '\u001b[32m',
  purple: '\u001b[34m',
  pink  : '\u001b[35m',
  gray  : '\u001b[37m',
  blue  : '\u001b[36m'
}
var EMPTY = '';

function typeOf(c) {
  if (c === null || typeof c === "undefined") {
    return String(c);
  } else {
    return Object.prototype.toString.call(c).replace(/\[object |\]/g, EMPTY).toLowerCase();
  }
}

function c(i, v) {
  return colors[i] + v + reset;
}

if (!global.__colorx_loaded) {
  var polyfill = {};

  for(var i in colors) {
    (function(i) {
      polyfill[i] = {
        get: function() {
          var v = this.valueOf();

          if (typeOf(v) === 'object') {
            v = JSON.stringify(v);
          }
          return c(i, v);
        }
      }
    })(i);
  }
  Object.defineProperties(Object.prototype, polyfill);
  global.__colorx_loaded = true;
}
