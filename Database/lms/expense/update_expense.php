<?php
include '../connect.php';
header("Content-Type: application/json; charset=UTF-8");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = $_POST['id'] ?? '';
    $amount = $_POST['amount'] ?? '';
    $note = $_POST['note'] ?? '';
    $date = $_POST['date'] ?? '';
    $category_id = $_POST['category_id'] ?? '';
    $user = $_POST['user'] ?? '';

    if ($id == '' || $amount == '' || $note == '' || $date == '' || $user == '') {
        echo json_encode(["status" => "fail", "message" => "Missing required fields"]);
        exit;
    }

    $sql = "UPDATE giaodich SET amount='$amount', note='$note', date='$date', category_id='$category_id' 
            WHERE id='$id' AND user='$user'";

    if (mysqli_query($conn, $sql)) {
        echo json_encode(["status" => "success"]);
    } else {
        echo json_encode(["status" => "fail", "message" => mysqli_error($conn)]);
    }
} else {
    echo json_encode(["status" => "fail", "message" => "Invalid request"]);
}
