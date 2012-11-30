package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gbif.ecat.model.ParsedName;
import org.gbif.ecat.parser.NameParser;

import controllers.Places;

public class Search
{
  public String taxaText; //original query text
  public List<String> taxas = new ArrayList<String>();
  public String place = "";
  public ArrayList<String> places = new ArrayList<String>();
  public String placeText; //original query text
  public ArrayList<String> placesText = new ArrayList<String>();
  public boolean onlyWithCoordinates = false;
  public String dataset = "";
  public List<Long> datasetsIds = new ArrayList<Long>();
  public ArrayList<Float[]> boundingBoxes = new ArrayList<Float[]>();
  public Float[] boundingBox;
  public String dateText = "";
  public Integer fromDate;
  public Integer toDate;


  public static Search parser(String searchTaxa, String searchPlace, String searchDataset, String searchDate, boolean searchCoordinates)
  {
	Search search = new Search();
	/*** Taxa parser ***/
	if (searchTaxa != null && !searchTaxa.isEmpty())
	{
	  searchTaxa = searchTaxa.trim();
	  search.taxaText = searchTaxa;
	  String[] splittedSearchTaxa = searchTaxa.split(";");
	  if (splittedSearchTaxa.length >= 1)
	  {
	    for (int i = 0; i < splittedSearchTaxa.length; ++i)
	    {
	      search.taxas.add(splittedSearchTaxa[i]);
	    }
	  }	
	}
	/*** Place parser ***/
	if (searchPlace != null && !searchPlace.isEmpty())
	{
	  search.placeText = searchPlace;
	  String[] splittedSearch = searchPlace.split(";");
	  for (int h = 0; h < splittedSearch.length; ++h)
	  {
		search.placesText.add(splittedSearch[h]);
		if (splittedSearch[h].startsWith("[") && splittedSearch[h].endsWith("]"))
		{
		  search.boundingBoxes.add(Search.extractBoundingBox(splittedSearch[h]));
		}
		else
		{
		  if (splittedSearch[h] != null || !splittedSearch[h].isEmpty()) search.place += " " + Place.enrichSearchWithPlaces(splittedSearch[h]);
		  if (!search.place.isEmpty())
		  {
			search.boundingBoxes.add(Search.extractBoundingBox(search.place));
			String[] splittedEnrichedSearch = search.place.split(";");
			search.place = "";
			for (int i = 0; i < splittedEnrichedSearch.length; ++i)
			{
			  if (!splittedEnrichedSearch[i].startsWith("[") && !splittedEnrichedSearch[i].endsWith("]"))
			  {
				search.places.add(splittedEnrichedSearch[i]);
			  }		  		
			}

		  }
		  
		}
	  }
	  System.out.println(search.places.size());
	   
	}	
	/*** Coordinates ***/
	if (searchCoordinates) search.onlyWithCoordinates = true;
	/*** Dataset ***/
	if (searchDataset != null && !searchDataset.isEmpty()) 
	{
	  search.dataset = searchDataset.replaceAll("\'", "");	
	  search.datasetsIds = Dataset.getDatasetsIds(searchDataset);
	}
	/*** Date ***/
	if (searchDate != null && !searchDate.isEmpty()) 
	{
	  search.dateText = searchDate.trim();	  
	  if (search.dateText.split("-").length == 2)
	  {
		search.fromDate = Integer.valueOf(search.dateText.split("-")[0]);
		search.toDate = Integer.valueOf(search.dateText.split("-")[1]);		
	  }
	  else if (search.dateText.split("-").length == 1)
	  {
		search.fromDate = Integer.valueOf(search.dateText.split("-")[0]);
		search.toDate = Integer.valueOf(search.dateText.split("-")[0]);
	  }
	}
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
	  String[] splittedEnrichedSearch = place.split(";");
	  for (int i = 0; i < splittedEnrichedSearch.length; ++i)
	  {
		if (splittedEnrichedSearch[i].startsWith("[") && splittedEnrichedSearch[i].endsWith("]"))
		{
		  splittedEnrichedSearch[i] = splittedEnrichedSearch[i].replaceAll("[\\[,\\]]", " ").trim();
		  System.out.println(splittedEnrichedSearch[i]);
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