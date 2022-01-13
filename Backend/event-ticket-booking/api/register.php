<?php
    header('Content-Type: application/json');


		include('../includes/database_connection.php');

		$firstname = mysqli_real_escape_string($conn, $_POST['firstname']);
		$lastname = mysqli_real_escape_string($conn, $_POST['lastname']);
		$email = mysqli_real_escape_string($conn, $_POST['email']);
		$phone = mysqli_real_escape_string($conn, $_POST['phone']);

		//-----Check if form datas are not filled-----
         if (empty($email)) {
			echo json_encode([
				"response_code"=>400
			]);
			exit();
        }
		if (empty($phone)) {
			echo json_encode([
				"response_code"=>400
			]);
			exit();
        }
        if (empty($firstname)) {
			echo json_encode([
				"response_code"=>400
			]);
			exit();
        }
        if (empty($lastname)) {
			echo json_encode([
				"response_code"=>400
			]);
			exit();
		} 

		//-----End Check if form datas are not filled-----

		else {
			//check if email is valid
			if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
				echo json_encode([
					"response"=>403,
					"status"=>102,
					"message"=>"Invalid Email"
				]);
				exit();
			}
			else {
				//-----Check if email is already exists----- =
				$sql = "SELECT * FROM users WHERE email = ?;";
				$stmt = mysqli_stmt_init($conn);
				if(!mysqli_stmt_prepare($stmt, $sql)){
					return 0;
				} else {
					mysqli_stmt_bind_param($stmt, "s", $email);
					mysqli_stmt_execute($stmt);
					$result = mysqli_stmt_get_result($stmt);
					if ($result->num_rows > 0) {
						echo json_encode([
							"response_code"=>403,
							"message"=>"Email Already Exists"
						]);
						exit();
					}
					//-----End Check if username or email is already exists-----
					else {
						$sql = "INSERT INTO users ( firstname, lastname, email, phone)
						VALUES (?, ?,  ?, ?)";
						$stmt = mysqli_stmt_init($conn);
						if(!mysqli_stmt_prepare($stmt, $sql)){
							echo json_encode([
								"response_code"=>500
							]);
						} else {
							mysqli_stmt_bind_param($stmt, "ssss", $firstname, $lastname, $email, $phone);
							if(mysqli_stmt_execute($stmt)){
								//if user successfully created, then create access_token
								$user_id = $stmt->insert_id;
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
								VALUES (?, ?, ?)";
								$stmt = mysqli_stmt_init($conn);
								if(!mysqli_stmt_prepare($stmt, $sql)){
									echo json_encode([
										"response_code"=>500
									]);
								} else {
									mysqli_stmt_bind_param($stmt, "sss", $user_id, $token, $expiry_date);
									if(mysqli_stmt_execute($stmt)){
										echo json_encode([
											"response_code"=>201,
											"access_token" => $token,
											"firstname" => $firstname
										]);
									} else {
										echo json_encode([
											"response_code"=>500
										]);
									}
								}  
							}else {
								echo json_encode([
									"response_code"=>500
								]);
							}
						}							
					}
				}
			}
		}
?>