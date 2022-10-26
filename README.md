
<p align="center">
<img src="./dash.svg" alt="" /> </p>
<h1> <li> Super Duber Drive Cloud Storage 🗂️ ☁️</li> </h1>
 <p align="center">
<img width="500" alt="Picturjje1" src="https://user-images.githubusercontent.com/90301688/198152161-b8884d21-7ef6-4de6-9d76-7ee476454e27.png" style="margin-left: 100px;">
 <br> <br> <br>  

<p>
 You have been hired by SuperDuperDrive, which is a brand new company aiming to make a dent in the Cloud Storage market and is already facing stiff competition from rivals like Google Drive and Dropbox. That hasn't dampened their spirits at all, however. They want to include personal information management features in their application to differentiate them from the competition, and the minimum viable product includes three user-facing features:     <li>Simple File Storage: Upload/download/remove files</li>
  <li>Note Management: Add/update/remove text notes </li>
  <li>Password Management: Save, edit, and delete website credentials.</li>
  SuperDuperDrive wants you to focus on building the web application with the skills you acquired in this course. That means you are responsible for developing the server, website, and tests, but other tasks like deployment belong to other teams at the company.
   </p>
   
   
<p align="center">
<h1> <li> Requirements and Roadmap 🛠️🔭</li> </h1>
<h1>The back-end </h1>
The Back-End
The back-end is all about security and connecting the front-end to database data and actions.

<li>Managing user access with Spring Security<li>
* You have to restrict unauthorized users from accessing pages other than the login and signup pages. To do this, you must create a security configuration class that extends the WebSecurityConfigurerAdapter class from Spring. Place this class in a package reserved for security and configuration. Often this package is called security or config.
* Spring Boot has built-in support for handling calls to the /login and /logout endpoints. You have to use the security configuration to override the default login page with one of your own, discussed in the front-end section.
* You also need to implement a custom AuthenticationProvider which authorizes user logins by matching their credentials against those stored in the database.
* Handling front-end calls with controllers
You need to write controllers for the application that bind application data and functionality to the front-end. That means using Spring MVC's application model to identify the templates served for different requests and populating the view model with data needed by the template.
* The controllers you write should also be responsible for determining what, if any, error messages the application displays to the user. When a controller processes front-end requests, it should delegate the individual steps and logic of those requests to other services in the application, but it should interpret the results to ensure a smooth user experience.
* It's a good idea to keep your controllers in a single package to isolate the controller layer. Usually, we simply call this package controller!
If you find yourself repeating tasks over and over again in controller methods, or your controller methods are getting long and complicated, consider abstracting some methods out into services! For example, consider the HashService and EncryptionService classes included in the starter code package service. These classes encapsulate simple, repetitive tasks and are available anywhere dependency injection is supported. Think about additional tasks that can be similarly abstracted and reused, and create new services to support them!
* Making calls to the database with MyBatis mappers
Since you were provided with a database schema to work with, you can design Java classes to match the data in the database. These should be POJOs (Plain Old Java Objects) with fields that match the names and data types in the schema, and you should create one class per database table. These classes typically are placed in a model or entity package.
* To connect these model classes with database data, implement MyBatis mapper interfaces for each of the model types. These mappers should have methods that represent specific SQL queries and statements required by the functionality of the application. They should support the basic CRUD (Create, Read, Update, Delete) operations for their respective models at the very least. You can place these classes in (you guessed it!) the mapper package.
</p>
