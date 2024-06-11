import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;


public class passWordRecoveryController {

    @FXML
    private TextField Email_Input;
    
    @FXML
    private Label errorMessage;

    protected int vericationcode;
    protected String userEmail;
    
    @FXML
    void RetrivePWDBtn(ActionEvent event) throws IOException {
        //send an email verification code
        this.userEmail = Email_Input.getText();
        if(emailExistInDB(userEmail) == false || userEmail.isEmpty()){
            errorMessage.setText("Invalid email"); 
        }else{
            //send email to verification class
            verificationController.setVerificationCode(userEmail);
            //send email to the retPassword controller
            reSetPasswordConstroller.setUserEmail(userEmail);
            //switch to sign in page
            Parent root = FXMLLoader.load(getClass().getResource("verification.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }   
    }

    @FXML
    void backBtn(ActionEvent event) throws IOException {
        //switch to sign in page
        Parent root = FXMLLoader.load(getClass().getResource("signIn_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean emailExistInDB(String email){
        boolean emailValid = false;
        try {
            DBconnection newCon = new DBconnection();
            String sql = " Select userEmail from userinformation where userEmail = '"+email+"'";
            ResultSet response = newCon.getUserInfor(sql);
            if(response.next())
                emailValid = true;
            response.close();

            //close connection
            newCon.closeConnection();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return emailValid;
    }

    public static void sendEmail(String recpientName, String RecipientEmail,int  verificationCode) {

        Email email = new Email();
    
        email.setFrom("no-reply@EazeBank.com", "MS_DJV5Jb@trial-pq3enl6y3prg2vwr.mlsender.net");
        email.addRecipient(recpientName, RecipientEmail);

        email.setSubject("Verification Code");
        email.setPlain("This is the text content: testing code 3034");
        email.setHtml("<h1> Verification Code</h1>");
        email.setHtml("<h3>This is your verification code do not show it to anyone "+verificationCode+" and is only valid for 5 minutes</h3>");
    
        MailerSend ms = new MailerSend();
        ms.setToken("mlsn.ef07e653196cfaca5623061fb5f1a330226ec9245c6d0cab7dad5f67cf454ce4");
    
        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
    
            e.printStackTrace();
        }
    }

    public String generateVerificationCode(){
        //generate verification code
        String code = "";
        Random randomNum = new Random();
        int codelength = 5;
        for (int i = 0; i<codelength;i++){
            int num1 = randomNum.nextInt(0,9);
            code += num1;
        }
        System.out.println(code);
        return code;
    }


} 
