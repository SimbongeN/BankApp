/**
 * the extra method such as withdraw are for later feature which need to be added
 * also when money is recieved it must indicate as recieved not deposited always it must have variaty 
 */


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccountInformation{
    LocalDate todaysDate = LocalDate.now();

    //format for transactiosn (typeOfTranscaction : amount : accountnumber : date)
    ArrayList<String> Transaction = new ArrayList<>();

    private int AccountNumber;
    private double balance;
    //private String userName;
    userInformation CurrentUser;

    //deflaut constractor accepts userInformation class as an argument 
    //on if there recieved money of there no money recieved recievedAmount = 0
    public AccountInformation(userInformation CurrentUser){

        //initializes all variables to current user 
        this.CurrentUser = CurrentUser;
        this.AccountNumber = CurrentUser.getAccNumber();
        this.balance = CurrentUser.getBalance();
        //this.userName = CurrentUser.getName();
        
    }

    //Account get method
    public int getAccNumber(){
        return this.AccountNumber;
    }

    //get transcation method 
    public ArrayList<String> getList(){
        return this.Transaction;
    }

    //method to add transcations for loading 
    public void addToList(String transaction){
        this.Transaction.add(transaction);
    }

    //deposite method 
    public void deposite(double amount){
        if(amount >= 50){
            //update changes made to balance of the user 
            this.balance += amount;
            CurrentUser.setBalance(this.balance);

            //add to transaction array
            //format for transactiosn (typeOfTranscaction : date : amount : accountnumber)
            String transactionLine = "deposited"+":"+ todaysDate + ":"+ amount + ":" + AccountNumber ;
            Transaction.add(transactionLine);

            //lines to show after transaction on UI
            //only for testing remove after testing 
            String line = "Transaction successfull";
            System.out.println(line); 
            System.out.println("new balance is: "+ this.balance);
        }else{
            //line to display for invalid transactions on UI
            //only for testing remove after testing
            String line = "Transaction unsuccessfull";
            System.out.println(line);
            System.out.println("invalid amount: "+amount);

            //throw exception
            throw new IllegalArgumentException("invalid amount");
        }
    }

    //withdrawal method
    public void withdrawal(double amount){
        if(amount <= this.balance){
            //update changes made to balance of the user 
            this.balance -= amount;
            CurrentUser.setBalance(this.balance);

            //add to transaction array
            //format for transactiosn (typeOfTranscaction: date : amount : accountnumber )
            String transactionLine = "withdrawal"+":"+ todaysDate + ":"+ amount + ":" + AccountNumber ;
            Transaction.add(transactionLine);

            //lines to show after transaction on UI
            //only for testing remove after testing 
            String line = "Transaction successfull";
            System.out.println(line); 
            System.out.println("new balance is: "+ this.balance);
        }else{
            //line to display for invalid transactions on UI
            //only for testing remove after testing
            String line = "Transaction unsuccessfull";
            System.out.println(line);
            System.out.println("invalid amount: "+ amount);

            //throw exception
            throw new IllegalArgumentException("invalid amount");
        }
    }

    //transfer method        //remember for internal transfer bankName = internal
    public void transferMoney(String BankName, int AccountNumber, double amount){

        ArrayList<String> Banks = new ArrayList<>();
        String[] bank = {"Internal","Standard Bank", "Capitec", "Absa","FNB" };
        for(String bankName : bank)
            Banks.add(bankName);

        //convert account number to string to check its length
        String accNumberTo_String = String.valueOf(AccountNumber);

        //amount has to be greater then 0 and account number must not equal 0 and must be greater then 10000(account number must have 5 digits)
        if(Banks.contains(BankName) && accNumberTo_String.length() >= 5 && amount > 0 && amount <= this.balance){

            for (String currentBank : Banks) {

                //check which bank to send money to
                if(currentBank.equals(BankName)){
                    //transaction is succesful decrement user balance and update user infor
                    this.balance -= amount;
                    CurrentUser.setBalance(this.balance);

                    //add to transaction array
                    //format for transactiosn (typeOfTranscaction: date : amount : accountnumber )
                    String transactionLine = "Transfered"+":" + todaysDate + ":"+ amount + ":" + AccountNumber;
                    Transaction.add(transactionLine);

                    //lines to show after transaction on UI
                    //only for testing remove after testing 
                    String line = "Transaction successfull";
                    System.out.println(line); 
                    System.out.println("new balance is: "+ this.balance);
                }
            }

        }else{
            //throw exception
            throw new IllegalArgumentException("bankname invalid");
        }
    }

    //recieved money method and send money method (internal transfer)
    public void InternalTransfer(int AccNo, double amount) throws SQLException, ClassNotFoundException{
        String toAddTransaction  = "";
        if(amount > 0 && (amount < this.balance)){

            //transaction is succesful change user reciever balance and update transaction of the reciever 
            double recieverBalance = getUserBalance(AccNo);
            DBconnection createConnection = new DBconnection();

            recieverBalance +=amount;
            String sql = "UPDATE `bankapp_database`.`account_information` SET `acc_balance` = '"+recieverBalance+"' WHERE (`AccNo` = '"+AccNo+"')";
            createConnection.updateDB(sql);

            sql = " Select AccTransactions from account_information where AccNo = "+AccNo;
            ResultSet rs = createConnection.getUserInfor(sql);
            while(rs.next()){
                String result = rs.getString(1);
                toAddTransaction = result;
            }
            
            toAddTransaction += "|"+"deposited"+":"+ todaysDate + ":"+ amount + ":" + AccountNumber;
            sql = "UPDATE `bankapp_database`.`account_information` SET `AccTransactions` = '"+toAddTransaction+"' WHERE (`AccNo` = '"+AccNo+"');";
            createConnection.updateDB(sql);

            

            //decrement sender balance 
            this.balance -= amount;
            //also update balance for sender user 
            sql = "UPDATE `bankapp_database`.`account_information` SET `acc_balance` = '"+this.balance+"' WHERE (`AccNo` = '"+this.AccountNumber+"')";
            createConnection.updateDB(sql);

            //update transactions
            toAddTransaction = "Transfered"+":"+ todaysDate + ":"+ amount + ":" + AccNo;
            Transaction.add(toAddTransaction);
            createConnection.closeConnection();

        }else{
            //do nothing
        }
    }

    //add to databaseMethod (will do it once database requirement are meant)
    public void addTo_database(ArrayList<String> Transactions) throws SQLException, ClassNotFoundException{
        //convert ArrayList to one string variables
        String toAddTransaction = "";
        int size = Transactions.size();
        for(int i = 0 ; i < size; i++){
            if(i != (size-1))
                toAddTransaction += Transactions.get(i) + "|";
            else
                toAddTransaction += Transactions.get(i);
        }

        DBconnection createConnection = new DBconnection();
        String sql = "UPDATE `bankapp_database`.`account_information` SET `AccTransactions` = '"+toAddTransaction+"' WHERE (`AccNo` = '"+this.AccountNumber+"');";
        createConnection.updateDB(sql);

        //also update balance for sender user 
        sql = "UPDATE `bankapp_database`.`account_information` SET `acc_balance` = '"+this.balance+"' WHERE (`AccNo` = '"+this.AccountNumber+"')";
        createConnection.updateDB(sql); 

        createConnection.closeConnection();
    }

    //get user balance that must recieve money
    public double getUserBalance(int AccNo) throws SQLException, ClassNotFoundException{
         
        String sql = "Select acc_balance from account_information where AccNo = "+AccNo;
        double recieverBalance = 0;
        DBconnection createConnect = new DBconnection();
        ResultSet rs = createConnect.getUserInfor(sql);
        if(rs.next() == false)
            throw new IllegalAccessError("no such user");
        else     
            recieverBalance = Double.parseDouble(rs.getString(1));

        createConnect.closeConnection();
        return recieverBalance;
    }

    public String toString(){
        String toReturn = "This is their account details:"+ AccountNumber + balance + Transaction;
        return toReturn;
    }
}
