/**
 * Sample Skeleton for 'home_page.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class home_Controller implements Initializable{

    private static userInformation userInfo;
    private static AccountInformation userAcc;

    public static void getUserAcc(userInformation userInform, AccountInformation userAccount){
        userInfo = userInform;
        userAcc = userAccount;
    }

    @FXML // fx:id="AccExpire_date_Label"
    private Label AccExpire_date_Label; // Value injected by FXMLLoader

    @FXML // fx:id="AccountNumber_Label"
    private Label AccountNumber_Label; // Value injected by FXMLLoader

    @FXML // fx:id="CurrentMonth_Label"
    private Label CurrentMonth_Label; // Value injected by FXMLLoader

    @FXML // fx:id="balance_Label"
    private Label balance_Label; // Value injected by FXMLLoader

    @FXML // fx:id="totalMonth_ExpenseLabel"
    private Label totalMonth_ExpenseLabel; // Value injected by FXMLLoader

    @FXML // fx:id="total_IcomeLabel"
    private Label total_IcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="userName_Label"
    private Label userName_Label; // Value injected by FXMLLoader

    @FXML
    void history_button(ActionEvent event) throws IOException {
        //switch to history page
        Parent root = FXMLLoader.load(getClass().getResource("history_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void home_button(ActionEvent event) throws IOException{

        //send userdetails to history controller class
        history_Controller.getUserAccount(userInfo,userAcc);

        //switch to home page
        Parent root = FXMLLoader.load(getClass().getResource("home_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void transfer_button(ActionEvent event) throws IOException {

        //send userdetails to transfer controller class
        transfer_Controller.getUserAcc(userInfo, userAcc);

        //switch to Transfer page
        Parent root = FXMLLoader.load(getClass().getResource("Transfer_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //send userdetails to history controller class
        history_Controller.getUserAccount(userInfo,userAcc);

        //send userdetails to transfer controller class
        transfer_Controller.getUserAcc(userInfo, userAcc);

        getUserAcc(userInfo,userAcc);
        
        setLabels();
        try {
            
            calculateTotals();
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    private void setLabels(){
        //get month and year 
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String currentdate = month +"/"+year;

        //get label values from userInformation and AccountInformation classes
        String balance_value = "R"+ home_Controller.userInfo.getBalance();
        String userName = home_Controller.userInfo.getName();
        String AccNo = String.valueOf(home_Controller.userInfo.getAccNumber());
        Random randomNum = new Random();
        int expireDate = randomNum.nextInt(1,12);
        String expireDateString = " ";

        if(expireDate <= 12){
            expireDateString = expireDate+"/27";
        }else{
            expireDate = expireDate/12;
            expireDateString = expireDate+"/27";
        }

        //set new text for each label
        userName_Label.setText(userName);
        balance_Label.setText(balance_value);
        AccountNumber_Label.setText(AccNo);
        AccExpire_date_Label.setText(expireDateString);
        CurrentMonth_Label.setText(currentdate);

    }

    //calculate total expendure and income for current month 
    public void calculateTotals() throws ClassNotFoundException, SQLException{
        int userAccNo = home_Controller.userAcc.getAccNumber();
        String sql = " Select AccTransactions from account_information where AccNo = "+userAccNo;
        DBconnection createConnect = new DBconnection();
        ResultSet rs = createConnect.getUserInfor(sql);

        StringTokenizer token;
        double debitAmount = 0;
        double creditAmount = 0;
        LocalDate transactionDate;
        LocalDate curDate = LocalDate.now();
        ArrayList<String> transactions = new ArrayList<>();

        while(rs.next()){
            String result = rs.getString(1);
    
            if(result != null){
                token = new StringTokenizer(result, "|");
                int numOfToken = token.countTokens();
                for (int i = 0; i<numOfToken; i++){
                    transactions.add(token.nextToken());
                }
            }  
        }
        createConnect.closeConnection();
        
        if(!transactions.isEmpty())
            for(String line: transactions){
                if(line.equals("null")){
                    
                }else{
                    //add transactions to user lists for other scene such as transfer and history
                    userAcc.addToList(line);
                    
                    token = new StringTokenizer(line, ":");
                    String transcationType = token.nextToken();
                    String deposite = "deposited";
                    transactionDate = LocalDate.parse(token.nextToken());

                    if(transcationType.equals(deposite)){
                        if(transactionDate.getMonth() == curDate.getMonth() )
                            creditAmount += Double.parseDouble(token.nextToken());
                        else
                            token.nextToken();
                    }else{
                        if(transactionDate.getMonth() == curDate.getMonth() )
                            debitAmount += Double.parseDouble(token.nextToken());
                        else
                            token.nextToken();
                    }
                    token.nextToken(); 
                    
                }
            }else{

            }
                
        totalMonth_ExpenseLabel.setText("R"+String.valueOf(debitAmount));
        total_IcomeLabel.setText("R"+String.valueOf(creditAmount));
    }

}
