package application;
   import java.util.Scanner;
   import java.io.*;

    public class BankRunner{
       public static void main(String[] args){
         Scanner sc = new Scanner(System.in);
      //all the variables
         int acc, trans, transfer, service;
         double amount=0, chequingBal, savingsBal;
         String first, last, gender;
         int pin1=0, pin2=1, date, month, year;
         String birthdate, eCard="";
         String [] list;
         Bank customer = new Bank();
         Account userAcc;
         Account other;
         Transactions action;
         boolean cont=false;
         String []message;
      
         while (!cont){
         //welecome interface      	
            System.out.println("Welcome");
            System.out.println("Select one of the following options:");
            System.out.print("1. Create a new account\n2. Already have an account\n>> ");
            acc = sc.nextInt();
         
            switch(acc){
               case 1://create an account
                  sc.nextLine();
                  
               	//storing all information
                  System.out.print("Enter your first name: ");
                  first=sc.nextLine();
                  System.out.print("Enter your last name: ");
                  last = sc.nextLine();
                  System.out.print("Enter your gender(F or M): ");
                  gender = sc.nextLine();
                  System.out.print("Enter your birthdate(mm dd yyyy): ");
                  birthdate=sc.nextLine();
                  list=birthdate.split(" ");
                  month=Integer.parseInt(list[0]);
                  date=Integer.parseInt(list[1]);
                  year=Integer.parseInt(list[2]);
                  
               	//if two pins entered r not the same            
                  if(pin1!=pin2){
                     do{
                        pin1=0;
                        while (pin1<1000||pin1>9999){//checking pin number size
                           System.out.print("Enter a new 4-digit PIN(*PIN can't start with 0*): ");
                           pin1=sc.nextInt();
                           if (pin1<1000||pin1>9999){
                              System.out.println("Pin has be to 4 digit and cannot start with 0!");
                           }
                        }
                        System.out.print("Re-enter your PIN to validate: ");
                        pin2=sc.nextInt();
                        if (pin1!=pin2){
                           System.out.println("\nYour PIN does not match.\n");
                        }
                     }while(pin1!=pin2);
                  }
                  
               	//generate 6-digit card num for a new user
                  int num;
                  for(int i=0; i<6; i++){
                     num = 1+((int)(Math.random()*9));
                     eCard += String.valueOf(num);
                  }
                  System.out.println("Your card number is: "+eCard);
                  customer.setupUser(first, last, gender, month, year, date, eCard, pin1, 25, 0, 0);
                  customer.savePersonalInfo();
                  cont=true;
                  break;
            
               case 2://access to an exist account
                  sc.nextLine();
               
                  System.out.print("Enter your card number: ");
                  eCard=sc.nextLine();
                  cont=true;
               
                  if(!customer.searchClient(eCard)){//check if the account exists
                     do{
                        System.out.println("Your card number does not exist. Please try again or enter -1 to exit.\n");
                        System.out.print("Enter your card number: ");
                        eCard=sc.nextLine(); 
                     
                        if(eCard.equals("-1")){
                           System.out.println("");
                        }
                        else if(customer.searchClient(eCard)){
                           customer.loadClient(eCard);
                        }
                     }while(!customer.searchClient(eCard));
                  }
                  else{
                     customer.loadClient(eCard);
                  }
               
               //ask for pin number
                  if (!eCard.equals("-1")){
                     do{
                        System.out.println("Enter your pin number(enter -1 to return to the first page):");
                        pin1=sc.nextInt();
                        if (pin1==-1){
                           cont=false;
                        }
                     }while(!customer.checkPIN(pin1)&&(pin1!=-1));
                  }
                  break;
            
               default: System.out.println("Please choose one of the displayed options.");
            }
         }
      
      
      
         if (cont&&!eCard.equals("-1")){//how to continue
            do{//account choosing
               System.out.println();
               System.out.println("Select an account:");
               System.out.print("1. Savings Account\n2. Chequing Account\n3. Exit\n>> ");
               acc=sc.nextInt();
               System.out.println();
            
            
               if(acc==1||acc==2) {//if selection is not exit
                 //get the accounts
                  if(acc==1){
                     userAcc=customer.user.getSavings();
                     other=customer.user.getChequing();
                  }
                  else {
                     userAcc=customer.user.getChequing();
                     other=customer.user.getSavings();
                  }
               //initialize transaction
                  action=new Transactions (userAcc);
               
                  do{//transaction choosing
                     System.out.println("Select a transaction.");
                     System.out.print("1.Deposit\n2.Withdrawals\n3.Transfer\n4.Transaction Histroy\n5.Other servicen\n6.Exit\n>> ");
                     trans=sc.nextInt();
                  
                     if (trans>=1&&trans<=3){
                        if (trans== 1){ //deposit
                           System.out.print("Enter the amount you wish to deposit: ");
                           amount=sc.nextDouble();
                           action.setAction(2);
                        }
                     
                        if (trans==2)	{//withdraw
                           System.out.print("Enter the amount you wish to withdraw: ");   
                           amount=sc.nextDouble();
                           action.setAction(1);
                        }
                     
                        if (trans==3) {//transfer
                           System.out.println("Select the one of following options.");
                           System.out.print("1.Transfer amount in from the other account\n2. Transfer amount out to the other account\n>> ");
                           transfer=sc.nextInt();
                           System.out.println("Enter the aount you wish to transfer:");
                           amount=sc.nextDouble();
                           if (transfer==1){//transfer in
                              action.setAction(3);
                           }
                           else if (transfer==2){//transfer out
                              action.setAction(4);
                           }
                        }
                                   
                        if (amount>0){//if positive amount entered
                        //confirmation message
                           message=action.confirm(amount,other);
                           for (int i=0;i<3;i++){
                              System.out.println(message[i]);               
                           }
                        //carrying out the transactions
                           if (action.getAction()==1){
                              action.withdraw(amount);
                           }
                           else if(action.getAction()==2){
                              action.deposit(amount);
                           }
                           else if(action.getAction()==3){
                              action.transferIn(amount,other);
                           }
                           else if (action.getAction()==4){
                              action.transferOut(amount,other);
                           }
                           customer.saveTransaction(action,amount,other);
                        }
                        else {
                           System.out.println("Please enter a positive amount.");
                        } 
                     }
                     //ask for history
                     else if (trans== 4){
                        customer.history();
                     }
                     //other services
                     else if (trans==5){
                        System.out.println("Select one of the following options:");
                        System.out.print("1. Change PIN\n2. Change Name\n>> ");
                        service=sc.nextInt();
                     
                        int oldPIN, newPIN1, newPIN2;
                        String newFirst1, newFirst2, newLast1, newLast2;
                     
                        switch(service){
                           case 1: // change PIN
                              System.out.print("Enter the current PIN: ");
                              oldPIN =sc.nextInt();
                           
                              if(!customer.checkPIN(oldPIN)){//check old pin
                                 do{
                                    sc.nextLine();
                                    System.out.println("PIN does not match.");
                                    System.out.print("Enter your current PIN: ");
                                    oldPIN=sc.nextInt();
                                 } while(!customer.checkPIN(oldPIN));
                              }
                           //ask for new pin
                              System.out.print("Enter new PIN(*PIN can't start with 0*): ");
                              newPIN1=sc.nextInt();
                              System.out.print("Re-enter new PIN: ");
                              newPIN2=sc.nextInt();
                           //cheque of the 2 new pins entered are the same
                              if(newPIN1!=newPIN2){
                                 do{
                                    sc.nextLine();
                                    System.out.println("PIN does not match\n");
                                    System.out.print("Enter new PIN(*PIN can't start with 0*): ");
                                    newPIN1=sc.nextInt();
                                    System.out.print("Re-enter new PIN: ");
                                    newPIN2=sc.nextInt();
                                 } while(newPIN1!=newPIN2);
                              }
                           //change pin
                              customer.changePIN(newPIN2);
                              customer.savePersonalInfo();
                              System.out.println("PIN has been reset.\n");
                           
                              break;
                        
                           case 2: //change name
                              int choice;
                              System.out.println("Select one of the following:");
                              System.out.print("1. Change First name\n2. Change Last name\n3. Change both name\n>> ");
                              choice=sc.nextInt();
                           
                              if(choice==1){//change first name only
                                 sc.nextLine();
                                 System.out.print("Enter the new first name: ");
                                 newFirst1=sc.nextLine();
                                 System.out.print("Re-enter the new first name to verify: ");
                                 newFirst2=sc.nextLine();
                                 customer.checkFirstName(newFirst1,newFirst2);
                              }
                              
                              else if(choice==2){//change last name only
                                 sc.nextLine();
                                 System.out.print("Enter the new last name: ");
                                 newLast1=sc.nextLine();
                                 System.out.print("Re-enter the new last name to verify: ");
                                 newLast2=sc.nextLine();
                              
                                 customer.checkLastName(newLast1,newLast2);
                              
                              }
                              else{ // change both
                                 sc.nextLine();
                                 System.out.print("Enter the new first name: ");
                                 newFirst1=sc.nextLine();
                                 System.out.print("Re-enter the new first name to verify: ");
                                 newFirst2=sc.nextLine();
                                 customer.checkFirstName(newFirst1,newFirst2);
                              
                                 System.out.print("Enter the new last name: ");
                                 newLast1=sc.nextLine();
                                 System.out.print("Re-enter the new last name to verify: ");
                                 newLast2=sc.nextLine();
                                 customer.checkLastName(newLast1,newLast2);
                              }
                        }
                     }
                     else if (trans==6){//if exit
                        customer.savePersonalInfo();
                     } 
                  } while(trans!=6||trans>6||trans<1);//when to exit
               }
               else if (acc==3){//out put exit message
                  System.out.println("Thank you");
               }
               else{
                  System.out.println("Wrong input, please enter again");
               }
            }while(acc!=3);//when to exit
         }
      } // main method  
   } // class