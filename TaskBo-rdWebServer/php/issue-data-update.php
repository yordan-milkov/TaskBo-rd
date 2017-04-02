<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

$name = trim($_POST['name']);
$description = trim($_POST['description']);
$users = trim($_POST['users']);


            if(isset($_POST['issueUID'])) {//update issue
                $issueUID = trim($_POST['issueUID']);
                //$issueCount = mysql_query('SELECT `issueUID` FROM `issues` WHERE `name` = "'.mysql_real_escape_string($name).'"');
                //$issueCount = mysql_numrows($issueCount);
                if(true){//$issueCount == 0){
                    mysql_query("UPDATE issues SET name = '$name', description = '$description', users = '$users' WHERE issueUID = '$issueUID'");
                    if(mysql_error()) {
                        echo mysql_error();
                    }
                    $success = array('error'=> 0);
                    echo json_encode($success);
                }
                else
                {
                    $err = array('error'=> 1, 'msg' => "Issue Update failed: Issue with such UID doesn't exist!", 'errNum' => 999);
                    echo json_encode($err);
                }
            } else {//create issue
                $issueCount = mysql_query("SELECT name FROM issues
                    WHERE name = .mysql_real_escape_string('$name')");
                $issueCount = mysql_numrows($issueCount);
                if($issueCount == 0){
                    $groupUID = trim($_POST['groupUID']);
                   mysql_query('INSERT INTO issues (name, description, users, groupUID)
                        VALUES("'.addslashes($name).'", "'.$description.'", "'.$users.'", "'.$groupUID.'")');
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
