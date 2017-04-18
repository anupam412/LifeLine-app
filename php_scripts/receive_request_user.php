<?php
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
/*
$lat1= 25.540788;


$lon1 = 84.8507968;
*/

    $min=10000;
    $indx=-1;
    $conn = mysqli_connect($servername, $usernam, $passwor,'lifeline');
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    $sql1="SELECT `latitude`,`longitude` FROM  userlist WHERE `username` LIKE '$username'";
    mysqli_select_db($conn,'lifeline');
    $result1 = $conn->query($sql1);
    if(! $result1->num_rows )
    {
      echo 'fail';
    }
    else
    {
        $row1=$result1->fetch_assoc();
        $lat1= $row1["latitude"];
        $lon1=$row1["longitude"];

        //$sql_temp="INSERT INTO userlist (username,fname,lname,dob,gender,email,contact,password) VALUES ('e','c','c','c','c','c','c','c')";
        //mysqli_query($conn,$sql_temp);
    }
$conn1 = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn1->connect_error) {
    die("Connection failed: " . $conn1->connect_error);
}


    $sql="SELECT request.username,driverlist.username as user,driverlist.latitude,driverlist.longitude FROM request CROSS JOIN driverlist  WHERE driverlist.available LIKE '1' and request.username LIKE '$username'";
    mysqli_select_db($conn,'lifeline');
    $result = $conn1->query($sql);
    //echo $result[]->username;
    if(! $result->num_rows )
    {
      echo 'fail';
    }
    else
    {
 for( $i = 0; $i<$result->num_rows; $i++ ) {
    $row=$result->fetch_assoc();
$lat2= $row["latitude"];
$lon2 = $row["longitude"];


//mysql_free_result($result);
    $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;

  if($miles<$min)
  {
      $min=$miles;
      $indx=$i;
      $driver=$row["user"];
  }



}
$conn1 = mysqli_connect($servername, $usernam, $passwor,'lifeline');
// Check connection
if ($conn1->connect_error) {
    die("Connection failed: " . $conn1->connect_error);
}

echo $username.$driver;
$sql="INSERT INTO active_connections (user,driver) VALUES('$username','$driver')";
$sql1="UPDATE driverlist SET available=0 WHERE username LIKE $driver";
mysqli_select_db($conn1,'lifeline');
$result = $conn1->query($sql);
$result1 = $conn1->query($sql1);
if(! $result->num_rows )
{
  echo 'fail';
}

}

?>
