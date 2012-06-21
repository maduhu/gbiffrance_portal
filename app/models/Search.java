package models;

import controllers.Places;

public class Search
{
  public String text; //original text
  public String taxa;
  public String textTaxa; //original taxa text
  public String place;
  public String textPlace; //original place text
  public boolean onlyWithCoordinates = false;
     
  public static Search parser(String textSearch)
  {
	System.out.println("textSearch: " + textSearch);
	Search search = new Search();
	textSearch = textSearch.trim();
	search.text = textSearch;
	String[] splittedSearch = textSearch.split("\\]\\[");
	for (int i = 0; i < splittedSearch.length; ++i)
	{
	  if (splittedSearch.length > 1)
	  {
		  if (i == 0) splittedSearch[i] = splittedSearch[i] + ']'; 
		  else if (i == splittedSearch.length - 1) splittedSearch[i] = '[' + splittedSearch[i];
		  else splittedSearch[i] = '[' + splittedSearch[i] + ']';
	  }
	  System.out.println("splittedSearch[" + i + "]: " + splittedSearch[i]); 
	  if(splittedSearch[i].startsWith("[place:") && splittedSearch[i].endsWith("]"))
 	  {
		splittedSearch[i] = splittedSearch[i].replaceFirst("\\[place:", "");		
		splittedSearch[i] = splittedSearch[i].replaceFirst("\\]", "");
		search.textPlace = splittedSearch[i];  
	    search.place = Place.enrichSearchWithPlaces(search.textPlace);
	  }
	  if(splittedSearch[i].startsWith("[taxa:") && splittedSearch[i].endsWith("]"))
	  {
		splittedSearch[i] = splittedSearch[i].replaceFirst("\\[taxa:", "");
		splittedSearch[i] = splittedSearch[i].replaceFirst("\\]", "");
	    search.taxa = splittedSearch[i];  	    
	  }
	  if(splittedSearch[i].equals("[onlyWithCoordinates]"))
	  {
		search.onlyWithCoordinates = true;
	  }
	}
	
    System.out.println("locality: " + search.place);
    System.out.println("taxa: " + search.taxa);
           
	return search;  
  }
  
  /* Extracts bounding box information from the search */
  public static Float[] extractBoundingBox(Search search)
  {
    float boundingBoxSWLatitude = 0;
    float boundingBoxSWLongitude = 0;
    float boundingBoxNELatitude = 0;
    float boundingBoxNELongitude = 0;	  
    if (search.place != null)
    {
      String[] splittedEnrichedSearch = search.place.split(" ");
      for (int i = 0; i < splittedEnrichedSearch.length; ++i)
      {
	    if (splittedEnrichedSearch[i].startsWith("{{") && splittedEnrichedSearch[i].endsWith("}}"))
	    {
		  splittedEnrichedSearch[i] = splittedEnrichedSearch[i].replaceAll("[{,}]", " ").trim();
		  //System.out.println(splittedEnrichedSearch[i]);
		  splittedEnrichedSearch[i] = splittedEnrichedSearch[i].replaceAll("  ", " ");
		  String[] boundingBox = splittedEnrichedSearch[i].split(" ");
		  System.out.println(boundingBox[0] + " " +  boundingBox[1] + " " +boundingBox[2] + " " + boundingBox[3]);
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