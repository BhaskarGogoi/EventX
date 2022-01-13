<?php

	include ('../includes/header.php');
	echo "<title>Edit Event</title>";
     //csrf token 
     $token = md5(uniqid(rand(), true));
     $_SESSION['csrf_token'] = $token;
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
                 include('../includes/database_connection.php');
                 $event_id = $_GET['eventid'];
                 $sql = "SELECT * FROM events WHERE event_id = ?";
                 $stmt = mysqli_stmt_init($conn);
                 if(!mysqli_stmt_prepare($stmt, $sql)){
                     echo "Error";
                 } else {
                     mysqli_stmt_bind_param($stmt, "s", $event_id);
                     mysqli_stmt_execute($stmt);
                     $result = mysqli_stmt_get_result($stmt);
                     if($result->num_rows > 0){
                         $row = mysqli_fetch_assoc($result);
                     } else {
                         echo "
                         <div class='col-sm-10'>
                             <div class='shadow-sm p-5 rounded'>
                                 Error
                             </div>
                         </div>";
                         die();
                     }
                 }
            ?>
            <div class="col-sm-10">
                <div class="shadow p-5 rounded">
                    <?php
                         $url = "http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
                         if (strpos($url, 'error=500')!== false) {
                             echo "
                                 <div class='alert alert-danger alert-dismissible fade show' role='alert'>
                                     Internal Server Error!
                                     <button type='button' class='close' data-dismiss='alert' aria-label='Close'>
                                         <span aria-hidden='true'>&times;</span>
                                     </button>
                                 </div>";
                         }  
                         if (strpos($url, 'success')!== false) {
                             echo "
                                 <div class='alert alert-success alert-dismissible fade show' role='alert'>
                                     Updated Successfully!
                                     <button type='button' class='close' data-dismiss='alert' aria-label='Close'>
                                         <span aria-hidden='true'>&times;</span>
                                     </button>
                                 </div>";
                         }
                    ?>
                    <h2>Edit Event</h2>
                    <form action="includes/edit-event.script.php" method="POST">
                        <div class="form-row">
                            <input type="hidden" name="csrf_token" value="<?php echo $token ?>">
                            <input type="hidden" name="event_id" value="<?php echo $row['event_id'] ?>">
                            <div class="form-group col-md-6">
                                <label>Name of the event</label>
                                <input type="text" class="form-control" name="event_name"
                                    placeholder="Name of the event" value="<?php echo $row['event_name'] ?>" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Hosted By</label>
                                <input type="text" name="hosted_by" class="form-control" placeholder="Hosted by"
                                    value="<?php echo $row['hosted_by'] ?>" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Date</label>
                                <input type="date" name="date" class="form-control" value="<?php echo $row['date'] ?>"
                                    required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Time</label>
                                <input type="time" name="time" class="form-control" value="<?php echo $row['time'] ?>"
                                    required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Duration</label>
                                <select id="inputState" name="duration" class="form-control" required>
                                    <option selected><?php echo $row['duration'] ?> Hour</option>
                                    <?php 
                                        if($row['duration'] != 1){
                                            echo "<option value='1'>1 Hour</option>";
                                        }
                                        if($row['duration'] != 2){
                                            echo "<option value='2'>2 Hour</option>";
                                        }
                                        if($row['duration'] != 3){
                                            echo "<option value='3'>3 Hour</option>";
                                        }
                                        if($row['duration'] != 4){
                                            echo "<option value='4'>4 Hour</option>";
                                        }
                                        if($row['duration'] != '5+'){
                                            echo "<option value='5+'>5+ Hour</option>";
                                        }
                                    ?>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Maximum Tickets</label>
                                <input type="number" class="form-control" name="max_tickets"
                                    placeholder="Maximum Tickets" value="<?php echo $row['max_tickets'] ?>" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Mode</label>
                                <select id="inputState" name="event_mode" class="form-control" required>
                                    <?php 
                                    if($row['event_mode'] == "Offline"){
                                        echo "
                                            <option selected value='Offline'>Offline</option>";
                                    } else {
                                        echo "
                                            <option selected value='Online'>Online</option>";
                                    }
                                    ?>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Event Type</label>
                                <select name="event_type" class="form-control" required>
                                    <option selected><?php echo $row['event_type'] ?></option>
                                    <?php
                                        if($row['event_type'] != "Music"){
                                            echo "<option>Music</option>";
                                        }
                                        if($row['event_type'] != "Stand Up"){
                                            echo "<option>Stand Up</option>";
                                        }
                                        if($row['event_type'] != "Play"){
                                            echo "<option>Play</option>";
                                        }
                                        if($row['event_type'] != "Workshop"){
                                            echo "<option>Workshop</option>";
                                        }
                                        if($row['event_type'] != "Exhibition"){
                                            echo "<option>Exhibition</option>";
                                        }
                                        if($row['event_type'] != "Talks"){
                                            echo "<option>Talks</option>";
                                        }
                                    ?>
                                </select>
                            </div>
                        </div>
                        <?php 
                        if($row['address_area']){ ?>
                        <div id="addressFields">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>Area</label>
                                    <input type="text" class="form-control" name="area" placeholder="Area"
                                        value="<?php echo $row['address_area'] ?>">
                                </div>
                                <div class="form-group col-md-6">
                                    <label>Locality</label>
                                    <input type="text" class="form-control" name="locality" placeholder="Locality"
                                        value="<?php echo $row['address_locality'] ?>">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>City</label>
                                    <input type="text" class="form-control" name="city" placeholder="City"
                                        value="<?php echo $row['address_city'] ?>">
                                </div>
                                <div class="form-group col-md-6">
                                    <label>State</label>
                                    <input type="text" class="form-control" name="state" placeholder="State"
                                        value="<?php echo $row['address_state'] ?>">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>Pin</label>
                                    <input type="text" class="form-control" name="pin" placeholder="PIN"
                                        value="<?php echo $row['address_pin'] ?>">
                                </div>
                            </div>
                        </div>
                        <?php
                        } else { ?>
                        <div id="addressFields">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <input type="hidden" class="form-control" name="area" placeholder="Area"
                                        value="<?php echo $row['address_area'] ?>">
                                </div>
                                <div class="form-group col-md-6">
                                    <input type="hidden" class="form-control" name="locality" placeholder="Locality"
                                        value="<?php echo $row['address_locality'] ?>">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <input type="hidden" class="form-control" name="city" placeholder="City"
                                        value="<?php echo $row['address_city'] ?>">
                                </div>
                                <div class="form-group col-md-6">
                                    <input type="hidden" class="form-control" name="state" placeholder="State"
                                        value="<?php echo $row['address_state'] ?>">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <input type="hidden" class="form-control" name="pin" placeholder="PIN"
                                        value="<?php echo $row['address_pin'] ?>">
                                </div>
                            </div>
                        </div>
                        <?php
                        }
                        ?>
                        <div class="form-group">
                            <label>About the show</label>
                            <textarea class="form-control" name="about" rows="3"
                                required><?php echo $row['about'] ?></textarea>
                        </div>
                        <div class="form-group">
                            <label>Ticket Price (&#x20B9;)</label>
                            <input type="text" name="price" class="form-control" placeholder="Ticket Price"
                                value="<?php echo $row['price'] ?>" required>
                        </div>
                        <button type="submit" name="submit" class="btn btn-warning">Submit</button>
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