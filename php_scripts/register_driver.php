<?php
$json = file_get_contents('php://input');
if(isset($json))
{
    $person=json_decode($json);
    $u = $person->{'username'};
    $f = $person->{'firstname'};
    $l = $person->{'lastname'};
    $d = $person->{'dateofbirth'};
    $g = $person->{'gender'};
    $e = $person->{'email'};
    $c = $person->{'contactno'};
    $passwor = $person->{'password'};
}
else
{
   //echo "error";
}
$servername='localhost';
$username='root';
$password='1234';
$conn = mysqli_connect($servername, $username, $password,'lifeline');
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql="INSERT INTO driverlist (username,fname,lname,dob,gender,email,contact,password) VALUES('$u','$f','$l','$d','$g','$e','$c','$passwor')";
mysqli_select_db($conn,'driverlist');
$retval = mysqli_query($conn ,$sql );
if($retval =='1')
{
  echo 'success';
}
else
{
    echo "fail";
}
mysqli_close($conn);
?>
