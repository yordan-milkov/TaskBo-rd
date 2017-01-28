<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

$uid = trim($_POST['UID']);
$pass = trim($_POST['password']);

        // $pass = md5($pass);
        $query = mysql_query("SELECT * FROM users WHERE UID='$uid' AND password = '$pass'");

        $count = mysql_numrows($query);
        if($count == 1){
            $rs = mysql_fetch_assoc($query);

            echo json_encode($rs);
        }
        else
        {
            echo "Unable to connect to DB: " . mysql_error();
            $err = array('res' => "Username or password is invalid!$uid" );
            echo json_encode($err);
        }

?>