<?php
    $username = "root";
    $password = "raspy";
    $dbname = "taskbo-rd";
    $server = "192.168.0.200";
    mysql_connect($server, $username, $password);
    mysql_select_db($dbname);
    mysql_query('SET NAMES UTF8');
?>