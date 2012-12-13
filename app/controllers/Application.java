package controllers;

import play.data.validation.Match;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.*;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import static org.elasticsearch.index.query.QueryBuilders.*;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.util.JSON;



import models.*;
import models.Util;

public class Application extends Controller {

    /**
     * Normalizes the search before creating the corresponding request 
     * @param searchTaxa
     * @param searchPlace
     * @param searchCoordinates
     * @param searchDataset
     * @param searchDate
     */
	public static void search(String searchTaxa, String searchPlace, String searchCoordinates, 
	  						String searchDataset, 
	  						@Match("^([0-9][0-9][0-9][0-9])(-[0-9][0-9][0-9][0-9])?") String searchDate)
  {
	searchTaxa = Util.normalize(searchTaxa);
	searchPlace = Util.normalize(searchPlace);
	searchDataset = Util.normalize(searchDataset);
	searchDate = Util.normalize(searchDate);
	
	Search search = new Search();
	search.placeText = searchPlace;
	search.taxaText = searchTaxa;
	search.dataset = searchDataset;
	if (validation.hasError("searchDate")) 
	{
		flash("errorDate", null);
		search.dateText = "";
	}
	else 
	{
		search.dateText = searchDate;
	}
	search.onlyWithCoordinates = Boolean.parseBoolean(searchCoordinates);
	render("Application/Search/search.html", search);
  }

  /**
   * Renders the portal home page (Application/index.html)
   */
  public static void index() {
	render();
  } 
}