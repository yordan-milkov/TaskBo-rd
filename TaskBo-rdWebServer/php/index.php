<?php

header('Access-Control-Allow-Origin: *');
header('Content-type: application/json');
header('Access-Control-Allow-Headers: x-requested-with'); 
include 'DB_Functions.php';
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $username = $_POST['username'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByUsernameAndPassword($username, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            $response["user"]["uid"] = $user["unique_id"];
            $response["user"]["username"] = $user["username"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Wrong username or password or user with that username is already logged in in other device!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new user
        $username = $_POST['username'];
        $password = $_POST['password'];

        // check if user is already existed
        if ($db->isUserExisted($username)) {
            // user is already existed - error response
            $response["error"] = 1;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($username, $password);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["user"]["uid"] = $user["unique_id"];
                $response["user"]["username"] = $user["username"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } else if ($tag == 'logOut') {
      if ($db->logOutUser($_POST['username'], $_POST['uid'])) {
        $response["success"] = 1;
        echo json_encode($response);
      } else {
        $response["error"] = 1;
        $response["error_msg"] = "Error occured in logOut";
        echo json_encode($response);     
      }
    } else if ($tag == 'updateLocation') {
      if ($db->updateUserLocation($_POST['username'], $_POST['uid'], $_POST['latitude'], $_POST['longitude'])) {  
        $response["success"] = 1;
        echo json_encode($response);
      } else {
        $response["error"] = 1;
        $response["error_msg"] = "Error occured for updating user info.";
        echo json_encode($response);
      }  
    } else if ($tag == 'getUsers') {
      $users = $db->getUsers($_POST["username"]);
      if ($users != false) {
        $response["success"] = 1;
        $response["data"] = $users;
        echo json_encode($response);     
      } else {
        $response["success"] = 1;
        echo json_encode($response);  
      }    
    } else if ($tag == 'deleteUser'){
        $db->deleteUser($_POST['username']);
    } else {
        echo "Invalid Request";
    }
} else {
    echo "Access Denied";
}
?>
