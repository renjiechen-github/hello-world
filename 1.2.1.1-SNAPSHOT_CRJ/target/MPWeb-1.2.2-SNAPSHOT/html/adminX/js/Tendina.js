
/*
Tendina jQuery plugin v0.8.1

Copyright (c) 2014 Ivan Prignano
Released under the MIT License
 */
/* 代码整理：懒人之家 www.lanrenzhijia.com */
(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __slice = [].slice;

  (function($, window) {
    var Tendina;
    Tendina = (function() {
      Tendina.prototype.defaults = {
        animate: true,
        speed: 500
      };

      function Tendina(el, options) {
        this._clickHandler = __bind(this._clickHandler, this);
        this.options = $.extend({}, this.defaults, options);
        this.$el = $(el);
        this._checkOptions();
        this.$el.addClass('tendina');
        this.firstLvlSubmenu = ".tendina > li";
        this.secondLvlSubmenu = ".tendina > li > ul > li";
		this.treeLvlSubmenu = ".tendina > li > ul > li > ul > li";
        this.firstLvlSubmenuLink = "" + this.firstLvlSubmenu + " > a";
        this.secondLvlSubmenuLink = "" + this.secondLvlSubmenu + " > a";
		this.treeLvlSubmenuLink = "" + this.treeLvlSubmenu + " > a";
        this._hideSubmenus();
        this._bindEvents();
      }

      Tendina.prototype._bindEvents = function() {
        return $(document).on('click.tendina', "" + this.firstLvlSubmenuLink + ", " + this.secondLvlSubmenuLink+ ", " + this.treeLvlSubmenuLink, this._clickHandler);
      };

      Tendina.prototype._unbindEvents = function() {
        return $(document).off('click.tendina');
      };

      Tendina.prototype._isFirstLevel = function(clickedEl) {
        if ($(clickedEl).parent().parent().hasClass('tendina')) {
          return true;
        }
      };
	  
	  Tendina.prototype._isSecondLevel = function(clickedEl) {
        if ($(clickedEl).parent().parent().parent().hasClass('tendina')) {
          return true;
        }
      };

      Tendina.prototype._clickHandler = function(event) {
        var clickedEl, submenuLevel;
        clickedEl = event.currentTarget;
		if(this._isFirstLevel(clickedEl)){
			$('.tendina > li > a.more').attr('style','background-image:url(/html/adminX/images/plus-white.png);');;
		}
        submenuLevel = this._isFirstLevel(clickedEl) ? this.firstLvlSubmenu :this._isSecondLevel(clickedEl)? this.secondLvlSubmenu:this.treeLvlSubmenu;
        if (this._hasChildenAndIsHidden(clickedEl)) {
          event.preventDefault();
          return this._openSubmenu(submenuLevel, clickedEl);
        } else if (this._isCurrentlyOpen(clickedEl)) {
		  event.preventDefault();
          return this._closeSubmenu(clickedEl);
        }
      };

      Tendina.prototype._openSubmenu = function(el, clickedEl) {
        var $clickedNestedMenu, $firstNestedMenu, $lastNestedMenu;
        $firstNestedMenu = $(el).find('> ul');
        $lastNestedMenu = $(el).find('> ul > li > ul');
        $clickedNestedMenu = $(clickedEl).next('ul');
        $(el).removeClass('selected');
        $(clickedEl).parent().addClass('selected');
        //显示一级菜单上门的
		$(el).parent().find('.more').first().attr('style','background-image:url(/html/adminX/images/plus-white.png);');
		this._close($firstNestedMenu);
        this._open($clickedNestedMenu);
        if (el === this.firstLvlSubmenu) {
          $(el).find('> ul > li').removeClass('selected');
          this._close($lastNestedMenu);
        }
		if (this.options.closeCallback) {
          this.options.closeCallback($firstNestedMenu.parent());
        }
        if (this.options.openCallback) {
          return this.options.openCallback($(clickedEl).parent());
        }
      };

      Tendina.prototype._closeSubmenu = function(el) {
        var $clickedNestedMenu;
        $clickedNestedMenu = $(el).next('ul');
        $(el).parent().removeClass('selected');
        this._close($clickedNestedMenu);
        if (this.options.closeCallback) {
          return this.options.closeCallback($(el).parent());
        }
      };

      Tendina.prototype._open = function($el) {
        if (this.options.animate) {
          return $el.slideDown(this.options.speed);
        } else {
          return $el.show();
        }
      };

      Tendina.prototype._close = function($el) {
        if (this.options.animate) {
          return $el.slideUp(this.options.speed);
        } else {
          return $el.hide();
        }
      };

      Tendina.prototype._hasChildenAndIsHidden = function(el) {
        return $(el).next('ul').length > 0 && $(el).next('ul').is(':hidden');
      };

      Tendina.prototype._isCurrentlyOpen = function(el) {
        return $(el).parent().hasClass('selected');
      };

      Tendina.prototype._hideSubmenus = function() {
        $("" + this.firstLvlSubmenu + " > ul, " + this.secondLvlSubmenu + " > ul").hide();
        return $("" + this.firstLvlSubmenu + " > ul").removeClass('selected');
      };

      Tendina.prototype._showSubmenus = function() {
        $("" + this.firstLvlSubmenu + " > ul, " + this.secondLvlSubmenu + " > ul").show();
        return $("" + this.firstLvlSubmenu).removeClass('selected');
      };

      Tendina.prototype._checkOptions = function() {
        if (this.options.animate !== true || false) {
          console.warn("jQuery.fn.Tendina - '" + this.options.animate + "' is not a valid parameter for the 'animate' option. Falling back to default value.");
        }
        if (this.options.speed !== parseInt(this.options.speed)) {
          return console.warn("jQuery.fn.Tendina - '" + this.options.speed + "' is not a valid parameter for the 'speed' option. Falling back to default value.");
        }
      };

      Tendina.prototype.destroy = function() {
        this.$el.removeData('tendina');
        this._unbindEvents();
        this._showSubmenus();
        this.$el.removeClass('tendina');
        return this.$el.find('.selected').removeClass('selected');
      };

      Tendina.prototype.hideAll = function() {
        return this._hideSubmenus();
      };

      Tendina.prototype.showAll = function() {
        return this._showSubmenus();
      };

      return Tendina;

    })();
    return $.fn.extend({
      tendina: function() {
        var args, option;
        option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
        return this.each(function() {
          var $this, data;
          $this = $(this);
          data = $this.data('tendina');
          if (!data) {
            $this.data('tendina', (data = new Tendina(this, option)));
          }
          if (typeof option === 'string') {
            return data[option].apply(data, args);
          }
        });
      }
    });
  })(window.jQuery, window);

}).call(this);
/* 代码整理：懒人之家 www.lanrenzhijia.com */