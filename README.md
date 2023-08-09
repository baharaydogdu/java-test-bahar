# Booking Application

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-program">About The Program</a>
      <ul>
		<li><a href="#purpose">Purpose</a></li>
		<li><a href="#constraints">Contraints</a></li>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li> 
    <li><a href="#usage">Prepare Environment</a></li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>

## About The Program

### Purpose

This program represents a booking api that processes batches of booking requests. It accepts the text input and process them based on the booking rules and returns a structured json response.

### Constraints
Booking Rules
1) Each booking request is in the following format:
   
    [Request submission time, in YYYY-MM-DD HH:MM:SS format] [Employee id]

    [Meeting start time, in YYYY-MM-DD HH:MM format] [Meeting duration in hours] 

2) No part of a meeting may fall outside of office hours
3) Meetings may not overlap
4) The booking submission system only allows one submission at a time, so submission times are guaranteed to be unique.
5) Booking must be processed in the chronological order in which they were submitted.
6) The ordering of booking submissions in the supplied input is not guaranteed.

### Built With

* This program is written in Java 11.
* Spring Boot Framework is used for building REST API.
* Maven is used as build tool.

## Prepare Environment

Clone the repository

```
git@github.com:baharaydogdu/java-test-bahar.git
```

Navigate to project folder

```
cd java-test-bahar/
```

Build project

```
mvn clean install
```

Run project
```
mvn spring-boot:run
```

## Usage
### REST API

To use application by using Postman, please follow below steps

1) Import below Postman collection or just simply create a new post request.
   
   {
	"info": {
		"_postman_id": "3a84eb66-5d71-4acc-b92a-b30a7311b614",
		"name": "BookingApplication",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "http://localhost:8080/book",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"name": "Content-Type",
						"value": "text/plain",
						"type": "text"
					}
				],
				"body": {
					"mode": "file",
					"file": {}
				},
				"url": {
					"raw": "http://localhost:8080/book",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"book"
					]
				}
			},
			"response": []
		}
	]
}

3) Choose input file as below and send request
![image](https://github.com/baharaydogdu/java-test-bahar/assets/47500612/2a5f72d7-4447-4e17-b9fe-bbd5f45f41b3)

### Example Input - Output

Example Input:

![image](https://github.com/baharaydogdu/java-test-bahar/assets/47500612/3a0e04ba-51c6-41c9-9455-a6518f2f5d8d)


Example Output:

![image](https://github.com/baharaydogdu/java-test-bahar/assets/47500612/a536d54b-1755-42ed-b6db-7640592fed6c)


