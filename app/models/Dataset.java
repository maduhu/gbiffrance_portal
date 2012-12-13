package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

import play.modules.morphia.Model;


/**
 * The Dataset class is related to the MongoDB Dataset collection created after the harvesting process (gbiffrance-harvest)
 */
@Entity
public class Dataset extends Model
{		
  /***
   * the dataset ID
   */
  @Id	
  public Long id;
  /***
   * the dataset name
   */
  public String name;
  /***
   * the access point to the dataset DwC-Archive or standardised XML
   */
  public String url;
  /***
   * the dataset type: ipt, digir, tapir, biocase
   */
  public String type;
  /***
   * the dataset harvest process status
   */
  public String status;
  /***
   * the temporary directory where data is stored during the harvest process
   */
  public String tempDirectory;
  /***
   * if an error occurs, shows the alphabetical range on which the harvest process stopped
   */
  public String currentLower;
  /***
   * flag that informs if the dataset is from outside the national network
   */
  public boolean fromOutside;
  /***
   * the dataset title
   */
  public String title;
  /***
   * the dataset description
   */
  public String description;
  /***
   * the language used in the dataset
   */
  public String resourceLanguage;
  /***
   * the dataset basis of record
   */
  public String basisOfRecord;
  /***
   * the dataset contact name
   */
  public String resourceContactName;
  /***
   * the dataset contact role
   */
  public String resourceContactRole;
  /***
   * the dataset contact address
   */
  public String resourceContactAddress;
  /***
   * the dataset contact email
   */
  public String resourceContactEmail;
  /***
   * the dataset contact telephone
   */
  public String resourceContactTelephone;

  /***
   * the dataset creator name
   */
  public String resourceCreatorName;
  /***
   * the dataset creator role
   */
  public String resourceCreatorRole;
  /***
   * the dataset creator address
   */
  public String resourceCreatorAddress;
  /***
   * the dataset creator email
   */
  public String resourceCreatorEmail;
  /***
   * the dataset creator telephone
   */
  public String resourceCreatorTelephone;

  /***
   * the dataset metadata creator name
   */
  public String metadataProviderName;
  /***
   * the dataset metadata creator role
   */
  public String metadataProviderRole;
  /***
   * the dataset metadata creator address
   */
  public String metadataProviderAddress;
  /***
   * the dataset metadata creator email
   */
  public String metadataProviderEmail;
  /***
   * the dataset metadata creator telephone
   */
  public String metadataProviderTelephone;
  /***
   * the dataset geographic coverage description
   */
  public String geographicCoverageDescription;
  /***
   * the dataset geographic coverage bounding coordinates
   */
  public String geographicCoverageBoundingCoordinates;
  /***
   * the dataset taxonomic coverage description
   */
  public String taxonomicCoverageDescription;
  /***
   * the dataset taxonomic coverage taxon list
   */
  public String taxonomicCoverageTaxonList;
  /***
   * the dataset temporal coverage date
   */
  public String temporalCoverageDate;
  /***
   * the dataset general keywords
   */
  public String keywords;
  /***
   * the dataset sampling description
   */
  public String samplingDescription;
  /***
   * quality control done on the dataset
   */
  public String qualityControl;
  /***
   * how the resource shoul be cited
   */
  public String resourceCitation;
  /***
   * external link to the dataset owner website
   */
  public String homePageLink;
  /***
   * DwC-Archive link 
   */
  public String dwcArchiveLink;
  /***
   * concatenated list of tags that are used to retrieve the dataset on the search bar
   */
  public String tagsText;
  /***
   * table of tags that are used to retrieve the dataset on the search bar
   */
  public String[] tags;

  
  /***
   * the data publisher linked to the dataset
   * @see DataPublisher
   */
  @Reference
  public DataPublisher dataPublisher;
  
  
  /**
   * Returns the dataset id list corresponding to the search
   * @param search
   * 			dataset names or tags
   * @return Dataset id list corresponding to the search
   */
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


