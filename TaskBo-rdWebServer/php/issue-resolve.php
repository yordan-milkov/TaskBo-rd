<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

    $isResolved = trim($_POST['isResolved']);
    $issueUID= trim($_POST['issueUID']);

    mysql_query("UPDATE issues SET isResolved = '$isResolved' WHERE issueUID = '$issueUID'");
    if(mysql_error()) {
        echo mysql_error();
    }
     $success = array('error'=> 0);
     echo json_encode($success);
            
?>
