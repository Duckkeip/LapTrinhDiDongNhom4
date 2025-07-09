<?php
$host = "localhost";
$username = "root";
$password = ""; // Mặc định XAMPP không có mật khẩu
$database = "lms"; // ⚠️ Đổi tên DB cho đúng

$conn = mysqli_connect($host, $username, $password, $database);
mysqli_set_charset($conn, "utf8");

if (!$conn) {
    die("Kết nối thất bại: " . mysqli_connect_error());
}
?>
