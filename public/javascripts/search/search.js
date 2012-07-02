  function addFilter(type)
  { 
	if (type == "coordinates") $("#input-search-" + type).val("true");
	if (type == "place" || type == "taxa" || type == "coordinates")
    {
      $("#search-" + type).show();
      $("#search-submit").show();      
    }
    
  }
  
  function removeFilter(type)
  { 
    if (type == "coordinates") $("#input-search-" + type).val("false");
    if (type == "place" || type == "taxa") $("#input-search-" + type).val('');
    if (type == "place" || type == "taxa" || type == "coordinates")
    {
      $("#search-" + type).hide();        
    }
    
  }
  
  function searchTaxas(search, page)
  {  
	// temporary solution
	search = search.replace(/ /g,"%20");
    $('#taxas').load(taxas({search: search, page: page}));
    $('#taxas').show()
  }
  
  function searchOccurrences(searchTaxa, searchPlace, onlyWithCoordinates, from)
  {
	searchTaxa = searchTaxa.replace(/ /g,"%20");
	searchPlace = searchPlace.replace(/ /g,"%20");
	$('#occurrences').load(occurrences({searchTaxa: searchTaxa, searchPlace: searchPlace, onlyWithCoordinates: onlyWithCoordinates, from: from}));
    $('#occurrences').show() 
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
    $('#datasets').load(datasets({search: search}));
    $('#datasets').show() 
  }
    
  $(document).ready(function() {
	if ($("#input-search-taxa").val() == '') $("#search-taxa").hide();
	if ($("#input-search-place").val() == '') $("#search-place").hide();
	if ($("#input-search-coordinates").val() == "false") $("#search-coordinates").hide();
	if ($("#input-search-taxa").val() == '' && $("#input-search-place").val() == '' && $("#input-search-coordinates").val() == "false")
		$("#search-submit").hide();
    
    /* Show filters */
    $("#search-filter-place").click(function() {addFilter('place')})
    $("#search-filter-taxa").click(function() {addFilter('taxa')})
    $("#search-filter-coordinates").click(function() {addFilter('coordinates')})
    
    /* Hide filters */
    $("#search-place").dblclick(function() {removeFilter('place')})
    $("#search-taxa").dblclick(function() {removeFilter('taxa')})
    $("#search-coordinates").dblclick(function() {removeFilter('coordinates')})
    
    //searchDatasets('${search}');
  });