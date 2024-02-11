# BankApp: A JavaFX Banking System

BankApp is a mobile application that simulates a real banking system. It's built using Java and leverages JavaFX for the user interface, JDBC for database connectivity, and MySQL as the backend database.

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

## Features to Be Implemented

1. **Forget Me Button**:
    - Implement the "Forget Me" functionality.
    - When users click this button, they should receive an email with instructions on how to delete their account or personal data.

2. **Remember Me (Password Method)**:
    - Allow users to choose the "Remember Me" option during login.
    - Implement session management to keep users logged in across sessions.

3. **Terms and Conditions (TnC) Page**:
    - Create an FXML page that displays the terms and conditions.
    - Users should be able to view and accept the TnC before using the app.

## MySQL Tables

1. **userinformation**:
    - This table stores user-related information.
    - Fields include:
        - `user_id` (primary key)
        - `username`
        - `email`
        - `password` (hashed)
        - Additional fields as needed (e.g., name, address)

2. **Account_information**:
    - This table holds account-specific details.
    - Fields include:
        - `account_id` (primary key)
        - `user_id` (foreign key referencing `userinformation`)
        - `account_number`
        - `balance`
        - Additional fields as needed (e.g., account type, transaction history)

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
