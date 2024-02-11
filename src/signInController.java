/**
 * Sample Skeleton for 'signIn_page.fxml' Controller Class
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

public class signInController {

    @FXML // fx:id="Email_Input"
    private TextField Email_Input; // Value injected by FXMLLoader

    @FXML // fx:id="ErrorMassage_Label"
    private Label ErrorMassage_Label; // Value injected by FXMLLoader

    @FXML // fx:id="passowrdInput"
    private PasswordField passowrdInput; // Value injected by FXMLLoader

    @FXML // fx:id="rememberBtn"
    private RadioButton rememberBtn; // Value injected by FXMLLoader

    @FXML
    void forgotPassword_button(ActionEvent event) {

    }

    @FXML
    void signIn_button(ActionEvent event) throws IOException{

        String userEmail = Email_Input.getText();
        String userPass = passowrdInput.getText();
         
        if(userEmail.isEmpty() || userPass.isEmpty()){
            ErrorMassage_Label.setText("Incorrect password or email");
        }

        try {
            
            //user Authentication process
            //checking userPassword 
            String sql = " Select userPassword from userinformation where userEmail = '"+userEmail+"'";
            DBconnection createConnection = new DBconnection();
            ResultSet rs = createConnection.getUserInfor(sql);
            String password = null;
            while(rs.next()){
                System.out.println(rs.getString(1));
                String result = rs.getString(1);
                password = result;
            }
            rs.close();
            createConnection.closeConnection();
            
            //if password is correct grent access
            if(password.equals(userPass)){

                //retrieving user information and account number 
                //retrieving user name
                sql = " Select userName from userinformation where userEmail = '"+userEmail+"'";
                // st = con.prepareStatement(sql);
                createConnection = new DBconnection();
                rs = createConnection.getUserInfor(sql);
                String userName = ""; 
                while (rs.next()) {
                    String result = rs.getString(1);
                    userName = result;
                }
                rs.close();
                createConnection.closeConnection();

                //retrieving user Account number 
                sql = " Select userAccNo from userinformation where userEmail = '"+userEmail+"'";
                createConnection = new DBconnection();
                rs = createConnection.getUserInfor(sql);
                int userAccNo = 0;
                while (rs.next()) {
                    int result = rs.getInt(1);
                    userAccNo = result;
                }
                rs.close();
                createConnection.closeConnection();

                //retrieving user balance
                sql = " Select acc_balance from account_information where AccNo = "+userAccNo;
                // st = con.prepareStatement(sql);
                createConnection = new DBconnection();
                rs = createConnection.getUserInfor(sql);
                int userBalance= 0;
                while (rs.next()) {
                    int result = rs.getInt(1);
                    userBalance = result;
                }
                createConnection.closeConnection();

                //create userInformation and AccountInformation objects
                userInformation getUser = new userInformation(userName, userEmail, userPass, userBalance);
                getUser.setAccNumber(userAccNo);
                AccountInformation userAcc = new AccountInformation(getUser); 

                //send user information and user Account to home page 
                home_Controller.getUserAcc(getUser, userAcc);

                //grent access
                //switch to home page
                Parent root = FXMLLoader.load(getClass().getResource("home_page.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                ErrorMassage_Label.setText("Incorrect password or email");
                throw new IllegalAccessError("Unable to grent access");
            }
            

        } catch (Exception e) {
            
            ErrorMassage_Label.setText("Incorrect password or email"); 
            e.printStackTrace();
        }

        
    }

    @FXML
    void signUp_redirect(ActionEvent event) throws IOException {

        //switch to sign up page
        Parent root = FXMLLoader.load(getClass().getResource("Sign_up_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
