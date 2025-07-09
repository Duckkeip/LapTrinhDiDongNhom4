<?php
include "../connect.php";
$user = $_GET['user'];
$query = "SELECT ns.*, lct.name as category_name FROM ngansach ns
          JOIN loaichitieu lct ON ns.category_id = lct.id
          WHERE ns.user = '$user'";
$data = mysqli_query($conn, $query);
$budgets = array();

while ($row = mysqli_fetch_assoc($data)) {
    $budgets[] = $row;
}
echo json_encode($budgets);
?>
