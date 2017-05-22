<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$row = mysql_query(
	"SELECT users.UID, users.name
	FROM `users`");
	$arr = array();
 	while($rs = mysql_fetch_assoc($row)) {
 		$arr[] = $rs;
	};
	echo json_encode($arr);
?>