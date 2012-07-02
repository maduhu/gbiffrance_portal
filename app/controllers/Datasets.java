package controllers;

import play.*;
import play.data.validation.Required;
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
  
  public static void edit(Long id) {
    Dataset dataset = Dataset.findById(id);
    render(dataset);
  }
  
  public static void save(@Required Long id, 
		  				  @Required String title, 
		  				  String description,
		  				  String resourceLanguage,
		  				  String basisOfRecord,
		  				  String resourceContactName,
		  				  String resourceContactRole,
		  				  String resourceContactAddress,
		  				  String resourceContactEmail,
		  				  String resourceContactTelephone,		  				  
		  				  String resourceCreatorName,
		  				  String resourceCreatorRole,
		  				  String resourceCreatorAddress,
		  				  String resourceCreatorEmail,
		  				  String resourceCreatorTelephone,		  				  
		  				  String metadataProviderName,
		  				  String metadataProviderRole,
		  				  String metadataProviderAddress,
		  				  String metadataProviderEmail,
		  				  String metadataProviderTelephone,		  				  
		  				  String geographicCoverageDescription,
		  				  String geographicCoverageBoundingCoordinates,
		  				  String taxonomicCoverageDescription,
		  				  String taxonomicCoverageTaxonList,
		  				  String temporalCoverageDate,
		  				  String keywords,
		  				  String samplingDescription,
		  				  String qualityControl,
		  				  String resourceCitation,
		  				  String homePageLink,
		  				  String dwcArchiveLink)
  {
	  if(validation.hasErrors()){
		  params.flash(); // add http parameters to the flash scope
		  validation.keep(); // keep the errors for the next request
		  edit(id);
	  }
	  else
	  {
	    Dataset dataset = Dataset.findById(id);
		dataset.title = title;
		dataset.description = description;
		dataset.resourceLanguage = resourceLanguage;
		dataset.basisOfRecord = basisOfRecord;
		dataset.resourceContactName = resourceContactName;
		dataset.resourceContactRole = resourceContactRole;
		dataset.resourceContactAddress = resourceContactAddress;
		dataset.resourceContactEmail = resourceContactEmail;
		dataset.resourceContactTelephone = resourceContactTelephone;
		dataset.resourceCreatorName = resourceCreatorName;
		dataset.resourceCreatorRole = resourceCreatorRole;
		dataset.resourceCreatorAddress = resourceCreatorAddress;
		dataset.resourceCreatorEmail = resourceCreatorEmail;
		dataset.resourceCreatorTelephone = resourceCreatorTelephone;
		dataset.metadataProviderName = metadataProviderName;
		dataset.metadataProviderRole = metadataProviderRole;
		dataset.metadataProviderAddress = metadataProviderAddress;
		dataset.metadataProviderEmail = metadataProviderEmail;
		dataset.metadataProviderTelephone = metadataProviderTelephone;
		dataset.geographicCoverageDescription = geographicCoverageDescription;
		dataset.geographicCoverageBoundingCoordinates = geographicCoverageBoundingCoordinates;
		dataset.taxonomicCoverageDescription = taxonomicCoverageDescription;
		dataset.taxonomicCoverageTaxonList = taxonomicCoverageTaxonList;
		dataset.temporalCoverageDate = temporalCoverageDate;
		dataset.keywords = keywords;
		dataset.samplingDescription = samplingDescription;
		dataset.qualityControl = qualityControl;
		dataset.homePageLink = homePageLink;
		dataset.dwcArchiveLink = dwcArchiveLink;
						
		dataset.save();
		Datasets.show(id);
	  }	
  }
  
}