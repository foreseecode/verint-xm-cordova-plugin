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

'use strict';

function _type(type) {
  return function(obj) {
    return {}.toString.call(obj) === '[object ' + type + ']'.toLowerCase();
  }
}

var Type = {
  isString: _type('String'),
  isObject: _type('Object'),
  isFunction: _type('Function'),
  isArray: Array.isArray || _type('Array'),
  isNumber: _type('number')
};

module.exports = Type;

