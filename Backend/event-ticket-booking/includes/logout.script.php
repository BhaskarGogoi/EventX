<?php
	
if(isset($_POST['submit'])) {	
	session_start();
	session_unset();
	session_destroy();
	header ("Location: //localhost/event-ticket-booking/index");
}
?>