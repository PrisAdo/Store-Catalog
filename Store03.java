//Store02.java
//netID: pga160130
//Priscilla Adomako
//Final Project (catalog and shop)

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


interface Validation{
	public boolean isNonEmptyString(String s);
	public boolean isPositiveInput(double d);
}

public class Store03 implements Validation {
	
	public static void main (String[] args){
		ArrayList<Book> BOOK_CAT = new ArrayList<Book>();
		ArrayList<DVD> DVD_CAT=new ArrayList<DVD>();
		String choice="";
		
		while(!choice.equals("C")){        
			
			System.out.println("**Welcome to the Comets Books and DVDs Store**\r\n" +   //menu options
					"Please select your role:\r\n" + 
					"A ~ store manager\r\n" + 
					"B ~ customer\r\n" + 
					"C ~ exit store");
			
			
			Store03 inst=new Store03(); //instance of class
			
			Scanner input=new Scanner(System.in);
			choice=input.nextLine();
			
			while(!choice.equals("A") && !choice.equals("B") && !choice.equals("C") || !inst.isNonEmptyString(choice)) {           //call validation	
				System.out.println("Invalid input. Please select proper role:\r\n " +
					    "A ~ store manager\r\n" + 
						"B ~ customer\r\n" + 
						"C ~ exit store\r");
				input=new Scanner(System.in);
				choice=input.nextLine();
			}
			
			switch(choice.charAt(0)) {                                           //options
			case 'A': A(BOOK_CAT, DVD_CAT);
					  break;
			case 'B':
					Shopping(BOOK_CAT, DVD_CAT);
				//ADD STUFF HERE
					  break;
			case 'C':
				System.out.println("Exiting Store.");
			}
		}
	}
	
	static void A(ArrayList<Book> BC, ArrayList<DVD> DC){                                                         //option A
		String UN;
		String PW;
		Scanner input=new Scanner(System.in);
		Store03 inst=new Store03(); //instance of class
		
		System.out.println("Please enter your username: ");                  //ask for username
		UN=input.nextLine();
		
		while(!inst.isNonEmptyString(UN)){
		System.out.println("Please enter your username: ");
		input=new Scanner(System.in);
		UN=input.nextLine();
		}
		
		System.out.println("Please enter your password: ");                 //ask for password
		PW=input.nextLine();
		
		while(!inst.isNonEmptyString(PW)){
		System.out.println("Please enter your password: ");
		input=new Scanner(System.in);
		PW=input.nextLine();
		}
		
		String INFO= UN+","+PW;
		
		
		boolean credsfound=inst.Search_creds(INFO); //look through 'credentials.txt' file 
		
		if(credsfound)
			CatalogSection(BC, DC);      //call catalog section		
		else
			System.out.println("Unrecognized Credentials");
		
		
	}
	
	boolean Search_creds(String INF) {
		boolean result=false;
		File file=new File("credentials.txt");
		
		
		try {
			 Scanner scan=new Scanner(file);
			 
			 while(scan.hasNext()) {                      //search through text in text file
				
				 String Line=scan.nextLine();
				 if(Line.contains(INF)) {
					 result=true;
					 break;
				 } 	
				 else
					result=false;
			 }
		}
		catch(FileNotFoundException F) {
			System.out.println(F);
		}
		catch(Exception E) {                               //generic exception caught
			System.out.println(E);
			
		}

		return result;
	}
	
	//**********************************************CATALOG********************************************
	
	abstract class CatalogItem {                   //abstract class
		private String title;
		private Double price;
		
		public CatalogItem() {                     //constructor
		}
		
		
		public void setTitle(String t){
			title=t;
		}
		
		public void setPrice(double p) {
			price=p;
		}
		
		public String getTitle() {
			return title;
		}
		
		public Double getPrice(){
			return price;                        
		}
		
	}
	
