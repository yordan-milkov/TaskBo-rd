<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

if(isset($_POST['UID'])) 
{
    $UID = trim($_POST['UID']);
    $name = trim($_POST['name']);
    $GSM = trim($_POST['GSM']);
    $mail = trim($_POST['mail']);
    
    mysql_query("UPDATE users SET name = '$name', GSM = '$GSM', mail = '$mail' WHERE UID = '$UID'");
    if(mysql_error()) {
        echo mysql_error();
    }
} 
// else if(isset($_POST['groupUID']))
// {//create issue
//     $groupUID = trim($_POST['groupUID']);
//     mysql_query("INSERT INTO issues (name, description, users, groupUID) VALUES( '$name', '$description', '$users', '$groupUID' )");
//     if(mysql_error()) {
//         echo mysql_error();
//     }
//     else
//     {
//         echo json_encode(mysql_insert_id());
//     }
// }

?>
