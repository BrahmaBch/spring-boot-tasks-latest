## Spring Boot Rest APIs for uploading and download Files to Database
#### Our Spring Boot Application will provide APIs for:
######  --> uploading File to PostgreSQL
######  --> downloading File database with the link
######  getting list of Files’ information (file name, url, type, size)
#### These are APIs to be exported:
######  Methods	Urls	Actions
######      POST	/upload	upload a File
######      GET	/files	get List of Files (name, url, type, size)
######      GET	/files/[fileId]	download a File
      
## Technology
  Java 11
  Spring Boot 3 (with Spring Web MVC)
  PostgreSQL
  Gradle 7.6.1
