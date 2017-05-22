<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$name = trim($_POST['name']);
$description = trim($_POST['description']);

    if(isset($_POST['groupUID'])) {//update group
        $groupUID = trim($_POST['groupUID']);
        mysql_query("UPDATE groups SET name = '$name', description = '$description' WHERE UID = '$groupUID'");
        if(mysql_error()) {
            echo mysql_error();
        }
        $success = array('error'=> 0);
        echo json_encode($success);
    }
    else
    {//create group
        mysql_query("INSERT INTO groups (name, description) VALUES('$name', '$description')");
        if(mysql_error()) {
            echo mysql_error();
        }
        else
            echo json_encode(mysql_insert_id());
    }

?>
