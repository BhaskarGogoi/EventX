<?php
header('Content-Type: application/json');
include('../includes/database_connection.php');

$event_id = mysqli_real_escape_string($conn, $_POST['event_id']);

//get event details
$sql_event = "SELECT * FROM events WHERE event_id = ?;";
$stmt = mysqli_stmt_init($conn);
if(!mysqli_stmt_prepare($stmt, $sql_event)){
    echo json_encode([
        "response_code"=>500, 
    ]);
}else {
    mysqli_stmt_bind_param($stmt, "s", $event_id);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);
    $row = mysqli_fetch_assoc($result);
    $data[] = $row;

    //check number of total tickets left
    $sql_bookings = "SELECT * FROM bookings WHERE event_id = ?;";
    $stmt2 = mysqli_stmt_init($conn);
    if(!mysqli_stmt_prepare($stmt2, $sql_bookings)){
        echo json_encode([
            "response_code"=>500, 
        ]);
    } else {
        mysqli_stmt_bind_param($stmt2, "s", $event_id);
        mysqli_stmt_execute($stmt2);
        $result2 = mysqli_stmt_get_result($stmt2);
        $tickets_left = 0;
        while($row2 = mysqli_fetch_assoc($result2)) {
            $tickets_left = $tickets_left + $row2['no_of_tickets'];
        }
        $total_tickets_left = $row['max_tickets'] - $tickets_left;
    
        echo json_encode([
            "response_code"=>200,
            "data"=>$data, 
            "ticketsLeft"=>$total_tickets_left
        ]);
    }
  
}