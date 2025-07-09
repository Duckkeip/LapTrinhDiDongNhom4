<?php
include "../connect.php";

$sql = "SELECT * FROM loaichitieu";
$result = mysqli_query($conn, $sql);

$categories = array();

while ($row = mysqli_fetch_assoc($result)) {
    $categories[] = array(
        "id" => (int)$row['id'],
        "name" => $row['name'],
        "type" => $row['type']
    );
}

echo json_encode($categories);
?>
