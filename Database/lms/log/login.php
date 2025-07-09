<?php
file_put_contents("debug.txt", json_encode([
    'POST' => $_POST,
    'method' => $_SERVER['REQUEST_METHOD']
]));

$conn = new mysqli("localhost", "root", "", "lms");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (!empty($_POST['username']) && !empty($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Lấy password và role
    $stmt = $conn->prepare("SELECT password, role FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $stmt->bind_result($dbPassword, $role);

    if ($stmt->fetch()) {
        if ($password === $dbPassword) {
            echo $role; // admin hoặc user
        } else {
            echo "failure"; // sai mật khẩu
        }
    } else {
        echo "failure"; // user không tồn tại
    }

    $stmt->close();
} else {
    echo "missing_fields";
}

$conn->close();
?>
