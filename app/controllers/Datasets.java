package controllers;

import play.*;
import play.db.DB;
import play.mvc.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;

import static org.elasticsearch.index.query.QueryBuilders.*;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import static org.elasticsearch.node.NodeBuilder.*;


import models.*;

public class Datasets extends Controller {
     
  public static void search(String search) {
    String[] splittedSearch = search.split(" ");
    List<Dataset> datasets = new ArrayList<Dataset>();
	for (int i = 0; i < splittedSearch.length; ++i)
	{
		datasets.addAll((Collection) Dataset.find("name", splittedSearch[i]).asList());
	}	
	render("Application/Search/datasets.html", datasets);
  }
	
	
  public static void show(Long id) {
    Dataset dataset = Dataset.findById(id);
    render(dataset);
  } 
}