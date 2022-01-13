<?php
    session_start();
    ob_start();
    require __DIR__. '/../config.php';

?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <meta name="description" content="Minor Project Submission for MCA 4th Semester.">
    <meta name="author" content="Bhaskarjyoti Gogoi">
    <link rel="icon" href="<?php echo $http_host; ?>/images/favicon.png" sizes="16x16">
    <link rel="stylesheet" href="<?php echo $http_host; ?>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<?php echo $http_host; ?>/css/style.css">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
        integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">