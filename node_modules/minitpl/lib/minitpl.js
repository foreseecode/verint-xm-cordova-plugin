/* ================================================================
 * minitpl by xdf(xudafeng[at]126.com)
 *
 * first created at : Thu Jun 05 2014 15:53:38 GMT+0800 (CST)
 *
 * ================================================================
 * Copyright 2014 xdf
 *
 * Licensed under the MIT License
 * You may not use this file except in compliance with the License.
 *
 * ================================================================ */

"use strict";

;(function(root,factory){
  'use strict';
  /* amd like aml https://github.com/xudafeng/aml.git */
  if(typeof define === 'function' && define.amd) {
    return define(['exports'], factory);
  }else if(typeof exports !== 'undefined') {
    return factory(exports);
  }else{
    /* browser */
    factory(root['minitpl'] || (root['minitpl'] = {}));
  }
})(this,function(exports){

  /* static */
  var EMPTY = '';
  var ANONYMOUS = 'anonymous';
  var TAGS = {
    WRAP : '\n',
    BLANK: ' '
  };
  var EscapeHash = function(){
    return {
      '&':'&amp;',
      '>':'&gt;',
      '<':'&lt;',
      '"':'&quot;',
      "'":'&#x27;',
      "/":'&#x2f;'
    };
  };
  var IfStatementReg = /(?:^|\n)if\s+\S+/,
      ElseStatementReg = /(?:^|\n)else\s*/,
      ElseIfStatementReg = /(?:^|\n)elseif\s+\S+.*/,
      EachStatementReg = /(?:^|\n)each\s+\S+\s+in\s+\S+/,
      LiteralsStatementReg = /\=*\s*\$\w+([\.]\w+)*\s*/,
      IncludeStatementReg = new RegExp(''),
      StopStatementReg = /\s*stop\s*/,
      CommentStatementReg = /(?:^|\n)#[^\n]*/,
      IfAndEachEndStatementReg = /(?:^|\n)\/(each|if)\s*/,
      $StatementReg = /\$[\w\d]+/g;

  /**
   * util for wanpi
   */
  var _;
  function __(){};
  function __typeof(type){
    return function(obj){
      return Object.prototype.toString.call(obj) === '[object ' + type + ']';
    };
  };
  __.prototype = {
    log:function (l){
      CONFIG['debug'] && console && this.isObject(console.log) && console.log(l);
    },
    indexOf:function(i,s){
      return !!~s.indexOf(i);
    },
    mix:function(r,s){
      for(var i in s){
        r[i] = s[i];
      };
      return r;
    },
    each:function(object, fn) {
      if(!object){
        return;
      }
      for(var i in object){
        if(i !== 'length' && i !== 'item' && object.hasOwnProperty(i)){
          fn.call(this,object[i],i);
        }
      }
      return object;
    },
    isString:function(){
      return __typeof('String');
    },
    isObject:function(){
      return __typeof('Object');
    },
    trim:function(c){
      return c.replace(/(^\s*)|(\s*$)/g, EMPTY);
    },
    escape:function(s){
      this.each(EscapeHash(),function(v,k){
        s = s.replace(new RegExp(k,'g'),v);
      });
      return s;
    },
    unEscape:function(s){
      this.each(EscapeHash(),function(v,k){
        s = s.replace(new RegExp(v,'g'),k);
      });
      return s;
    }
  };
  /**
   * init tool
   */
  _ = new __();

  /**
   * @constructor parse
   * @param {String} source
   * @return {String} template
   */
  function Parse(cfg){
    this.source = cfg.source;
    this.init();
  }
  Parse.prototype = {
    init:function(){
      var that = this;
      that.code = EMPTY;
      that.scanner();
      that.build();
      that.compass();
      return that.code;
    },
    scanner:function(){
      var that = this,
      source = that.source,
      tagOpen = CONFIG.tags.open,
      tagClose = CONFIG.tags.close;
      _.each(source.split(tagOpen),function(i){
        i = i.split(tagClose);
        var $0 = i[0],$1 = i[1];
        if (i.length == 1) {
          that.code += that.parseStatic($0);
        } else {
          that.code += that.parseLogic($0);
          if ($1) {
            that.code += that.parseStatic($1);
          }
        }
      });
    },
    compass:function(){
      if(CONFIG.compress){
        //this.code.replace(new RegExp('\\n','g'),'');
      }
    },
    build:function(){
      var that = this;
      function buildUtil(s){
        var code = 'var _ = _||{};_.each='+_.each.toString() + ';_.escape=' + _.escape.toString() + ';' + TAGS.WRAP;
        return code + s;
      }
      this.code =
        '"use strict";'+ TAGS.WRAP +
        'var $out = \'\';' + TAGS.WRAP +
        buildUtil(that.code) + TAGS.WRAP +
        'return $out;' + TAGS.WRAP;
    },
    filter:function(s){
      var that = this;
      return s
        .replace(/\\/g, "\\\\")
        .replace(/\n/g, "\\n")
        .replace(/\r/g, "\\r")
        .replace(/\t/g, "\\t");
    },
    parseLogic:function(code){
      var _code = EMPTY;
      if(IfStatementReg.test(code)){
        _code = 'if(' + _.trim(_.trim(code).split('if')[1].replace($StatementReg,function(e){
          return '$data.' + e.split('$')[1];
        })) + '){';
      }else if(ElseIfStatementReg.test(code)){
        _code = '}else if(' + _.trim(_.trim(code).split('elseif')[1].replace($StatementReg,function(e){
          return '$data.' + e.split('$')[1];
        })) + '){';
      }else if(ElseStatementReg.test(code)){
        _code = '}else{';
      }else if(EachStatementReg.test(code)){
        var _each = _.trim(code).split(' ');
        var $val = _each[1].split(',')[0];
        var $index = _each[1].split(',')[1];
        _code = '_.each(';
        _code += '$data.' + _each[3].split('$')[1];
        _code += ',function(';
        _code += $val;
        if($index){
          _code += ',' + $index;
        }
        _code += '){' + TAGS.WRAP;
        _code += 'var $data = {};' + TAGS.WRAP;
        _code += '$data.' + $val + ' = '+ $val + ';' + TAGS.WRAP;
        if($index){
          _code += '$data.' + $index + ' = '+ $index + ';' + TAGS.WRAP;
        }
      }else if(LiteralsStatementReg.test(code)){
        if(_.indexOf('=',code)){
          _code = '$out += '+ _.escape('$data.' + code.split('=')[1].split('$')[1]) + ';';
        }else{
          _code = '$out += ' + '$data.' + code.split('$')[1] + ';';
        }
      }else if(StopStatementReg.test(code)){
        _code = 'return $out;';
      }else if(CommentStatementReg.test(code)){
        if(!CONFIG.compress){
          _code = '//' + _.trim(code).split('#')[1];
        }
      }else if(IfAndEachEndStatementReg.test(code)){
        _code = '}';
        if(_.indexOf('each',code)){
          _code += ');';
        }
      }else {
        _.log('error -----------'+ code +'-------------')
      }
      return _code + TAGS.WRAP;
    },
    parseStatic:function(code){
      var that = this;
      return '$out += \'' + that.filter(code) + '\';' + TAGS.WRAP;
    }
  }

  /**
   * @constructor render
   * @param {String} tpl
   * @param {Object} data
   * @param {Object} cfg config
   * @return {HTMLElement | null} The HTMLElement
   */
  function Render(){
    var arg = arguments;
    _.log('render template');
    /* merge config*/
    arg[2] && config(arg[2]);
    var tpl = arg[0],data = arg[1];
  }

  function _compile(id,source){
    if(id){
    }
    var res = new Parse({
      source: source
    }).init().toString();
    return new Function('$data',res);
  }
  /**
   * @constructor compile
   * @param {String} id
   * @param {String} source
   * @param {Object} cfg config
   * @return {String} template
   */
  function Compile(){
    var arg = arguments;
    var id,source;
    _.log('source compile');
    if(arg[1]){
      if(arg[2]){
        source = arg[1];
        id = arg[0];
      }else{
        if(_.isString(arg[1])){
          source = arg[1];
          id = arg[0];
        }else{
          source = arg[0];
        }
      }
    }else{
      source = arg[0];
    }
    try {
      return _compile(id,source);
    } catch (e) {
      e.id = id || source;
      e.name = 'Syntax Error';
    }
  }

  function Cache(){
    this.cache = {};
  }
  Cache.prototype = {
    get:function(id){
      return this.cache[id] ? this.cache[id] : null;
    },
    set:function(id,tpl){
      this.cache[id] = tpl;
    }
  };
  //init cache
  var cache = new Cache();

  /**
   * @constructor template
   * @param {String} id
   * @param {Object} data
   * @param {Object} cfg config
   * @return {HTMLElement | null} The HTMLElement
   */
  function Template(){
    var type;
    switch(arguments.length){
      default:
      case 0:
        return _.log('parameter error');
        break;
      case 1:
        return compile.apply(this,arguments);
        break;
      case 2:
      case 3:
        return render.apply(this,arguments);
        break;
    }
  };

  /**
   * config default
   * cache template cache
   */
  var CONFIG = {
    cache : true,
    escape : true,
    tags:{
      open :'<#',
      close:'#>'
    },
    compress:true,
    debug:false
  };
  function Config(cfg){
    _.mix(CONFIG,cfg);
  }

  /**
   * exports public method
   */
  exports._ = _;
  exports.config = Config;
  exports.render = Render;
  exports.compile = Compile;
  exports.template = Template;
});
