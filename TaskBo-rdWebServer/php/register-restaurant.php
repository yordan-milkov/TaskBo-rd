<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/json');
include 'dbConfig.php';
$restName = trim($_POST['restName']);
$restDesc = trim($_POST['restDesc']);
$restMail = trim($_POST['restMail']);
$restGSM = trim($_POST['restGSM']);
            $userCount = mysql_query('SELECT `rest_id` FROM `restaurants`
                WHERE `rest_mail` = "'.mysql_real_escape_string($restMail).'"');
            $userCount = mysql_numrows($userCount);
            if($userCount == 0){
              mysql_query('INSERT INTO restaurants (rest_name, rest_info, rest_mail, rest_phone)
                    VALUES("'.addslashes($restName).'", "'.$restDesc.'", "'.$restMail.'", "'.$restGSM.'")');
               if(mysql_error()) {
                    echo mysql_error();
                }
                $success = array('error'=> 0);
                echo json_encode($success);
            }
            else
            {
                $err = array('error'=> 1, 'msg' => "Restaurant with such mail already exist!", 'errNum' => 453);
                echo json_encode($err);
            }
?>