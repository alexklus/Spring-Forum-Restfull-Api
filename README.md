# Spring-Forum-Restfull-Api
A basic forum build with spring-boot, spring security, hibernate, JPA and PostgreSQL
# SPRING RESTFUL API FORUM WITH SPRING BOOT, SPRING SECURITY, JPA, HIBERNATE, JWT 

Simple secured blog with option to sign-up, login, add topics, comment topics and like comment.

## Requirments 
1. JAVA 1.8
2. MAVEN
3. PostgreSQL / MySQL
-------------------------------
```
Aplication.properties

spring.main.banner-mode=off
logging.level.org.springframework=ERROR

server.port=

spring.datasource.url=

spring.datasource.username=

spring.datasource.password=

spring.jpa.show-sql=false

spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database=postgresql

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect

spring.jpa.hibernate.ddl-auto=update

spring.thymeleaf.enabled=false

jwt.expiration.time=900000
```
----------------------------

## Auth

URL | Method | Description | 
----|--------|-------------|
api/auth/login| POST | LOGIN | 
api/auth//signup | POST| REGISTRATION |
api/auth/refresh/token| POST | REFRASH EXPIRED TOKEN |
api/auth/logout | POST | LOGOUT 
api/auth//resetpassword | POST | RESET PASSWORD



## Topics
URL | Method | Description |
----|--------|-------------|
api/topics | POST | ADD NEW TOPIC |
api/topics?page={pageNumber}&size={pageSize}| GET | GET PAGINATED TOPICS
api/topics/{id} | GET | GET TOPIC BY ID|
api/topics/{id} | DELETE | DELETE TOPIC |
api/topics/{id} | PUT | UPDATE TOPIC



## Comments
URL | Method | Description |
----|--------|-------------|
/api/topics/{topicId}/comments | POST | ADD NEW COMMENT
/api/topics/{topicId}/comments?page={pageNumber}&size={pageSize} | GET | GET PAGINATED COMMETNS 
/api/topics/{topicId}/comments/{commentId} | PUT | EDIT CURRENT COMMENT 
/api/topics/{topicId}/comments/{commentId} | DELETE | DELETE CURRENT COMMENT 
/api/topics/{topicId}/comments/{commentId} | GET | GET COMMENT BY ID
/api/topics/{topicId}/comments/{commentId} | PUT | EDIT CURRENT COMMENT 
/api/topics/{topicId}/comments/{commentId}/like | POST | ADD LIKE TO COMMENT
/api/topics/{topicId}/comments/{commentId}/dislike | POST | REMOVE LIKE



## Edit profile 
URL | Method | Description |
----|--------|-------------|
api/editprofile/username | PUT | CHANGE USER NAME 
api/editprofile/email | PUT | CHANGE EMAIL ADRESS
api/editprofile/password/ | PUT | CHANGE PASSWORD 
api/editprofile/avatar/ | POST | UPLOAD AVATAR


-----------------------------------------------------------------
# Json valid Request bodies
#### LOGIN
```
url:  api/auth/login                          
                                              
{
    "username":"test1",
    "password":"test2"
}

Response: 
{
    "authenticationToken": "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYxNzIxODIxNSwiZXhwIjoxNjE3MjE5MTE2fQ.Phu5UedWAWoWNsha-ismp78f0aaWyEp7vQ5noQ1GuSDeijecYMbKS0e9bduPVyxA-sAlmKuWJBKd23Bm2dc6nP4-8uu7LDXcBGPa8D6hF-f8RIW-6mMiIIXrSTCKg66A39t8mP34EK7KlBre3bXAm5_sUL92zqMQke77CdGn4dPuR6mK2DyVVXeD145dMKZESrBvopSFxyxgksFzMODPBw9y7Fx9KDWGvHhkHXlZrL-2ZS_ScVVBnm_4kvwj1zRWFSuUUR8VJ0-1pLeMqoQ4595r9RDZuInYoWie-csgTFf9AqZ4iqQLgXawPZ1ZXzcPkQ63L4ieAfV4AkxVkAF0Fw",
    "username": "test1",
    "expiresAt": "2021-03-31T19:31:56.207Z",
    "refreshToken": "e3f79256-3a35-4da6-84e6-9108d28d23bf"
}

```
#### SIGNUP 
```
url: api/auth/signup
{
    "username":"test455",
    "email":"test7554@gmial.com",
    "password": "test5",
    "passwordConfirmation":"test5"
}

Response: 
User Register successfully!
```

#### LOGOUT
```
url: api/auth/signup/logout
{
    "refreshToken":"860ab299-87c0-4246-a405-bebc61145cf4",
    "userName":"test1"
}
Response: 

Refresh Token Deleted Successfully!!
```

#### EDIT USERNAME
```
url: api/editprofile/username

{
    "currentUserName":"test1",
    "newUserName": "newUserNbla"
}

Response: 
{
    "id": 1,
    "userName": "newUsername",
    "email": "test1@gmail.com"
}
```
#### FORGOT PASSWORD
```
url: api/editprofile/password
{
    "userName": "test2",
    "email": "test2@gmail.com"
}
Response: 
NEW PASSWORD SENDED TO YOUR EMAIL!
```
#### EDIT EMAIL
```
url: api/editprofile/email
{
    "currentEmail":"test1@gmail.com",
    "newEmail": "newemail@gmail.com"
}
Response: 
{
    "id": 1,
    "userName": "test1",
    "email": "newemail@gmail.com"
}
```

#### GET PAGINETE TOPICS
```
api/topics?page={pageNumber}&size={pageSize}
{

}
Response: 
{
    "content": [
        {
            "id": 2,
            "topic": "test",
            "author": "test1"
        },
        {
            "id": 3,
            "topic": "test",
            "author": "test1"
        }
    ],
    "page": 0,
    "size": 2,
    "totalElements": 2,
    "lastPage": true
}
```

#### GET TOPIC
```
url: api/topics/{topiId}
{

}

Response:
{
    "id": 2,
    "topic": "test",
    "author": "test1"
}
```

#### UDATE TOPIC
```
url: api/topics/{topiId}
{
  "id": 1,
  "content" : "new content"
  "username" : "username"
}

Response:
{
    "id": topic if,
    "topic":  topic body ,
    "author": "author of the topic "
}
```

### GET PAGINATED COMMENT BY TOPIC ID
```
url: /api/topics/2/comments?page={page > 0}&size={size of page}
{

}

Response:
{
    "content": [
        {
            "id": 1,
            "content": "title",
            "topicId": 2,
            "author": "test1",
            "likes": 0
        },
        {
            "id": 2,
            "content": "title",
            "topicId": 2,
            "author": "test1",
            "likes": 0
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 2,
        "unpaged": false,
        "paged": true
    },
    "last": false,
    "totalElements": 5,
    "totalPages": 3,
    "size": 2,
    "number": 0,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```

### EDIT COMMENT 
```
url: /api/topics/2/comments{commentId}
{
  "id":"id"
  "content": "new content"
  "username": "author of the comment"
}

DELETE AND GET IT THE SAME TO NEED BODY!
