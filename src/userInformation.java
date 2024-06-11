//import java.util.*;

import java.sql.*;
import java.util.Random;


public class userInformation{

   public static int IdCount;
   private static int counter = 1000100;
   String userName;
   private String email;
   private String password;
   private int accNumber;
   private double balance;
   
   public userInformation(){
      //user information cannot be null 
   }
   
   public userInformation(String name,String email,String password,double balance){
      setName(name);
      setPassword(password);
      setBalance(balance);
      setEmail(email);
      generateAccNumber(counter);
   }
   
   public void setName(String name){
      this.userName = name;
   }

   public String getName(){
      return userName;
   }
   
   public void setBalance(double balance){
      if(balance>0)
        this.balance=balance;
      else
        throw new IllegalArgumentException("Invalid balance");
   }

   public double getBalance(){
      return balance;
   }
   
   public void setPassword(String password){
      if(password.length()>=5)
         this.password=password;
      else
        throw new IllegalArgumentException("Invalid password");
   }

   public String getPassword(){
      return this.password;
   }
   
   public void generateAccNumber(int accNumber){
      Random randomNum = new Random();
      int genRandomNum = randomNum.nextInt(1000000, 9999999);
      if(!getId(genRandomNum))
         this.accNumber=genRandomNum; 
      System.err.println(this.accNumber);
   }

   public void setAccNumber(int accNumber){
      this.accNumber=accNumber;
   }

   public int getAccNumber(){
      return accNumber;
   }
   
   public boolean validate(String email){
      int count1 = 0;
      int count2 = 0;
   
      for(int i=0;i<email.length();i++){
          if(email.charAt(i)=='@'){
             count1++;
          }else if(email.charAt(i)=='.'){
             count2++;
          }
      }
   
   
     if(email.charAt(0)!='@' && email.charAt(email.length()-1)!='@' && email.charAt(0)!='.' && email.charAt(email.length()-1)!='.' && count1==1 && count2==1)
         return true;
     else
         return false;
   }    
     
   public void setEmail(String email){
        if(validate(email)==true)
           this.email=email;
        else
           throw new IllegalArgumentException("Invalid email");
   }

   public String getEmail(){
      return email;
   }
     
   public String toString(){
         return userName +" : "+ email +" : "+password+" : "+balance+" : "+accNumber;
   }

   public boolean getId(int accountNo){
      boolean checkAccountNo = false;
      try {
         DBconnection createConnection = new DBconnection();
         //get hightest Id
         ResultSet rs = createConnection.getUserInfor("SELECT userAccNo FROM userinformation;");
         
         while(rs.next()){
                
            int CurrentAccountNo = Integer.parseInt(rs.getString(1));
            System.err.println("here CurrentAccountNo "+CurrentAccountNo);
            if(CurrentAccountNo == accountNo){
               checkAccountNo = true;
            }
         }
         createConnection.closeConnection();
            
      } catch (Exception e) {
         
         //do nothing 
      }
      return checkAccountNo;
   }
}