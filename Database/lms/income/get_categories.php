<?php
include '../connect.php';

$type = $_GET['type']; // income hoặc expense

$result = mysqli_query($conn, "SELECT * FROM loaichitieu WHERE type = '$type'");
$categories = [];

while ($row = mysqli_fetch_assoc($result)) {
    $categories[] = $row;
}

echo json_encode($categories);
?>
