/**
 * Sample Skeleton for 'history_page.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class history_Controller implements Initializable{

    private static AccountInformation userAcc;
    private static userInformation userInfo;
    //get userAccount information
    public static void getUserAccount(userInformation userInformation,AccountInformation getuserAcc){
        userAcc = getuserAcc;
        userInfo = userInformation;
    }

    ArrayList<String> monthOne = new ArrayList<>();
    ArrayList<String> MonthTwo = new ArrayList<>();
    ArrayList<String> MonthThree = new ArrayList<>();
    ArrayList<String> AllTrans = new ArrayList<>();
    ArrayList<Month> threeMonths = new ArrayList<>();

    @FXML // fx:id="transcation_listview"
    private ListView<String> transcation_listview; // Value injected by FXMLLoader

    @FXML // fx:id="userName_Label"
    private Label userName_Label; // Value injected by FXMLLoader

    @FXML
    private ChoiceBox<Month> monthsChoice;

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
        //send userdetails to transfer controller class
        transfer_Controller.getUserAcc(userInfo, userAcc);
        
        //switch to Transfer page
        Parent root = FXMLLoader.load(getClass().getResource("Transfer_page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void selectMonth(ActionEvent event) {
        transcation_listview.getItems().clear();
        Month monthChoice = monthsChoice.getValue();

        ArrayList<String> history = new ArrayList<>();
        StringTokenizer token;
        String transToAdd = "";
        if(monthChoice.equals(threeMonths.get(0))){
            for(String trans: monthOne){
                token = new StringTokenizer(trans, ":");
                transToAdd = token.nextToken() +  " on "+ token.nextToken()+ " amount: R" +token.nextToken()+" to account no:"+token.nextToken();
                history.add(transToAdd);
            }
            if(history.isEmpty()){
                history.add("No transactions for this month");
            }
            transcation_listview.getItems().clear();
            transcation_listview.getItems().addAll(history);

        }else if(monthChoice.equals(threeMonths.get(1))){
            for(String trans: MonthTwo){
                token = new StringTokenizer(trans, ":");
                transToAdd = token.nextToken() +  " on "+ token.nextToken()+ " amount: R" +token.nextToken()+" to account no:"+token.nextToken();
                history.add(transToAdd);
            }
            if(history.isEmpty()){
                history.add("No transactions for this month");
            }
            transcation_listview.getItems().clear();
            transcation_listview.getItems().addAll(history);

        }else if(monthChoice.equals(threeMonths.get(2))){
            for(String trans: MonthThree){
                token = new StringTokenizer(trans, ":");
                transToAdd = token.nextToken() +  " on "+ token.nextToken()+ " amount: R" +token.nextToken()+" to account no:"+token.nextToken();
                history.add(transToAdd);
            }
            if(history.isEmpty()){
                history.add("No transactions for this month");
            }
            transcation_listview.getItems().clear();
            transcation_listview.getItems().addAll(history);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //send back the user details 
        home_Controller.getUserAcc(userInfo, userAcc);

        //send userdetails to transfer controller class
        transfer_Controller.getUserAcc(userInfo, userAcc);
        
        //set user name
        userName_Label.setText(userInfo.getName());
        setListView();

        //set month options 
        monthOptions();
    }

    public void monthOptions(){ 
        monthsChoice.getItems().addAll(threeMonths);
        monthsChoice.setValue(threeMonths.get(0));
    }

    public void setListView(){
        StringTokenizer token;
        String transToAdd = "";

        //display user history 
        ArrayList<String> Temphistory = userAcc.getList();
        ArrayList<String> history = new ArrayList<>();
        for(String trans: Temphistory){
            if(trans.equals("null"))
                Temphistory.remove(trans);
            else{
                token = new StringTokenizer(trans, ":");
                transToAdd = token.nextToken() +  " on "+ token.nextToken()+ " amount: R" +token.nextToken()+" to account no:"+token.nextToken();
                history.add(transToAdd);
            }
        }
        
        if(history.isEmpty())
            history.add("no transaction");

        AllTrans = history;
        transcation_listview.getItems().addAll(AllTrans);
        selectByMonth(Temphistory);
    }

    public void selectByMonth(ArrayList<String> Temphistory){

        Month transactionMonth;
        LocalDate transDate;
        StringTokenizer token;

        //seperate data by two months 
        LocalDate CurrentMonth = LocalDate.now();
        Month currentMonth = CurrentMonth.getMonth();
        threeMonths.add(currentMonth);

        LocalDate monthBefore = CurrentMonth.minusMonths(1);
        Month beforeMonth = monthBefore.getMonth();
        threeMonths.add(beforeMonth);

        LocalDate twoBeforeMonth = CurrentMonth.minusMonths(2);

        Month twobeforeMonth = twoBeforeMonth.getMonth();
        threeMonths.add(twobeforeMonth);

        for(String currTrans: Temphistory){
            token = new StringTokenizer(currTrans, ":");
            //get rid of the first token
            token.nextToken();

            transDate = LocalDate.parse(token.nextToken());
            transactionMonth = transDate.getMonth();

            if(currentMonth.equals(transactionMonth)){
                monthOne.add(currTrans);
            }else if(beforeMonth.equals(transactionMonth)){
                MonthTwo.add(currTrans);
            }else if(twobeforeMonth.equals(transactionMonth)){
                MonthThree.add(currTrans);
            }

            //get rid of the remaing tokens
            token.nextToken();
            token.nextToken();
            
        }
    }

}
