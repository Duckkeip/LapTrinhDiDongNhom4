<?php
$host = "localhost"; $user = "root"; $pass = ""; $db = "lms";
$conn = new mysqli($host, $user, $pass, $db);

if (isset($_POST['username'])) {
    $username = $_POST['username'];

    $stmt = $conn->prepare("SELECT * FROM user WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();
    if ($user = $result->fetch_assoc()) {
        echo json_encode($user);
    } else {
        echo "NOT_FOUND";
    }
} else {
    echo "INVALID";
}
