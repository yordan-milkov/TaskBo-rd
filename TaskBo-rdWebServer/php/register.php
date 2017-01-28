<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/json');
include 'dbConfig.php';

$userName = trim($_POST['userName']);
$pass = trim($_POST['userPass']);
$nameReal = trim($_POST['userRegNameReal']);
$address = trim($_POST['userAddr']);
$phone = trim($_POST['userPhone']);
            $userCount = mysql_query('SELECT `user_id` FROM `users`
                WHERE `name` = "'.mysql_real_escape_string($userName).'"');
            $userCount = mysql_numrows($userCount);
            if($userCount == 0){
              mysql_query('INSERT INTO users (name, name_real, password, phone, address)
                    VALUES("'.addslashes($userName).'", "'.$nameReal.'", "'.md5($pass).'", "'.$phone.'", "'.$address.'")');
               if(mysql_error()) {
                    echo mysql_error();
                }
                $success = array('error'=> 0);
                echo json_encode($success);
            }
            else
            {
                $err = array('error'=> 1, 'msg' => "Username already exist!", 'errNum' => 453);
                echo json_encode($err);
            }
?>
