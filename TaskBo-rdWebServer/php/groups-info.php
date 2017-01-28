<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/json');
include 'dbConfig.php';

$userUID = trim($_GET['UID']);

        $row = mysql_query('SELECT groupusers.groupUID, groups.name, groups.description FROM `groupusers` INNER JOIN `groups` ON groupusers.groupUID = groups.UID WHERE groupusers.userUID ="'.$userUID.'"');
        	$arr = array();
        	while($rs = mysql_fetch_assoc($row)) {
        		$arr[] = $rs;
        	};
        	echo json_encode($arr);
?>