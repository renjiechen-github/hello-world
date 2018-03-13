/*
 Copyright 2008-2012 Allan Jardine, all rights reserved.

 This source file is free software, under either the GPL v2 license or a
 BSD style license, available at:
   http://datatables.net/license_gpl2
   http://datatables.net/license_bsd

 This source file is distributed in the hope that it will be useful, but 
 WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 or FITNESS FOR A PARTICULAR PURPOSE. See the license files for details.

 For details please refer to: http://www.datatables.net
*/
'use strict';(function(c,d,e){(function(b){"function"===typeof define&&define.amd?define(["jquery"],b):jQuery&&!jQuery.fn.dataTable&&b(jQuery)})(function(b){var a=function(a){require("core.columns.js");require("core.data.js");require("core.draw.js");require("core.ajax.js");require("core.filter.js");require("core.info.js");require("core.init.js");require("core.length.js");require("core.page.js");require("core.processing.js");require("core.scrolling.js");require("core.sizing.js");require("core.sort.js");require("core.state.js");require("core.support.js");require("api.methods.js");require("api.internal.js");this.each(function(){require("core.constructor.js")});return this};require("api.static.js");a.version="1.9.4";a.settings=[];a.models={};require("model.ext.js");require("model.search.js");require("model.row.js");require("model.column.js");require("model.defaults.js");require("model.defaults.columns.js");require("model.settings.js");a.ext=b.extend(!0,{},a.models.ext);require("ext.classes.js");require("ext.paging.js");require("ext.sorting.js");require("ext.types.js");b.fn.DataTable=a;b.fn.dataTable=a;b.fn.dataTableSettings=a.settings;b.fn.dataTableExt=a.ext})})(window,document);