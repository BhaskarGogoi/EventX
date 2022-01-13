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
                    $event_id = $_GET['eventid'];
                    $sql = "SELECT * FROM events WHERE event_id = ? ORDER BY event_id DESC";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        echo "Error";
                    } else {
                        mysqli_stmt_bind_param($stmt, "s", $_GET['eventid']);
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
                    <h4>Event Details</h4>
                    <div class="row">
                        <div class="col-sm-4">
                            <img src="<?php echo $http_host; ?>/images/event-images/<?php echo $row['event_id']; ?>.jpg"
                                alt="">
                        </div>
                        <div class="col-sm-8">
                            <h5><?php echo $row['event_name']; ?></h5>
                            <h6><?php echo "$row[event_type] -  $row[event_mode] - $row[duration] Hour"; ?></h6>
                            <h6><?php echo "$row[date] -  $row[time]"; ?></h6>
                            <h6><?php echo "&#8377; $row[price]"; ?></h6>
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-sm-3 shadow-sm mr-3 p-4">
                            <h5><b>Hosted By</b></h5>
                            <h6><?php echo $row['hosted_by']; ?></h6>
                            <?php 
                            if($row['address_area'] == null){
                                echo "
                                    <h6><b><i class='fas fa-film' style='color: #FF6666;'></i> Online Event</b></h6>
                                ";
                            } else {
                                echo "
                                    <h4><b><i class='fas fa-map-marker-alt' style='color: #FF6666;'></i> Address</b></h4>    
                                    <p>
                                        $row[address_area], $row[address_locality]<br>
                                        <b>$row[address_city]</b> $row[address_state]<br>
                                        $row[address_pin]
                                    </p>";
                            }
                            ?>
                        </div>
                        <div class="col-sm-5 shadow-sm mr-3 p-4">
                            <h5><b>About</b></h5>
                            <p><?php echo $row['about']; ?></p>
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
                            <h6>Tickets Sold - <?php echo $tickets_sold; ?> </h6>
                            <h6>Tickets Left - <?php echo $row['max_tickets'] - $tickets_sold; ?></h6>
                            <h6>Income - &#8377; <?php echo $tickets_sold * $row['price']; ?> </h6>
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