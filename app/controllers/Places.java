package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.*;

import com.google.gson.JsonObject;
import models.*;

public class Places extends Controller {

  public static void search(String textPlace)
  {
	boolean results = false;
	textPlace = textPlace.replaceAll(" ", "%20");
	List<Place> places = new ArrayList<Place>();

	HttpResponse geoResponse = WS.url("http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
	//System.out.println("http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
	if (geoResponse.success())
	{
	  JsonObject jsonObject = geoResponse.getJson().getAsJsonObject().get("places").getAsJsonObject();
	  System.out.println("Search Places : " + "http://where.yahooapis.com/v1/places.q('"+textPlace+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
	  int jsonCount = jsonObject.get("count").getAsInt(); 
	  if (jsonCount >= 1)	  
	  {	  
		for (int i = 0; i < jsonCount; ++i)
		{
		  Place place = new Place();  
		  int id = jsonObject.get("place").getAsJsonArray().get(i).getAsJsonObject().get("woeid").getAsInt();
		  place.id = id;
		  String placeTypeName = jsonObject.get("place").getAsJsonArray().get(i).getAsJsonObject().get("placeTypeName").getAsString();
		  place.placeTypeName = placeTypeName;

		  HttpResponse geoResponseFr = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  System.out.println("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
		  String nameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  String country = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country").getAsString();
		  String placeTypeNameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("placeTypeName").getAsString();
		  float centroidLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("latitude").getAsFloat();
		  float centroidLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("longitude").getAsFloat();
		  float boundingBoxSWLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("latitude").getAsFloat();
		  float boundingBoxSWLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("longitude").getAsFloat();
		  float boundingBoxNELatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("latitude").getAsFloat();
		  float boundingBoxNELongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("longitude").getAsFloat();

		  place.nameFr = nameFr;
		  place.country = country;
		  place.placeTypeNameFr = placeTypeNameFr;
		  place.centroidLatitude = centroidLatitude;
		  place.centroidLongitude = centroidLongitude;
		  place.boundingBoxSWLatitude = boundingBoxSWLatitude;
		  place.boundingBoxSWLongitude = boundingBoxSWLongitude;
		  place.boundingBoxNELatitude = boundingBoxNELatitude;
		  place.boundingBoxNELongitude = boundingBoxNELongitude;

		  HttpResponse geoResponseEn = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=en&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameEn = geoResponseEn.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  place.nameEn = nameEn;

		  HttpResponse geoResponseEs = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=es&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameEs = geoResponseEs.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  place.nameEn = nameEs;

		  HttpResponse geoResponseDe = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=de&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameDe = geoResponseDe.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  place.nameDe = nameDe;		  

		  places.add(place);
		  results = true;	  
		}
	  }
	  render("Application/Search/places.html", results, places);
	} 

  } 

}