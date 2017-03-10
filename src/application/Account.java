package application;

 /*Class Name:Account
 Author: Seohee Jung,John Dong, Benn Xu
 Date:June.8th
 School: AY Jackson
 Purpose: The parent class of both Savings and Chequing
 */  
    abstract class Account{
      //variables
      protected double balance;
   	
   	//abstract class that requires to enter a double variable
       abstract void deduct(double amount);
       abstract void add(double amount);
       abstract String name();
       
      //constructors
       public Account(double amount){
         balance=amount;
      }
         
   //mutator and accessors 
       public void setBalance(double amount){
         balance=amount;
      }
       public double getBalance(){
         return balance; 
      }
      
   //toString method that returns a string value of the balance of the account
       public String toString(){
         return ""+String.valueOf(balance);
      }
   }