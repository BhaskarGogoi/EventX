<?php
// include "../config.php";
require __DIR__. '/../config.php';

$conn = mysqli_connect($db_host, $db_username, $db_password, $db_name);

if (!$conn) {
die("Connection Failed: ".mysqli_connect_error());
}