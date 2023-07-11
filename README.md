<p align="center">
<img src="images/ATM-APP.png">
<p>
<p align="center">
<img src="images/atm.png">
<p>

Project description
----

### *This is a simple ATM application written in Java using Spring Boot. ATM-app supports authentication and authorization. All CRUD operations are implemented in application*

ATM features in ATM application :
----
- The ATM deposit and withdraws amounts in multiples of 100, 200, 500 banknotes;

Admin features in ATM application :
----
- The admin can create an ATM;
- The admin can replenish the ATM;
- The admin can get information about which banknotes are in the ATM;
- The admin can add role to User.

User features in ATM application :
----
- Register;
- Authorization;
- The user can create a bank account;
- The User can deposit money to a bank account via ATM;
- The User can withdraw money from bank account via ATM;
- The User can transfer money from one bank account to another bank account.

The application is developed in N-Tier Architecture
----
- Presentation tier, controller layer.
- Logic tier, service layer;
- Data tier, repository layer;

For easy testing, you can use the following instruction
----
- To register a user, you need to send a POST-request to: http://localhost:8080/register with the body {"username": "Vitalii", "password": "1234"}. Content-Type: application/json;
- To Authorization a user, you need to send a POST-request to: http://localhost:8080/login with the body {"username": "Vitalii", "password": "1234"}. Content-Type: application/json;
### To make the following requests, you must be logged in as an administrator
- To create an ATM, you need to send a POST-request to: http://localhost:8080/atm/create with the body {"banknotes": []}. Content-Type: application/json;
- To replenish the ATM, you need to send a POST-request to: http://localhost:8080/atm/add-money/atm/1 with the body [{"value": 100}, {"value": 100}, {"value": 200}, {"value": 200}, {"value": 500}, {"value": 500}]. Content-Type: application/json;
- To get information about which banknotes are in the ATM, you need to send a GET-request to: http://localhost:8080/atm/get-all-banknotes/1
- To add role to User, you need to send a PUT-request to: http://localhost:8080/user/add-role-to-user/3/role/ADMIN
### To make the following requests, you must be logged in as a user
- To create a bank account, you need to send a POST-request to: http://localhost:8080/bank_account/create/user/3
- To deposit money to his bank account, you need to send a PUT-request to: http://localhost:8080/atm/deposit/atm/1/bank-account/1 with the body [{"value": 100}, {"value": 200}, {"value": 500}]. Content-Type: application/json;
- To withdraw money from bank account, you need to send a PUT-request to: http://localhost:8080/atm/withdraw/atm/1/bank-account/1/amount/300
- To transfer money from one bank account to another bank account, you need to send a PUT-request to: http://localhost:8080/transaction/transfer-money/from-account/1/to-account/2/amount/500

### *If you will be using Postman for testing, you can also use:*  
https://red-moon-54652.postman.co/workspace/Team-Workspace~cfed11ce-5b08-4497-872f-e9df998fc0f2/collection/25447589-67276792-eee2-4d2e-9568-3761f61302e3?action=share&creator=25447589

Technologies
----
- Java 11.0.12 2021-07-20 LTS
- Apache Maven 3.8.1
- Spring 
  - Boot
  - Core
  - Security
  - Web MVC
- Junit 5
- Mockito
- Lombok
- MapStruct
- Swagger http://localhost:8080/swagger-ui.html
