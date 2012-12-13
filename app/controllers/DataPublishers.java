package controllers;

import play.*;
import play.db.DB;
import play.libs.WS;
import play.libs.WS.HttpResponse;
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

public class DataPublishers extends Controller {

  /**
	* Show datapublisher information (DataPublishers/show.html)
	*/	
  public static void show(Long id) {
	DataPublisher dataPublisher = DataPublisher.findById(id);
	List<Dataset> datasets = Dataset.find("dataPublisher", dataPublisher).asList();

	render(dataPublisher, datasets);
  } 
}