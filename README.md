# Redd Clone Back End

By Ariel Mendoza and Steven Huang

## Technologies Used

#### Languages

* Java
* PostgreSQL for testing purposes and initial database setup

#### Libraries and Frameworks

* Spring
* Hibernate
* Spring Security
* Bcrypt
* JSON Web Tokens

#### Tools

* Postman for route / endpoint testing
* Ecclipse
* Apache Tomcat 9 to run server
* Maven
* Pivotal Tracker for user stories
* Draw.io for ERD

## Installation 

To run server:
* Fork and clone the repository
* Import the project into your IDE as a Maven project
* Run the server from your IDE

## Approach

We began the project using Pivotal Tracker to write out our user stories. Once we had completed them, we sketched out our entities for the project and how we thought we would best handle relationships. Completing that, we made our initial ERD on draw.io. Once the ERD was completed, we moved onto setting our timelines and expected deliverables. Satisfied with our planning to that point, we began to code.

For the majority of the project, we coded using paired programming during class hours. For after class hours, we split our work based on entities so that we could avoid branch conflicts as much as possible, which we did for the most part. We followed the same convention once we moved onto testing as is reflected by our branches on Git. We were careful to check out branches as we built out the project so as to keep master as pristine as possible while we implemented new features.  

## Challenges

Descriptions of any major hurdles you had to overcome.

## Unsolved Problems

* While the post deletion endpoint works, it wasn't implemented on the front end during the first project, so that issue persists

## User Stories

[Pivotal Tracker](https://www.pivotaltracker.com/n/projects/2407490)

## ERD

<img src="./Project 2 ERD.png" alt="erd" />


## Deliverables and Timeline

|	Timeline	|	Deliverables	|
|	-----------	|	-----------	|
|	10/21 Mon	|	Planning, ERD and User Stories	|
|	10/22 Tue	|	Create, Read All, Read One, Delete Posts, Create, Read All (testing), Read One, Delete User	|
|	10/23 Wed	|	Update Post, Create Comments, Read Comments by Post, Read Comments by User, Delete Comments	|
|	10/24 Thur	|	Authentication and Authorization with JWT, Create User Profile, Update User Profile,	|
|	10/25 Fri	|	Unit Testing and Debugging	|
|	10/26 Sat	|	Unit Testing, Debug, Refactor	|
|	10/27 Sun	|	Feature Freeze, Work On Presentation	|
