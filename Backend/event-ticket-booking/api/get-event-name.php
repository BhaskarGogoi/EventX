<?php
    header('Content-Type:application/json');
    include('../includes/database_connection.php');
    
    $event_id = mysqli_real_escape_string($conn, $_POST['event_id']);
    
    
    $sql = "SELECT * FROM events WHERE event_id = '$event_id'";
    $result = $conn->query($sql);
    $row = mysqli_fetch_assoc($result);
    $event_name = $row['event_name'];
    echo json_encode(["status"=>"success", "event_name" =>$event_name ]);

?>