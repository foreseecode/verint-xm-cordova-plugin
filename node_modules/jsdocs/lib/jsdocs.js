/* ================================================================
 * jsdocs by xdf(xudafeng[at]126.com)
 *
 * first created at : Mon Jun 16 2014 16:55:47 GMT+0800 (CST)
 *
 * ================================================================
 * Copyright 2014 xdf
 *
 * Licensed under the MIT License
 * You may not use this file except in compliance with the License.
 *
 * ================================================================ */

'use strict';

var path = require('path');
var join = path.join;
var sep = path.sep;
var extname = path.extname;
var fs = require('fs');
var util = require('xutil');
var fileUtil = util.file;
var minitpl = require('minitpl');
var mkdirp = require('mkdirp').sync;
var marked = require('marked');
require('colorx');

var template = path.join(__dirname, '..', 'template', 'page.tpl');
template = fs.readFileSync(template, 'utf-8');
template = minitpl.compile(template);

var stylesheet = path.join(__dirname, '..', 'template', 'page.css');
stylesheet = fs.readFileSync(stylesheet, 'utf-8');
stylesheet = compress(stylesheet);

function compress(content) {
  return content.replace(/\r|\n|\t/g,'');
}

function JsDocs(cfg) {
  this.input = path.resolve(cfg.input);
  this.output = path.resolve(cfg.output);
  this.init();
}

var proto = JsDocs.prototype;

proto.init = function() {
  var that = this;
  this.index = [];

  if(!fileUtil.isExistedDir(that.output))  mkdirp(that.output);
  fileUtil.traversal(that.input ,function(result) {

    if(extname(result) !== '.md') return;
    var relativePath = path.relative(that.input ,result);
    that.index.push(relativePath);
  });

  // check for /index.md
  this.checkIndex();

  fileUtil.traversal(that.output ,function(result) {

    if(extname(result) !== '.htm') return;
    var relativePath = path.relative(that.output ,result);

    // remove old htm files
    //fs.unlinkSync(relativePath);
  });  
  this.generate();
  console.log('> api docs build success'.blue + ' in <<'.blue + this.output.gray + '>>'.blue);
}

proto.generate = function() {
  var that = this;
  this.index.forEach(function(i) {
    that.renderFile(i);
  });
}

proto.renderFile = function(result) {
  var that = this;
  var src = join(this.input, result);
  var text = fileUtil.readFile(src, 'utf-8');
  var dist = join(this.output, result);
  var fields = marked(text);

  var ctx = {
    title: path.basename(result),
    fields: fields,
    list: this.generateList(result),
    stylesheet:stylesheet
  }
  var dirname = path.dirname(dist);

  if(!fileUtil.isExistedDir(dirname)) mkdirp(dirname);
  fileUtil.writeFile(dist.replace('.md', '.htm'), template(ctx));
}

proto.generateList = function(result) {
  var slideList = this.index;
  var res = [];
  var dir = path.dirname(result);
  slideList.forEach(function(i) {
    res.push({
      name: i,
      href: path.relative(dir, i.replace('.md', '.htm'))
    });
  });
  return res.reverse();
}

proto.checkIndex = function() {
  var flag;
  this.index.forEach(function(i) {
    if (i === 'index.md') {
      flag = true;
    }
  });

  if(!flag) {
    fileUtil.writeFile(path.join(this.output, 'index.htm'), '<script>location.href="' + this.index.reverse()[0].replace('.md', '.htm') + '"</script>');
  }
}

module.exports = JsDocs;
