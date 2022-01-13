<?php
    header('Content-Type:application/json');
    include('../includes/database_connection.php');
    include('auth.php');
    
    $token = mysqli_real_escape_string($conn, $_POST['token']);
    
    $user_id = authenticate($token); //on successful authintication returns user_id or on failed returns 0
    if($user_id != 0){
        $sql_bookings = "SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_id DESC;";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql_bookings)){
            echo json_encode([
                "response_code"=>500, 
            ]);
        } else {
            mysqli_stmt_bind_param($stmt, "s", $user_id);
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);
            if($result->num_rows > 0){
                $status = 'success';
                while($row = mysqli_fetch_assoc($result)) {
                    $sql_events = "SELECT * FROM events WHERE event_id = ?;";
                    $stmt2 = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt2, $sql_events)){
                        echo json_encode([
                            "response_code"=>500, 
                        ]);
                    } else {
                        mysqli_stmt_bind_param($stmt2, "s", $row['event_id']);
                        mysqli_stmt_execute($stmt2);
                        $result2 = mysqli_stmt_get_result($stmt2);
                        $row2 = mysqli_fetch_assoc($result2);
                        $event_name = $row2['event_name'];
                        $roww = array(
                            "booking_id" => "$row[booking_id]",
                            "event_id" => "$row[event_id]",
                            "no_of_tickets" => "$row[no_of_tickets]",
                            "total_price" => "$row[total_price]",
                            "booking_date" => "$row[booking_date]",
                            "event_name" => "$event_name",
                        );
                        $data[] = $roww;
                    }
                }
                echo json_encode([
                    "response_code"=>200, 
                    "data"=>$data, 
                    "image_url" => "images/event-images/"
                ]);
            }else{
                echo json_encode([
                    "response_code"=>400
                ]);
            } 
        }
    } else {
        echo json_encode([
            "response_code"=>401
        ]);
    }
      
?>