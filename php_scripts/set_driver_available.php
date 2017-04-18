<?php
$json = file_get_contents('php://input');
if(isset($json))
{
$person=json_decode($json);
$username = $person->{'drivername'};
$avail = $person->{'availability'};

}
echo("");
$servername='localhost';
$usernam='root';
$passwor='1234';
    $conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
    echo("");

    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    $sql1="UPDATE driverlist SET available='$avail' WHERE username LIKE '$username'";
    //$sql="DELETE FROM `active_connections` WHERE username LIKE $username";
    echo("");

    mysqli_select_db($conn,'lifeline');
    echo("");

    $result1 = $conn->query($sql1);
    echo("");

    //$result = $conn->query($sql);
    



?>
