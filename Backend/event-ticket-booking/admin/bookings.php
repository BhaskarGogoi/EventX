<?php

	include ('../includes/header.php');
	echo "<title>Admin Dashboard</title>";
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
            <div class="col-sm-10">
                <div class="row">
                    <?php
                    include('../includes/database_connection.php');
                    $sql = "SELECT * FROM bookings";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        echo "Error";
                    } else {
                        $result = $conn->query($sql);
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

                            $total_pages_sql = "SELECT COUNT(*) FROM bookings";
                            $result = mysqli_query($conn,$total_pages_sql);
                            $total_rows = mysqli_fetch_array($result)[0];
                            $total_pages = ceil($total_rows / $no_of_records_per_page);

                            $sql_events = "SELECT * FROM bookings JOIN events on bookings.event_id = events.event_id JOIN users on bookings.user_id = users.u_id ORDER BY booking_id DESC LIMIT ?, ?";
                            $stmt_events = mysqli_stmt_init($conn);
                            if(!mysqli_stmt_prepare($stmt_events, $sql_events)){
                                echo "Error";
                            } else {
                                mysqli_stmt_bind_param($stmt_events, "ss", $offset, $no_of_records_per_page);
                                mysqli_stmt_execute($stmt_events);
                                $res_data = mysqli_stmt_get_result($stmt_events);
                                echo "
                                <h2>Bookings</h2>
                                <table class='table shadow-sm rounded'>
                                    <thead class='thead-dark'>
                                        <tr>
                                            <th scope='col'>Booking ID</th>
                                            <th scope='col'>Event Name</th>
                                            <th scope='col'>Event Type</th>
                                            <th scope='col'>Booked By</th>
                                            <th scope='col'>Tickets Sold</th>
                                        </tr>
                                    </thead>
                                    <tbody>";
                                while($row = mysqli_fetch_array($res_data)){
                                    echo"<tr>
                                        <th>$row[booking_id]</th>
                                        <th><a href='booking-details?bookingid=$row[booking_id]'>$row[event_name]</a></th>
                                        <td>$row[event_type]</td>
                                        <td>$row[firstname] $row[lastname]</td>";
                                        $sql2 = "SELECT * FROM bookings WHERE event_id = $row[event_id]";
                                        $result2 = $conn->query($sql2);
                                        $tickets_sold = 0;
                                        while($row2 = mysqli_fetch_assoc($result2)){
                                            $tickets_sold = $tickets_sold + $row2['no_of_tickets'];
                                        }
                                        echo "<td>$tickets_sold</td>
                                    </tr>";
                                }
                                echo "</tbody>
                                </table>";
                            }
                            
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
                        } else {
                            echo "<img class='image-center' src='$http_host/images/empty.svg' alt='empty'>
                            <h3 class='text-center mt-3 font-weight-bold'>No Events Found!</h3>";
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