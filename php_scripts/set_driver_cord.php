<?php
$json = file_get_contents('php://input');
/*if(isset($json))
{
    $person=json_decode($json);
    $username = $person->{'username'};
    $latitude = $person->{'latitude'};
    $longitude = $person->{'longitude'};
}*/
$username="a";
$password="a";
$latitude="10";
$longitude="10";
$servername='localhost';
$usernam='root';
$passwor='1234';
$conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql="UPDATE driverlist SET latitude=$latitude, longitude=$longitude WHERE username LIKE '$username' AND password LIKE '$password'";
mysqli_select_db($conn,'driverlist');
if (mysqli_query($conn, $sql)) {
    echo "Record updated successfully";
}
else{
    echo "fail";
}
mysqli_close($conn);
?>
