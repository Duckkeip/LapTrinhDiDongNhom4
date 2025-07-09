<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

file_put_contents("debugchange.txt", "username=" . $_POST['username'] . "\n", FILE_APPEND);
file_put_contents("debugchange.txt", "currentpass=" . ($_POST['currentpass'] ?? 'null') . "\n", FILE_APPEND);
file_put_contents("debugchange.txt", "newpass=" . ($_POST['newpass'] ?? 'null') . "\n", FILE_APPEND);

$host = "localhost"; $user = "root"; $pass = ""; $db = "lms";
$conn = new mysqli($host, $user, $pass, $db);

if (isset($_POST['username']) && isset($_POST['currentpass']) && isset($_POST['newpass'])) {
    $username = $_POST['username'];
    $currentpass = $_POST['currentpass'];
    $newpass = $_POST['newpass'];

    // Lấy mật khẩu từ DB (không mã hóa)
    $stmt = $conn->prepare("SELECT password FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $stored_password = $row['password'];

        if ($currentpass === $stored_password) {
            // Mật khẩu đúng → cập nhật mật khẩu mới (không mã hóa)
            $update = $conn->prepare("UPDATE users SET password = ? WHERE username = ?");
            $update->bind_param("ss", $newpass, $username);
            if ($update->execute()) {
                echo "OK";
            } else {
                echo "FAIL_UPDATE";
            }
        } else {
            echo "WRONG_PASS";
        }
    } else {
        echo "WRONG_PASS";
    }

} else {
    echo "INVALID";
}
