<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8"/>
    <title><#=$title#> - DOCS</title>
    <link rel="shortcut icon" href="https://i.alipayobjects.com/common/favicon/favicon.ico" type="image/x-icon" />
    <style>
      <#=$stylesheet#>
    </style>
  </head>
  <body>
    <div id="page">
      <div class="header">
        <h1>DOCS</h1>
      </div>
      <div class="side">
        <h1>
          catalog
        </h1>
        <ul>
          <#each val,index in $list#>
          <li>
            <a href="<#=$val.href#>">
            <#=$val.name#>
            </a>
          </li>
          <#/each#>
        </ul>
      </div>
      <div class="content">
        <#=$fields#>
      </div>
      <div class="footer">
        <p>Copyright 2014, Powered by <a href="https://github.com/xudafeng/jsdocs" target="_blank">JsDocs</a></p>
      </div>
    </div>
  </body>
</html>

