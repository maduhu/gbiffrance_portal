package controllers;

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

  public static void search(String searchTaxa, String searchPlace, String searchCoordinates, 
	  						String searchDataset, String searchDate)
  {
	searchTaxa = Util.normalize(searchTaxa);
	searchPlace = Util.normalize(searchPlace);
	searchDataset = Util.normalize(searchDataset);
	searchDate = Util.normalize(searchDate);
	Search search = new Search();
	search.placeText = searchPlace;
	search.taxaText = searchTaxa;
	search.dataset = searchDataset;
	search.dateText = searchDate;
	search.onlyWithCoordinates = Boolean.parseBoolean(searchCoordinates);
	render("Application/Search/search.html", search);
  }

  public static void index() {
	render();
  } 
}