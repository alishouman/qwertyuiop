<?php
$servername = "localhost";
$username = "root";
$password = "1234567";
$Db_name = "test_server";
// Create connection
$connectionToDB = new mysqli($servername, $username, $password);
// Check connection
if ($connectionToDB->connect_error) {
    die("Connection failed: " .$connectionToDB->connect_error);
} 

//Create database
$sql = "CREATE DATABASE ".$Db_name;
if ($connectionToDB->query($sql) === TRUE) {
    echo "Database created successfully<br/>";
} else {
    echo "Error creating database<br/>" . $connectionToDB->error;
}

echo "Now Creating Tables..<br/>";

$connectionToDB = new mysqli($servername, $username, $password, $Db_name);
// creating candidates table table 
$newtable = "CREATE TABLE users ( Username VARCHAR(20), FirstName VARCHAR(20), LastName VARCHAR(20) ,Password VARCHAR(20),Candidate VARCHAR(20), 
		Address VARCHAR(20),Age INT, PRIMARY KEY (Username) )";
	//$connectionToDB->query($newtable);

if ($connectionToDB->query($newtable) === TRUE) {
    echo "users Table has been successfully created<br/>";
} else {
    echo "Error creating users table<br/>" . $connectionToDB->error;
}

// creating registration table table 
$newtable = "CREATE TABLE candidates ( candidate_name VARCHAR(20), number_of_votes INT, 
	PRIMARY KEY (candidate_name) )";

if ($connectionToDB->query($newtable) === TRUE) {
    echo "Candidates Table has been successfully created<br/>";
} else {
    echo "Error creating Candidates table<br/>" . $connectionToDB->error;
}

$connectionToDB->close();
?>