package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

import play.modules.morphia.Model;

@Entity
public class Dataset extends Model
{		
  /*** Harvester information ***/
  @Id	
  public Long id;
  public String name;
  public String url;
  public String type;
  public String status;
  public String tempDirectory;
  public String currentLower;
  public boolean fromOutside;


  /*** Portal information [based on EML] ***/
  public String title;
  public String description;
  public String resourceLanguage;
  public String basisOfRecord;

  //Resource contact
  public String resourceContactName;
  public String resourceContactRole;
  public String resourceContactAddress;
  public String resourceContactEmail;
  public String resourceContactTelephone;

  //Resource creator
  public String resourceCreatorName;
  public String resourceCreatorRole;
  public String resourceCreatorAddress;
  public String resourceCreatorEmail;
  public String resourceCreatorTelephone;

  //Metadata provider
  public String metadataProviderName;
  public String metadataProviderRole;
  public String metadataProviderAddress;
  public String metadataProviderEmail;
  public String metadataProviderTelephone;

  //GeographicCoverage
  public String geographicCoverageDescription;
  public String geographicCoverageBoundingCoordinates;

  //TaxonomicCoverage
  public String taxonomicCoverageDescription;
  public String taxonomicCoverageTaxonList;

  //TemporalCoverage
  public String temporalCoverageDate;

  //Keywords
  public String keywords;

  //SamplingMethods
  public String samplingDescription;
  public String qualityControl;

  //Citations
  public String resourceCitation;

  //Externals links
  public String homePageLink;
  public String dwcArchiveLink;

  public String tagsText;
  public String[] tags;

  @Reference
  public DataPublisher dataPublisher;
  
  
  
  public static List<Long> getDatasetsIds(String search)
  {
	List<Dataset> datasets = new ArrayList<Dataset>();
	List<Long> datasetsIds = new ArrayList<Long>();
	String[] splittedSearch = search.split(";");
	for (int h = 0; h < splittedSearch.length; ++h)
	{ 
      splittedSearch[h] = Util.normalize(splittedSearch[h]);
      splittedSearch[h] = splittedSearch[h].toLowerCase();    	
      datasets.addAll((Collection) Dataset.find("tags", splittedSearch[h]).asList());		
      //Removes duplicates
      for (int i = 0; i < datasets.size(); ++i)
        for (int j = i + 1; j < datasets.size(); ++j)
          if (datasets.get(i).id == datasets.get(j).id)
    	    datasets.remove(j);
    	for (int i = 0; i < datasets.size(); ++i) datasetsIds.add(datasets.get(i).id);	 
	  }
	return datasetsIds;
  }


}


