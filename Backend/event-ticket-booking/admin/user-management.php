<?php

	include ('../includes/header.php');
	echo "<title>User Management</title>";
?>

</head>
<!-- body -->

<body class="main-layout">
    <?php
		include('../includes/nav.php');
        if(!isset($_SESSION['admin_id'])){
            header ("Location: login?not-loggedin");
        }
	?>

    <div class="container mt-4">
        <div class="row">
            <div class="col-sm-2">
                <?php
                    include('includes/side-nav.php');
                ?>
            </div>
            <?php
                //Search 
                $query = "all";
                if(isset($_GET['submit'])){
                    $query = $_GET['query'];
                }
            ?>
            <div class="col-sm-10">
                <div class="row">
                    <form class="form-inline" action="" method="GET">
                        <div class="form-group">
                            <input
                                style="height: 38px; margin-top: 7px; width: 850px; padding: 6px; margin-right: 10px;"
                                type="text" name="query" class="form-control"
                                placeholder="Search user by Name, Email, Phone Number" required>
                        </div>
                        <button type="submit" name="submit" class="btn btn-warning mb-4">Search</button>
                    </form>
                    <?php
                    include('../includes/database_connection.php');
                    if($query == "all"){
                        $sql = "SELECT * FROM users";
                    } else {
                        $sql = "SELECT * FROM users WHERE firstname LIKE ? OR lastname LIKE ?  OR email LIKE ?  OR phone LIKE ?";
                    }
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        echo "Error";
                    } else {
                        if($query != "all"){
                            mysqli_stmt_bind_param($stmt, "ssss",$query, $query, $query, $query);
                        }
                        mysqli_stmt_execute($stmt);
                        $result = mysqli_stmt_get_result($stmt);
                        if (mysqli_num_rows($result) > 0) {
                            if (isset($_GET['pageno'])) {
                                $pageno = $_GET['pageno'];
                            } else {
                                $pageno = 1;
                            }
                            $no_of_records_per_page = 8;
                            $offset = ($pageno-1) * $no_of_records_per_page;

                            $total_pages_sql = "SELECT COUNT(*) FROM users";
                            $result = mysqli_query($conn,$total_pages_sql);
                            $total_rows = mysqli_fetch_array($result)[0];
                            $total_pages = ceil($total_rows / $no_of_records_per_page);

                            if($query == "all"){
                                $sql_users = "SELECT * FROM users ORDER BY u_id DESC LIMIT ?, ?";
                            } else {
                                $sql_users = "SELECT * FROM users WHERE firstname LIKE ? OR lastname LIKE ?  OR email LIKE ?  OR phone LIKE ? ORDER BY u_id DESC LIMIT ?, ?;";
                            }
                            $stmt_users = mysqli_stmt_init($conn);
                            if(!mysqli_stmt_prepare($stmt_users, $sql_users)){
                                echo "Error";
                            } else {
                                if($query == "all"){
                                    mysqli_stmt_bind_param($stmt_users, "ss", $offset, $no_of_records_per_page);
                                } else {
                                    mysqli_stmt_bind_param($stmt_users, "ssssss",$query, $query, $query, $query, $offset, $no_of_records_per_page);
                                }
                                mysqli_stmt_execute($stmt_users);
                                $res_data = mysqli_stmt_get_result($stmt_users);
                                echo "
                                <table class='table shadow-sm rounded'>
                                    <thead class='thead-dark'>
                                        <tr>
                                            <th scope='col'>ID</th>
                                            <th scope='col'>Name</th>
                                            <th scope='col'>Email</th>
                                            <th scope='col'>Phone</th>
                                            <th scope='col'>Status</th>
                                            <th scope='col'>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>";
                                while($row = mysqli_fetch_array($res_data)){
                                    echo"<tr>
                                            <th>$row[u_id]</th>
                                            <td>$row[firstname] $row[lastname]</td>
                                            <td>$row[email]</td>
                                            <td>$row[phone]</td>
                                            <td>$row[status]</td>";
                                                if($row['status'] == "active"){
                                                    echo 
                                                    "<td>
                                                        <form action='includes/user-action.php' method='post'>
                                                            <input type='hidden' name='action' value='blocked' required>
                                                            <input type='hidden' name='user_id' value='$row[u_id]' required>
                                                            <button style='color: red; background:none;'>Block</button>
                                                        </form>
                                                    </td>";
                                                } else {
                                                    echo
                                                    "<td>
                                                        <form action='includes/user-action.php' method='post'>
                                                            <input type='hidden' name='action' value='active' required>
                                                            <input type='hidden' name='user_id' value='$row[u_id]' required>
                                                            <button style='color: green; background:none;'>Activate</button>
                                                        </form>
                                                    </td>";
                                                }
                                        echo "
                                        </tr>";
                                }
                                echo "</tbody>
                                </table>";
                            }
                            //hide pagination on search by query
                            if($query == "all"){
                        ?>

                    <div class="pagi">
                        <ul class="pagination">
                            <li><a href="?pageno=1">First</a></li>
                            <li class="<?php if($pageno <= 1){ echo 'disabled'; } ?>">
                                <a
                                    href="<?php if($pageno <= 1){ echo '#'; } else { echo "?pageno=".($pageno - 1); } ?>">Prev</a>
                            </li>
                            <li class="<?php if($pageno >= $total_pages){ echo 'disabled'; } ?>">
                                <a
                                    href="<?php if($pageno >= $total_pages){ echo '#'; } else { echo "?pageno=".($pageno + 1); } ?>">Next</a>
                            </li>
                            <li><a href="?pageno=<?php echo $total_pages; ?>">Last</a></li>
                        </ul>
                    </div>

                    <?php 
                            }
                        } else {
                            echo "<img class='image-center' src='$http_host/images/empty.svg' alt='empty'>";
                        }
                    }                    
                    ?><br>
                </div>
            </div>
        </div>
    </div>
    <?php
		include('../includes/footer.php');
	?>
</body>

</html>