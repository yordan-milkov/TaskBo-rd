<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$issueUID = trim($_GET['issueUID']);

        $row = mysql_query("SELECT * FROM checks WHERE issueUID = '$issueUID'");
        	$arr = array();
        	while($rs = mysql_fetch_assoc($row)) {
        		$arr[] = $rs;
        	};
        	echo json_encode($arr);
?>