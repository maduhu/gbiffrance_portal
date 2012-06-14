package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

import play.modules.morphia.Model;

@Entity
public class Dataset extends Model
{		
  @Id	
  public Long id;
  public String name;
  public String url;
  public String type;
  public String status;
  public String tempDirectory;
  public String currentLower;
  public boolean fromOutside;
  
  @Reference
  public DataPublisher dataPublisher;
  
  public Dataset() {}
  
  public Dataset(String name, String url, String type, DataPublisher dataPublisher)
  {
	this.name = name;
	this.url = url;
	this.type = type; 
	this.status = "EMPTY";
	this.dataPublisher = dataPublisher;
  }
  
  public void markDataset(String status)
  {
	this.status = status;
  }
}


