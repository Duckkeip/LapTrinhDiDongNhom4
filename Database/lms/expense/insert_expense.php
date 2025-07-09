<?php
header("Content-Type: application/json; charset=UTF-8");
include '../connect.php';

// Ghi log để debug (nếu cần)
file_put_contents('log.txt', print_r($_POST, true));

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $amount = $_POST['amount'] ?? '';
    $note = $_POST['note'] ?? '';
    $date = $_POST['date'] ?? '';
    $category_id = $_POST['category_id'] ?? '';
    $user = $_POST['user'] ?? '';

    if ($amount == '' || $note == '' || $date == '' || $user == '') {
        echo "\"missing_data\""; // hoặc: echo "\"fail\"";
        exit;
    }

    $sql = "INSERT INTO giaodich (amount, note, date, category_id, user, type)
            VALUES ('$amount', '$note', '$date', '$category_id', '$user', 'expense')";

    if (mysqli_query($conn, $sql)) {
        echo "\"success\"";
    } else {
        $error = mysqli_error($conn);
    file_put_contents('log.txt', "SQL Error: " . $error, FILE_APPEND);
        echo "\"fail\"";
    }
} else {
    echo "\"invalid_method\"";
}
?>
