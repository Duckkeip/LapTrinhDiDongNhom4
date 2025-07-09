<?php
include "../connect.php";

$id = $_GET['id'];
$query = "DELETE FROM ngansach WHERE id = ?";
$stmt = $conn->prepare($query);
$stmt->bind_param("i", $id);
$success = $stmt->execute();

echo json_encode(["success" => $success]);
?>