	static void CatalogSection(ArrayList<Book> BOOK_CAT, ArrayList<DVD> DVD_CAT) {            //CATALOG SECTION
		boolean option09=false;
		int choice=0;
		
		Store03 I=new Store03();
		
		
		while(option09!=true) {
			DisplayOptions();
			Scanner Input=new Scanner(System.in);
			choice=Input.nextInt();	
			switch(choice) {
			case 1:
				I.AddBook(BOOK_CAT, false);                   //add book
				break;
			case 2:
				I.AddBook(BOOK_CAT, true);                    //add audio book
				break;
			case 3:
				I.AddDVD(DVD_CAT);                            //add dvd
				break;
			case 4:
				I.RemoveBook(BOOK_CAT);                       //remove book
				I.DisplayCat(BOOK_CAT, DVD_CAT);
				break;
			case 5: 
				I.RemoveDVD(DVD_CAT);
				I.DisplayCat(BOOK_CAT, DVD_CAT);             //remove dvd
				break;
			case 6:
				I.DisplayCat(BOOK_CAT, DVD_CAT);             //display catalog
				break;
			case 7:
				I.BackupFile(BOOK_CAT, DVD_CAT);
				System.out.println("Backup created");        //create backup
				break;
			case 9:                                          //done
				option09=true;
				break;
			default:
				System.out.println("Invaild otpion.");
			}
		}	
	}
	
	public static void DisplayOptions(){                                    //DisplayOptions Method
		System.out.println("Choose from one of the following options");
		System.out.println("1 ~ Add Book");
		System.out.println("2 ~ Add AudioBook");
		System.out.println("3 ~ Add DVD");
		System.out.println("4 ~ Remove Book");
		System.out.println("5 ~ Remove DVD");
		System.out.println("6 ~ Display Catalog");
		System.out.println("7 ~ Create backup file");
		System.out.println("9 ~ Exit Catalog section");
		}
	
	//ADD BOOK METHOD
	
	public void AddBook(ArrayList<Book> BC, boolean isAudio) {       
		Store03 inst=new Store03();
		int ISBN=0;
		boolean ISBNexists=false;
		String title=null, author=null;
		Scanner In=new Scanner(System.in);
		System.out.println("Please enter the ISBN of the book: ");
		
		
		while(!inst.isNonString(In, true)) {
			System.out.println("Please enter valid ISBN of the book: ");
			In=new Scanner(System.in);
		}
		
		ISBN=In.nextInt();
		
		while(!inst.isNonEmptyString(String.valueOf(ISBN)) && !inst.isPositiveInput(ISBN)){   //valid ISBN
			System.out.println("Please enter the valid ISBN of the book: ");
			In=new Scanner(System.in);
			ISBN=In.nextInt();
		}
		
		for(int x=0; x<BC.size(); x++) {                          //searching for existing ISBN
			
			if(BC.get(x).getISBN()==ISBN)
				ISBNexists=true;
		}
		
		
		if (ISBNexists==true) {                         
			System.out.println("Book already exists.");
		}
		else {																   //if ISBN doesn't exist	                                         
			Double price=0.0;  												   //ask for other info
			Double runTime=0.0;
			System.out.println("Please enter book title: ");     
			
			In=new Scanner(System.in);
			
			title=In.nextLine();	
			while(!inst.isNonEmptyString(title)) {                             //valid title
				System.out.println("Please enter valid book title: ");
				In=new Scanner(System.in);
				title=In.nextLine();	
			}
			
			System.out.println("Please enter book author: ");
			
			author=In.nextLine();
			while(!inst.isNonEmptyString(author)) {                             //valid author
				System.out.println("Please enter valid book author: ");
				In=new Scanner(System.in);
				author=In.nextLine();	
			}
			
			System.out.println("Please enter book price: ");
			
			while(!inst.isNonString(In, false)) {
				System.out.println("Please enter valid book price: ");
				In=new Scanner(System.in);
			}
			
			price=In.nextDouble();
			while(!inst.isNonEmptyString(String.valueOf(price)) && !inst.isPositiveInput(price) && In.hasNextDouble()){    //valid price
				System.out.println("Please enter valid book price: ");
				In=new Scanner(System.in);
				price=In.nextDouble();
			}
			
			if(isAudio==true) {                                     //if book is AudioBook
				System.out.println("Please enter run time: ");
				
				
				while(!inst.isNonString(In, false)) {
					System.out.println("Please enter valid run time: ");
					In=new Scanner(System.in);
				}
				runTime=In.nextDouble();
				
				while(!inst.isNonEmptyString(String.valueOf(runTime)) && !inst.isPositiveInput(runTime) && In.hasNextDouble()){    //valid price
					System.out.println("Please enter valid run time: ");
					In=new Scanner(System.in);
					price=In.nextDouble();
				}
			}
			
			if(isAudio==false)
				BC.add(new Book(title,author,ISBN,price));
			else
				BC.add(new AudioBook(runTime, title,author, ISBN, price));
			
		}
	}
	
