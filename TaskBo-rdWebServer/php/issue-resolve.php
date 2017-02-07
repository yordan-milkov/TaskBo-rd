<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

    $isResolved = trim($_POST['isResolved']);
    $issueUID= trim($_POST['issueUID']);

    $issueCount = mysql_query("SELECT * FROM issues WHERE issueUID = '$issueUID'");
    $issueCount = mysql_numrows($issueCount);
    if($issueCount != 0){
        mysql_query("UPDATE issues SET isResolved = '$isResolved' WHERE issueUID = '$issueUID'");
        if(mysql_error()) {
            echo mysql_error();
        }
        $success = array('error'=> 0);
        echo json_encode($success);
    }
    else
    {
        $err = array('error'=> 1, 'msg' => "Issue Resolve failed: Issue with such UID doesn't exist!", 'errNum' => 999);
        echo json_encode($err);
    }
            
?>
