<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

    $name = trim($_POST['name']);

    if(isset($_POST['checkUID']) {//update check
        $checkUID = trim($_POST['checkUID']);
            $checkCount = mysql_query("SELECT * FROM checks WHERE checkUID = '$checkUID'");
        $checkCount = mysql_numrows($checkCount);
        if($checkCount != 0){
            mysql_query("UPDATE checks SET name = '$name' WHERE checkUID = '$checkUID'");
            if(mysql_error()) {
                echo mysql_error();
            }
            $success = array('error'=> 0);
            echo json_encode($success);
        }
        else
        {
            $err = array('error'=> 1, 'msg' => "IssueCheck $checkUID Update failed: IssueCheck with such UID doesn't exist!", 'errNum' => 999);
            echo json_encode($err);
        }
    } else {//create check
        $checkCount = mysql_query("SELECT * FROM checks WHERE name = '$name'");
        $checkCount = mysql_numrows($checkCount);
        if($checkCount == 0){
           mysql_query('INSERT INTO checks (name) VALUES("'$name'")');
           if(mysql_error()) {
                echo mysql_error();
            }
            $success = array('error'=> 0);
            echo json_encode($success);
        }
        else
        {
            $err = array('error'=> 1, 'msg' => "Issue Create failed: Issue with such Name already exist!", 'errNum' => 999);
            echo json_encode($err);
        }
    }

            
?>
