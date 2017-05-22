<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$name = trim($_POST['name']);
$description = trim($_POST['description']);
$users = trim($_POST['users']);


            if(isset($_POST['issueUID'])) 
            {//update issue
                $issueUID = trim($_POST['issueUID']);
               
                mysql_query("UPDATE issues SET name = '$name', description = '$description', users = '$users' WHERE issueUID = '$issueUID'");
                if(mysql_error()) {
                    echo mysql_error();
                }
            } 
            else if(isset($_POST['groupUID']))
            {//create issue
                $groupUID = trim($_POST['groupUID']);
                mysql_query("INSERT INTO issues (name, description, users, groupUID) VALUES( '$name', '$description', '$users', '$groupUID' )");
                if(mysql_error()) {
                    echo mysql_error();
                }
                else
                {
                    echo json_encode(mysql_insert_id());
                }
            }

?>
