package models;

import java.util.ArrayList;
import java.util.List;

public class Taxa
{
	
	//ECAT Information
	public long taxonId;
	public long parent_id;
	
	public String rank;
	
	public long kingdomID;
	public long phylumID;
	public long classID;
	public long orderID;
	public long familyID;
	public long genusID;
	public long speciesID;
	
	public Integer nbPhylum;
	public Integer nbClass;
	public Integer nbOrder;
	public Integer nbFamily;
	public Integer nbGenus;
	public Integer nbSpecies;

	public String accordingTo;
	public String speciesIdentifiedBy;
	public String genusIdentifiedBy;
	
	public String scientificName;
	public String canonicalName;
	
	public String kingdom;
	public String canonicalKingdom;
	public String phylum;
	public String canonicalPhylum;
	public String classs;
	public String canonicalClass;
	public String order;
	public String canonicalOrder;
	public String family;
	public String canonicalFamily;
	public String genus;
	public String canonicalGenus;
	public String species;
	public String canonicalSpecies;
	
	//EOL Information
	public long eolID;
	public String description;
	public List<ArrayList<String>> mediaURLs;
	
	
	
}