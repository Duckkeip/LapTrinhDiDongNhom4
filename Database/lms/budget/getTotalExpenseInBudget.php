<?php
include "../connect.php";

$user = $_GET['user'];
$category_id = $_GET['category_id'];
$start_date = $_GET['start_date'];
$end_date = $_GET['end_date'];

// Chỉ tính các khoản chi (type = 'expense')
$query = "SELECT SUM(amount) as total_expense FROM giaodich 
          WHERE user = ? 
          AND category_id = ? 
          AND DATE(date) BETWEEN ? AND ?
          AND type = 'expense'";

$stmt = $conn->prepare($query);
$stmt->bind_param("siss", $user, $category_id, $start_date, $end_date);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();

$total = $row['total_expense'];

// Trả về dạng JSON
header('Content-Type: application/json');
echo json_encode(["total" => $total ? (float)$total : 0]);
?>
