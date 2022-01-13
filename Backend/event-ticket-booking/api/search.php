<?php
    header('Content-Type:application/json');
    include('../includes/database_connection.php');
    $search_key = mysqli_real_escape_string($conn, $_POST['search_key']);
    $s_key = "%$search_key%";

    $sql = "SELECT * FROM events WHERE event_name LIKE ? OR event_type LIKE ?;";
    $stmt = mysqli_stmt_init($conn);
    if(!mysqli_stmt_prepare($stmt, $sql)){
        return 0;
    } else {
        mysqli_stmt_bind_param($stmt, "ss", $s_key, $s_key);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);
        if($result->num_rows > 0 ){
            while($row = mysqli_fetch_assoc($result)) {
                $sql2 = "SELECT * FROM events WHERE event_id = ?;";
                $stmt2 = mysqli_stmt_init($conn);
                if(!mysqli_stmt_prepare($stmt2, $sql2)){
                    return 0;
                } else {
                    mysqli_stmt_bind_param($stmt2, "s", $row['event_id']);
                    mysqli_stmt_execute($stmt2);
                    $result2 = mysqli_stmt_get_result($stmt2);
                    $row2 = mysqli_fetch_assoc($result2);          
                    $data[] = $row2;
                }               
            }
            echo json_encode([
                "status"=>"success", 
                "data"=>$data, 
                "image_url" => "images/event-images/"
            ]);
        } else {
            echo json_encode(["status"=>"not found"]);
        }
    }
?>