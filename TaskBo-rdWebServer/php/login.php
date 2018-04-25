<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded');
include 'dbConfig.php';

$uid = trim($_POST['UID']);
$pass = trim($_POST['password']);

        $query = mysql_query("SELECT * FROM users WHERE UID='$uid' AND password = '$pass'");

        if( mysql_numrows($query) == 1) {
            $rs = mysql_fetch_assoc($query);
            echo json_encode($rs);
        }
        else
        {
            $query2 = mysql_query("SELECT * FROM users WHERE UID='$uid'");

            if( mysql_numrows($query2) == 1) {
                echo "password";
            }
            else
            {
                echo "user";
            }
        }

?>