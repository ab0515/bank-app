package application;
/*Class Name:Client
 Author: Seohee Jung,John Dong, Benn Xu
 Date:June.8th
 School: AY Jackson
 Purpose: A class that contains all information of a client
 */  	
    class Client{
    //variables
      private String firstN;
      private String lastN;
      private int month;
      private int year;
      private int date;
      private String cardNum;
      private int pin;
      private String gender;
      private int num;//number of transactions
      private Account []list; 
   
   //constructor
       public Client (String firstN,String lastN, String gender, int month, int year, int date, String cardNum, int pin, int num,double savings,double chequing ){
         this.firstN=firstN;
         this.lastN=lastN;
         this.gender=gender;
         this.month=month;
         this.year=year;
         this.date=date;
         this.cardNum=cardNum;
         this.pin=pin;
         this.num=num;
         list=new Account[2];
      		
      	//creating the client with the number of accounts they asked to create
         for (int i=0;i<num;i++){
            if (i==0){
               list[i]=new Savings(savings,num);
            }
            else if (i==1){
               list[i]=new Chequing(chequing);
            }
         }
      }
   //accessors and mutators
       public String getFirstN(){
         return firstN;
      }
       public void setFirstN(String firstN){
         this.firstN=firstN;
      }
       public String getLastN(){
         return lastN;
      }
       public void setLastN(String lastN){
         this.lastN=lastN;
      }
       public int getMonth(){
         return month;
      }
       public void setMonth(int month){
         this.month=month;
      }
       public int getYear(){
         return year;
      }
       public void setYear(int year){
         this.year=year;
      }
       public int getDate(){
         return date;
      }
       public void setDate(int date){
         this.date=date;
      }
       public String getCardNum(){
         return cardNum;
      }
       public void setCardNum(String cardNum){
         this.cardNum=cardNum;
      }
       public int getPIN(){
         return pin;
      }
       public void setPIN(int pin){
         this.pin=pin;
      }
       public String getGender(){
         return gender;
      }
       public void setGender(String gender){
         this.gender=gender;
      }
       public int getNum(){
         return num;
      }
       public void setNum(int num){
         this.num=num;
      }
       public double getSavingsBal(){
         return list[0].getBalance();   
      }
       public double getChequingBal(){
         return list[1].getBalance();
      }
   
       public Account getSavings(){
         return list[0];
      }
       public Account getChequing(){
         return list[1];
      }
      
   //a method that returns a string value of the information of the user
       public String toStringInfo(){
         return pin+"\n"+gender+"\n"+month+"\n"+date+"\n"+year+"\n"+firstN+"\n"+lastN+"\n"+list[0].getBalance()+"\n"+list[1].getBalance();//
      }
   //a method that returns a string value of the information of each account that the user has
       public String toStringAccount(){
         String value="";
         value+=num+"\n";
         for (int i=0;i<num;i++){
            value+=list[i].name()+"\n";
            value+=list[i].getBalance()+"\n";
         }
         return value;
      }
   
   
   
   
   
   }