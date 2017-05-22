<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';


    $userName = trim($_POST['name']);
    $action = trim($_POST['action']);
    $groupUID = trim($_POST['groupUID']);

    if ( $action == '0' )
    {
        mysql_query("DELETE FROM `groupusers` WHERE groupusers.groupUID = '$groupUID' and groupusers.userUID = '$userName'");
        if(mysql_error()) {
            echo mysql_error();
        }
        $success = array('error'=> 0);
        echo json_encode($success);
    }
    else if ( $action == '1' )
    {
        mysql_query("INSERT INTO `groupusers` (groupUID, userUID) VALUES('$groupUID','$userName')");
        if(mysql_error()) {
            echo mysql_error();
        }
        $success = array('error'=> 0);
        echo json_encode($success);
    }
?>
