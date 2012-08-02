<?
//avoid deleting passwords and so on each time we git...
function connect() {
	$connexion = pg_connect ("host=my_host dbname=my_dbname user=my_user password=my_pwd");
	return $connexion;
}
?>