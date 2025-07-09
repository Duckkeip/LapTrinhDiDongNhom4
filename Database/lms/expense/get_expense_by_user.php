<?php
include '../connect.php'; // Kết nối MySQL

header("Content-Type: application/json; charset=UTF-8");

if (isset($_GET['username'])) {
    $user = $_GET['username'];

    $sql = "SELECT g.*, l.name AS category_name, l.type AS category_type
            FROM giaodich g 
            JOIN loaichitieu l ON g.category_id = l.id 
            WHERE g.user = '$user' AND l.type = 'expense' 
            ORDER BY g.id DESC";

    $result = mysqli_query($conn, $sql);

    if (!$result) {
        echo json_encode(["error" => "Lỗi SQL", "message" => mysqli_error($conn)]);
        exit;
    }

    $incomes = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $incomes[] = $row;
        file_put_contents('logdate.txt', $row['date'] . "\n", FILE_APPEND); // ✅ đặt trong while
    }

    echo json_encode($incomes);
} else {
    echo json_encode(array("error" => "Username is required"));
}
?>
