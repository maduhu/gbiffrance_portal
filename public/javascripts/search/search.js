  function addFilter(type)
  { 
	if (type == "coordinates") $("#input-search-" + type).val("true");
	if (type == "place" || type == "taxa" || type == "coordinates" || type == "dataset")
    {
      $("#search-" + type).show();
      $("#search-submit").show();      
    }
    
  }
  
  function removeFilter(type)
  { 
    if (type == "coordinates") $("#input-search-" + type).val("false");
    else if (type == "place" || type == "taxa" || type == "dataset") $("#input-search-" + type).val('');
    if (type == "place" || type == "taxa" || type == "coordinates" || type == "dataset")
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
  
  function searchOccurrences(searchTaxa, searchPlace, searchDataset, onlyWithCoordinates, from)
  {
	searchTaxa = searchTaxa.replace(/ /g,"%20");
	searchPlace = searchPlace.replace(/ /g,"%20");
	searchDataset = searchDataset.replace(/ /g,"%20");
	
	$('#occurrences').load(occurrences({searchTaxa: searchTaxa, searchPlace: searchPlace, searchDataset: searchDataset, onlyWithCoordinates: onlyWithCoordinates, from: from}));
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
    $('#datasets').load(datasets({search: search}));
    $('#datasets').show() 
  }
     
  $(document).ready(function() {
	if ($("#input-search-taxa").val() == '') $("#search-taxa").hide();
	if ($("#input-search-place").val() == '') $("#search-place").hide();
	if ($("#input-search-coordinates").val() == "false") $("#search-coordinates").hide();
	if ($("#input-search-dataset").val() == '') $("#search-dataset").hide();
    
    /* Show filters */
    $("#search-filter-place").click(function() {addFilter('place')})
    $("#search-filter-taxa").click(function() {addFilter('taxa')})
    $("#search-filter-dataset").click(function() {addFilter('dataset')})
    $("#search-filter-coordinates").click(function() {addFilter('coordinates')})
    
    /* Hide filters */
    $("#search-place").dblclick(function() {removeFilter('place')})
    $("#search-taxa").dblclick(function() {removeFilter('taxa')})
    $("#search-dataset").dblclick(function() {removeFilter('dataset')})
    $("#search-coordinates").dblclick(function() {removeFilter('coordinates')})
    
    //searchDatasets('${search}');
  });