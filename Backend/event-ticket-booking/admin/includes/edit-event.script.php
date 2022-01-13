<?php
	session_start();

	if (isset($_POST['submit'])) {

		include('../../includes/database_connection.php');

		$csrf_token = mysqli_real_escape_string($conn ,$_POST['csrf_token']);
		$event_id = mysqli_real_escape_string($conn ,$_POST['event_id']);
		$event_name = mysqli_real_escape_string($conn, $_POST['event_name']);
		$event_type = mysqli_real_escape_string($conn, $_POST['event_type']);
		$date = mysqli_real_escape_string($conn, $_POST['date']);
		$time = mysqli_real_escape_string($conn, $_POST['time']);
		$duration = mysqli_real_escape_string($conn, $_POST['duration']);
		$max_tickets = mysqli_real_escape_string($conn, $_POST['max_tickets']);
		$hosted_by = mysqli_real_escape_string($conn, $_POST['hosted_by']);
		$about = mysqli_real_escape_string($conn, $_POST['about']);
		$price = mysqli_real_escape_string($conn, $_POST['price']);
		$area = mysqli_real_escape_string($conn, $_POST['area']);
		$locality = mysqli_real_escape_string($conn, $_POST['locality']);
		$city = mysqli_real_escape_string($conn, $_POST['city']);
		$state = mysqli_real_escape_string($conn, $_POST['state']);
		$pin = mysqli_real_escape_string($conn, $_POST['pin']);

		//-----Check if form datas are not filled-----
        if (empty($csrf_token)) {
			header ("Location:../edit-event.php?error");
			exit();
        }
		if (empty($event_id)) {
			header ("Location:../edit-event.php?error");
			exit();
        }
         if (empty($event_name)) {
			hheader ("Location:../edit-event.php?error");
			exit();
        }
		if (empty($event_type)) {
			header ("Location:../edit-event.php?error");
			exit();
        }
        if (empty($hosted_by)) {
			hheader ("Location:../edit-event.php?error");
			exit();
        }
        if (empty($date)) {
            header ("Location:../edit-event.php?error");
			exit();
		}
        if (empty($time)) {
            header ("Location:../edit-event.php?error");
			exit();
		} if (empty($duration)) {
            header ("Location:../edit-event.php?error");
			exit();
		} 
		if (empty($max_tickets)) {
            header ("Location:../edit-event.php?error");
			exit();
		} 
        if (empty($about)) {
            header ("Location:../edit-event.php?error");
			exit();
		} 
        if (empty($price)) {
            header ("Location:../edit-event.php?error");
			exit();
		} 
		//-----End Check if form datas are not filled-----

		else {
			//validate CSRF TOKEN
			if($_POST['csrf_token'] == $_SESSION['csrf_token']){
                $sql = "UPDATE events SET 
                    event_name = ?,
                    event_type = ?,
                    hosted_by = ?,
                    date = ?,
                    time = ?,
                    duration = ?,
                    price = ?,
                    max_tickets = ?,
                    about = ?,
                    address_area = ?,
                    address_locality = ?,
                    address_city = ?,
                    address_state =?,
                    address_pin = ?
                    WHERE event_id = '$event_id'";
                $stmt = mysqli_stmt_init($conn);
                if(!mysqli_stmt_prepare($stmt, $sql)){
                    return 0;
                } else {
                    mysqli_stmt_bind_param($stmt, "ssssssssssssss", $event_name, $event_type, $hosted_by, $date, $time, $duration, $price, $max_tickets, $about, $area, $locality, $city, $state, $pin);
                    mysqli_stmt_execute($stmt);
                    $result = mysqli_stmt_get_result($stmt);
                    if($stmt->affected_rows != 0){
                        header ("Location:../edit-event.php?success&eventid=$event_id");
                        exit();
                    } else {
                        header ("Location:../edit-event.php?error=500&eventid=$event_id");
                        exit();
                    }
                }
			} else {
				header ("Location:../edit-event.php?error");
			}                          
		}
	} else {
        header ("Location:../edit-event.php?error");
		exit();
	}
?>