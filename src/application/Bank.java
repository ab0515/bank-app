package application;

 /*Class Name:Bank
 Author: Seohee Jung,John Dong, Benn Xu
 Date:June.8th
 School: AY Jackson
 Purpose: The Bank class that's able to store and withdraw information from a text file
 */  
   import java.io.*;
   import java.util.Scanner;
    class Bank{
   //variables
      Client user;
      Scanner sc= new Scanner(System.in);
   
   //method to setup the user(almost the same as constructor)
       public void setupUser(String first,String last, String gender, int month, int year, int date, String card, int pin, int num, double savings, double chequing){
         user = new Client(first, last, gender, month, year, date, card, pin, num,savings, chequing);
      }
   
   //method to store information in a text file
       public void savePersonalInfo(){// create or upload client
         try{
            BufferedWriter out = new BufferedWriter(new FileWriter("clients\\"+user.getCardNum()+"1.txt"));
            out.write("2");
            out.newLine();
            out.write(""+String.valueOf(user.getPIN()));
            out.newLine();
            out.write(""+user.getGender());
            out.newLine();
            out.write(""+user.getMonth());
            out.newLine();
            out.write(""+user.getDate());
            out.newLine();
            out.write(""+user.getYear());
            out.newLine();
            out.write(""+user.getFirstN());
            out.newLine();
            out.write(""+user.getLastN());
            out.newLine();
            out.write(""+user.getSavings());	
            out.newLine();
            out.write(""+user.getSavingsBal());
            out.newLine();
            out.write(""+user.getChequingBal());
            out.newLine();
            out.close();
         }
             catch(IOException iox){
               System.out.println("Problem with creating file");
            }
      }
   
   // method to search for a client with their card number entered as a string parameter and return a boolean
       public boolean searchClient(String num){
         if(num.equals("-1")){
            return true;
         }
         File f=new File("clients\\"+num+"1.txt");
         return f.exists();
      }
      
   // a method that checks the PIN with an integer number as a parameter and returns a boolean
       public boolean checkPIN(int num){
         return (num==user.getPIN());
      }
   
   //a method that loads existing user information
       public void loadClient(String card){
         String first, last, gender;
         int m, d, y, num, pin, trans;
         double savings, chequing;
      
         try{
            BufferedReader in = new BufferedReader(new FileReader("clients\\"+card+"1.txt"));
         
            num=Integer.parseInt(in.readLine());
            pin=Integer.parseInt(in.readLine());
            gender=in.readLine();
            m=Integer.parseInt(in.readLine());
            d=Integer.parseInt(in.readLine());
            y=Integer.parseInt(in.readLine());
            first=in.readLine();
            last=in.readLine();
            trans=Integer.parseInt(in.readLine());
            savings=Double.parseDouble(in.readLine());
            chequing=Double.parseDouble(in.readLine());
            user = new Client(first, last, gender, m, y, d, card, pin, trans, savings, chequing);
         
            in.close();
         }
             catch(IOException iox){
               System.out.println("Problem with loading file");
            }
      }
   
   // a method that saves each transaction taken, with Transaction, amount and other Account as the parameter 
       public void saveTransaction(Transactions trans, double amount, Account other){      
         try{
            BufferedWriter out = new BufferedWriter(new FileWriter("clients\\"+user.getCardNum()+"0.txt", true));
            String []message;
            if((trans.getAction()==1)||(trans.getAction()==2)){//if its withdraw or deposit
               message=trans.toString(trans.confirm(amount,other));
               for (int i=0;i<5;i++){
                  out.write(message[i]);
                  out.newLine();
               }
               out.write("");
            }
            else{
               message=trans.toStringTransfer(trans.confirm(amount,other),other);//if its transfering
               for (int i=0;i<6;i++){
                  out.write(message[i]);
                  out.newLine();
               }
               out.write("");
            }
            out.close();
         }
             catch(IOException iox){
               System.out.println("Problem with saving file");
            }
      }
   	
   	//a method that shows the history of all transactions
       public void history(){
         String message;   
         try{
            BufferedReader in = new BufferedReader(new FileReader("clients\\"+user.getCardNum()+"0.txt"));
            message=in.readLine();
            while(message!=null){
               System.out.println(message);
               message=in.readLine();
            }
         }
             catch(IOException iox){
               System.out.println("Problem with loading history transactions file");
            }
      }
   
   //*****************other services**********************//
   //a method that changes the pin with a integer parameter
       public void changePIN(int num){
         user.setPIN(num);
         System.out.println("");
      }
   	//a method that checks the first name with two String parameters
       public void checkFirstName(String first1, String first2){
         if(!first1.equals(first2)){
            do{
            //sc.nextLine();
               System.out.println("Your name does not match.\n");
               System.out.print("Enter the new first name: ");
               first1=sc.nextLine();
               System.out.print("Re-enter the new first name to verify: ");
               first2=sc.nextLine();  
            } while(!first1.equals(first2));
         }
         changeFirstName(first2);
         savePersonalInfo();
         System.out.println("Your first name has been changed.\n");
      }
   
   //a method that checks the last name with two String parameters
       public void checkLastName(String last1, String last2){
         if(!last1.equals(last2)){
            do{
            //sc.nextLine();
               System.out.println("Your name does not match.\n");
               System.out.print("Enter the new last name: ");
               last1=sc.nextLine();
               System.out.print("Re-enter the new last name to verify: ");
               last2=sc.nextLine();
            }while(!last1.equals(last2));
         }
         changeLastName(last2);
         savePersonalInfo();
         System.out.println("Your last name has been changed.\n");
      }
      
   //a method that changes the first name with a string parameter
       public void changeFirstName(String first){
         user.setFirstN(first);
      }   
      //a method that changes the last name with a string parameter
       public void changeLastName(String last){
         user.setLastN(last);
      }
   }