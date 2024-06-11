import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class verificationController extends passWordRecoveryController implements Initializable{

    @FXML
    private Label displayEmailLabel;

    @FXML
    private TextField veriCode1;

    @FXML
    private Label errorLabel;

    @FXML
    private Label timer_Label;

    @FXML
    private Button resendBtn;

    private  final int RESEND_EMAIL_DELAY = 30 * 1000; // 30 seconds
    private  final int VERIFICATION_CODE_EXPIRY = 5 * 60 * 1000; // 15 minutes

    public static String userEmailPass;

    public static String btnClicked;

    public static userInformation user;
    public static AccountInformation userAcc;

    public static void setBtnClicked(String btnClick){
        btnClicked = btnClick;
    }
    public static void setVerificationCode(String email){
        userEmailPass = email;
    }

    public static void setUserInfo(userInformation user1, AccountInformation userAcc1){
        user = user1;
        userAcc = userAcc1;
    }
    /*
     * testing if verification works 
     * replace this with prompting user to enter a new password if cerification code is legit
     */
    @FXML
    void verifyBtn(ActionEvent event) {
        String userCode = veriCode1.getText();
        System.out.println(userCode);
        System.out.println(super.vericationcode);
        if((userCode.isEmpty())){
            errorLabel.setText("Invalid code");
        }else{
            int userCodeInt = Integer.parseInt(userCode);
            if(super.vericationcode == userCodeInt){
                System.out.println(vericationcode);
                if(btnClicked.equals("forgotBtn")){
                    try {
                        //switch to resetPassword in page
                            Parent root = FXMLLoader.load(getClass().getResource("reSetPassword.fxml"));
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(btnClicked.equals("SignUpBtn")){
                    try {

                        //save information to database
                        SignUpController.savetoDatabase(user,userAcc,SignUpController.SaltValue);

                        //switch to resetPassword in page
                            Parent root = FXMLLoader.load(getClass().getResource("home_page.fxml"));
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                errorLabel.setText("Invalid code"); 
            }
        }
    }

    @FXML
    void ResendCodeBtn(ActionEvent event) {
        //initialize vericationcode
        vericationcode = Integer.parseInt(generateVerificationCode());
        try{
            sendEmail("lucas",userEmailPass, vericationcode);
        }catch(Exception e){
            errorLabel.setText("You are offline");
        }
        //hide resend btn 
        resendBtn.setVisible(false);
        CountDown();
        timer();
        
    }

    public String showPartEmail(String email){
        String emailToShow = String.valueOf(email.charAt(0));
        String[] splitedEmail = email.split("@");
        for(int i = 0; i < email.length()/2 ; i++){
            emailToShow += "*";
        }

        emailToShow += "@"+splitedEmail[1];
        return emailToShow;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //initialize vericationcode
         vericationcode = Integer.parseInt(generateVerificationCode());
         try{
            //send email to user
            sendEmail("User",userEmailPass,vericationcode);
         }catch(Exception e){
            errorLabel.setText("Your are offline");
         }

         //display user email
         displayEmailLabel.setText(showPartEmail(userEmailPass));

         //hide resend btn 
         resendBtn.setVisible(false);

         //display the timer
         CountDown();
         timer();
    }

    public void CountDown(){
        timer_Label.setText("Time left: 30 seconds");
        Timeline timeline = new Timeline();
        timeline.setCycleCount(30);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            int time = Integer.parseInt(timer_Label.getText().split(": ")[1].split(" ")[0]);
            if (time > 0) {
                timer_Label.setText("Time left: " + (time - 1) + " seconds");
            }
        }));
        timeline.playFromStart();    
    }

    public void timer(){
        Timer timer = new Timer();

        // Schedule task to enable email resend after 30 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("User can now request to resend email.");

                //enable resend btn to be visible
                resendBtn.setVisible(true);                
            }
        }, RESEND_EMAIL_DELAY);

        // Schedule task to invalidate verification code after 5 minutes
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Verification code is now invalid.");
                // Code to invalidate verification code goes here

                //change verification code
                vericationcode = Integer.parseInt(generateVerificationCode());
            }
        }, VERIFICATION_CODE_EXPIRY);
    }

}
