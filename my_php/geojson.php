<?

include 'config.php';
connect();
//name of selected taxa
$taxa=$_GET['taxa'];
$collection=$_GET['collection'];
//genus,specie...
$type=$_GET['type'];
//kingdom,phylum,class,order,family,genus,scientificname,specificepithet
switch ($type)
{

case 'specificepithet':
$array='array_specificepithet';
break;

case 'species':
$array='array_scientificname';
break;


case 'genus':
$array='array_genus';
break;

case 'family':
$array='array_family';
break;


case 'order':
$array='array_order';
break;

case 'class':
$array='array_class';
break;

case 'phylum':
$array='array_phylum';
break;

case 'kingdom':
$array='array_kingdom';
break;

}
//POLYGON QUERY for future

//$query="SELECT ST_AsGeoJSON(t2.the_geom,2),SUM(t2.counter) FROM ( SELECT (regexp_matches(t::text, E'(".$taxa."[\+])([0-9]+)'::text)::text[])[2]::integer as counter, poly.the_geom from (select the_geom,array_genus from $collection where array_genus ~ '".$taxa."'::text) as t inner join half_degree_france poly ON poly.the_geom && t.the_geom and CONTAINS(poly.the_geom,t.the_geom)) t2 group by the_geom";

//this query does not work with complex strings (ex: Genus Species (Linneo, 1998)

$query="SELECT ST_AsGeoJSON(t2.the_geom,2),t2.counter FROM ( SELECT (regexp_matches(t::text, E'(".$taxa."[\+])([0-9]+)'::text)::text[])[2]::integer as counter, t.the_geom from (select the_geom,$array from $collection where $array ~ '".$taxa."'::text) t ) t2 group by counter,t2.the_geom";

 $result = pg_query($connexion,$query); 

 $i=0;
 $data=array();
 $geojson = array(
      'type'      => 'FeatureCollection',
      'features'  => array()
   ); 

$geojson['taxa']=$taxa;
    while($row=pg_fetch_row($result)) 
    {  
    if ($row[0]!='')  //hack; if no geometry, just skip it
    {
		$feature = array(
         'type' => 'Feature',
         'geometry' => json_decode($row[0], true),
          'properties' => 
          array(          
          'count' => $row[1],
          'style'  =>   array( 
          'fillColor' => 'blue'          
          )
          )   
     	);
     	
 	$data['counts'][]=$row[1];
 	$i++;
      array_push($geojson['features'], $feature);
      }
      }
 
  //ONLY 4 INTERVALS... to improve

   $geojson['max']=(int)(max($data['counts']));
   $geojson['min']=(int)(min($data['counts']));
   $geojson['data_counts']=$i;
   
    $medium=(int)(($geojson['max']+$geojson['min'])/2);
    $json['medium']=$medium;
    $to_add_remove=(int)(($geojson['max']-$medium)/2);
   
    $geojson['interval_1']=$medium-$to_add_remove;
    $geojson['interval_2']=$medium+$to_add_remove;
   
    $json=json_encode($geojson,JSON_NUMERIC_CHECK );

  //for the moment we are on different domains (localhost:9000 calling this PHP in localhost:8887, so we have to use JSONP
 if($_GET['callback']) { echo $_GET['callback']."(".$json.")"; } else { echo $json; };
 
  


?>