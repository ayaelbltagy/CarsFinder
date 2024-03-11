1#. CarsFinder
is a task required for skills approval for Gen. C Android Interview.


#2. Task description
Suppose you have a car booking api that returns a list of Cars available for booking. The response is returned as in the attached files:
Create an android project that handles this api call (use a mock api,or just put the files in the project files and handle it (fake the response from the file))
As a user, I want to be able to open the main search screen, and search for a car with it’s price and colour.
If there’s a match, I need to see a list of cars. When I click on any of the list items, I need to see all the details of that car.
If there’s no match, I need to know that there’s no car available with this criteria.
All search options are optional, If I didn’t select any, I need to see a list of all the available cars. App it will be two screen home screen and details


#3. Architecture
- In previous section, the challenge of task which add json file and read it as a static data so I depend on Modulations and DI to inject this data for view model from use case 
- Repository is responable for get source of data which in our case is json file.
About Design pattern :
MVVM -> to separates views from business logic.
Kotlin coroutines -> to simplify code that executes asynchronously.
Dagger hilt -> for DI
Jetpack compose and Material Design 3 -> for ui design

#4. unit tests
- view model testing
