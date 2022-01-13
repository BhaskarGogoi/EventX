<?php

	include 'includes/header.php';
	echo "<title>About</title>";
?>

</head>


<body class="main-layout">

   <?php
		include('includes/nav.php');
	?>
   <!-- end header -->
   <div class="brand_color">
      <div class="container">
         <div class="row">
            <div class="col-md-12">
               <div class="titlepage">
                  <h2>about</h2>
               </div>
            </div>
         </div>
      </div>

   </div>


   <div class="about">
      <div class="container">
         <div class="row">
            <dir class="col-xl-6 col-lg-6 col-md-12 col-sm-12">
               <div class="about_box">
                  <figure><img src="images/logo.png" /></figure>
               </div>
            </dir>
            <dir class="col-xl-6 col-lg-6 col-md-12 col-sm-12">
               <div class="about_box">
                  <h3>EventX</h3>
                  <p>is a online event ticket booking system which allows users to book and pay for tickets of events
                     thought this system. Users can book tickets directly through this system. </p>
               </div>
            </dir>
         </div>
      </div>
   </div>
   <?php
		include('includes/footer.php');
	?>
</body>

</html>