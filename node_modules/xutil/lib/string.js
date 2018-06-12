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

/**
 * escape
 */

function _escape(c){
  return String(c)
    .replace(/&(?!\w+;)/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
};

function _trim(str){
  return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

var Str = {
  trim: _trim,
  escape: _escape
};

module.exports = Str;

