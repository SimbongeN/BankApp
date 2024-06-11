# BankApp: A JavaFX Banking System

BankApp is a mobile application that simulates a real banking system. The application was built using Java and leverages JavaFX for the user interface, JDBC for database connectivity, and MySQL as the backend database. Through this project, I gained hands-on experience in implementing user authentication features, allowing users to securely log in using their credentials. I also learned about the intricacies of database management and the importance of robust security measures in financial applications and systems.I learnt concepts such as combination of hashing and salting techniques/algorithems in storing sensetive user information on database systems, In this application I used a SHA-3 (Secure Hashing algorithem) together with a salting technique for storing of user passwords. This project not only enhanced my technical skills but also provided me with valuable insights into the design and development of user-centric software solutions.

## Features Implemented

1. **User Authentication**:
    - Users can log in using their credentials.
    - Passwords are securely hashed and stored in the database.

2. **Account Management**:
    - Users can view their account details (balance, account number, etc.).
    - Transactions (deposits, withdrawals) are recorded.

3. **Database Integration**:
    - JDBC is used to connect to the MySQL database.
    - Database schema includes tables for users, accounts, and transactions.

## APP OVERVIEW 
### Landing page
![picture of application home page](App_pictures/landing.png)

### Sign in and Sign up pages
![picture of application home page](App_pictures/login.png)  ![picture of application home page](App_pictures/SignUp.png)

### Home and Transaction Pages
![picture of application adding task page](App_pictures/home.png)  ![picture of application adding task page](App_pictures/transctions.png)

## MySQL Tables

1. **userinformation**:
    - This table stores user-related information.
    - Fields include:
        - `user_id` (primary key)
        - `username`
        - `email`
        - `password` (hashed)
        - `saltValue`
        - Additional fields as needed (e.g., name, address)

2. **Account_information**:
    - This table holds account-specific details.
    - Fields include:
        - `account_id` (primary key)
        - `user_id` (foreign key referencing `userinformation`)
        - `account_number`
        - `balance`
        - `Transactions`

## How to Get Started

1. **Clone the Repository**:
    - Clone the BankApp GitHub repository to your local machine.

2. **Setup MySQL Database**:
    - Ensure you have MySQL installed.
    - Create a new database for BankApp.
    - Update the database configuration in the project (e.g., `application.properties`).

3. **Build and Run**:
    - Build the project using your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
    - Run the application.

4. **Contribute**:
    - If you'd like to contribute, feel free to fork the repository and submit pull requests.
    - Follow the coding conventions and best practices.

## Additional Notes

- Keep the README up-to-date as you make changes to the project.
- Provide clear instructions for setting up the environment, dependencies, and running the app.
- Include any relevant screenshots or diagrams if applicable.

Remember, a good README helps other developers understand your project, encourages collaboration, and makes your work stand out. Happy coding! 🚀
