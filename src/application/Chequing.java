package application;
/*Class Name:Chequing
 Author: Seohee Jung,John Dong, Benn Xu
 Date:June.8th
 School: AY Jackson
 Purpose: The child class of Account and all transactions that a chequing account require
 */ 
    public class Chequing extends Account{
   //constructor
       public Chequing(double amount){
         super(amount);
      }
      
   	//a method that deducts the balance with with a double variable as its parameter
       public void deduct(double amount){
         double oldBal=balance;
         if(balance==0){
            System.out.println("Since your balance in Savings account is 0, it's impossible to withdraw.");
         }
         else if (amount>balance){
            balance=0;
            System.out.println("Your original balance was $"+oldBal+", now it is $0.");
         }
         else{
            balance-=amount;
         }
      }
      
       //a method that adds to the balance with a double variable as its parameter
       public void add(double amount){
         balance+=amount;
      }
      
   	//a method that returns String value of the name of the account type
       public String name(){
         return "Chequing";
      }
   	
   	//a method that returns string value of the number of transaction left
       public String toString(){
         return super.toString();
      }
   
   
   
   }