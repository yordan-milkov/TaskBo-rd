<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$groupUID = trim($_GET['groupUID']);

        $row = mysql_query("SELECT * FROM groups WHERE UID = '$groupUID'");
        	$rs = mysql_fetch_assoc($row);
        	echo json_encode($rs);
?>