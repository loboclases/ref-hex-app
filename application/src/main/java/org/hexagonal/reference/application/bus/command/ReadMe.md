Interfaces in this package should be put in a shared kernel jar so it is reusable in all the bounded context

However, due to didactic purposes, it is put as a separate package in the application layer 

additionally, a command SHOULD NEVER RETURN a value. Instead, there are some alternative depeding on the use case:
* Using mediator/pub sub pattern
* Pre generate the id before invoking the service that creates a resource...

at the end, it will not be possible to create a resource and return the just created resource in one go
However, it is possible to make a call to retrieve and ID in the screen where the user inserts all the data and then send all the data including the ID
This approach could lead to problems with the ORM. 

Think twice before using Command Bus, and in case of doubts, use a simple application service