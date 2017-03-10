package application;
/*Class Name:Savings
 Author: Seohee Jung,John Dong, Benn Xu
 Date:June.8th
 School: AY Jackson
 Purpose: The child class of Account and all transactions that a savings account require
 */  
    public class Savings extends Account{
      //variables and constants
      private static int LIMIT=25;//limit of the number of transctions 
      private int transactions;
      
   //mutators and accessors
       public int getTransactions(){
         return transactions;
      }
       public void setTransactions(int num){
         transactions=num;
      }
      
   //contructors
       public Savings(double amount,int num){
         super(amount);
         transactions=num;
      }
      
   	//a method that deducts the balance with with a double variable as its parameter
       public void deduct(double amount){
         if(balance==0){
         ///////// CHANGED /////////////∫∫
            System.out.println("There is no change made in your Savings account since your initial balance in Savings account was 0, .");
         }
         else if (amount>balance){
            transactions-=1;
            System.out.println("Your original balance was $"+balance+" now it is $0");
            balance=0;
         }
         else{
            balance-=amount;
            transactions-=1;
         }
      
      }       
      //a method that adds to the balance with a double variable as its parameter
       public void add(double amount){
         balance+=amount;
         transactions-=1;
      }
      
     //a method that returns String value of the name of the account type
       public String name(){
         return "Savings";
      } 	
   	//a method that returns string value of the number of transaction left
       public String toString(){
         return String.valueOf(transactions);
      }
   	
   }