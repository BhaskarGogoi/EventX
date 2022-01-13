<?php

	include ('../includes/header.php');
	echo "<title>User Register</title>";
?>

</head>
<!-- body -->

<body class="main-layout">

    <?php
		include('../includes/nav.php');
	?>

    <div class="container">
        <div class="row mt-3">
            <div class="col-sm-12 shadow p-5 rounded">
                <form action="includes/register.script.php" method="POST">
                    <h2>Admin Registration</h2>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Firstname</label>
                        <input type="text" class="form-control" name="firstname" placeholder="Firstname">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Lastname</label>
                        <input type="text" class="form-control" name="lastname" placeholder="Lastname">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Email</label>
                        <input type="email" class="form-control" name="email" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" class="form-control" name="password" placeholder="Password">
                    </div>

                    <button type="submit" name="submit" class="btn btn-warning">Register</button>
                    &nbsp; &nbsp; Already have an account? Login <a
                        href="<?php echo $http_host; ?>/admin/login.php">here</a>
                </form>
            </div>
        </div>
    </div>
</body>

</html>