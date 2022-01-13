<?php
	session_start();
	include('../../includes/database_connection.php');

    $action = mysqli_real_escape_string($conn, $_POST['action']);
	$user_id = mysqli_real_escape_string($conn, $_POST['user_id']);
    
    if (empty($action)) {
        echo "Error";
		exit();
	}
    if (empty($action)) {
        echo "Error";
		exit();
	}
    
    if($action == "blocked"){
        $sql = "UPDATE users SET status = 'blocked' WHERE u_id = ?";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
            header('Location: ' . $_SERVER['HTTP_REFERER']);
            exit;
        } else {
            mysqli_stmt_bind_param($stmt, "s", $user_id);
            if(mysqli_stmt_execute($stmt)){
                header('Location: ' . $_SERVER['HTTP_REFERER']);
                exit;
            }else {
                echo "Internal Error 500!";
            }
        }
        
    } else if($action == "active"){
        $sql = "UPDATE users SET status = 'active' WHERE u_id = ?";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
            header('Location: ' . $_SERVER['HTTP_REFERER']);
            exit;
        } else {
            mysqli_stmt_bind_param($stmt, "s", $user_id);
            if(mysqli_stmt_execute($stmt)){
                header('Location: ' . $_SERVER['HTTP_REFERER']);
                exit;
            }else {
                echo "Internal Error 500!";
            }
        }
        
    }
  
?>