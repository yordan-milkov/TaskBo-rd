<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/json');
include 'dbConfig.php';
        $row = mysql_query('SELECT * FROM `restaurants`');
        	$arr = array();
        	while($rs = mysql_fetch_assoc($row)) {
        		$arr[] = $rs;
        	};
        	echo json_encode($arr);
?>