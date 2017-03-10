package application;
 /*Class Name:Transaction
 Author: Seohee Jung,John Dong, Benn Xu
 Date:June.8th
 School: AY Jackson
 Purpose: The class that carries out every transaction
 */
   import java.util.Date;
   import java.util.Locale;
   import java.text.SimpleDateFormat;
    public class Transactions{
    //variables
      private Account userAccount;
      private int action;
      
   	//mutators and accessors
       public int getAction(){
         return action;
      }
       public void setAction(int num){
         action=num;
      }
   
   //constructor
       public Transactions(Account userAccount){
         this.userAccount=userAccount;
         action=0;
      }
   
   //withdraw method that deducts the amount from the account with a double paramter
       public void withdraw(double amount){
         userAccount.deduct(amount);
      }
      
   //deposit method that adds the amount to the account with a double paramter
       public void deposit(double amount){
         userAccount.add(amount);
      }
      
   //transfer method that transfers amount from the other account to the current account with a double and Account paramter
       public void transferIn(double amount, Account other){
         userAccount.add(amount);
         other.deduct(amount);
      }
      
   //transfer method that transfers amount to the other account from the current account with a double and Account paramter
       public void transferOut(double amount,Account other){
         userAccount.deduct(amount);
         other.add(amount);
      }
   
   //confrim method that returns an array of string with double and Account parameters
       public String []confirm(double amount,Account other){
         String [] phrase=new String[3];
         phrase[0]=userAccount.name();
        //transaction type
         if (action==1){
            phrase[1]="Withdraw";
         }
         else if(action==2){
            phrase[1]="Deposit";
         }
         else if(action==3){
            phrase[1]="Transfer from "+other.name();
         }
         else if(action==4){
            phrase[1]="Transfer to "+other.name();
         }   
         phrase[2]=String.valueOf(amount);
         return phrase;
      }
      
      //need to change to sentrences? 
      //toString method that changes every transaction to String for later storing with a String array as a parameter
       public String []toString(String[] phrase){
         SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy hh:mm a",Locale.CANADA);
         Date d=new Date();
         String[] message=new String[5];
         message[0]=phrase[0];
         message[1]=phrase[1];
         message[2]=phrase[2];
         message[3]=String.valueOf(userAccount.getBalance());
         message[4]=format.format(d);
         return message;
      }
      
     //toString method that changes every transfer transaction to String for later storing with a String array as a parameter
       public String []toStringTransfer(String[] phrase,Account other){
         SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy hh:mm a",Locale.CANADA);
         Date d=new Date();
         String[] message=new String[6];
         message[0]=phrase[0];
         message[1]=phrase[1];
         message[2]=phrase[2];
         message[3]=String.valueOf(userAccount.getBalance());
         message[4]=String.valueOf(other.getBalance());
         message[5]=format.format(d);
         return message;
      }
   }