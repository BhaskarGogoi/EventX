<?php
header('Content-Type:application/json');
header("X-XSS-Protection: 1;");
include('../includes/database_connection.php');

$event_type = mysqli_real_escape_string($conn, $_POST['event_type']);

if($event_type == "latest"){
    $sql = "SELECT * FROM events ORDER BY event_id DESC LIMIT 4";
    $stmt = mysqli_stmt_init($conn);
    if(!mysqli_stmt_prepare($stmt, $sql)){
        echo json_encode([
            "response_code"=>500, 
        ]);
    } else {
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);
        while($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
            $image = "images/event-images/";        
        }
    }    
} else {
    $sql = "SELECT * FROM events WHERE event_type = ? ORDER BY event_id DESC LIMIT 4;";
    $stmt = mysqli_stmt_init($conn);
    if(!mysqli_stmt_prepare($stmt, $sql)){
        echo json_encode([
            "response_code"=>500, 
        ]);
    } else {
        mysqli_stmt_bind_param($stmt, "s", $event_type);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);
        while($row = mysqli_fetch_assoc($result)) {
            $data[] = $row;
            $image = "images/event-images/";
        }
    }
}

echo json_encode([
    "response_code"=>200,
    "data"=>$data, 
    "image"=>$image
]);