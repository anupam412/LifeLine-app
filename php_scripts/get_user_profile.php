<?php
//$js = $_GET["json"];
$json = file_get_contents('php://input');
if(isset($json))
{
$person=json_decode($json);
$username = $person->{'username'};

}
echo("");
//$username='a';
//$password='test';
$servername='localhost';
$usernam='root';
$passwor='1234';
// Create connection
$conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql="SELECT * FROM  userlist WHERE `username` LIKE '$username' ";
mysqli_select_db($conn,'lifeline');
$result = $conn->query($sql);
if(! $result->num_rows )
{
  echo 'fail';
}
else
{
    $row=$result->fetch_assoc();
    echo $row["fname"]." ".$row["lname"]."/".$row["dob"]."/".$row["blood"]."/".$row["gender"]."/".$row["contact"]."/".$row["donate"]."/".$row["email"]."/5";
}
?>
