<?php
    ob_start();
	include ('../includes/header.php');
	echo "<title>Event Details</title>";
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
                    include('../includes/database_connection.php');
                    $booking_id = $_GET['bookingid'];
                    $sql = "SELECT * FROM bookings JOIN events on bookings.event_id = events.event_id JOIN users on bookings.user_id = users.u_id WHERE booking_id = ? ORDER BY booking_id DESC";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        echo "Error";
                    } else {
                        mysqli_stmt_bind_param($stmt, "s", $_GET['bookingid']);
                        mysqli_stmt_execute($stmt);
                        $result = mysqli_stmt_get_result($stmt);
                        if($result->num_rows > 0){
                            $row = mysqli_fetch_assoc($result);
                        } else {
                            echo "Error";
                        }
                    }                    
                ?>
            </div>
            <div class="col-sm-10">
                <div class="shadow p-5 rounded">
                    <h4>Booking Details</h4>
                    <div class="row mt-4">
                        <div class="col-sm-4 shadow-sm mr-3 p-4">
                            <h5><b>Booked By</b></h5>
                            <h6>Name: <?php echo $row['firstname'] .' '. $row['lastname']; ?></h6>
                            <h6>Phone: <?php echo $row['phone']; ?></h6>
                            <h6>Email: <?php echo $row['email']; ?></h6>
                        </div>
                        <div class="col-sm-4 shadow-sm mr-3 p-4">
                            <h5><b>Event</b></h5>
                            <h5><?php echo $row['event_name']; ?></h5>
                            <h6><?php echo "$row[event_type] -  $row[event_mode] - $row[duration] Hour"; ?></h6>
                            <h6><?php echo "$row[date] -  $row[time]"; ?></h6>
                            <h6><?php echo "&#8377; $row[price]"; ?></h6>
                        </div>
                        <div class="col-sm-3 shadow-sm p-4">
                            <?php
                                $sql2 = "SELECT * FROM bookings WHERE event_id = $row[event_id]";
                                $result2 = $conn->query($sql2);
                                $tickets_sold = 0;
                                while($row2 = mysqli_fetch_assoc($result2)){
                                    $tickets_sold = $tickets_sold + $row2['no_of_tickets'];
                                }
                            ?>
                            <h5 class='mb-3'><b>Booking Summary</b></h5>
                            <h6>No of tickets - <?php echo $row['no_of_tickets']; ?> </h6>
                            <h6>Total Price- &#8377; <?php echo $row['total_price']; ?></h6>
                            <h6>Date: <?php echo $row['booking_date']; ?> </h6>
                        </div>
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