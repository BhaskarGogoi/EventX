<?php

// $conn = mysqli_connect("localhost", "db_user", "Abcd1997@@1",  "event_ticket_booking");
$conn = mysqli_connect("localhost", "root", "",  "event_ticket_booking");

if (!$conn) {
	die("Connection Failed: ".mysqli_connect_error());
}