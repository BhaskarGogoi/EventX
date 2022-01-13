<?php
    header('Content-Type:application/json');
    include('database_connection.php');
    $event_category = mysqli_real_escape_string($conn, $_POST['event_category']);

    $sql = "SELECT * FROM events WHERE event_type = ?;";
    $stmt = mysqli_stmt_init($conn);
    if(!mysqli_stmt_prepare($stmt, $sql)){
        echo json_encode([
            "response_code"=>500, 
        ]);
    } else {
        mysqli_stmt_bind_param($stmt, "s", $event_category);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);
        if($result->num_rows > 0 ){
            while($row = mysqli_fetch_assoc($result)) {           
                $data[] = $row;
            }
            echo json_encode([
                "response_code"=>200, 
                "data"=>$data, 
                "image_url" => "images/event-images/"
            ]);
        } else {
            echo json_encode([
                "response_code"=> 404
            ]);
        }
    }
?>