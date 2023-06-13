# close_backend

## Interface

### Get ducks received
`GET /users/ducks/received`

return a collection of the users who have sent a duck to the user currently authenticated

### Get ducks sent
`GET /ducks/sent`

Retrieves the ducks which has been sent by the user who is currently authenticated

### Delete the currently authenticated user
`DELETE /users`

Deletes the user who is currently authenticated

### Add an interest to a User
`PUT /users/addInterest/{interestName}`

| Name         | Type   | Required | Description                                  |
|--------------|--------|----------|----------------------------------------------|
| interestName | String | Yes      | Name of the interest to remove from the user |

Removes an interest to the User currently authenticated. If the interest specified doesn't
exist already, it returns an error.

### Remove an interest from a User
`PUT /users/addInterest/{interestName}`

| Name         | Type   | Required | Description                             |
|--------------|--------|----------|-----------------------------------------|
| interestName | String | Yes      | Name of the interest to add to the user |

Adds an interest to the User currently authenticated. If the interest specified doesn't
exist already, it creates it on the database.

### Authenticate
`POST /authenticate`

| Name | Type                  | Required | Description                           |
|------|-----------------------|----------|---------------------------------------|
| body | AuthenticationRequest | Yes      | Username and password to authenticate |


Authenticates the user on the system. If the credentials are right it returns the Token 
to authenticate the next requests

This is how the body of the request must look like

```
{
    "username":"myUsernameToLogIn",
    "password":"mySecretPassword"
}
```

### Register
`POST /register`

| Name | Type | Required | Description           |
|------|------|----------|-----------------------|
| body | User | Yes      | User to be registered |

Registers a user in the system, given their information.

This is how the body of the request must look like

```
{
  "profileName":"Enzo",
  "username":"enzoFernandez5",
  "age":22,
  "phone":"624 423 123",
  "password":"secretPassword",
}
```



Get the Ducks which have been sent to the currently authenticated user

### Send duck 
`POST /users/ducks/send`

| Name       | Type | Required | Description                       |
|------------|------|----------|-----------------------------------|
| receiverId | Long | Yes      | User who the Duck will be sent to |

Sends a duck from the user currently authenticated to another one

### Delete duck sent
`DELETE /users/ducks/reclaim`

| Name       | Type | Required | Description                   |
|------------|------|----------|-------------------------------|
| receiverId | Long | Yes      | User who the Duck was sent to |

Deletes a duck which has been sent before, from the user currently authenticated to the onw with the ID specified


### Create a new interest
`POST /interest`


This is how the body of the request must look like
```
{
    "name":"chess"
}
```
Creates a new interest, given its name

### Delete an interest
`DELETE /interest/{ineterestName}`


Deletes an interest, given its name

### Get user information
`GET /getUserInfo`

Returns the information about the user currently authenticated

### Get users close to a location
`GET /users/search/{latitude},{longitude},{radius}`

| Name       | Type   | Required | Description                      |
|------------|--------|----------|----------------------------------|
| latitude | double | Yes      | latitud to look close users from |
| longitude | double   | Yes      | longitude to look close users from    |
| radius | double   | Yes      | radius to look close users from    |

Searches for users within a certain radius from a given latitude and longitude

### Get close users
`GET /users/{userId}/close/{radius}`

| Name   | Type   | Required | Description                   |
|--------|--------|----------|-------------------------------|
| radius | double | Yes      |The radius within which to search for users  |

Searches for users within a certain radius from the authenticated user

### Send location
`POST /users/{userId}/location`

This is how the body of the request must look like
```
{
    "latitude":39.49513035722328,
    "longitude":-0.3328990255544027
}
```

Sends the location of the authenticated user to the server

