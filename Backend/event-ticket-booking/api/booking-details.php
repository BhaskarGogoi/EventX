<?php
    header('Content-Type:application/json');
    include('../includes/database_connection.php');
    include('auth.php');
    
    $token = mysqli_real_escape_string($conn, $_POST['token']);    
    $booking_id = mysqli_real_escape_string($conn, $_POST['booking_id']);

    $user_id = authenticate($token); //on successful authintication returns user_id or on failed returns 0
    if($user_id != 0){
        $sql_booking_details = "SELECT * FROM bookings WHERE booking_id = ?;";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql_booking_details)){
            echo json_encode([
                "response_code"=>500, 
            ]);
        } else {
            mysqli_stmt_bind_param($stmt, "s", $booking_id);
            mysqli_stmt_execute($stmt);
            $booking_result = mysqli_stmt_get_result($stmt);
            $bookings = mysqli_fetch_assoc($booking_result);            
            if($bookings){
                $sql_event_details = "SELECT * FROM events WHERE event_id = ?;";
                $stmt2 = mysqli_stmt_init($conn);
                if(!mysqli_stmt_prepare($stmt2, $sql_event_details)){
                    echo json_encode([
                        "response_code"=>500, 
                    ]);
                } else {
                    mysqli_stmt_bind_param($stmt2, "s", $bookings['event_id']);
                    mysqli_stmt_execute($stmt2);
                    $event_result = mysqli_stmt_get_result($stmt2);
                    $event = mysqli_fetch_assoc($event_result); 
                    $booking_details[] = $bookings;
                    $event_details[] = $event;
                    echo json_encode([
                        "response_code"=>200, 
                        "booking_details"=>$booking_details, 
                        "event_details" => $event_details, 
                        "image_url" => "images/event-images/"
                    ]);
                }                
            } else {
                echo json_encode([
                    "response_code"=>500, 
                ]);
            }
          
        }        
    } else {
        echo json_encode([
            "response_code"=>401  //unauthorized
        ]);
    }

?>