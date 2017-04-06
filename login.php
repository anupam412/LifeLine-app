
<?php

//$js = $_GET["json"]; 
$json = file_get_contents('php://input');

if(isset($json))
{
$person=json_decode($json);
$username = $person->{'username'};
$password = $person->{'password'};
}
else
{
   //echo "error";
}


$servername='localhost';
$usernam='root';
$passwor='1234';


// Create connection
$conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql="SELECT `username` FROM  userlist WHERE `username` LIKE '$username' AND `password` LIKE '$password'";


mysqli_select_db($conn,'lifeline');
$retval = mysqli_query($conn ,$sql );


if(! $retval->num_rows )
{
  echo 'fail';
}
else
{
   
 
    echo "success";
 
 
  }
  
  
 
   ?>
 
  




