	//ADD DVD METHOD
	
	public void AddDVD(ArrayList<DVD> DC) {                       
		Store03 inst=new Store03();
		int dvdCode=0;
		boolean dvdCodeExists=false;
		String title=null, director=null;
		int year=0;
		Scanner In=new Scanner(System.in);
		
		System.out.println("Please enter DVD code: ");
		
		while(!inst.isNonString(In, true)) {
			System.out.println("Please enter valid DVD code: ");
			In=new Scanner(System.in);
		}
		
		dvdCode=In.nextInt();
		
		while(!inst.isNonEmptyString(String.valueOf(dvdCode)) && !inst.isPositiveInput(dvdCode) && In.hasNextInt()){
			System.out.println("Please enter valid DVD code: ");
			In=new Scanner(System.in);
			dvdCode=In.nextInt();
		}
		
		for(int x=0; x<DC.size(); x++) {                       //search for existent dvd code
			if(DC.get(x).getDVDCode()==dvdCode)
			dvdCodeExists=true;
		}
		
		if (dvdCodeExists==true) {
			System.out.println("DVD already exists.");
		}
		else {
			Double price=0.0;  												   //ask for other info
			System.out.println("Please enter DVD title: ");
			
			In=new Scanner(System.in);
			
			title=In.nextLine();	
			while(!inst.isNonEmptyString(title)) {                             //valid title
				System.out.println("Please enter valid DVD title: ");
				In=new Scanner(System.in);
				title=In.nextLine();	
			}
			
			System.out.println("Please enter DVD director: ");
			
			director=In.nextLine();
			while(!inst.isNonEmptyString(director)) {                             //valid director
				System.out.println("Please enter valid DVD director ");
				In=new Scanner(System.in);
				director=In.nextLine();	
			}
			
			System.out.println("Please enter DVD price: ");
			
			while(!inst.isNonString(In, false)) {
				System.out.println("Please enter valid DVD price: ");
				In=new Scanner(System.in);
			}
			
			price=In.nextDouble();
			
			while(!inst.isNonEmptyString(String.valueOf(price)) && !inst.isPositiveInput(price) && In.hasNextDouble()){    //valid dvd price
				System.out.println("Please enter valid DVD price: ");
				In=new Scanner(System.in);
				price=In.nextDouble();
			}
			
			System.out.println("Please enter year: ");
			
			while(!inst.isNonString(In, true)) {
				System.out.println("Please enter valid year: ");
				In=new Scanner(System.in);
			}
			
			year=In.nextInt();
			
			while(!inst.isNonEmptyString(String.valueOf(year)) && !inst.isPositiveInput(year) && In.hasNextInt()){    //valid year
				System.out.println("Please enter valid year: ");
				In=new Scanner(System.in);
				year=In.nextInt();
			}
			
			DC.add(new DVD(title, director, price, year, dvdCode));
		}
	}
	
	//REMOVE BOOK METHOD
	
	public void RemoveBook(ArrayList<Book> BC) {
		Store03 inst=new Store03();
		int ISBN=0;
		boolean ISBNmatch=false;
		boolean ISBNexists=false;
		int x=0;
		int index=0;
		Scanner In= new Scanner(System.in);
		
		System.out.println("Enter ISBN number: ");
		
		ISBN=In.nextInt();
		
		while(!inst.isNonEmptyString(String.valueOf(ISBN)) && !inst.isPositiveInput(ISBN)){   //valid ISBN
			System.out.println("Please enter the  ISBN of the book: ");
			In=new Scanner(System.in);
			ISBN=In.nextInt();
		}
		
		for(int y=0; y<BC.size(); y++) {                       //check for existing ISBN
			if(BC.get(y).getISBN()==ISBN)
				ISBNexists=true;
				
		}
		
		if (ISBNexists==false) {
			System.out.println("Book doesn't exist.");
		}
		else {
			while(x<BC.size() && ISBNmatch==false) {          
				if(ISBN==BC.get(x).getISBN()) {
					ISBNmatch=true;
					index=x;
				}
				x++;
		}
			BC.remove(index);                                      //remove book info if ISBN exists
		}
	}
	
	//REMOVE DVD METHOD
	
