<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/json');
include 'dbConfig.php';
$restID = trim($_GET['id']);
        $row = mysql_query('SELECT * FROM `restaurants` WHERE rest_id="'.$restID.'"');
        	$rs = mysql_fetch_assoc($row);
        	echo json_encode($rs);
?>