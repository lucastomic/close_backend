# Close Guide 
This document serves as a guide to the libraries used in our project, and provides instructions on how to use them
effectively. It is intended for developers who are new to the project, or who require guidance on how to use the
libraries in the most efficient and effective way.

The guide is divided into sections, with each section focusing on a different library. Each section includes a brief
overview of the library, its main features and functionality, and practical examples of how to use it in the project.

The aim of this guide is to provide a consistent approach to using the libraries in our project, ensuring that all
developers are familiar with their capabilities, and that best practices are followed throughout the development
process. By following the guidelines outlined in this document, we can ensure that our project is well-structured,
maintainable, and scalable.

Please note that this document is subject to change as new libraries are added to the project, or as existing libraries
are updated. We encourage all developers to refer to the latest version of this guide to ensure that they are following
the most up-to-date guidelines.

## Spring

### About 
Spring is a lightweight, open-source framework for developing Java-based enterprise applications. The framework is
characterized by its inversion of control (IoC) and aspect-oriented programming (AOP) features, which provide a high
degree of modularity, separation of concerns, and extensibility.

At its core, Spring implements the IoC design pattern, which involves delegating the creation and management of objects
to a container, rather than to the objects themselves. The container maintains a registry of objects, or beans, and
their dependencies, and is responsible for instantiating and wiring them together at runtime. This approach allows
developers to create loosely coupled and highly testable code, as well as to easily swap out components or switch
between different configurations.

AOP, on the other hand, provides a way to encapsulate cross-cutting concerns, such as logging, security, and transaction
management, into modular units called aspects. Aspects can be applied to any number of objects or methods, without
requiring the objects or methods themselves to be modified. Spring implements AOP using dynamic proxies or byte code
instrumentation, depending on the target platform, and provides a range of pointcut expressions and advice types for
customizing the behavior of aspects.

In addition to its core features, Spring provides a comprehensive set of modules and extensions that cover a wide range
of enterprise concerns, including web development, data access, security, messaging, and integration. For example,
Spring MVC is a module that provides a web application framework based on the model-view-controller (MVC) architectural
pattern, while Spring Data is a module that provides a consistent API for working with various data stores, such as
relational databases, NoSQL databases, and key-value stores. Similarly, Spring Security is a module that provides a
powerful and flexible security framework for enterprise applications, with features such as authentication,
authorization, and access control.

Overall, Spring is a highly modular, flexible, and extensible framework that offers developers a rich set of tools and
techniques for building robust and scalable enterprise applications in Java. Its emphasis on modularity, separation of
concerns, and convention over configuration makes it well-suited for complex and rapidly changing environments, while
its support for industry standards and best practices ensures compatibility and interoperability with a wide range of
tools and platforms.

### Controllers

A controller in a web application is responsible for handling HTTP requests from clients (such as browsers or mobile
devices) and returning appropriate responses. Controllers act as a bridge between the presentation layer (the user
interface) and the service layer (where the business logic and data access happens).

The primary role of a controller is to receive incoming requests, extract any necessary information from the request
(such as query parameters or request body), and pass that information to the appropriate service or business logic layer
to perform the required operation (such as creating a new user or retrieving a list of products).

After the service layer completes its operation, the controller typically generates an HTTP response by formatting the
output from the service layer into a format that can be consumed by the client, such as JSON or HTML. The controller is
also responsible for setting the appropriate HTTP status codes and headers to provide additional information to the
client.

Good controller design involves separating presentation logic from business logic, following the Single Responsibility
Principle, and being easy to test and maintain. By properly separating concerns, controllers can be made more modular
and reusable, leading to cleaner and more maintainable code.



### Services

A service class is responsible for providing high-level business logic and functionality for an application.
It acts as a middle layer between the controllers (or endpoints) and the data access layer, providing a way
to encapsulate complex logic and functionality in a reusable and maintainable manner.

Service classes typically perform operations that are related to a specific domain or use case of the application.
For example, in a user management system, a UserService might provide methods for creating, retrieving, updating,
and deleting user accounts, as well as methods for performing various user-related operations such as authentication
and authorization.

Service classes should adhere to the single responsibility principle and should not contain any presentation logic.
They should be stateless and their methods should return data transfer objects or entities that can be used by
the controllers to generate HTTP responses. Service classes should also be designed to be easily testable and
should follow best practices such as dependency injection to make them more maintainable and extensible.


### Repositories

A repository is a design pattern that provides an abstraction layer between the application code and the data access
layer. The primary responsibility of a repository is to encapsulate the logic required to interact with a specific type
of data storage, such as a relational database or a document store.

In a typical implementation, a repository provides methods for performing CRUD (Create, Read, Update, Delete) operations
on the data it manages. For example, a UserRepository might have methods for creating a new user account (if simple),
retrieving a user by their ID or email address, updating a user's profile information, or deleting a user account.

Repositories should adhere to the Single Responsibility Principle, meaning that they should be responsible for managing
data access for a specific entity or domain object. They should also be designed to be easily testable, using mock or
in-memory implementations for testing purposes.

Good repository design involves separating data access concerns from business logic, following best practices for
database interaction (such as using parameterized queries to prevent SQL injection), and being designed to be easily
extensible in the future.


### Services vs Repositories

A service is responsible for encapsulating business logic and coordinating actions between different parts of an
application. It typically exposes a set of methods that can be called by other parts of the application to perform
specific tasks or operations.

A repository, on the other hand, is responsible for encapsulating data access logic and providing an abstraction layer
over the database or data store. It typically exposes a set of methods that can be called by other parts of the
application to interact with the data it manages.

In general, services are higher-level abstractions that orchestrate multiple components and coordinate business logic,
while repositories are lower-level abstractions that handle data access and provide a more direct interface to the
database or data store.

Services and repositories often work together to provide a complete solution for an application. Services use
repositories to access and manipulate data, while repositories provide the underlying data storage and retrieval
functionality that services rely on. By separating these concerns, services and repositories can be designed, tested,
and maintained more easily and independently.

## Interface

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