	public void RemoveDVD(ArrayList<DVD> DC) {
		Store03 inst=new Store03();
		int dvdCode=0;
		boolean validInput=false;
		boolean DCmatch=false;
		boolean DCexists=false;
		int x=0;
		int index=0;
		Scanner In= new Scanner(System.in);
		
		System.out.println("Enter DVD code number: ");
		
		dvdCode=In.nextInt();
		
		while(!inst.isNonEmptyString(String.valueOf(dvdCode)) && !inst.isPositiveInput(dvdCode)){   //valid DVD code
			System.out.println("Please enter DVD code: ");
			In=new Scanner(System.in);
			dvdCode=In.nextInt();
		}
		
		for(int y=0; y<DC.size(); y++) {												//check for existing DVD code
			if(DC.get(y).getDVDCode()==dvdCode)
			DCexists=true;
		}
		
		if (DCexists==false) {
			System.out.println("DVD doesn't exists.");
		}
		else {
		
			while(x<DC.size() && DCmatch==false) {
				if(dvdCode==DC.get(x).getDVDCode())
					DCmatch=true;
					index=x;
		}
			DC.remove(index);                                           //remove DVD info for existing DVD code
		}
	}
	
	//DISPLAY CATALOG METHOD
	
	public void DisplayCat(ArrayList<Book> BC, ArrayList<DVD> DC) {
		
		for(int x=0; x<BC.size(); x++){                                   //list Books
			
				System.out.println(BC.get(x).toString());
		}
		
			System.out.println("-----------------------------------------------------------------------------------------------------------------");
			
			for(int y=0; y<DC.size(); y++)                                //list DVD
				System.out.println(DC.get(y).toString());
				
	}
	
	//BACKUP FILE METHOD
	
	public void BackupFile(ArrayList<Book> BC, ArrayList<DVD> DC) {
		
		Date D=new Date();
		SimpleDateFormat DForm=new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		
		String BackupFile="catalog_backup_"+DForm.format(D)+".txt";  //insert file name
		File backup=new File(BackupFile);
		try {
			PrintWriter PW =new PrintWriter(backup);
			for(int x=0; x<BC.size(); x++)                                 //list Books in file
				PW.println(BC.get(x).toString());
			
				PW.println("-----------------------------------------------------------------------------------------------------------------");
				
			for(int y=0; y<DC.size(); y++)                                //list DVD in file
				PW.println(DC.get(y).toString());
				
			PW.close();
		}
		catch(FileNotFoundException F) {
			System.out.println(F);
		}
		catch(Exception S) {
			System.out.println(S);
		}
	}
	

	
    //***********************************************************************************************
	
    //*************************************SHOPPING*************************************************
	
	static void Shopping(ArrayList<Book> BC, ArrayList<DVD> DC){
		boolean option09=false;
		int choice;
		int InvOption;
		Double totalPrice;
		Store03 I=new Store03();
		ArrayList<Book> BookCART=new ArrayList<Book>();
		ArrayList<DVD> DVDCART=new ArrayList<DVD>();
		int x=0;
		int y=0;
		int t=0;
		
		while(option09!=true) {
			Display_Options();                              //call options display
			Scanner Input=new Scanner(System.in);
			choice=Input.nextInt();	
			
			switch(choice) {
			case 1:                                        //display books
				if (BC.size()==0)
					System.out.println("There are currently no books listed.");
				else
					I.DisplayBooks(BC);
				break;
			case 2:
				if (DC.size()==0) 
					System.out.println("There are currently no DVDs listed.");
				else
					I.DisplayDVDs(DC);                         //display dvds
				break;
			case 3:
				if (BC.size()==0)
					System.out.println("There are currently no books on sale.");
				else {
					System.out.println("Enter inventory number of the book item you would like to add: ");
					InvOption=getInventoryNumber(BC, DC, 1);                   //add Book to cart
					I.AddBookToCart(BookCART, BC, InvOption, x, t);
					x++;
					t++;
				}
				break;
			case 4:
				if(DC.size()==0) 
					System.out.println("There are currently no DVDs on sale.");
				else {
					System.out.println("Enter inventory number of the DVD item you would like to add: ");
					InvOption=getInventoryNumber(BC, DC, 2);                   //add DVD to cart
					I.AddDVDToCart(DVDCART, DC, InvOption, y, t);
					y++;
					t++;
				}
				break;
			case 5: 
				if (BookCART.size()==0)
					System.out.println("There are currently no books in your cart");
				else {
					Input=new Scanner(System.in);
					System.out.println("Enter inventory number of the Book item you would like to remove:");
					InvOption=getInvNum(BookCART, DVDCART, 1);                  //remove Book from cart
					BookCART.remove(InvOption-1);
					x--;
				}
				break;
			case 6:
				if(DVDCART.size()==0)
					System.out.println("There are currently no DVDs in your cart");
				else {
					Input=new Scanner(System.in);
					System.out.println("Enter inventory number of the DVD item you would like to remove:");
					InvOption=getInvNum(BookCART, DVDCART, 2);                  //remove DVD from cart
					DVDCART.remove(InvOption-1);
					y--;
				}
				break;
			case 7:
				if(BookCART.size()!=0 || DVDCART.size()!=0) {
					I.DisplayCart(BookCART, DVDCART);
					I.getTotal(BookCART, DVDCART);
				}
				else
					System.out.println("Your cart is empty.");
				break;
			case 8:
				totalPrice=I.getTotal(BookCART, DVDCART);
				System.out.format("%s%.2f%n", "Total Price: $", totalPrice);
				System.out.println("");
				BookCART.clear();
				DVDCART.clear();
				break;
			case 9:
				option09=true;
			}
		}
		
	}
	
