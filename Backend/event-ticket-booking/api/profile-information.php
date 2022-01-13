<?php
    header('Content-Type:application/json');

    include('../includes/database_connection.php');
    include('auth.php');

    $token = mysqli_real_escape_string($conn, $_POST['token']);
    $user_id = authenticate($token); //on successful authintication returns user_id or on failed returns 0
    if($user_id != 0){
        $sql = "SELECT * FROM users WHERE u_id = ?;";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
            return 0;
        } else {
            mysqli_stmt_bind_param($stmt, "s", $user_id);
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);
            $row = mysqli_fetch_assoc($result);
            $firstname = $row['firstname'];
            $lastname = $row['lastname'];
            $email = $row['email'];
            $phone = $row['phone'];

            echo json_encode([
                "response_code"=>'200', 
                'firstname' => $firstname,
                'lastname' => $lastname,
                'email' => $email,
                'phone' => $phone
            ]);
        }        
    } else {
        echo json_encode([
            "response_code"=>'401'
        ]);
    }

    
    
?>