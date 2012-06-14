package models;

import java.util.ArrayList;

/*
 * ElasticSearch Collection created after the harvesting process (gbiffrance-harvest)
 */


public class Place
{
  public int id;
  public String name;
  public String nameFr;
  public String nameEn;
  public String nameEs;
  public String nameDe;
  
  public float centroidLatitude;
  public float centroidLongitude;
  
  public float boundingBoxSWLatitude;
  public float boundingBoxSWLongitude;
  public float boundingBoxNELatitude;
  public float boundingBoxNELongitude;
  
  public String placeTypeName;
  public String country;
  
}
