<?php
header('Access-Control-Allow-Origin: *');
header('Content-type: application/x-www-form-urlencoded; charset=utf-8');

include 'dbConfig.php';

function userExist($UID) {
    $query = mysql_query("SELECT * FROM users WHERE UID='$UID'");
    $count = mysql_numrows($query);
    return ($count == 1);
}

function setPassword($newPass, $UID){
    
}

if(isset($_POST['UID'])) 
{
    $UID = trim($_POST['UID']);
    if(isset($_POST['newPass']))
    {
        $newPass = trim($_POST['newPass']);
        if ( isset( $_POST['oldPass'] ) )
        {
            $oldPass = trim($_POST['oldPass']);
            $query = mysql_query("SELECT * FROM users WHERE UID='$UID' AND password = '$oldPass'");
            $count = mysql_numrows($query);
            if($count == 1)
            {
                mysql_query("UPDATE users SET password = '$newPass' WHERE UID = '$UID'");
                if(mysql_error())
                {
                    echo mysql_error();
                }
                else
                {
                    echo "done";
                }
            }
            else{
                echo "Wrong password" . mysql_error();
                $err = array('res' => "Password is invalid! $uid" );
                echo json_encode($err);
            }
        }
        else{
            if ( userExist($UID) )
            {
                echo "Existing user";
            }
            else{
                mysql_query("INSERT INTO users (UID, password) VALUES ('$UID', '$newPass')" );
                echo mysql_error();
                echo "done";
            }
        }
    }
    else{
        if(userExist($UID))
        {
            echo "Existing user";
        }
        else{
            echo "Register!";
        }
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
