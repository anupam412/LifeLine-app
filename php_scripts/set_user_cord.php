<?php
$json = file_get_contents('php://input');
if(isset($json))
{
    $person=json_decode($json);
    $username = $person->{'username'};
    $latitude = $person->{'latitude'};
    $longitude = $person->{'longitude'};
}
echo($username); //important else error
//$username="a";
//$password="a";
//$latitude="10";
//$longitude="10";
$servername='localhost';
$usernam='root';
$passwor='1234';
$conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn->connect_error) {
    die("Connection failed:  ".$conn->connect_error);
}
$sql1="UPDATE userlist SET latitude=$latitude WHERE username LIKE '$username'";
$sql2="UPDATE userlist SET longitude=$longitude WHERE username LIKE '$username'";
mysqli_select_db($conn,'lifeline');

mysqli_query($conn, $sql1);
mysqli_query($conn, $sql2);
mysqli_close($conn);
?>
