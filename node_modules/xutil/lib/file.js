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

var fs = require('fs');
var path = require('path');

//static
var EMPTY = '';

function _touch(){

}

function _rm (){

}

function _mkdir(){

}

function _chmod(p, mode){
  fs.chmodSync(p, mode);
}

function _readFile(p, encoding) {
  return fs.readFileSync(p, encoding);
}

function _writeFile(p, data){
  if(p === EMPTY) return false;
  fs.writeFileSync(p, data);
}

function _isExistedFile(p){
  if(p === EMPTY) return false;
  return fs.existsSync(p) && fs.statSync(p).isFile();
}

function _isExistedDir(p){
  if(p === EMPTY) return false;
  return fs.existsSync(p) && fs.statSync(p).isDirectory();
}

/**
 * walk target
 * @param {String} target
 * @param {Function} callback
 */
function _traversal(target, callback) {
  var dirs = fs.readdirSync(target);
  dirs.forEach(function(name) {
    var p = path.resolve(target, name);

    if (_isExistedFile(p)) {
      callback(p);
    }

    if (_isExistedDir(p)) {
      _traversal(p, callback);
    }
  })
}

var File = {
  touch: _touch,
  rm: _rm,
  mkdir :_mkdir,
  chmod: _chmod,
  isExistedFile: _isExistedFile,
  isExistedDir: _isExistedDir,
  readFile: _readFile,
  writeFile: _writeFile,
  traversal: _traversal
};

module.exports = File;



