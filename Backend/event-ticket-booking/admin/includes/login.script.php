<?php
	session_start();
	include('../../includes/database_connection.php');

	$email = mysqli_real_escape_string($conn, $_POST['email']);
	$password = mysqli_real_escape_string($conn ,$_POST['password']);
	$csrf_token = mysqli_real_escape_string($conn ,$_POST['csrf_token']);

	//-----Check if form datas are not filled-----
	if (empty($csrf_token)) {
		header ("Location: ../login?error=empty");
		exit();
	}
	if (empty($email)) {
		header ("Location: ../login?error=empty");
		exit();
	}
	if (empty($password)) {
		header ("Location: ../login?error=empty");
		exit();
	} 
	//----- End Check if form datas are not filled-----

	//validate CSRF TOKEN
	if($_POST['csrf_token'] == $_SESSION['csrf_token']){
		//-----Check For Hash Password and Dehash-----
		$sql = "SELECT * FROM admin WHERE email = ?";
		$stmt = mysqli_stmt_init($conn);
		if(!mysqli_stmt_prepare($stmt, $sql)){
			echo "Error";
		} else {
			mysqli_stmt_bind_param($stmt, "s", $email);
			mysqli_stmt_execute($stmt);
			$result = mysqli_stmt_get_result($stmt);
			$row = mysqli_fetch_assoc($result);
			$hash_password = $row['password'];
			$dehash = password_verify($password, $hash_password);
			if ($dehash == 0) {
				header ("Location: ../login?error=not-found");
				exit();
			}
			//-----End Check For Hash Password and Dehash-----  

			else {
				$sql = "SELECT * FROM admin WHERE email = ?  AND password = ? ";
				$stmt = mysqli_stmt_init($conn);
				if(!mysqli_stmt_prepare($stmt, $sql)){
					echo "Error";
				} else {
					mysqli_stmt_bind_param($stmt, "ss", $email, $hash_password);
					mysqli_stmt_execute($stmt);
					$result = mysqli_stmt_get_result($stmt);
					$row = mysqli_fetch_assoc($result);

					if (!$row) {
						echo "Your username or password is incorrect";
					} else {
						$_SESSION['admin_id'] = $row['a_id'];
						$_SESSION['firstname'] = $row['firstname'];
					}
					header ("Location: ../dashboard");
				}
			}
		}
	} else {
		header ("Location: ../login?error=empty");
		exit();
	}
?>