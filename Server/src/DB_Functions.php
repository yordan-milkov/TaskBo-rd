<?php

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: x-requested-with"); 
include 'DB_Connect.php';
class DB_Functions {

    private $db;

    //put your code here
    // constructor
    function __construct() {
        //require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($username, $password) {
        $uuid = uniqid('', true);
        $result = mysql_query("INSERT INTO users(unique_id, username, password, active) VALUES('$uuid', '$username', '$password', 1)");
        // check for successful store
        if ($result) {
            // get user details 
            $result = mysql_query("SELECT * FROM users WHERE unique_id='$uuid'");
            // return user details
            return mysql_fetch_array($result);
        } else {
            return false;
        }
    }

    /**
     * Get user by username and password
     */
    public function getUserByUsernameAndPassword($username, $password) {
        $result = mysql_query("SELECT * FROM users WHERE username = '$username'") or die(mysql_error());
        // check for result 
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysql_fetch_array($result);
            if ($result["active"] != false) {
              //$result["error"] = 1;
              //$result["errorMessage"] = "User with (username='$username') is currently logged in other device.";
              return false;           
            }            
            // check for password equality
            if ($password == $result['password']) {
                // user authentication details are correct
                //$result['password'] = "****"; 
                mysql_query("UPDATE users SET active=1 WHERE username='$username'");  
                return $result;
            } else {
              //$result["error"] = 1;
              //$result["errorMessage"] = "wrong password";
              return false;          
            }
        } else {
            //$result["error"] = 1;
            //$result["errorMessage"] = "Wrong username";
            return false;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($username) {
        $result = mysql_query("SELECT username from users WHERE username = '$username'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }
    
    /**
    * LogOut user
    */
    public function logOutUser($username, $uid) {
      $result = mysql_query("UPDATE users SET active=0 WHERE username='$username' AND unique_id='$uid'"); 
      if($result) {
      return true;
      } else {
      return false;
      }
    }
    
    /**
    * Update users longitude & latitude
    */
    public function updateUserLocation($username, $uid, $latitude, $longitude) {
      $result = mysql_query("UPDATE users SET latitude='$latitude',
                                              longitude='$longitude',
                                              active=1
                                          WHERE 
                                              username='$username' 
                                          AND 
                                              unique_id='$uid'");  
      if($result) {
        return true;
      } else {
        return false;
      }  
    }
    
    /**
    * Get users
    */
    public function getUsers($username) {
      $result = mysql_query("SELECT username, active, latitude, longitude FROM users WHERE username!='$username' AND active!=0");
      $no_of_rows = mysql_num_rows($result);
      if ($no_of_rows > 0) {
        $users = array();
        while($row = mysql_fetch_assoc($result)){
          array_push($users, $row);
        }
  
        return $users;
      } else {
        return false;
      }
    }
    
    public function deleteUser($username) {
      if ($username == "***ALL***") {
        mysql_query("DELETE FROM users");
      } else {
        mysql_query("DELETE FROM users WHERE username='$username'");      
      }
    }
}

?>
