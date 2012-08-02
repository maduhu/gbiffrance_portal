<?
require './config.php';
$connexion=connect();

$taxa=$_GET['taxa'];
$collection_id=$_GET['dataset'];

$query="select name from dataset where id=$collection_id";

$result = pg_query($connexion,$query); 
   while($row=pg_fetch_row($result)) 
   {
   
   $collection='simple_'.$row[0];   
   }

$array='array_'.$_GET['type'];

// parenthesis cause problems on regex parsing, we must escape
if ($array=='array_scientificname')
{
  $taxa= str_replace('(', '\\\(',$taxa);

 $taxa= str_replace( ')', '\\\)',$taxa);
}
$query = "SELECT extent(the_geom) from $collection where $array ~ '$taxa'";

$result = pg_query($connexion,$query); 

$geojson=array();

  while ($row = pg_fetch_array($result, NULL, PGSQL_ASSOC)) 
  {
  	//"BOX(-5.08689442415 42.3901,8.09702 51.0807748932)"
    $extents=substr($row['extent'],4,-1);
    $extents=explode(',',$extents);
    foreach ($extents as $k=>$v)
    {
      $extents=explode(' ',$v);
      $geojson['main_bbox'][]=(int)$extents[0].','.(int)$extents[1];

    }
  }
$json=json_encode($geojson,JSON_NUMERIC_CHECK );

 //for the moment we are on different domains (localhost:9000 calling this PHP in localhost:8887, so we have to use JSONP
 if($_GET['callback']) { echo $_GET['callback']."(".$json.")"; } else { echo $json; };
?>