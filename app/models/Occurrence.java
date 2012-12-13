package models;

import java.util.ArrayList;

/**
 * The Occurrence class is related to the ElasticSearch collection created after the harvesting process (gbiffrance-harvest)
 * @author Michael Akbaraly
 *
 */
public class Occurrence
{
  public Integer id;	

  //Record-level
  public String typee;
  public String modified;
  public String language;
  public String rights;
  public String rightsHolder;
  public String accessRights;
  public String bibliographicCitation;
  public String referencess;
  public String institutionID;
  public String collectionID;

  public String datasetID;

  public String institutionCode;

  public String collectionCode;

  public String datasetName;
  public String ownerInstitutionCode;

  public String basisOfRecord;
  public String informationWithheld;
  public String dataGeneralizations;
  public String dynamicProperties;

  //Occurrence
  public String occurrenceID;
  public String catalogNumber;
  public String occurrenceRemarks;
  public String recordNumber;
  public String recordedBy;
  public String individualID;
  public String individualCount;
  public String sex;
  public String lifeStage;
  public String reproductiveCondition;
  public String behavior;
  public String establishmentMeans;
  public String occurrenceStatus;
  public String preparations;
  public String disposition;
  public String otherCatalogNumbers;
  public String previousIdentifications;
  public String associatedMedia;
  public String associatedReferences;
  public String associatedOccurrences;
  public String associatedSequences;
  public String associatedTaxa;

  //Event
  public String eventID;
  public String samplingProtocol;
  public String samplingEffort;
  public String eventDate;
  public String eventTime;
  public String startDayOfYear;
  public String endDayofYear;

  public String year;
  public String month;
  public String day;
  public String verbatimEventDate;
  public String habitat;
  public String fieldNumber;
  public String fieldNotes;
  public String eventRemarks;

  //Location
  public String locationID;
  public String higherGeographyID;
  public String higherGeography;

  public String continent;
  public String waterBody;
  public String islandGroup;
  public String island;

  public String country;
  public String countryCode;
  public String stateProvince;
  public String county;
  public String municipality;

  public String locality;
  public String verbatimLocality;
  public String verbatimElevation;
  public String minimumElevationInMeters;
  public String maximumElevationInMeters;
  public String verbatimDepth;
  public String minimumDepthInMeters;
  public String maximumDepthInMeters;
  public String minimumDistanceAboveSurfaceInMeters;
  public String maximumDistanceAboveSurfaceInMeters;
  public String locationAccordingTo;
  public String locationRemarks;
  public String verbatimCoordinates;
  public String verbatimLatitude;
  public String verbatimLongitude;
  public String verbatimCoordinateSystem;
  public String verbatimSRS;

  public String decimalLatitude;

  public String decimalLongitude;
  public String geodeticDatum;
  public String coordinateUncertaintyInMeters;
  public String coordinatePrecision;
  public String pointRadiusSpatialFit;
  public String footprintWKT;
  public String footprintSRS;
  public String footprintSpatialFit;
  public String georeferencedBy;
  public String georeferencedDate;
  public String georeferenceProtocol;
  public String georeferenceSources;
  public String georeferenceVerificationStatus;
  public String georeferenceRemarks;

  //GeologicalContext
  public String geologicalContextID;
  public String earliestEonOrLowestEonothem;
  public String latestEonOrHighestEonothem;
  public String earliestEraOrLowestErathem;
  public String latestEraOrHighestErathem;
  public String earliestPeriodOrLowestSystem;
  public String latestPeriodOrHighestSystem;
  public String earliestEpochOrLowestSeries;
  public String latestEpochOrHighestSeries;
  public String earliestAgeOrLowestStage;
  public String latestAgeOrHighestStage;
  public String lowestBiostratigraphicZone;
  public String highestBiostratigraphicZone;
  public String lithostratigraphicTerms;
  public String groupp;
  public String formation;
  public String member;
  public String bed;

  //Identification
  public String identificationID;
  public String identifiedBy;
  public String dateIdentified;
  public String identificationVerificationStatus;
  public String identificationRemarks;
  public String identificationQualifier;
  public String typeStatus;

  //Taxon
  public String taxonID;
  public String scientificNameID;
  public String acceptedNameUsageID;
  public String parentNameUsageID;
  public String originalNameUsageID;
  public String nameAccordingToID;
  public String namePublishedInID;
  public String taxonConceptID;

  public String scientificName;
  public String acceptedNameUsage;
  public String parentNameUsage;
  public String originalNameUsage;
  public String nameAccordingTo;
  public String namePublishedIn;
  public String namePublishedInYear;
  public String higherClassification;
  public String kingdom;
  public String phylum;
  public String classs; //class is a Java keyword
  public String orderr; //order is a SQL keyword
  public String family;
  public String genus;
  public String subgenus;
  public String specificEpithet;
  public String infraSpecificEpithet;
  public String kingdom_interpreted;
  public String phylum_interpreted;
  public String classs_interpreted;
  public String orderr_interpreted;
  public String family_interpreted;
  public String genus_interpreted;
  public String subgenus_interpreted;
  public String specificEpithet_interpreted;
  public String infraSpecificEpithet_interpreted;
  public String taxonRank;
  public String verbatimTaxonRank;
  public String scientificNameAuthorship;
  public String vernacularName;
  public String nomenclaturalCode;
  public String taxonomicStatus;
  public String nomenclaturalStatus;
  public String taxonRemarks;


  public String taxonStatus;
  public String ecatConceptId;
  public String ecatParentId;

  /**
   * Hit score get by the occurrence during a search  
   */
  public float score;

  
  /**
   * Related dataset
   * @see Dataset
   */
  public Dataset dataset;

  public Occurrence(){}

}
