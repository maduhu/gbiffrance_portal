package models;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import play.modules.morphia.Model;

/*
 * MongoDB Collection created during the harvesting process (gbiffrance-harvest)
 */

@Entity
public class DataPublisher extends Model
{	
	@Id
	public Long id;
	public String name;
	public String description;
	public String address;
	public String administrativeContact;
	public String technicalContact;
	public String latitude;
	public String longitude;
	public ArrayList<String> tags;
	public String mediaURL;
	public String imageURL;
}
