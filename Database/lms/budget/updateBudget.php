<?php
include "../connect.php";

$id = $_POST['id'];
$category_id = $_POST['category_id'];
$amount = $_POST['amount'];
$start_date = $_POST['start_date'];
$end_date = $_POST['end_date'];
$user = $_POST['user'];

$query = "UPDATE ngansach SET category_id=?, amount=?, start_date=?, end_date=?, user=? WHERE id=?";
$stmt = $conn->prepare($query);
$stmt->bind_param("idsssi", $category_id, $amount, $start_date, $end_date, $user, $id);

if ($stmt->execute()) {
    echo "success";
} else {
    echo "fail";
}
?>
