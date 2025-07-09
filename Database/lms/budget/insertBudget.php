<?php
include "../connect.php";
$category_id = $_POST['category_id'];
$amount = $_POST['amount'];
$start_date = $_POST['start_date'];
$end_date = $_POST['end_date'];
$user = $_POST['user'];

$query = "INSERT INTO ngansach (category_id, amount, start_date, end_date, user)
          VALUES ('$category_id', '$amount', '$start_date', '$end_date', '$user')";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => "success"]);
} else {
    echo json_encode(["status" => "error"]);
}
?>
