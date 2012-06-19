package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.*;

import com.google.gson.JsonObject;
import models.*;

  public class Places extends Controller {
    
  public static void search(String search)
  {
	boolean results = false;
    String[] splittedSearch = search.split(" ");
	List<Place> places = new ArrayList<Place>();
	for (int i = 0; i < splittedSearch.length; ++i)
	{
	  HttpResponse geoResponse = WS.url("http://where.yahooapis.com/v1/places.q('"+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
	  JsonObject jsonObject = geoResponse.getJson().getAsJsonObject().get("places").getAsJsonObject();
	  //System.out.println("Search Places : " + "http://where.yahooapis.com/v1/places.q('"+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
	  int jsonCount = jsonObject.get("count").getAsInt();
	  /* For places like New York */
	  if (jsonCount == 1) 
	  {		  
		if (i-1 >= 0)  
		{
			HttpResponse geoResponse2 = WS.url("http://where.yahooapis.com/v1/places.q('"+splittedSearch[i-1]+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
			JsonObject jsonObject2 = geoResponse2.getJson().getAsJsonObject().get("places").getAsJsonObject();
			//System.out.println("Search Places 2 : " + "http://where.yahooapis.com/v1/places.q('"+splittedSearch[i-1]+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
			int jsonCount2 = jsonObject2.get("count").getAsInt();
			//System.out.println("count2 " + jsonCount2);
			if (jsonCount2 == 1)
			{
			  geoResponse = geoResponse2;
			  jsonObject = jsonObject2;
			  jsonCount = jsonCount2;
			}	
		}
	  }
	  /* For places like Buenos Aires */
	  if (jsonCount == 0) 
	  {		  
		if (i-1 >= 0)  
		{
			HttpResponse geoResponse2 = WS.url("http://where.yahooapis.com/v1/places.q('"+splittedSearch[i-1]+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
			JsonObject jsonObject2 = geoResponse2.getJson().getAsJsonObject().get("places").getAsJsonObject();
			//System.out.println("Search Places 2 : " + "http://where.yahooapis.com/v1/places.q('"+splittedSearch[i-1]+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
			int jsonCount2 = jsonObject2.get("count").getAsInt();
			//System.out.println("count2 " + jsonCount2);
			if (jsonCount2 == 1)
			{
			  geoResponse = geoResponse2;
			  jsonObject = jsonObject2;
			  jsonCount = jsonCount2;
			}	
		}
	  }
	  if (jsonCount == 1)	  
	  {	  
		Place place = new Place();  
		int id = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("woeid").getAsInt();
		place.id = id;
		String placeTypeName = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("placeTypeName").getAsString();
		place.placeTypeName = placeTypeName;
		if (placeTypeName.equals("Country") || placeTypeName.equals("Colloquial") || placeTypeName.equals("State") || placeTypeName.equals("Region") || placeTypeName.equals("Department") || placeTypeName.equals("Town") || placeTypeName.equals("Overseas Region") || placeTypeName.equals("Island"))
		{
	      HttpResponse geoResponseFr = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  float centroidLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("latitude").getAsFloat();
		  float centroidLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("longitude").getAsFloat();
		  float boundingBoxSWLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("latitude").getAsFloat();
		  float boundingBoxSWLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("longitude").getAsFloat();
		  float boundingBoxNELatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("latitude").getAsFloat();
		  float boundingBoxNELongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("longitude").getAsFloat();
		  
		  place.nameFr = nameFr;
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
	}
	render("Application/Search/places.html", results, places);
  } 
		
		
  /* Adds places translations to the search ex: search = Pica pica France -> search = Pica pica France France Francia Frankreich*/
  public static String enrichSearchWithPlaces(String search)
  {	  
	int count = 0;
	String[] splittedSearch = search.split(" ");
	for (int i = 0; i < splittedSearch.length; ++i)
	{
	  //System.out.println("http://where.yahooapis.com/v1/places.q('"+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
	  HttpResponse geoResponse = WS.url("http://where.yahooapis.com/v1/places.q('"+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
	  JsonObject jsonObject = geoResponse.getJson().getAsJsonObject().get("places").getAsJsonObject();
	  count = jsonObject.get("count").getAsInt();
	  if (count == 1) 
	  {  
		int id = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("woeid").getAsInt();
		String placeTypeName = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("placeTypeName").getAsString();
		if (placeTypeName.equals("Country") || placeTypeName.equals("Colloquial") || placeTypeName.equals("State") || placeTypeName.equals("Region") || placeTypeName.equals("Department") || placeTypeName.equals("Town") || placeTypeName.equals("Overseas Region"))
		{
          HttpResponse geoResponseFr = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  search += " " + nameFr;
		  //float centroidLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("latitude").getAsFloat();
		  //float centroidLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("longitude").getAsFloat();
		  //search += " " + centroidLatitude+"#"+centroidLongitude;	
		  
		  float boundingBoxSWLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("latitude").getAsFloat();
		  float boundingBoxSWLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("southWest").getAsJsonObject().get("longitude").getAsFloat();
		  float boundingBoxNELatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("latitude").getAsFloat();
		  float boundingBoxNELongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("boundingBox").getAsJsonObject().get("northEast").getAsJsonObject().get("longitude").getAsFloat();
		  
		  search += " {{"+boundingBoxSWLatitude+","+boundingBoxSWLongitude+"}{"+boundingBoxNELatitude+","+boundingBoxNELongitude+"}}";		 
		  
		  HttpResponse geoResponseEn = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=en&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameEn = geoResponseEn.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  search += " " + nameEn;
			  
		  HttpResponse geoResponseEs = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=es&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameEs = geoResponseEs.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  search += " " + nameEs;
				  				
		  HttpResponse geoResponseDe = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=de&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  String nameDe = geoResponseDe.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
		  search += " " + nameDe;
		  
		  if (!geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country").getAsString().equals("France"))
		  {
		    String countryCode = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("country attrs").getAsJsonObject().get("code").getAsString();
		    search += " " + countryCode;
		  }
		  
		}		
	  }
	}
	//System.out.println("search: " + search);
	return search;
  } 

  public static List<Place> getPlacesFromSearch(String search)
  {
	  String[] splittedSearch = search.split(" ");
		List<Place> places = new ArrayList<Place>();
		for (int i = 0; i < splittedSearch.length; ++i)
		{
		  HttpResponse geoResponse = WS.url("http://where.yahooapis.com/v1/places.q('"+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
		  JsonObject jsonObject = geoResponse.getJson().getAsJsonObject().get("places").getAsJsonObject();
		  //System.out.println("Get Places From Search : " + "http://where.yahooapis.com/v1/places.q('"+splittedSearch[i]+"')?format=json&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--");
		  int jsonCount = jsonObject.get("count").getAsInt();
		  if (jsonCount == 1) 
		  {
			Place place = new Place();  
			int id = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("woeid").getAsInt();
			place.id = id;
			String placeTypeName = jsonObject.get("place").getAsJsonArray().get(0).getAsJsonObject().get("placeTypeName").getAsString();
			place.placeTypeName = placeTypeName;
			if (placeTypeName.equals("Country") || placeTypeName.equals("Colloquial") || placeTypeName.equals("State") || placeTypeName.equals("Region") || placeTypeName.equals("Department") || placeTypeName.equals("Town") || placeTypeName.equals("Overseas Region"))
			{
		      HttpResponse geoResponseFr = WS.url("http://where.yahooapis.com/v1/place/" + id + "?format=json&lang=fr&appid=M3lUf_vV34FjRZ.y0gzSptK7oUgWsLVnIJp_GD32DD1Ae7nfam.UgjnRV9PZlxzQYg--").get();
			  String nameFr = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("name").getAsString();
			  float centroidLatitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("latitude").getAsFloat();
			  float centroidLongitude = geoResponseFr.getJson().getAsJsonObject().get("place").getAsJsonObject().get("centroid").getAsJsonObject().get("longitude").getAsFloat();
			  place.nameFr = nameFr;
			  place.centroidLatitude = centroidLatitude;
			  place.centroidLongitude = centroidLongitude;
						  			  	
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
			}
		  }
		} 
		return places;
  }
  
}