	//DISPLAY OPTIONS METHOD
	
	public static void Display_Options(){
		System.out.println("Choose from one of the following options");
		System.out.println("1 ~ Browse books inventory (price low to high)\r\n" + 
				"2 ~ Browse DVDs inventory (price low to high)\r\n" + 
				"3 ~ Add a book to the cart\r\n" + 
				"4 ~ Add a DVD to the cart\r\n" + 
				"5 ~ Delete a book from cart\r\n" + 
				"6 ~ Delete a DVD from cart\r\n" + 
				"7 ~ View cart\r\n" + 
				"8 ~ Checkout\r\n" + 
				"9 ~ Done Shopping"); 
		}
	
	//DISPLAY BOOK (LOW TO HIGH) METHOD
	
	public void DisplayBooks(ArrayList<Book> BC) {
		Store03 inst=new Store03();
		ArrayList<Book> BookCopy= new ArrayList<Book>();
	
		for(int x=0; x<BC.size(); x++) {           //make copy of BOOK arraylist
			BookCopy.add(BC.get(x));
			BookCopy.get(x).setTitle(BC.get(x).getTitle());
			BookCopy.get(x).setInvNum(x+1);
			BookCopy.get(x).setPrice(BC.get(x).getPrice()*BookCopy.get(x).getDiscount());
			BookCopy.get(x).setISBN(BC.get(x).getISBN());
			BookCopy.get(x).setAuthor(BC.get(x).getAuthor());
		}
		
		for (int i=1; i<BookCopy.size(); i++)                    //order copy by price
	    {
	        Double key = BookCopy.get(i).getPrice();
	        Book key02=BookCopy.get(i);
	        int j = i-1;

	        while(j>=0 && Compare(BookCopy.get(j).getPrice(), key)==true)
	        //while (j>=0 && BookCopy.get(j).getPrice()>key)
	        {
	            BookCopy.set(j+1, BookCopy.get(j));
	            j--;
	        }
	        BookCopy.set(j+1, key02);
	    }
		
		
		System.out.println("Inventory Number   Book              Author        Prices     ISBN");
		System.out.println("-------------------------------------------------------------------");
		
		for(int i=0; i<BookCopy.size(); i++)                                   //print out books
		{
			System.out.format("%-19d", BookCopy.get(i).getInvNum());
			System.out.format("%-16s", BookCopy.get(i).getTitle());
			System.out.format("%-16s", BookCopy.get(i).getAuthor());
			System.out.format("%s%-10.2f","$", BookCopy.get(i).getPrice());
			System.out.format("%-16d%n", BookCopy.get(i).getISBN());
		}
		
		
		//System.out.println(BookCopy.get(0).getISBN()+","+BookCopy.get(0).getPrice());
		//System.out.println(BC.get(0).getISBN());
	}
	
	//DISPLAY DVD (LOW TO HIGH) METHOD
	
