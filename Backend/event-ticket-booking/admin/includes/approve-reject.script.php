<?php
	session_start();
	include('../../includes/database_connection.php');


	$status = mysqli_real_escape_string($conn, $_POST['status']);
	$event_id = mysqli_real_escape_string($conn, $_POST['event_id']);
    
    if (empty($status)) {
        header("location: ../event-details?eventid=$event_id");
		exit();
	}
    if (empty($event_id)) {
        header("location: ../event-details?eventid=$event_id");
		exit();
	}

    $sql = "UPDATE events SET status = '$status' WHERE event_id = '$event_id'";
    $result = $conn->query($sql);
    
    if($conn->affected_rows != 0){
        header("location: ../event-details?eventid=$event_id");
    }else {
        echo "Internal Error 500!";
    }
?>