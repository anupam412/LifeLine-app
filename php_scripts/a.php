<?php
$json = file_get_contents('php://input');
if(isset($json))
{
$person=json_decode($json);
$username = $person->{'drivername'};
}
echo("");
$servername='localhost';
$usernam='root';
$passwor='1234';
    $min=10000;
    $indx=-1;
    $conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    $sql1="SELECT user FROM  active_connections WHERE `driver` LIKE '$username'";
    mysqli_select_db($conn,'lifeline');
    $result1 = $conn->query($sql1);
    if(! $result1->num_rows )
    {
      echo 'fail';
    }
    else
    {
        $row=$result1->fetch_assoc();
        $user= $row["user"];
        echo $user;
    }
?>
