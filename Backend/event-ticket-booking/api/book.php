<?php
    header('Content-Type:application/json');

    include('../includes/database_connection.php');

    include('auth.php');

    $token = mysqli_real_escape_string($conn, $_POST['token']);
    $event_id = mysqli_real_escape_string($conn, $_POST['event_id']);
    $total_price = mysqli_real_escape_string($conn, $_POST['total_price']);
    $no_of_tickets = mysqli_real_escape_string($conn, $_POST['qty']);
    $payment_ref_id = mysqli_real_escape_string($conn, $_POST['payment_ref_id']);
    $date = date("d/m/Y"); 

    $user_id = authenticate($token); //on successful authintication returns user_id or on failed returns 0
    if($user_id != 0){
        $payment_reference_id = mt_rand(10000000, 1000000000);
        //insrting booking data to database
        $sql = "INSERT INTO bookings ( event_id, user_id, no_of_tickets, total_price, payment_reference_id, booking_date)
        VALUES (?, ?, ?, ?, ?, ?);";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
            echo json_encode([
                "response_code"=>500
            ]);
        } else {
            mysqli_stmt_bind_param($stmt, "ssssss", $event_id, $user_id, $no_of_tickets, $total_price, $payment_ref_id, $date);
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);
            if($conn->affected_rows != 0){
                $last_id = $conn->insert_id;
                echo json_encode([
                    "response_code"=>201, 
                    'booking_ref_id' => $last_id
                ]);
            }else {
                echo json_encode([
                    "response_code"=>500
                ]);
            }  
        }
    } else {
        echo json_encode([
            "response_code"=>401
        ]);
    }

       
?>