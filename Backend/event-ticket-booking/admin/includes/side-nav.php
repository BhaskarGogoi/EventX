<?php
    $filename = basename($_SERVER["SCRIPT_FILENAME"], '.php');
?>

<div class="side_nav shadow-sm rounded">
    <ul>
        <a href="dashboard">
            <li <?php echo ($filename == 'dashboard') ? 'class="side-nav-active"' : ''; ?>><i
                    class="fas fa-tachometer-alt"></i> Dashboard</li>
        </a>
        <a href="bookings">
            <li <?php echo ($filename == 'bookings') ? 'class="side-nav-active"' : ''; ?>><i class="fas fa-receipt"></i>
                Bookings
            </li>
        </a>
        <a href="create-event">
            <li <?php echo ($filename == 'create-event') ? 'class="side-nav-active"' : ''; ?>><i
                    class="fab fa-elementor"></i> Create Event
            </li>
        </a>
        <a href="all-events">
            <li <?php echo ($filename == 'all-events') ? 'class="side-nav-active"' : ''; ?>><i
                    class="fas fa-ticket-alt"></i> All Events
            </li>
        </a>
        <a href="user-management">
            <li <?php echo ($filename == 'user-management') ? 'class="side-nav-active"' : ''; ?>><i
                    class="fas fa-user-cog"></i>
                User Management</li>
        </a>
    </ul>
</div>