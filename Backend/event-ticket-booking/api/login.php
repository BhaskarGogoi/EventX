<?php
    header('Content-Type: application/json');

	include('../includes/database_connection.php');

	$phone = mysqli_real_escape_string($conn, $_POST['phone']);
	$otp = mysqli_real_escape_string($conn, $_POST['otp']);
	$ref_no = mysqli_real_escape_string($conn, $_POST['ref_no']);

    if (empty($phone)) {
		echo json_encode([
			"response_code"=>400,
			"msg"=>"Phone is Empty!"
		]);
		exit();
	}

	if (empty($otp)) {
		echo json_encode([
			"response_code"=>400,
			"msg"=>"OTP is Empty!"
		]);
		exit();
	}

    if (empty($ref_no)) {
		echo json_encode([
			"response_code"=>400,
			"msg"=>"Ref No is Empty!"
		]);
		exit();
	}
    $sql = "SELECT * FROM otp WHERE ref_no = ?;";
	$stmt = mysqli_stmt_init($conn);
    if(!mysqli_stmt_prepare($stmt, $sql)){
        echo json_encode([
            "response_code"=>500, 
        ]);
    } else {
		mysqli_stmt_bind_param($stmt, "s", $ref_no);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);
		$row = mysqli_fetch_assoc($result);
		if($row['otp'] == $otp){
			//check if the user is already registered or not
			$sql = "SELECT * FROM users WHERE phone = ?;";
			$stmt = mysqli_stmt_init($conn);
			if(!mysqli_stmt_prepare($stmt, $sql)){
				echo json_encode([
					"response_code"=>500, 
				]);
			} else {
				mysqli_stmt_bind_param($stmt, "s", $phone);
				mysqli_stmt_execute($stmt);
				$user_result = mysqli_stmt_get_result($stmt);
				if($user_result->num_rows > 0){
					$user = mysqli_fetch_assoc($user_result);
					//check if user's status is active or not
					if($user['status'] == 'active'){
						///if user exists generate access token
						function generateRandomString($length = 50) {
							$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$*#';
							$charactersLength = strlen($characters);
							$randomString = '';
							for ($i = 0; $i < $length; $i++) {
								$randomString .= $characters[rand(0, $charactersLength - 1)];
							}
							return $randomString;
						}
						$token = generateRandomString();
						//token cxpiry date
						$currDate = date('d-m-Y');
						$expiry_date = date('d-m-Y', strtotime($currDate. ' + 180 days'));

						$sql = "INSERT INTO api_access_token (user_id, token, expiry_date)
						VALUES (? , ?, ?)";
						$stmt = mysqli_stmt_init($conn);
						if(!mysqli_stmt_prepare($stmt, $sql)){
							echo json_encode([
								"response_code"=>500
							]);
						} else {
							mysqli_stmt_bind_param($stmt, "sss", $user['u_id'], $token, $expiry_date);
							if(mysqli_stmt_execute($stmt)){
								echo json_encode([
									"response_code"=>200,
									"authentication"=>"success",
									"isRegistered" => 1,
									"access_token" => $token,
									"firstname" => $user['firstname'],
									"token_expiry" => $expiry_date
								]);
							} else {
								echo json_encode([
									"response_code"=>200,
									"authentication"=>"failed"
								]);
							} 
						}
					} else {
						echo json_encode([
							"response_code"=>200,
							"authentication"=>"blocked"
						]);
					}					          
				} else {
					echo json_encode([
						"response_code"=>200,
						"authentication"=>"success",
						"isRegistered" => 0
					]);
				}
			}			
		} else {
			echo json_encode([
				"response_code"=>200,
				"authentication"=>"wrong otp"
			]);
		}
	}
?>