<?php
$host = "localhost"; $user = "root"; $pass = ""; $db = "lms";
$conn = new mysqli($host, $user, $pass, $db);

if (isset($_POST['username']) && isset($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    $stmt = $conn->prepare("SELECT * FROM user WHERE username = ? AND password = ?");
    $stmt->bind_param("ss", $username, $password);
    $stmt->execute();
    $result = $stmt->get_result();

    echo ($result->num_rows > 0) ? "OK" : "FAIL";
} else {
    echo "INVALID";
}
