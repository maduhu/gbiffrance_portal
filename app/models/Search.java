package models;

import controllers.Places;

public class Search
{
  public String text = "";
  public String taxa = "";
  public String place = "";
  public String textPlace = "";
  public boolean onlyWithCoordinates = false;
     
  public static Search parser(String searchTaxa, String searchPlace, boolean searchCoordinates)
  {
	Search search = new Search();
	searchTaxa = searchTaxa.trim();
	search.taxa = searchTaxa;	
	search.textPlace = searchPlace;  
	if (!search.textPlace.isEmpty()) search.place = Place.enrichSearchWithPlaces(search.textPlace);
	if (searchCoordinates) search.onlyWithCoordinates = true;
	search.text = search.taxa + ' ' + search.place;
    //System.out.println("locality: " + search.place);
    //System.out.println("taxa: " + search.taxa);
           
	return search;  
  }
  
  /* Extracts bounding box information from the search */
  public static Float[] extractBoundingBox(String place)
  {
    float boundingBoxSWLatitude = 0;
    float boundingBoxSWLongitude = 0;
    float boundingBoxNELatitude = 0;
    float boundingBoxNELongitude = 0;	  
    if (place != null)
    {
      String[] splittedEnrichedSearch = place.split(" ");
      for (int i = 0; i < splittedEnrichedSearch.length; ++i)
      {
	    if (splittedEnrichedSearch[i].startsWith("{{") && splittedEnrichedSearch[i].endsWith("}}"))
	    {
		  splittedEnrichedSearch[i] = splittedEnrichedSearch[i].replaceAll("[{,}]", " ").trim();
		  //System.out.println(splittedEnrichedSearch[i]);
		  splittedEnrichedSearch[i] = splittedEnrichedSearch[i].replaceAll("  ", " ");
		  String[] boundingBox = splittedEnrichedSearch[i].split(" ");
		  //System.out.println(boundingBox[0] + " " +  boundingBox[1] + " " +boundingBox[2] + " " + boundingBox[3]);
		  boundingBoxSWLatitude = Float.valueOf(boundingBox[0]);
		  boundingBoxSWLongitude =  Float.valueOf(boundingBox[1]);
		  boundingBoxNELatitude =  Float.valueOf(boundingBox[2]);
		  boundingBoxNELongitude =  Float.valueOf(boundingBox[3]);
	    }		  		
      }
    }
    Float[] boundingBox = {boundingBoxSWLatitude, boundingBoxSWLongitude, boundingBoxNELatitude, boundingBoxNELongitude};
    
    return boundingBox;
  }
  
  
    
}