<?php
include '../connect.php';
header("Content-Type: application/json; charset=UTF-8");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = $_POST['id'] ?? '';

    if ($id == '') {
        echo json_encode(["status" => "fail", "message" => "missing_id"]);
        exit;
    }

    $sql = "DELETE FROM giaodich WHERE id = '$id'";

    if (mysqli_query($conn, $sql)) {
        echo json_encode(["status" => "success"]);
    } else {
        echo json_encode(["status" => "fail", "message" => mysqli_error($conn)]);
    }
} else {
    echo json_encode(["status" => "fail", "message" => "invalid_method"]);
}
?>