	public void DisplayDVDs(ArrayList<DVD> DC) {
		ArrayList<DVD> DVDCopy= new ArrayList<DVD>();
		
		for(int x=0; x<DC.size(); x++) {           //make copy of DVD arraylist
			DVDCopy.add(DC.get(x));
			DVDCopy.get(x).setTitle(DC.get(x).getTitle());
			DVDCopy.get(x).setInvNum(x+1);
			DVDCopy.get(x).setPrice(DC.get(x).getPrice()*DVDCopy.get(x).getDiscount());
			DVDCopy.get(x).setDirector(DC.get(x).getDirector());
			DVDCopy.get(x).setDVDCode(DC.get(x).getDVDCode());
		}
		
		for (int i=1; i<DVDCopy.size(); i++)                    //order copy by price
	    {
	        Double key = DVDCopy.get(i).getPrice();
	        DVD key02=DVDCopy.get(i);
	        int j = i-1;
	        
	        while(j>=0 && Compare(DVDCopy.get(j).getPrice(), key)==true)
	        //while (j>=0 && DVDCopy.get(j).getPrice()>key)
	        {
	            DVDCopy.set(j+1, DVDCopy.get(j));
	            j--;
	        }
	        DVDCopy.set(j+1, key02);
	    }
		
		System.out.println("Inventory Number   DVD             Director        Prices     DVD Code");
		System.out.println("-----------------------------------------------------------------------");
		
		for(int i=0; i<DVDCopy.size(); i++)                                   //print out DVDs
		{
			System.out.format("%-19d", DVDCopy.get(i).getInvNum());
			System.out.format("%-16s", DVDCopy.get(i).getTitle());
			System.out.format("%-16s", DVDCopy.get(i).getDirector());
			System.out.format("%s%-10.2f","$", DVDCopy.get(i).getPrice());
			System.out.format("%-16d%n", DVDCopy.get(i).getDVDCode());
		}
	}
	
	//ADD BOOK TO CART METHOD
	public void AddBookToCart(ArrayList<Book> BookC, ArrayList<Book>BC, int invNum, int x, int t) {
		
		//System.out.println(BookC.get(invNum-1).setTitle(BC.get(invNum-1).getTitle()););
		/*BookC.add(BC.get(invNum-1));
		BookC.get(x).setTitle(BC.get(invNum-1).getTitle());
		BookC.get(x).setAuthor(BC.get(invNum-1).getAuthor());
		BookC.get(x).setISBN(BC.get(invNum-1).getISBN());
		BookC.get(x).setPrice(BC.get(invNum-1).getPrice());
		BookC.get(x).setInvNum(x+1);
		*/
		
		BookC.add(new Book(BC.get(invNum-1).getTitle(),BC.get(invNum-1).getAuthor(),BC.get(invNum-1).getISBN(),BC.get(invNum-1).getPrice()));
		BookC.get(x).setInvNum(t+1);
		}
	
	//ADD DVD TO CART METHOD
	public void AddDVDToCart(ArrayList<DVD> DVDC, ArrayList<DVD>DC, int invNum, int y, int t) {
		/*DVDC.add(DC.get(invNum-1));
		DVDC.get(y).setTitle(DC.get(invNum-1).getTitle());
		DVDC.get(y).setPrice(DC.get(invNum-1).getPrice());
		DVDC.get(y).setDirector(DC.get(invNum-1).getDirector());
		DVDC.get(y).setDVDCode(DC.get(invNum-1).getDVDCode());
		DVDC.get(y).setInvNum(y+1);*/
		
		DVDC.add(new DVD(DC.get(invNum-1).getTitle(), DC.get(invNum-1).getDirector(), DC.get(invNum-1).getPrice(), DC.get(invNum-1).getYear(), DC.get(invNum-1).getDVDCode()));
		DVDC.get(y).setInvNum(t+1);
	}
	
	//DISPLAY CART METHOD
	public void DisplayCart(ArrayList<Book> BookC, ArrayList<DVD> DVDC) {
		int z=0;
		
		System.out.println("YOUR CART");
		for(int i=0; i<BookC.size(); i++)                                   //print out Books
		{
			//System.out.format("%-19d", BookC.get(i).getInvNum());
			System.out.format("%-19d", z+1);
			System.out.format("%-16s", BookC.get(i).getTitle());
			System.out.format("%s%-10.2f%n%n","$", BookC.get(i).getPrice());
			z++;
		}
		
		for(int i=0; i<DVDC.size(); i++)                                   //print out Books
		{
			//System.out.format("%-19d", DVDC.get(i).getInvNum());
			System.out.format("%-19d", z+1);
			System.out.format("%-16s", DVDC.get(i).getTitle());
			System.out.format("%s%-10.2f%n%n","$", DVDC.get(i).getPrice());
			z++;
		}

	}
	
	//get total in cart method
	
	
	
