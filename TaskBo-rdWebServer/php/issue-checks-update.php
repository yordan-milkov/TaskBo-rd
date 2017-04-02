<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

    if(isset($_POST['name'])) {
        $name = trim($_POST['name']);
        if(isset($_POST['checkUID'])) {//update check
            $checkUID = trim($_POST['checkUID']);
            mysql_query("UPDATE checks SET name = '$name' WHERE checkUID = '$checkUID'");
            if(mysql_error()) {
                echo mysql_error();
            }
            $success = array('error'=> 0);
            echo json_encode($success);
        }
        else {//create check
            $issueUID = trim($_POST['issueUID']);
            mysql_query("INSERT INTO checks (name, issueUID) VALUES('$name','$issueUID')");
            if(mysql_error()) {
                    echo mysql_error();
                }
            $success = array('error'=> 0);
            echo json_encode($success);
            
        }
    }
    else if(isset($_POST['checkUID'])) {//delete
        $checkUID = trim($_POST['checkUID']);
        mysql_query("DELETE FROM checks WHERE checkUID = '$checkUID'");
        if(mysql_error()) {
            echo mysql_error();
        }
        $success = array('error'=> 0);
    }
            
?>
