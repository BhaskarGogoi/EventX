<?php
	session_start();

	if (isset($_POST['submit'])) {

		include('../../includes/database_connection.php');

		$firstname = mysqli_real_escape_string($conn, $_POST['firstname']);
		$lastname = mysqli_real_escape_string($conn, $_POST['lastname']);
		$email = mysqli_real_escape_string($conn, $_POST['email']);
		$password = mysqli_real_escape_string($conn, $_POST['password']);

		//-----Check if form datas are not filled-----
         if (empty($email)) {
			header ("Location:../register?error=empty");

			exit();
        }
        if (empty($firstname)) {
			header ("Location:../register?error=empty");

			exit();
        }
        if (empty($lastname)) {
			header ("Location:../register?error=empty");

			exit();
		} if (empty($password)) {
			header ("Location:../register?error=empty");

			exit();
		} 

		//-----End Check if form datas are not filled-----

		else {
			//check if email is valid
			if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
				header ("Location:../register?email=invalid");
				exit();
			}
			else {
				//-----Check if email is already exists-----
				$sql = "SELECT * FROM admin WHERE email = ?";
				$stmt = mysqli_stmt_init($conn);
				if(!mysqli_stmt_prepare($stmt, $sql)){
					echo "Error";
				} else {
					mysqli_stmt_bind_param($stmt, "s", $email);
					mysqli_stmt_execute($stmt);
					$result = mysqli_stmt_get_result($stmt);
					if (mysqli_num_rows($result) > 0) {
						header ("Location:../register?error=email");
						exit();
					}
					//-----End Check if username or email is already exists-----
					else {
						$encrypted_password = password_hash($password, PASSWORD_DEFAULT); //hashing password
						$sql = "INSERT INTO admin ( firstname, lastname, email, password)
						VALUES ('$firstname', '$lastname',  '$email', '$encrypted_password')";
						$stmt = mysqli_stmt_init($conn);
						if(!mysqli_stmt_prepare($stmt, $sql)){
							header ("Location:../register?error");
							exit();
						} else {
							mysqli_stmt_bind_param($stmt, "ssss", $firstname, $lastname, $email, $encrypted_password);
							if(mysqli_stmt_execute($stmt)){
								header ("Location:../login?register=success");
							} else {
								header ("Location:../register?error");
								exit();
							}							
						}
					}
				}					
			}
		}
	} else {
		header ("Location:../register.php?submit-error");
		exit();
	}
?>