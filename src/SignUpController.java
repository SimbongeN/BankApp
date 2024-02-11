/**
 * Sample Skeleton for 'Sign_up_page.fxml' Controller Class
 */

import java.io.IOException;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML // fx:id="ErrorMassage_label"
    private Label ErrorMassage_label; // Value injected by FXMLLoader

    @FXML // fx:id="TnC_RadioBution"
    private RadioButton TnC_RadioBution; // Value injected by FXMLLoader

    @FXML // fx:id="email_input"
    private TextField email_input; // Value injected by FXMLLoader

    @FXML // fx:id="name_Input"
    private TextField name_Input; // Value injected by FXMLLoader

    @FXML // fx:id="password_input"
    private PasswordField password_input; // Value injected by FXMLLoader

    @FXML
    void Display_TnC_infor(ActionEvent event) {

    }

    @FXML
    void signIn_Button_redirect(ActionEvent event) throws IOException{

        //switch to sign in page 
        Parent root = FXMLLoader.load(getClass().getResource("signIn_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void submit_SignUp(ActionEvent event) throws IOException{

        String name = name_Input.getText();
        String email = email_input.getText();
        String password = password_input.getText();

        //initial balance given to user for creating account
        double balance = 50;

        try {

            //check whether user exit in database
            boolean checkUser = CheckDataBase(email,name);
            if(checkUser == true){
                throw new IllegalStateException();
            }

            //create user profile
            userInformation newUser = createUserProfile(name, email, password, balance);
            AccountInformation newAccount = createAccount(newUser);

            //send userInformation
            home_Controller.getUserAcc(newUser,newAccount);
            
            //save information to database
            savetoDatabase(newUser,newAccount);

            //switch to home page
            Parent root = FXMLLoader.load(getClass().getResource("home_page.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        }catch(IllegalStateException ex){
            ErrorMassage_label.setText("User name or email already exist! SignIn");
            ex.printStackTrace();
        }catch (Exception e) {
            
            ErrorMassage_label.setText("Invalid Inputs Fields");
            e.printStackTrace();
        }

    }

    //create user profile 
    public userInformation createUserProfile(String name, String email, String password, double balance){

        userInformation newUser = new userInformation(name,email,password,balance);
        return newUser;
    }

    public AccountInformation createAccount(userInformation newUser){
         //create user account
        AccountInformation userAcc = new AccountInformation(newUser);
        return userAcc;
    }

    //method to save user to database
    public void savetoDatabase(userInformation newUser, AccountInformation userAcc) throws IllegalArgumentException{

        String userName = newUser.getName();
        String userEmail = newUser.getEmail();
        String userPassword = newUser.getPassword();
        int userAccoNo = userAcc.getAccNumber();
        int IdCount;

        
        try {
            //saving newuser to database 
            
            String sql1 = "Select MAX(userID) as userID from userinformation;";
            DBconnection createCon = new DBconnection();
            ResultSet rs = createCon.getUserInfor(sql1);
            int IdCount2 = 0;
            while(rs.next()){    
                IdCount2 = Integer.parseInt(rs.getString(1));
                System.out.println(IdCount2);
            }
            createCon.closeConnection();
            IdCount = IdCount2+1;
            createCon = new DBconnection();
            String sql = "INSERT INTO `bankapp_database`.`account_information` (`userName`, `AccNo`, `acc_balance`)" + " VALUES "+"('" + userName + "','" +userAccoNo +"','" + 50 +"')";
            createCon.updateDB(sql);

            sql = "INSERT INTO `bankapp_database`.`userinformation` (`userID`, `userName`, `userEmail`, `userAccNo`, `userPassword`) VALUES " + "('" + IdCount +"', '"+ userName +"', '"+ userEmail +"', '"+ userAccoNo +"', '"+ userPassword +"');";
            createCon.updateDB(sql);

            
            createCon.closeConnection();

        } catch(SQLException ex){
            ex.printStackTrace();
            throw new IllegalAccessError();
        } catch (Exception e) {

            e.printStackTrace();
            throw new IllegalAccessError(); 
        }

    }

    //check user method 
    public boolean CheckDataBase(String userEmail, String username){
        boolean userExit = false;

        try{
            String sql = "Select userEmail from userinformation where userName = '"+username+"'";
            DBconnection createConnection = new DBconnection();

            ResultSet rs = createConnection.getUserInfor(sql);
            if(rs.next()==true){
                userExit = true;
            }
            rs.close();

            sql = "Select userName from userinformation where userEmail = '"+userEmail+"'";
            rs = createConnection.getUserInfor(sql);
            if(rs.next()==true){
                userExit = true;
            }
            createConnection.closeConnection();
        } catch (Exception e) {
            //handle exception
            e.printStackTrace();
        }
        return userExit;
    }

}