	public Double getTotal(ArrayList<Book> BookC, ArrayList<DVD> DVDC) {
		Double totalsum=0.0;
		
		for(int z=0; z<BookC.size(); z++) {
			totalsum+=BookC.get(z).getPrice();
		}
		
		for(int z=0; z<DVDC.size(); z++) {
			totalsum+=DVDC.get(z).getPrice();
		}
		
		return totalsum;
	}
	
	//Get inventory number method
	
	public static int getInventoryNumber(ArrayList<Book> BC, ArrayList<DVD> DC, int BorD) {                                                                          //get book option
		Scanner input=new Scanner(System.in);
		Store03 inst=new Store03();
		
		System.out.println("Enter -1 to redisplay items.");
		
		while(!inst.isNonString(input, true)) {                                //call a validation method
			System.out.println("Please enter valid option: ");
			input=new Scanner(System.in);
		}
		
		int invChoice = input.nextInt();
		
		
		while (!inst.isNonEmptyString(String.valueOf(invChoice)) || (invChoice<1 || invChoice>BC.size()+1))
		{
			
			if (invChoice==-1) {
				if(BorD==1) {
					inst.DisplayBooks(BC);
					System.out.println("Enter inventory number of the book item: ");
					System.out.println("Enter -1 to redisplay items.");
				}
				else if (BorD==2) {
					inst.DisplayDVDs(DC);
					System.out.println("Enter inventory number of the dvd item: ");
					System.out.println("Enter -1 to redisplay items.");
				}
				input=new Scanner(System.in);
				invChoice=input.nextInt();
			}
			else {
				System.out.println("Please enter valid option.");
				input=new Scanner(System.in);
				invChoice=input.nextInt();
			}
			
		}
		
		System.out.println(invChoice);
		
		return invChoice;                                           //return inventory choice
	
	}
	
	
	public static int getInvNum(ArrayList<Book> BC, ArrayList<DVD> DC, int BorD) {
		Scanner input=new Scanner(System.in);
		Store03 inst=new Store03();
		
		System.out.println("Enter -1 to redisplay items.");
		
		while(!inst.isNonString(input, true)) {                                //call a validation method
			System.out.println("Please enter valid option: ");
			input=new Scanner(System.in);
		}
		
		int invChoice = input.nextInt();
		
		
		/*while (!inst.isNonEmptyString(String.valueOf(invChoice)) || (invChoice<1 || invChoice>BC.size()+1))
		{
			
			if (invChoice==-1) {
				if(BorD==1) {
					inst.DisplayCart(BC, DC);
					System.out.println("Enter cart inventory number of the book item you would like to remove: ");
					System.out.println("Enter -1 to redisplay items.");
				}
				else if (BorD==2) {
					inst.DisplayDVDs(DC);
					System.out.println("Enter cart inventory number of the dvd item you would like to remove: ");
					System.out.println("Enter -1 to redisplay items.");
				}
				input=new Scanner(System.in);
				invChoice=input.nextInt();
			}
			else {
				System.out.println("Please enter valid option.");
				input=new Scanner(System.in);
				invChoice=input.nextInt();
			}
			
		}*/
		
		
			if(BorD==1) {
				while (!inst.isNonEmptyString(String.valueOf(invChoice)) || (invChoice<1 || invChoice>BC.size()+1)) {
					if (invChoice==-1) {
					inst.DisplayCart(BC, DC);
					System.out.println("Enter cart inventory number of the book item you would like to remove: ");
					System.out.println("Enter -1 to redisplay items.");
					
					input=new Scanner(System.in);
					invChoice=input.nextInt();
					}
					else {
						System.out.println("Please enter valid option.");
						input=new Scanner(System.in);
						invChoice=input.nextInt();
					}
				}
				
			}
		
			
			if(BorD==2) {
				while (!inst.isNonEmptyString(String.valueOf(invChoice)) || (invChoice<1 || invChoice>DC.size()+1)) {
					if (invChoice==-1) {
					inst.DisplayCart(BC, DC);
					System.out.println("Enter cart inventory number of the book item you would like to remove: ");
					System.out.println("Enter -1 to redisplay items.");
					
					input=new Scanner(System.in);
					invChoice=input.nextInt();
					}
					else {
						System.out.println("Please enter valid option.");
						input=new Scanner(System.in);
						invChoice=input.nextInt();
					}
				}
				
			}
			
		System.out.println(invChoice);
		
		return invChoice;                                           //return inventory choice
	
	}
	//**********************************************************************************************
	
	
	class Book extends CatalogItem{                         //BOOK CLASS
		private String Title;
		private Double Price;
		private String author;
		private int ISBN ;                                  //unique for each book
		private Double discount=0.9;
		private int InvNum;
		
