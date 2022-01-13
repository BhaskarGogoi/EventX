<?php

	include ('../includes/header.php');
	echo "<title>Account</title>";
?>

</head>
<!-- body -->

<body class="main-layout">
    <?php
		include('../includes/nav.php');
        if(!isset($_SESSION['admin_id'])){
            header ("login?not-loggedin");
        }
	?>

    <div class="container mt-4">
        <div class="row">
            <div class="col-sm-2">
                <?php
                    include('includes/side-nav.php');
                    include('../includes/database_connection.php');
                    $sql = "SELECT * FROM admin WHERE a_id = ?";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        echo "Error";
                    } else {
                        mysqli_stmt_bind_param($stmt, "s", $_SESSION['admin_id']);
                        mysqli_stmt_execute($stmt);
                        $result = mysqli_stmt_get_result($stmt);
                        $row = mysqli_fetch_assoc($result);
                    }                    
                ?>
            </div>
            <div class="col-sm-10">
                <div class="shadow p-5 rounded">
                    <h4>Personal Information</h4>
                    <form>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>First Name</label>
                                <input type="text" class="form-control" name="event_name"
                                    value='<?php echo $row['firstname'] ?>' disabled>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Lastname</label>
                                <input type="text" name="hosted_by" class="form-control"
                                    value='<?php echo $row['lastname'] ?>' disabled>

                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Email</label>
                                <input type="text" class="form-control" value='<?php echo $row['email'] ?>' disabled>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </div>
    <?php
		include('../includes/footer.php');
	?>
</body>

</html>