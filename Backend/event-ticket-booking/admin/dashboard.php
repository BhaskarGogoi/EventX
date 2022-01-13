<?php
    ob_start();
    header("X-XSS-Protection: 1; mode=block");
	include ('../includes/header.php');
	echo "<title>Admin Dashboard</title>";
    //csrf token 
    $token = md5(uniqid(rand(), true));
    $_SESSION['csrf_token'] = $token;
?>

</head>
<!-- body -->

<body class="main-layout">
    <!-- Delete event modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <h1 style='padding: 20px;'>Delete Event</h1>
                <div class="modal-body">
                    Are you sure?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>

                </div>
            </div>
        </div>
    </div>
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

                    //get total ticets sold
                    $sql = "SELECT * FROM bookings;";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        return 0;
                    } else {
                        mysqli_stmt_execute($stmt);
                        $result = mysqli_stmt_get_result($stmt);
                        $total_tickets_sold = 0;
                        $total_income = 0;
                        while($row = mysqli_fetch_assoc($result)) {
                            $total_tickets_sold = $total_tickets_sold + $row['no_of_tickets'];
                            $total_income = $total_income + $row['total_price'];
                        }
                    }

                    //get total events
                    $sql = "SELECT COUNT(*) AS total_events From events";
                    $stmt = mysqli_stmt_init($conn);
                    if(!mysqli_stmt_prepare($stmt, $sql)){
                        return 0;
                    } else {
                        mysqli_stmt_execute($stmt);
                        $result = mysqli_stmt_get_result($stmt);
                        $row = mysqli_fetch_assoc($result);
                    }
                   
                ?>
            </div>
            <div class="col-sm-10">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="card p-3" style="width: 18rem; background-color: #4d4cac;">
                            <div style=' color: #fff;'>
                                <div class="row">
                                    <div class="col-sm-4 ml-2">
                                        <div class="float-right dashboard-top-card-icon"
                                            style='background-color: #7776d7;'>
                                            <i class="fas fa-ticket-alt"></i>
                                        </div>
                                    </div>
                                    <div class="col-sm-7">
                                        <div class="float-left">
                                            <span style='font-size: 20px;'><?php echo $total_tickets_sold; ?></span>
                                            <br>
                                            Total Tickets Sold
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="card p-3" style="width: 18rem; background-color: #9698d6;">
                            <div style='color: #fff;'>
                                <div class="row">
                                    <div class="col-sm-4 ml-2">
                                        <div class="float-right dashboard-top-card-icon"
                                            style='background-color: #7776d7;'>
                                            <i class="fas fa-rupee-sign"></i>
                                        </div>
                                    </div>
                                    <div class="col-sm-7">
                                        <div class="float-left">
                                            <span style='font-size: 20px;'><?php echo $total_income; ?></span> <br>
                                            Total Income
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="card p-3" style="width: 18rem; background-color: #ff808b;">
                            <div style='color: #fff;'>
                                <div class="row">
                                    <div class="col-sm-4 ml-2">
                                        <div class="float-right dashboard-top-card-icon"
                                            style='background-color: #f3adb3;'>
                                            <i class="fas fa-microphone"></i>
                                        </div>
                                    </div>
                                    <div class="col-sm-7">
                                        <div class="float-left">
                                            <span style='font-size: 20px;'><?php echo $row['total_events']; ?></span>
                                            <br>
                                            Events Created
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><br>
                <?php
                $sql = "SELECT * FROM events  ORDER BY event_id DESC LIMIT 5";
                $stmt = mysqli_stmt_init($conn);
                if(!mysqli_stmt_prepare($stmt, $sql)){
                    return 0;
                } else {
                    mysqli_stmt_execute($stmt);
                    $result = mysqli_stmt_get_result($stmt);
                    if ($result->num_rows > 0) {
                        echo "
                        <table class='table shadow-sm rounded'>
                            <thead class='thead-dark'>
                                <tr>
                                    <th scope='col'>Event ID</th>
                                    <th scope='col'>Event Name</th>
                                    <th scope='col'>Event Type</th>
                                    <th scope='col'>Hosted By</th>
                                    <th scope='col'>Tickets Sold</th>
                                    <th scope='col'>Action</th>
                                </tr>
                            </thead>
                            <tbody>";
                            while($row = mysqli_fetch_assoc($result)) {
                                echo"<tr>
                                        <th>$row[event_id]</th>
                                        <th><a href='event-details?eventid=$row[event_id]'>$row[event_name]</a></th>
                                        <td>$row[event_type]</td>
                                        <td>$row[hosted_by]</td>";
                                        $sql2 = "SELECT * FROM bookings WHERE event_id = $row[event_id]";
                                        $result2 = $conn->query($sql2);
                                        $tickets_sold = 0;
                                        while($row2 = mysqli_fetch_assoc($result2)){
                                            $tickets_sold = $tickets_sold + $row2['no_of_tickets'];
                                        }
                                        echo "<td>$tickets_sold</td>
                                        <td>
                                            <form action='includes/event-action.script.php' method='POST' style='display: inline-block;'>
                                                <input type='hidden' name='csrf_token' value=$token required>
                                                <input type='hidden' name='action' value='edit' required>
                                                <input type='hidden' name='event_id' value='$row[event_id]' required>
                                                <button type='submit' class='action-button'>
                                                    <i class='far fa-edit' style='color: #ffa200;'></i> 
                                                </button>
                                            </form>        
                                            <form action='includes/event-action.script.php' method='POST' style='display: inline-block;'>
                                                <input type='hidden' name='csrf_token' value=$token required>
                                                <input type='hidden' name='action' value='delete' required>
                                                <input type='hidden' name='event_id' value='$row[event_id]' required>
                                                <button type='submit' class='action-button'>
                                                    <i class='far fa-trash-alt' style='color: red;'></i>
                                                </button>
                                            </form>                                            
                                        </td>
                                    </tr>";
                            }
                            echo"
                            </tbody>
                            </table>";
                    } else {
                        echo "<img class='image-center' src='$http_host/images/empty.svg' alt='empty'>
                        <h3 class='text-center mt-3 font-weight-bold'>No Events Found!</h3>";
                    } 
                }
                ?><br>
            </div>
        </div>
    </div>
    <?php
		include('../includes/footer.php');
	?>
</body>

</html>