		public Book() {
		
		}
		
		public Book(String T, String A, int I, double p) {                     //constructor
			Title=T;
			author=A; 
			ISBN=I;
			Price=p;
		}
		
		
		public void setTitle(String t){
			Title=t;
		}
		
		public void setPrice(double p) {
			Price=p;
		}
		
		public void setInvNum(int In) {
			InvNum=In;
		}
		
		public void setISBN(int I) {
			ISBN=I;
		}
		
		public void setAuthor(String A) {
			author=A;
		}
		
		public String getTitle() {
			return Title;
		}
		
		public Double getPrice(){                                   //getPrice Method
			return Price;                        
		}
		
		public int getISBN() {
			return ISBN;
		}
		
		public int getInvNum() {
			return InvNum;
		}
		
		public String getAuthor() {
			return author;
		}
		
		public Double getDiscount() {
			return discount;
		}
		
		public String toString() {
			
			String info="Title: "+Title+" | Author: "+author+" | Price: $"+Price+" | ISBN: "+ISBN;
			
			return info;
		}
	}

	class AudioBook extends Book{                                   //AUDIOBOOK CLASS
		private double runningTime;
		private Double discount=0.5;
		Book B;
		
		public AudioBook(){
			
		}
		
		public AudioBook(double RT, String title, String author, int I, double p ) { 					   //constructor
			B=new Book(title,author, I, p);
			runningTime=RT;
		}
		
		public String getTitle() {
			return B.getTitle();
		}
		
		public String getAuthor() {
			return B.getAuthor();
		}
		
		public Double getPrice(){
			B.getPrice();
			return (B.getPrice()*0.90);                         //discount           
		}
		
		public int getISBN() {
			return B.getISBN();
		}
		
		public Double getDiscount() {
			return discount;
		}
		public String toString() {
			String info=B.toString()+ " | RunningTime: "+runningTime;
			return info;
		}
	}

	class DVD extends CatalogItem{                              //DVD CLASS
		private String director;
		private int year;
		private int dvdcode;                                    //unique for each dvd
		
		private String title;
		private Double price;
		private int InvNum;
		private Double discount=0.8;
		
		public DVD(String T, String D, double P, int Y, int DC) {            		//constructor
			title=T;
			price=P;
			director=D;
			year=Y;
			dvdcode=DC;
		}
		
		public void setTitle(String t){
			title=t;
		}
		
		public void setPrice(double p) {
			price=p;
		}
		
		public void setInvNum(int In) {
			InvNum=In;
		}
		
		public void setDirector(String d) {
			director=d;
		}
		
		public void setDVDCode(int i) {
			dvdcode=i;
		}
		
		public void setDiscount(Double d) {
			discount=d;
		}
		
		public String getTitle() {
			return title;
		}
		
		public Double getPrice(){                                   //getPrice Method
			return price;                        
		}
		
		public int getDVDCode() {
			return dvdcode;
		}

		public int getInvNum() {
			return InvNum;
		}
		
		public String getDirector() {
			return director;
		}
		
		public Double getDiscount() {
			return discount;
		}
		
		public int getYear() {
			return year;
		}
		
		public String toString() {
			String info="Title: "+title+" | Director: "+director+" | Price: $"+price+" | Year: "+year+" | DvdCode: "+dvdcode;
			return info;
		}
	//***********************************************************************************************
	

		
	}
  
	
   //*****************************************VALIDATION********************************************
	
	public boolean isNonEmptyString(String s) {
			
			if(!s.isEmpty())
				return true;
			else
				System.out.println("Invalid input. Nothing entered.");
				return false;
			
		}
	public boolean isPositiveInput(double d) {
			if(d>0) 
				return true;
			else
				System.out.println("Invalid input. Non-positive input.");
				return false;
	}
	
	public boolean isNonString(Scanner i, boolean B) {
		if (i.hasNextInt() && B==true )
			return true;
		else if (i.hasNextDouble() && B==false)
			return true;
		else
			return false;
	}

	public static <Double extends Comparable<Double>> boolean Compare(Double o1, Double o2) {
		if (o1.compareTo(o2)==1)
			return true;
		else
			return false;
	}
}

