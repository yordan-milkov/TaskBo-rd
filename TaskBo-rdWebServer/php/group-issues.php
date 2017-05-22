<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$groupUID = trim($_GET['groupUID']);

        $row = mysql_query("SELECT name, description, issueUID, isResolved FROM issues WHERE groupUID = '$groupUID'");
        	$arr = array();
        	while($rs = mysql_fetch_assoc($row)) {
        		$arr[] = $rs;
        	};
        	echo json_encode($arr);
?>