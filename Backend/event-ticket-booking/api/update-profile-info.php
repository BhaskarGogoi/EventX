<?php
        header('Content-Type: application/json');
		include('../includes/database_connection.php');
        include('auth.php');

		$firstname = mysqli_real_escape_string($conn, $_POST['firstname']);
		$lastname = mysqli_real_escape_string($conn, $_POST['lastname']);
		$email = mysqli_real_escape_string($conn, $_POST['email']); 
        $token = mysqli_real_escape_string($conn, $_POST['token']);

        if (empty($email)) {
			echo json_encode([
				"response_code"=>400,
                "msg" => "Email is empty!"
			]);
			exit();
        }
        if (empty($firstname)) {
			echo json_encode([
				"response_code"=>400,
                "msg" => "Firstname is empty!"
			]);
			exit();
        }
        if (empty($lastname)) {
			echo json_encode([
				"response_code"=>400,
                "msg" => "Lastname is empty!"
			]);
			exit();
		}     

		else {
            $user_id = authenticate($token); //on successful authintication returns user_id or on failed returns 0
            if($user_id != 0){
                //check if email is valid
				if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
                    echo json_encode([
                        "response_code"=>400,
                        "msg" => "Invalid Email"
                    ]);
					exit();
				}
				else {
                    $sql = "UPDATE users SET firstname = ?, lastname = ?, email = ? WHERE u_id = ?";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        return 0;
                    } else {
                        mysqli_stmt_bind_param($stmt, "ssss", $firstname, $lastname, $email, $user_id);
                        mysqli_stmt_execute($stmt);
                        $result = mysqli_stmt_get_result($stmt);
                        if($stmt->affected_rows != 0){
                            echo json_encode([
                                "response_code"=>204
                            ]);
                        }else {
                            echo json_encode([
                                "response_code"=>500
                            ]);
                        }
                    }                    
				}   
            } else {
                echo json_encode([
                    "response_code"=>401
                ]);
            }
		}
?>