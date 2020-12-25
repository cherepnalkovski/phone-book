# phone-book

Minimal phone book application

##Features
- create contact 
- get contact

## Run application

1. git clone https://github.com/cherepnalkovski/phone-book.git
2. cd phone-book 
   mvn clean install
3. Install postgresql
  - Create database named phonebook
  - Create postgre user : postgres with password : password
4. Run the executable jar file created under /targer folder with `java -jar phone-book-0.0.1-SNAPSHOT.jar`


## API Interface

| Endpoint        | Method | Description                 | Request Example                                                               | Response Example                                                                      |
|-----------------|--------|-----------------------------|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| /phonebook/contacts   | GET    | Returns filtered contacts.       | phonebook/contacts?name=vladimir&phoneNumber=+38970123456                                                                          | `[{"id": 1,"firstName": "Vladimir","phoneNumber": "+38970555888"}]`                                   |
| /phonebook/contacts   | POST   | Create a new contact.       | `{"name": "Vladimir", "phoneNumber": "+38970555888"}` | `{"id": 1,"firstName": "Vladimir","phoneNumber": "+38970555888"}` |

## Happy with
* Ability to use Singleton dependencies to create an in-memory data store.
* JavaScript serialization is as I'd like out of the box.
* Component based architecture, styling is done at component level.
* React side is speedy!
* Well structured projects.
* Included Delete functionality, not in spec.
