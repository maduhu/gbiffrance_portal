package models;

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
              
  
  @Reference
  public DataPublisher dataPublisher;
  
}


