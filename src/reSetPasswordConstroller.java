import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class reSetPasswordConstroller extends passWordRecoveryController{

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Label displayEmailLabel;

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField newPassword;

    private static String userEmail;

    public static void setUserEmail(String email){
        userEmail = email;
    }

    @FXML
    void confirmBtn(ActionEvent event) {
        //call the forgotBtn method from the passordRecovery controller
        //implement logic for checking if the new password is matches
        String newPass = newPassword.getText();
        String confirm = confirmPassword.getText();
        if(newPass.equals(confirm)){
            EncryptionPassword hashPassword = new EncryptionPassword(confirm);
            try{
                UpdatePassword(userEmail,hashPassword);
            }catch(ClassNotFoundException ex){
                System.out.println("class does not exit");
            }catch(SQLException e){
                System.out.println("SQL expection was thrown");
            }

            try{
                //switch to sign in page
                Parent root = FXMLLoader.load(getClass().getResource("signIn_page.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
    }

    @FXML
    void signInBtn(ActionEvent event) {
        try{
            //switch to sign in page
            Parent root = FXMLLoader.load(getClass().getResource("signIn_page.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //method to retrieve userPassword 
    public void UpdatePassword(String email,EncryptionPassword hashPassword) throws ClassNotFoundException, SQLException{
        String HashedPassword = hashPassword.getEncryptPassword();
        String saltValue = hashPassword.getSalt();
        //change user password after verification and salt value 
        DBconnection newCon = new DBconnection();
        String sql = "UPDATE `bankapp_database`.`userinformation` SET `userPassword` = '" +HashedPassword+"' WHERE (`userEmail` = '"+email+"')";
        newCon.updateDB(sql);
        sql = "UPDATE `bankapp_database`.`userinformation` SET `saltValue` = '" +saltValue+"' WHERE (`userEmail` = '"+email+"')";
        newCon.updateDB(sql);
        //close connection
        newCon.closeConnection();
    }

}