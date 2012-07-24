package models;

import java.util.ArrayList;

import play.libs.WS;
import play.libs.WS.HttpResponse;

import com.google.gson.JsonObject;

/*
 * ElasticSearch Collection created after the harvesting process (gbiffrance-harvest)
 */


public class Place
{
  public int id;
  public String name;
  public String nameFr;
  public String nameEn;
  public String nameEs;
  public String nameDe;

  public float centroidLatitude;
  public float centroidLongitude;

  public float boundingBoxSWLatitude;
  public float boundingBoxSWLongitude;
  public float boundingBoxNELatitude;
  public float boundingBoxNELongitude;

  public String placeTypeNameFr;
  public String placeTypeName;
  public String country;



  /* Adds places translations to the search ex: search = Pica pica France -> search = Pica pica France France Francia Frankreich*/
  public static String enrichSearchWithPlaces(String textPlace)
  {	  
	int count = 0;
	textPlace = textPlace.replaceAll(" ", "%20");

	//System.out.println("http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
	HttpResponse geoResponse = WS.url("http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
	if (geoResponse.success())
	{
	  JsonObject jsonObject = geoResponse.getJson().getAsJsonObject().get("places").getAsJsonObject();
	  count = jsonObject.get("count").getAsInt();
	  if (count == 1) 
	  {  
		int id = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("woeid").getAsInt();
		String placeTypeName = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("placeTypeName").getAsString();
		//if (placeTypeName.equals("Country") || placeTypeName.equals("Colloquial") || placeTypeName.equals("State") || placeTypeName.equals("Region") || placeTypeName.equals("Department") || placeTypeName.equals("Town") || placeTypeName.equals("Overseas Region"))
		//{
		HttpResponse geoResponseFr = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		String nameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += " " + nameFr;
		//float centroidLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("latitude").getAsFloat();
		//float centroidLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("longitude").getAsFloat();
		//search += " " + centroidLatitude+"#"+centroidLongitude;	

		float boundingBoxSWLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("latitude").getAsFloat();
		float boundingBoxSWLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("longitude").getAsFloat();
		float boundingBoxNELatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("latitude").getAsFloat();
		float boundingBoxNELongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("longitude").getAsFloat();

		textPlace += " {{"+boundingBoxSWLatitude+","+boundingBoxSWLongitude+"}{"+boundingBoxNELatitude+","+boundingBoxNELongitude+"}}";		 

		HttpResponse geoResponseEn = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=en&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		String nameEn = geoResponseEn.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += " " + nameEn;

		HttpResponse geoResponseEs = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=es&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		String nameEs = geoResponseEs.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += " " + nameEs;

		HttpResponse geoResponseDe = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=de&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		String nameDe = geoResponseDe.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		textPlace += " " + nameDe;

		if (!(geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country").getAsString().equals("")) 
			&& !geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country").getAsString().equals("France")
			&& placeTypeName.equals("country"))
		{
		  String countryCode = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country attrs").getAsJsonObject().get("code").getAsString();
		  textPlace += " " + countryCode;
		}			
	  }	
	}
	//System.out.println("search: " + search);
	return textPlace;
  }  

}
