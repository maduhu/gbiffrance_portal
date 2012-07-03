package models;

import java.util.ArrayList;
import java.util.List;

import controllers.Places;

public class Search
{
  public String text = "";
  public String taxa = "";
  public String place = "";
  public String placeText = "";
  public boolean onlyWithCoordinates = false;
  public String dataset = "";
  public List<Long> datasetsIds = new ArrayList<Long>();
  public Float[] boundingBox;
  
     
  public static Search parser(String searchTaxa, String searchPlace, String searchDataset, boolean searchCoordinates)
  {
	Search search = new Search();
	/*** Taxa ***/
	searchTaxa = searchTaxa.trim();
	search.taxa = searchTaxa;	
	/*** Place ***/
	search.placeText = searchPlace;  
	if (search.placeText != null || !search.placeText.isEmpty()) search.place = Place.enrichSearchWithPlaces(search.placeText);
	if (!search.place.isEmpty())
    {
      search.boundingBox = Search.extractBoundingBox(search.place);
    }   
	/*** Coordinates ***/
	if (searchCoordinates) search.onlyWithCoordinates = true;
	/*** Dataset ***/
	if (!searchDataset.isEmpty()) 
	{
	  search.dataset = searchDataset;	
	  search.datasetsIds = Dataset.getDatasetsIds(searchDataset);
	}
	
	
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