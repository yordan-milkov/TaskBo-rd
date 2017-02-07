<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

    $isFinished = trim($_POST['isFinished']);
    $checkUID = trim($_POST['checkUID']);

    $checkCount = mysql_query("SELECT * FROM checks WHERE checkUID = '$checkUID'");
    $checkCount = mysql_numrows($checkCount);
    if($checkCount != 0){
        mysql_query("UPDATE checks SET isFinished = '$isFinished' WHERE checkUID = '$checkUID'");
        if(mysql_error()) {
            echo mysql_error();
        }
        $success = array('error'=> 0);
        echo json_encode($success);
    }
    else
    {
        $err = array('error'=> 1, 'msg' => "IssueCheck $checkUID Finish failed: IssueCheck with such UID doesn't exist!", 'errNum' => 999);
        echo json_encode($err);
    }
            
?>
    