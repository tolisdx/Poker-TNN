import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player 
{
	/**
	 * @uml.property  name="name"
	 */
	private String Name;// Το όνομα του παίχτη
	private static int ID=0;// Το ID του παίχτη (διαφορετικό για κάθε παίχτη του ίδιου τραπεζιού)
	/**
	 * @uml.property  name="stack"
	 */
	private int stack;// To stack του παίχτη
	/**
	 * @uml.property  name="max_combination"
	 */
	private int max_combination;// O καλύτερος συνδυασμός που έχει ο παίχτης
	/**
	 * @uml.property  name="folded"
	 */
	private boolean folded;// true εάν ο παίχτης έχει.....
	/**
	 * @uml.property  name="onSmallBLind"
	 */
	private boolean onSmallBLind;
	/**
	 * @uml.property  name="onBigBlind"
	 */
	private boolean onBigBlind;
	/**
	 * @uml.property  name="onDealer"
	 */
	private boolean onDealer;
	/**
	 * @uml.property  name="isPlaying"
	 */
	private boolean isPlaying;
	/**
	 * @uml.property  name="betted"
	 */
	protected int betted;// Τα χρήματα που έχει ο παίχτης μέσα στο stack
	/**
	 * @uml.property  name="choice"
	 */
	private int choice;
	/**
	 * @uml.property  name="has_played"
	 */
	private boolean has_played;
	
	/**
	 * @uml.property  name="hand"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="card"
	 */
	protected ArrayList<card> hand = new ArrayList<card>();// Το χέρι του παίχτη
	
	//Player(){}// Constructor
	
	Player(boolean automatic)// Constructor
	{
		hand.clear();
		betted = 0;// Αρχικά δεν έχει ποντάρει τίποτα
		folded = false;
		onSmallBLind = false;
		onBigBlind = false;
		onDealer = false;
		isPlaying = false;
		
		ID++;// Κάθε πάιχτης έχει ξεχωριστό ΙD
		Set_Stack(1500);// Θέτουμε το stack 1500 αρχικά για όλους
		
		if (!automatic)// Eάν τον παίχτη τον χειρίζεται φυσικό πρόσωπο
		{
			System.out.print("Please enter players name:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in) );
			try{Name = br.readLine();}catch(Exception e){}
			while(Name.equals(""))
			{
				System.out.print("Empty name field, try again:");
				try{Name = br.readLine();}catch(Exception e){}
			}
			
			Set_Name(Name);
			
		}
		else// Eάν τον παίχτη τον χειρίζεται ο υπολογιστής
		{
			// Του δίνουμε ένα τυχαίο όνομα από το αρχείο "names.txt"
			Random random = new Random();
			try
			{
				  FileInputStream fstream = new FileInputStream("names.txt");
				  DataInputStream in = new DataInputStream(fstream);
				  BufferedReader br = new BufferedReader(new InputStreamReader(in));
				  String strLine;
				  int name = random.nextInt(50);
				  while ((strLine = br.readLine()) != null)
				  {
					  if (name == 0)
					  {
						  Set_Name(strLine);
						  in.close();
						  break;
					  }
					  name--;
					  
				  }
				  in.close();
			}catch (Exception e){System.err.println("Error reading names.txt: " + e.getMessage());}
		}
	}
	
	// Get functions
	public int Get_Player_ID(){ return Player.ID; }
	public String Get_Name(){ return this.Name; }// Επιστρέφει το όνομα του παίχτη
	public int Get_Stack(){ return this.stack; }// Επιστρέφει το stack του παίχτη
	public void Print_Cards(){ for (int i=0;i<hand.size();i++)if (hand.get(i).open)System.out.print(hand.get(i).value + " "); }// Επιστρέφει τα φύλλα του παίχτη
	public int Get_Betted(){ return this.betted; }// Επιστρέφει πόσα έχει ποντάρει ο παίχτης μέχρι στιγμής
	public boolean Get_Folded(){ return this.folded; }// Επιστρέφει την κατάσταση fold του παίχτη
	public boolean Get_OnSmallBLind(){ return this.onSmallBLind; }// 
	public boolean Get_OnBigBLind(){ return this.onBigBlind; }// 
	public boolean Get_IsPlaying(){ return this.isPlaying; }// 
	public boolean Get_OnDealer(){ return this.onDealer; }// 
	public boolean Get_Has_Played(){ return has_played; }
	public int Get_Choice()// Επιστρέφει το choice του παίχτη
	{ 
		File f = new File("choice.txt");
		if (!f.exists())
			return -1;
		
		In input = new In ("choice.txt");
		choice = Integer.parseInt(input.readLine());
		input.close();
		
		Out output = new Out ("choice.txt");
		output.print(-1);
		output.close();
		
		return choice; 
	}
	
	// Set functions
	protected void Set_Name(String Name){ this.Name = Name; }// θέτει το όνομα του παίχτη
	protected void Set_Folded(boolean f){ this.folded = f; }// θέτει τον παίχτη folded
	protected void Set_OnSmallBLind(boolean f){ this.onSmallBLind = f; }// θέτει τον παίχτη 
	protected void Set_OnBigBLind(boolean f){ this.onBigBlind = f; }// θέτει τον παίχτη 
	protected void Set_IsPlaying(boolean f){ this.isPlaying = f; }// θέτει τον παίχτη 
	protected void Set_OnDealer(boolean f){ this.onDealer = f; }// θέτει τον παίχτη 
	protected void Set_Stack(int new_stack){ this.stack = new_stack; }// Επιστρέφει το stack του παίχτη
	protected void Set_Betted(int b){ this.betted = b; }// Επιστρέφει πόσα έχει ποντάρει ο παίχτης μέχρι στιγμής
	public void Set_Has_Played(boolean p){ this.has_played = p; }
	public void Set_Choice(int c)// θέτει το choice του παίχτη
	{ 
		Out output = new Out ("choice.txt");
		output.print(c);
		output.close();
	}

	// Bet
	protected void Bet(int bet_ammount)
	{
		Set_Stack(Get_Stack() - bet_ammount);// Αφαιρούνται από το stack του
		
		Set_Betted(Get_Betted() + bet_ammount);// Ενημερώνουμε πόσα έχει ποντάρει μέρχι στιγμής
		
		if (Get_Stack() < 0)
		{
			Set_Stack(0);
		}
	}
	
	/**
	 * Method that adds a card to the player
	 */
	public abstract boolean Set_Card(String c);
	/**
	 * Method that must exists to give a unique ID
	 * Just write
	 * protected int Get_ID()
	 * {
	 * return this.ID;
	 * }
	 */
	public abstract int Get_ID();
	/**
	 * Method that gets the max cards the player can have at this time
	 */
	public abstract int Get_Max_Cards();
	/**
	 * Method that sets the max cards the player can have at this time
	 */
	public abstract void Set_Max_Cards(int c);
}
