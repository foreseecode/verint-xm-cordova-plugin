/* ================================================================
 * minitpl by xdf(xudafeng[at]126.com)
 *
 * first created at : Thu Jun 05 2014 15:53:38 GMT+0800 (CST)
 *
 * ================================================================
 * Copyright 2013 xdf
 *
 * Licensed under the MIT License
 * You may not use this file except in compliance with the License.
 *
 * ================================================================ */

"use strict";

var fs = require('fs');
var minitpl = require('../');
var path = require('path');
var root = process.cwd();

var tpl = path.join(root,'test.tpl')
var data = fs.readFileSync(tpl, 'utf-8')

tpl = minitpl.compile(data)

console.log(tpl)
var content = tpl({'list' : [1,2,3]});
console.log(content)
