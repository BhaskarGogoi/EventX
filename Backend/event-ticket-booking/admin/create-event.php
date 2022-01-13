<?php

	include ('../includes/header.php');
	echo "<title>Create Event</title>";
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
            <div class="col-sm-10">
                <div class="shadow p-5 rounded">
                    <?php
                        $url = "http://".$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
                        if (strpos($url, 'error')!== false) {
                            echo "
                                <div class='alert alert-danger alert-dismissible fade show' role='alert'>
                                    Something went wrong!
                                    <button type='button' class='close' data-dismiss='alert' aria-label='Close'>
                                        <span aria-hidden='true'>&times;</span>
                                    </button>
                                </div>";
                        } 
                    ?>
                    <h4>Create Event</h4>
                    <form action="includes/create-event-script.php" method="POST" enctype='multipart/form-data'>
                        <div class="form-row">
                            <input type="hidden" name="csrf_token" value="<?php echo $token ?>">
                            <div class="form-group col-md-6">
                                <label>Name of the event</label>
                                <input type="text" class="form-control" name="event_name"
                                    placeholder="Name of the event" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Hosted By</label>
                                <input type="text" name="hosted_by" class="form-control" placeholder="Hosted by"
                                    required>

                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Date</label>
                                <input type="date" name="date" class="form-control" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Time</label>
                                <input type="time" name="time" class="form-control" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Duration</label>
                                <select id="inputState" name="duration" class="form-control" required>
                                    <option selected>Choose...</option>
                                    <option value='1'>1 Hour</option>
                                    <option value='2'>2 Hour</option>
                                    <option value='3'>3 Hour</option>
                                    <option value='4'>4 Hour</option>
                                    <option value='5+'>5+ Hour</option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Maximum Tickets</label>
                                <input type="number" class="form-control" name="max_tickets"
                                    placeholder="Maximum Tickets" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Mode</label>
                                <select id="inputState" name="event_mode" class="form-control"
                                    onchange='viewAddressField(this.value)' required>
                                    <option selected>Choose...</option>
                                    <option value='Offline'>Offline</option>
                                    <option value='Online'>Online</option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Show Type</label>
                                <select name="event_type" class="form-control" required>
                                    <option selected>Choose...</option>
                                    <option>Music</option>
                                    <option>Stand Up</option>
                                    <option>Play</option>
                                    <option>Workshop</option>
                                    <option>Exhibition</option>
                                    <option>Talks</option>
                                </select>
                            </div>
                        </div>
                        <div id="addressFields">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>Area</label>
                                    <input type="text" class="form-control" name="area" placeholder="Area">
                                </div>
                                <div class="form-group col-md-6">
                                    <label>Locality</label>
                                    <input type="text" class="form-control" name="locality" placeholder="Locality">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>City</label>
                                    <input type="text" class="form-control" name="city" placeholder="City">
                                </div>
                                <div class="form-group col-md-6">
                                    <label>State</label>
                                    <input type="text" class="form-control" name="state" placeholder="State">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label>Pin</label>
                                    <input type="text" class="form-control" name="pin" placeholder="PIN">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>About the show</label>
                            <textarea class="form-control" name="about" rows="3" required></textarea>
                        </div>
                        <div class="form-group">
                            <label>Ticket Price (&#x20B9;)</label>
                            <input type="text" name="price" class="form-control" placeholder="Ticket Price" required>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="exampleFormControlFile1">Image</label>
                                <input type="file" class="form-control-file" name='portrait_image' required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" required>
                                <label class="form-check-label">
                                    Agree to the terms and conditions.
                                </label>
                            </div>
                        </div>
                        <button type="submit" name="submit" class="btn btn-warning">Create Now</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script type='text/javascript'>
        function viewAddressField(mode) {
            if (mode === "Choose...") {
                document.getElementById("addressFields").style.display = "none";
            } else if (mode === 'Offline') {
                document.getElementById("addressFields").style.display = "block";
            } else if (mode === "Online") {
                document.getElementById("addressFields").style.display = "none";
            }
        }
    </script>
    <?php
		include('../includes/footer.php');
	?>
</body>

</html>