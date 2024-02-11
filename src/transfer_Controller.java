/**
 * Sample Skeleton for 'Transfer_page.fxml' Controller Class
 * 
 * for Transfer add more feature in the future also update AccountInformation transaction to that its not just deposite when money is recieved 
 * 
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class transfer_Controller implements Initializable{

    private static userInformation userInfo;
    private static AccountInformation userAcc;

    public static void getUserAcc(userInformation userInform, AccountInformation userAccount){
        userInfo = userInform;
        userAcc = userAccount;
    }

    @FXML // fx:id="AccNumber_Input"
    private TextField AccNumber_Input; // Value injected by FXMLLoader

    @FXML // fx:id="Amount_Input"
    private TextField Amount_Input; // Value injected by FXMLLoader
    
    @FXML // fx:id="BankOptions"
    private ChoiceBox<String> BankOptions;// Value injected by FXMLLoader

    @FXML // fx:id="processIndicator"
    private ProgressBar processIndicator; // Value injected by FXMLLoader

    @FXML // fx:id="transfer_Label"
    private Label transfer_Label; // Value injected by FXMLLoader

    @FXML
    void history_button(ActionEvent event) throws IOException {
        //send userdetails to history controller class
        history_Controller.getUserAccount(userInfo,userAcc);

        //switch to history page
        Parent root = FXMLLoader.load(getClass().getResource("history_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void home_button(ActionEvent event) throws IOException {
        //send back the user details 
        home_Controller.getUserAcc(userInfo, userAcc);
        
        //switch to home page
        Parent root = FXMLLoader.load(getClass().getResource("home_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void transfer_button(ActionEvent event) throws IOException{

        //switch to Transfer page
        Parent root = FXMLLoader.load(getClass().getResource("Transfer_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML //add more feature in the future like withdrawls 
    void transfer_button_send(ActionEvent event) {
        //Array of banks 
        String[] banks = {"Internal","Standard Bank", "Capitec", "Absa","FNB"};

        //get all the required info from  user to make transction 
        String BankName = BankOptions.getValue();
        int AccNo = Integer.parseInt(AccNumber_Input.getText());
        double amount = Double.parseDouble(Amount_Input.getText());

        if(BankName.equals(banks[0])){
           
            try {
                userAcc.InternalTransfer(AccNo, amount);
                userAcc.addTo_database(userAcc.getList());

                double decrement = userInfo.getBalance();
                userInfo.setBalance(decrement-amount);

                for(int i = 0; i<=100; i+=10)
                    processIndicator.setProgress(i);

                transfer_Label.setText("Transfer Successful");
            } catch (Exception e) {

                for(int i = 0; i<=100; i+=10)
                    processIndicator.setProgress(i);

                transfer_Label.setText("Transfer Failed");
                e.printStackTrace();
            }
            
        }else{
            try {
                userAcc.transferMoney(BankName,AccNo,amount);
                userAcc.addTo_database(userAcc.getList());

                for(int i = 0; i<=100; i+=10)
                    processIndicator.setProgress(i);

                transfer_Label.setText("Transfer Successful");
            } catch (Exception e) {
                
                for(int i = 0; i<=100; i+=10)
                    processIndicator.setProgress(i);

                transfer_Label.setText("Transfer Failed");
                e.printStackTrace();
            }
            
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BankOptions.getItems().addAll("Internal","Standard Bank", "Capitec", "Absa","FNB" );
        BankOptions.setValue("Internal");
    }

}
