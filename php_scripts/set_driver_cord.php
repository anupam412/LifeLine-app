<?php
$json = file_get_contents('php://input');
if(isset($json))
{
    $person=json_decode($json);
    $username = $person->{'username'};
    $latitude = $person->{'latitude'};
    $longitude = $person->{'longitude'};
}
//$username='rag';
echo($username); //important else error is coming
//$username="rag";
//$password="a";
//$latitude="200";
//$longitude="100";
//echo($username);
//echo($latitude);
//echo "           ";
//echo($longitude);
//echo($username);
//echo("afdsafsa");

$servername='localhost';
$usernam='root';
$passwor='1234';

//for($i=0;$i<20;$i++)
$conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql1="UPDATE driverlist SET latitude=$latitude WHERE username LIKE '$username'";
$sql2="UPDATE driverlist SET longitude=$longitude WHERE username LIKE '$username'";
mysqli_select_db($conn,'driverlist');

mysqli_query($conn, $sql1);
mysqli_query($conn, $sql2);
mysqli_close($conn);
?>
