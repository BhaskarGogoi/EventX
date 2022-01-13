<?php
	include ('../includes/header.php');
	echo "<title>Admin Login</title>";
    //csrf token 
    $token = md5(uniqid(rand(), true));
    $_SESSION['csrf_token'] = $token;
?>
</head>

<body class="main-layout">
    <?php
		include('../includes/nav.php');
	?>
    <div class="container">
        <div class="row mt-3" style='width: 200px; margin: auto;
            width: 50%;
            padding: 10px;'>
            <div class="col-sm-12 shadow p-5 rounded">
                <form action="includes/login.script.php" method="POST">
                    <h2>Admin Login</h2>
                    <div class="form-group">
                        <input type="hidden" name="csrf_token" value="<?php echo $token ?>" required>
                        <label for="exampleInputEmail1">Email</label>
                        <input type="email" class="form-control" name="email" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" class="form-control" name="password" placeholder="Password">
                    </div>
                    <button type="submit" name="submit" class="btn btn-warning">Login</button>
                </form>
            </div>
        </div>
    </div>
    <?php
		include('../includes/footer.php');
	?>
</body>

</html>