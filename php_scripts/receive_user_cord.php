<?php
//$js = $_GET["json"];
/*$json = file_get_contents('php://input');
if(isset($json))
{
$person=json_decode($json);
$username = $person->{'username'};
$password = $person->{'password'};
}*/
$username='test';
$password='test';
$servername='localhost';
$usernam='root';
$passwor='1234';
// Create connection
$conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql="SELECT `latitude`,`longitude` FROM  userlist WHERE `username` LIKE '$username' AND `password` LIKE '$password'";
mysqli_select_db($conn,'lifeline');
$result = $conn->query($sql);
if(! $result->num_rows )
{
  echo 'fail';
}
else
{
    $row=$result->fetch_assoc();
    echo $row["latitude"]."/".$row["longitude"];
}
?>