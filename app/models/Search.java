package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gbif.ecat.model.ParsedName;
import org.gbif.ecat.parser.NameParser;

import controllers.Places;

/**
 * The Search class is used to generate a taxe/place/dataset/date search
 * @author Michael Akbaraly
 *
 */
public class Search
{
  /**
   * original taxa query text
   */
  public String taxaText;
  /**
   * taxa list parsed from textual query
   */
  public List<String> taxas = new ArrayList<String>();

  public String place = "";
  /**
   * original place query text
   */
  public String placeText;
  /**
   * enriched place list parsed from textual query
   */
  public ArrayList<String> places = new ArrayList<String>();
  /**
   *  place list parsed from textual query
   */
  public ArrayList<String> placesText = new ArrayList<String>();
  /**
   * filter for searching only occurrences with coordinates
   */
  public boolean onlyWithCoordinates = false;
  public String dataset = "";
  /***
   * dataset IDs list
   */
  public List<Long> datasetsIds = new ArrayList<Long>();
  /**
   * bounding boxes list used to delimited the French territories
   */
  public ArrayList<Float[]> boundingBoxes = new ArrayList<Float[]>();
  public Float[] boundingBox;
  /**
   * textual date query
   */
  public String dateText = "";
  /**
   * extracted lowest date range
   */
  public Integer fromDate;
  /**
   * extracted highest date range
   */
  public Integer toDate;


  
  /***
   * Uses and enriches the given parameters before sending the request to the search engine
   * @param searchTaxa
   * @param searchPlace
   * @param searchDataset
   * @param searchDate
   * @param searchCoordinates
   * @return search, enriched request to send to the search engine
   */
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

  /**
   *  Extracts bounding box information from the search 
   */
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
		  splittedEnrichedSearch[i] = splittedEnrichedSearch[i].replaceAll("  ", " ");
		  String[] boundingBox = splittedEnrichedSearch[i].split(" ");
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