<?php
    $username = "root";
    $password = "";
    $dbname = "taskbo-rd";
    $server = "localhost";
    mysql_connect($server, $username, $password);
    mysql_select_db($dbname);
    mysql_query('SET NAMES UTF8');
?>