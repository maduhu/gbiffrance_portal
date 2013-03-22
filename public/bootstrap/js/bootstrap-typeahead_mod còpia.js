/* =============================================================
 * bootstrap-typeahead.js v2.0.0
 * http://twitter.github.com/bootstrap/javascript.html#typeahead
 * =============================================================
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============================================================ */

!function( $ ){
  
  "use strict"

  var Typeahead = function ( element, options ) {
    this.my_text='';

    this.$element = $(element)
    this.options = $.extend({}, $.fn.typeahead.defaults, options)
    this.search_type=this.options.search_type;
    this.matcher = this.options.matcher || this.matcher
    this.sorter = this.options.sorter || this.sorter
    this.highlighter = this.options.highlighter || this.highlighter
    this.$menu = $(this.options.menu).appendTo('body')
    this.source = this.options.source
    this.onselect = this.options.onselect
    this.strings = true
    this.shown = false
    this.listen()
  }

  Typeahead.prototype = {

    constructor: Typeahead

  , select: function () {
    var html_data=this.$menu.find('.active').attr('data-value')
    
     var val = JSON.parse(html_data)
        , text
    
if (this.search_type=='taxes')
{
   
//"<span><b></b></span> <span>Type: <b>Chine</b></span>  <span>Pays: <b>Pays</b></span>  <span id=\"place_bbox\" style=\"display:none\">Pays</span>"
    var my_text;
    var my_text_array=val.split('<span>')     
       var my_text=my_text_array[0].substring(0,my_text_array[0].length-1)
      window.taxa=my_text;

       var taxa_level=my_text_array[1].split('</span>')
taxa_level=taxa_level[0].split('[')

taxa_level=taxa_level[1].substring(0,taxa_level[1].length-1);
if (taxa_level=='class')  taxa_level='classs'
  if (taxa_level=='order')  taxa_level='orderr'
window.to_plot=taxa_level;


}
else
{
console.warn($(val))
var _this=$(val).find('b')
my_text='<tr><td><i class="icon-trash" style="color:#9C0890;margin-right: 10px;float:left;"></i></td><td><i class="icon-eye-open" style="color:#9C0890;margin-right: 10px;float:left;"></i></td><td>'+_this.eq(0).html()+'</td><td>'+_this.eq(1).html()+'</td><td>'+_this.eq(2).html()+'</td><td style="display:none" class="bbox_handler">'+_this.eq(3).html()+'</td> </tr>';
val=my_text
}
       //taxa_level=
this.$element.addClass('completed_search')

      if (!this.strings) text = val[this.options.property]
      else text = my_text

      var t=this.$element.val(text)


      if (typeof this.onselect == "function")
          this.onselect(val)

      return this.hide()
    }

  , show: function () {
      var pos = $.extend({}, this.$element.offset(), {
        height: this.$element[0].offsetHeight
      })

      this.$menu.css({
        top: pos.top + pos.height
      , left: pos.left
      })

      this.$menu.show()
      this.shown = true
      return this
    }

  , hide: function () {
      this.$menu.hide()
      this.shown = false
      return this
    }

  , lookup: function (event) {
      var that = this
        , items
        , q
        , value

      this.query = this.$element.val()

      if (typeof this.source == "function") {
        value = this.source(this, this.query)
        if (value) this.process(value)
      } else {
        this.process(this.source)
      }
    }

  , process: function (results) {
      var that = this
        , items
        , q


      if (results.length && typeof results[0] != "string")
          this.strings = false

      this.query = this.$element.val()

      if (!this.query) {
        return this.shown ? this.hide() : this
      }

      // items = $.grep(results, function (item) {
      //   if (!that.strings)
      //     item = item[that.options.property]
      //   if (that.matcher(item)) return item
      // })
      items=results

      if (!items.length) {
        return this.shown ? this.hide() : this
      }

      return this.render(items.slice(0, this.options.items)).show()
    }

  , matcher: function (item) {
      return ~item.toLowerCase().indexOf(this.query.toLowerCase())
    }

  , sorter: function (items) {
      var beginswith = []
        , caseSensitive = []
        , caseInsensitive = []
        , item
        , sortby

      while (item = items.shift()) {
        if (this.strings) sortby = item
        else sortby = item[this.options.property]

        if (!sortby.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(item)
        else if (~sortby.indexOf(this.query)) caseSensitive.push(item)
        else caseInsensitive.push(item)
      }

      return beginswith.concat(caseSensitive, caseInsensitive)
    }

  , highlighter: function (item) {
      return item.replace(new RegExp('(' + this.query + ')', 'ig'), function ($1, match) {
        return '<strong>' + match + '</strong>'
      })
    }

  , render: function (items) {
      var that = this

      items = $(items).map(function (i, item) {
        i = $(that.options.item).attr('data-value', JSON.stringify(item))
        if (!that.strings)
            item = item[that.options.property]
        i.find('a').html(that.highlighter(item))
        return i[0]
      })

      items.first().addClass('active')
      this.$menu.html(items)
      return this
    }

  , next: function (event) {
      var active = this.$menu.find('.active').removeClass('active')
        , next = active.next()

      if (!next.length) {
        next = $(this.$menu.find('li')[0])
      }

      next.addClass('active')
    }

  , prev: function (event) {
      var active = this.$menu.find('.active').removeClass('active')
        , prev = active.prev()

      if (!prev.length) {
        prev = this.$menu.find('li').last()
      }

      prev.addClass('active')
    }

  , listen: function () {
      this.$element
        .on('blur',     $.proxy(this.blur, this))
        .on('keypress', $.proxy(this.keypress, this))
        .on('keyup',    $.proxy(this.keyup, this))

      if ($.browser.webkit || $.browser.msie) {
        this.$element.on('keydown', $.proxy(this.keypress, this))
      }

      this.$menu
        .on('click', $.proxy(this.click, this))
        .on('mouseenter', 'li', $.proxy(this.mouseenter, this))
    }

  , keyup: function (e) {

      e.stopPropagation()
      e.preventDefault()

      switch(e.keyCode) {
        case 40: // down arrow
        case 38: // up arrow
          break

        case 9: // tab
        case 13: // enter
          if (!this.shown) return
          this.select()
          break

        case 27: // escape
          this.hide()
          break

        default:
          this.lookup()
      }

  }

  , keypress: function (e) {
      e.stopPropagation()
      if (!this.shown) return

      switch(e.keyCode) {
        case 9: // tab
        case 13: // enter
        case 27: // escape
          e.preventDefault()
          break

        case 38: // up arrow
          e.preventDefault()
          this.prev()
          break

        case 40: // down arrow
          e.preventDefault()
          this.next()
          break
      }
    }

  , blur: function (e) {

    
     var $target=$(e.currentTarget)

    var that = this
     if ($target.hasClass('search-query'))
     {

      e.stopPropagation()
      e.preventDefault()
       return false;
     }
      
     else
     {
      
      e.stopPropagation()
      e.preventDefault()
      setTimeout(function () { that.hide() }, 10)
     }
    // else
    // {

    // }
      
    }

  , click: function (e) {
      e.stopPropagation()
      e.preventDefault()

      this.select(this.my_text)
    }

  , mouseenter: function (e) {

      this.$menu.find('.active').removeClass('active')
      $(e.currentTarget).addClass('active')
    }

  }


  /* TYPEAHEAD PLUGIN DEFINITION
   * =========================== */

  $.fn.typeahead = function ( option ) {
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('typeahead')
        , options = typeof option == 'object' && option
      if (!data) $this.data('typeahead', (data = new Typeahead(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  $.fn.typeahead.defaults = {
    source: []
  , items: 650
  , item: '<li><a href="#"></a></li>'
  , onselect: function (selected_text) 
  {
  
   
   if (this.search_type=='taxes')
{
  var my_text_array=selected_text.split('<span>')
   this.my_text=my_text_array[0].substring(0,my_text_array[0].length-1)
$('#taxa-dropdown ul').append('<li>'+this.my_text+'<i class="icon-trash" style="color:#9C0890;margin-left: 20px;"></i></li>')  
  

  return this.my_text;
}

   if (this.search_type=='places')
{
 //$('.places_alert ul').append('<li>'+selected_text+'<i class="icon-eye-open" style="margin-left: 20px;"></i><i class="icon-trash" style="color:#9C0890;margin-left: 20px;"></i></li>')  
console.warn('onselect')
//$('#places tbody').append(selected_text);

$('#places-dropdown ul').append('<li>'+selected_text+'<i class="icon-eye-open" style="margin-left: 20px;"></i><i class="icon-trash" style="color:#9C0890;margin-left: 20px;"></i></li>')

  //      var my_html='<tr><td><i class="icon-trash" style="color:#9C0890;margin-right: 10px;float:left;"></i></td><td><i class="icon-eye-open" style="color:#9C0890;margin-right: 10px;float:left;"></i></td><td>'+selected_text+'</td><td>Pays</td><td>Chine</td><td style="display:none" class="bbox_handler">15.7754,73.55771,53.5606,134.7736</td><td style="display:none">36.89445,104.16565</td></tr>'; 
//$('#places tbody').append(my_html)

}

 this.$element.val('')
    //return text[0].substring(0,text[0].length-1)
  } , property: 'value'
  }

  $.fn.typeahead.Constructor = Typeahead


 /* TYPEAHEAD DATA-API
  * ================== */

  $(function () {
    $('body').on('focus.typeahead.data-api', '[data-provide="typeahead"]', function (e) {
      var $this = $(this)
      if ($this.data('typeahead')) return
      e.preventDefault()
      $this.typeahead($this.data())
    })
  })

}( window.jQuery );
