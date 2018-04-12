/* ================================================================
 * colorx by xdf(xudafeng[at]126.com)
 *
 * first created at : Sat Jun 07 2014 11:10:37 GMT+0800 (CST)
 *
 * ================================================================
 * Copyright 2013 xdf
 *
 * Licensed under the MIT License
 * You may not use this file except in compliance with the License.
 *
 * ================================================================ */

"use strict";

var colorx = require('../');

var str = 'colorx';
var obj = {
  author: 'xdf'
};
var func = function(){
  console.log('colorx');
  var c = function(){
  }
};
console.log(str.red, '\n', obj.yellow, '\n', func.blue);
