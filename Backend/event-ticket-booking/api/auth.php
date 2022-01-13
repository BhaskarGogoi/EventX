<?php
    function authenticate($token){
        header('Content-Type: application/json');
        include('../includes/database_connection.php');
        //create template
        $sql = "SELECT * FROM users JOIN api_access_token ON users.u_id=api_access_token.user_id WHERE api_access_token.token = ?;";
        //create a prepared statement
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
            return 0;
        } else {
            //Bind parameters to the placeholder
            mysqli_stmt_bind_param($stmt, "s", $token);
            //Run parameters inside database
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);
            $row = mysqli_fetch_assoc($result);
            //check if user exists
            if($row){
                //check if the user in active state
                if($row['status'] == 'active'){
                    // if user exists and status is active, returns user_id
                    return $row['user_id'];
                } else {
                    echo json_encode([
                        "response_code"=>'403', 
                        'status' => 'blocked'
                    ]);
                    die();
                }                
            } else {
                // if user doesnot exists returns 0
                return 0;
            }
        }        
    }
?>