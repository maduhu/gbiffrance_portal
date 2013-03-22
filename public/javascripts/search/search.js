  function addFilter(type)
  { 
	if (type == "coordinates") $("#input-search-" + type).val("true");
	if (type == "place" || type == "taxa" || type == "coordinates" || type == "dataset" || type == "date")
    {
      $("#search-" + type).show();
      $("#search-submit").show();      
    }
    if (!$('#search-submit').is(':visible')) ('##search-submit').show()
    
  }
  
  function removeFilter(type)
  { 
    if (type == "coordinates") $("#input-search-" + type).val("false");
    else if (type == "place" || type == "taxa" || type == "dataset" || type == "date") $("#input-search-" + type).val('');
    if (type == "place" || type == "taxa" || type == "coordinates" || type == "dataset" || type == "date")
    {
      $("#search-" + type).hide();        
    }  
    hideSubmit();

   //  if ($('##search-submit').is(':visible') ('##search-submit').show())

  }
  
  function hideSubmit()
  {
    if ($(".search-input").filter(":visible").length == 0) 
	{
	  $("#search-submit").hide(); 
	}	  
  }
  
  function searchTaxas(search, page)
  {  
	// temporary solution
  
	search = search.replace(/ /g,"%20");
    //$('#taxas').load(taxas({search: search, page: page}));
    //$('#taxas').show()
  }
  
  function searchOccurrences(searchTaxa, searchPlace, searchDataset, searchDate, onlyWithCoordinates, from)
  {
	searchTaxa = searchTaxa.replace(/ /g,"%20");
  
	searchPlace = searchPlace.replace(/ /g,"%20");
	searchDataset = searchDataset.replace(/ /g,"%20");
  console.warn(searchDataset)
	searchDate = searchDate.replace(/ /g,"%20");
  
  //occurrences defined in search.html (var occurrences = #{jsAction @Occurrences.search(':searchTaxa', ':searchPlace', ':searchDataset', ':searchDate', ':onlyWithCoordinates', ':from') /} )
	$('#occurrences').load(occurrences({searchTaxa: searchTaxa, searchPlace: searchPlace, searchDataset: searchDataset, searchDate: searchDate, onlyWithCoordinates: onlyWithCoordinates, from: from}),
    function ()
    {
var ids=[];
var id_objs_list=[]

$('.hoverable').each(function ()
{
var classes=$(this).attr('class').split(' ')
var _this_id_=classes[1];

if ($.inArray(_this_id_,ids)==-1)
{
var sp=$(this).find('div:first').text().trim()
var my_id=_this_id_
var _this_id_={}

_this_id_.id=my_id
_this_id_.speciesName=sp
_this_id_.count=1;
ids.push(my_id)
id_objs_list.push(_this_id_)
}
else
{
  for (var i in id_objs_list)
{

if (id_objs_list[i].id==_this_id_)
id_objs_list[i].count++
}
}
})




 function sortfunction(a,b){  
     return a.count < b.count ? 1 : -1;  
 }; 
 
var sorted=id_objs_list.sort(sortfunction)

var html=''
for (var i in sorted)
{

var this_obj=sorted[i];
html+='<tr><td class="sorted '+this_obj.id+'">'+this_obj.speciesName+'   <span class="badge badge-success">'+this_obj.count+'</span occurrences<p></td></tr>'
          
}

$('#occurrences_table').prepend(html)

var $not_sorted=$('#occurrences_table td').not('.sorted')

$not_sorted.hide()

$('.sorted').click(function ()
{
var this_conceptID=$(this).attr('class').split(' ')[1]

var selected=$.map($not_sorted,function (that)
{

if ($(that).hasClass(this_conceptID))
return that
})


$(selected).show()
var original_background=$(selected).css('background-color')


$('html,body').animate({
   scrollTop: $("#occurrences").offset().top+100
},500);

$('#occurrences_div').animate({
scrollTop: $(selected).offset().top+300
},2000)

$(selected).each(function ()
  {

$(this).animate({
  'background-color': '#8C86F9'},
  5000, function() {
    $(this).css('background-color',original_background)
});
  })
  
})

    });
	$('#occurrences').show(); 
  }
  
  function searchPlaces(search)
  {
	search = search.replace(/ /g,"%20");  
    $('#places').load(places({search: search}));
    $('#places').show() 
  }
  
  function searchDatasets(search)
  {
    search = search.replace(/ /g,"%20");
    search = search.replace(/\'/g,"%27");
    $('#datasets').load(datasets({search: search}));
    $('#datasets').show() 
  }
     
  $(document).ready(function() {	   


    /* Show filters */
    $("#search-filter-place").click(function() {
     
  addFilter('place')

    })
    $("#search-filter-taxa").click(function() {addFilter('taxa')})
    $("#search-filter-dataset").click(function() {
      //addFilter('dataset')
 var $this_trigger=$('#datapublishers_list-dropdown-trigger')


      $this_trigger.is(':visible') ? 
      $this_trigger.add('#datapublishers_list-dropdown').trigger('click').hide() : 
      $this_trigger.show().trigger('click')

  })
    $("#search-filter-coordinates").click(function() {addFilter('coordinates')})
    $("#search-filter-date").click(function() {addFilter('date')})
    
    
  });