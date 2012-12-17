package models;

import java.util.ArrayList;

import play.Logger;
import play.Play;
import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.google.gson.JsonObject;



/**
 * the Place class is used to stored the information coming from the Yahoo Geo! web service (http://where.yahooapis.com)
 * @author Michael Akbaraly
 *
 */
public class Place
{
  /**
   * the place ID
   */
  public int id;
  /**
   * the place name
   */
  public String name;
  /**
   * the place name in French
   */
  public String nameFr;
  /**
   * the place name in English
   */
  public String nameEn;
  /**
   * the place name in Spanish
   */
  public String nameEs;
  /**
   * the place name in German
   */
  public String nameDe;

  /**
   * the centroid latitude of the place
   */
  public float centroidLatitude;
  /**
   * the centroid longitude of the place
   */
  public float centroidLongitude;

  /**
   * the south west bounding box latitude of the place
   */
  public float boundingBoxSWLatitude;
  /**
   * the south west bounding box longitude of the place
   */
  public float boundingBoxSWLongitude;
  /**
   * the north east bounding box latitude of the place
   */
  public float boundingBoxNELatitude;
  /**
   * the north east bounding box longitude of the place
   */
  public float boundingBoxNELongitude;
  /**
   * the place type name in French
   */
  public String placeTypeNameFr;
  /**
   * the place type name
   */
  public String placeTypeName;
  /**
   * the place related country
   */
  public String country;

  public static String apiKey = Play.configuration.getProperty("yahoo.api");

  /**
   *  Adds places translations to the search ex: search = Pica pica France -> search = Pica pica France France Francia Frankreich
   *  @param texPlace
   *  			place or concatenated places list name 
   *  @return textPlace	enriched String with additional information
   */
  public static String enrichSearchWithPlaces(String textPlace)
  {	  
	int count = 0;
	textPlace = textPlace.replaceAll(" ", "%20");

	Logger.info("Yahoo web service request: " + "http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid="+apiKey);
	HttpResponse geoResponse = WS.url("http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid="+apiKey).get();
	if (geoResponse.success())
	{
	  JsonObject jsonObject = geoResponse.getJson().getAsJsonObject().get("places").getAsJsonObject();
	  count = jsonObject.get("count").getAsInt();
	  if (count == 1) 
	  {  
		int id = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("woeid").getAsInt();
		String placeTypeName = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("placeTypeName").getAsString();
		
		HttpResponse geoResponseFr = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid="+apiKey).get();
		String nameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += ";" + nameFr;
		
		float boundingBoxSWLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("latitude").getAsFloat();
		float boundingBoxSWLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("longitude").getAsFloat();
		float boundingBoxNELatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("latitude").getAsFloat();
		float boundingBoxNELongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("longitude").getAsFloat();

		textPlace += ";["+boundingBoxSWLatitude+","+boundingBoxSWLongitude+","+boundingBoxNELatitude+","+boundingBoxNELongitude+"]";		 

		HttpResponse geoResponseEn = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=en&appid="+apiKey).get();
		String nameEn = geoResponseEn.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += ";" + nameEn;

		HttpResponse geoResponseEs = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=es&appid="+apiKey).get();
		String nameEs = geoResponseEs.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += ";" + nameEs;

		HttpResponse geoResponseDe = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=de&appid="+apiKey).get();
		String nameDe = geoResponseDe.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += ";" + nameDe;

		if (!(geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country").getAsString().equals("")) 
			&& !geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country").getAsString().equals("France")
			&& placeTypeName.equals("country"))
		{
		  String countryCode = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country attrs").getAsJsonObject().get("code").getAsString();
		  textPlace += ";" + countryCode;
		}			
	  }	
	}
	Logger.info("Searched place: " + textPlace);
	return textPlace;
  }  

}
