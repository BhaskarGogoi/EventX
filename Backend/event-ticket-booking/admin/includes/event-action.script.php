<?php
	session_start();
	include('../../includes/database_connection.php');

	$csrf_token = mysqli_real_escape_string($conn ,$_POST['csrf_token']);
	$action = mysqli_real_escape_string($conn, $_POST['action']);
	$event_id = mysqli_real_escape_string($conn, $_POST['event_id']);
    
    if (empty($csrf_token)) {
        echo "Error";
		exit();
	}
    if (empty($action)) {
        echo "Error";
		exit();
	}
    if (empty($action)) {
        echo "Error";
		exit();
	}
    
	//validate CSRF TOKEN
	if($_POST['csrf_token'] == $_SESSION['csrf_token']){
        if($action == "delete"){
            $sql = "DELETE from events WHERE event_id = '$event_id'";
            $result = $conn->query($sql);            
            if($conn->affected_rows != 0){
                header("location: ../all-events");
            }else {
                echo "Internal Error 500!";
            }
        } else if($action == "edit"){
            header("location: ../edit-event?eventid=$event_id");
        }
    }  else {
        echo "Error";
    } 
?>