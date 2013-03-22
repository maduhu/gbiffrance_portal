package controllers;

import play.*;
import play.data.validation.Required;
import play.db.DB;
import play.mvc.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

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
import models.Util;

public class Datasets extends Controller {

  
  /**
   * Renders the dataset list corresponding to the search	
   * @param search
   */
  public static void search(String search) {
	List<Dataset> datasets = new ArrayList<Dataset>();
	if (!search.isEmpty())
	{
	  String[] splittedSearch = search.split(";");
	  for (int h = 0; h < splittedSearch.length; ++h)
	  {
    	  splittedSearch[h] = Util.normalize(splittedSearch[h]);
    	  splittedSearch[h] = splittedSearch[h].toLowerCase();
    	  Pattern regex = Pattern.compile("^" + splittedSearch[h]);
    	  datasets.addAll((Collection) Dataset.find("tags", regex).asList());
    	  //Removes duplicates IT IS NOT WORKING!!!!!!!!
    	  for (int i = 0; i < datasets.size(); ++i)
    		for (int j = i + 1; j < datasets.size(); ++j)
    		  if (datasets.get(i).id == datasets.get(j).id)
    		  	datasets.remove(j);
    			//datasets.remove(datasets.get(j));


	  }
	}
	
	render("Application/Search/datasets.html", datasets, search);
  }


  /**
   * Renders a dataset list in JSON format corresponding to a partial search
   * @param search
   */
  public static void autocomplete(String search)
  {
	search = Util.normalize(search);
	search = search.toLowerCase();  
	List<Dataset> datasets = new ArrayList<Dataset>();	
	Pattern regex = Pattern.compile("^" + search);
	datasets.addAll((Collection) Dataset.find("tags", regex).asList());
	renderJSON(datasets);
  }

  
  /**
 	* Show dataset information page (Datasets/show.html)
 	*/
  public static void show(Long id) {
	Dataset dataset = Dataset.findById(id);
	render(dataset);
  } 

  /**
	* Show dataset edition page (Datasets/show.html)
	*/
  public static void edit(Long id) {
	Dataset dataset = Dataset.findById(id);
	render(dataset);
  }

  /**
   * Saves in the database the edited dataset information
   * @param id
   * @param title
   * @param description
   * @param resourceLanguage
   * @param basisOfRecord
   * @param resourceContactName
   * @param resourceContactRole
   * @param resourceContactAddress
   * @param resourceContactEmail
   * @param resourceContactTelephone
   * @param resourceCreatorName
   * @param resourceCreatorRole
   * @param resourceCreatorAddress
   * @param resourceCreatorEmail
   * @param resourceCreatorTelephone
   * @param metadataProviderName
   * @param metadataProviderRole
   * @param metadataProviderAddress
   * @param metadataProviderEmail
   * @param metadataProviderTelephone
   * @param geographicCoverageDescription
   * @param geographicCoverageBoundingCoordinates
   * @param taxonomicCoverageDescription
   * @param taxonomicCoverageTaxonList
   * @param temporalCoverageDate
   * @param keywords
   * @param samplingDescription
   * @param qualityControl
   * @param resourceCitation
   * @param homePageLink
   * @param dwcArchiveLink
   * @param tags
   */
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
	  String dwcArchiveLink,
	  String tags)
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

	  tags = Util.normalize(tags);
	  dataset.tagsText = tags + ';' + Util.normalize(dataset.title.toLowerCase()) + ';' 
		  + Util.normalize(dataset.basisOfRecord.toLowerCase()) + ';' + Util.normalize(dataset.name.toLowerCase());
	  dataset.tags = dataset.tagsText.split(";");

	  dataset.save();
	  Datasets.show(id);
	}	
  }

}