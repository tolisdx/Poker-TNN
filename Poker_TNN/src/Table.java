import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*------------------------ Αυτή η class υλοποιεί ένα τραπέζι-------------------------*/
public abstract class Table 
{
	private static int ID=0;// Tο ID του τραπεζιού (μοναδικό για κάθε τραπέζι ανεξαρτήτως παιχνιδιού)
	/**
	 * @uml.property  name="players_limit"
	 */
	private int players_limit = 0;// Tο επιτρεπόμενο όριο παιχτών
	/**
	 * @uml.property  name="dealer"
	 */
	private int dealer;// Ποιος κάνει την μάνα την δεδομένη στιγμή
	/**
	 * @uml.property  name="player_on_smallBlind"
	 */
	private int player_on_smallBlind;// Tα small Blind και big Blind
	/**
	 * @uml.property  name="player_on_bigBlind"
	 */
	private int player_on_bigBlind;
	/**
	 * @uml.property  name="player_on_decision"
	 */
	private int player_on_decision;// Ο παίχτης που παίζει αυτήν την στιγμή
	/**
	 * @uml.property  name="total_stack"
	 */
	private int total_stack;// Ti περιέχει η στοίβα στο τραπέζι
	/**
	 * @uml.property  name="bet"
	 * @uml.associationEnd  
	 */
	protected Beting_System Bet;// ΤΟ σύστημα πονταρίσματος
	/**
	 * @uml.property  name="must_bet_to_follow"
	 */
	private int must_bet_to_follow;// Πόσα πρέπει να ποντάρει κάποιος για να ακολουθήσει
	/**
	 * @uml.property  name="allPlayers"
	 */
	protected int AllPlayers=0;
	/**
	 * @uml.property  name="f"
	 * @uml.associationEnd  
	 */
	public Frame F;
	/**
	 * @uml.property  name="gUI"
	 */
	protected boolean GUI;
	
	
	/**
	 * @uml.property  name="players"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="Player"
	 */
	protected ArrayList<Player> Players = new ArrayList<Player>();// Η λίστα με τους παίχτες του τραπεζιού
	/**
	 * @uml.property  name="cards"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	protected ArrayList<String> Cards =  new ArrayList<String>();// Η λίστα με τις διαθέσιμες κάρτες του τραπεζιού
	/**
	 * @uml.property  name="cards_Down"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="card"
	 */
	protected ArrayList<card> Cards_Down = new ArrayList<card>();// Η λίστα με τα κοινά φύλλα
	
	Table()// Constructor
	{
		ID++;	
		
		Set_Total_Stack(0);
	}
	
	// Set functions
	protected void Set_Players_Limit(int players_limit){ this.players_limit = players_limit; }
	protected void Set_Dealer(int dealer){ this.dealer = dealer; }
	protected void Set_Player_on_smallBlind(int smallBlind){ this.player_on_smallBlind = smallBlind; }
	protected void Set_Player_on_bigBlind(int bigBlind){ this.player_on_bigBlind = bigBlind; }
	protected void Set_Total_Stack(int s){ this.total_stack = s; }
	protected void Set_Player_on_decision(int p){ this.player_on_decision = p; }
	protected void Set_bet_follow(int b){  this.must_bet_to_follow = b;}
	//protected void Set_last_player_betted(int p){  this.last_player_betted = p;}
	
	
	// Get functions
	protected int Get_Table_ID(){ return Table.ID; }
	protected int Get_Players(){ return this.Players.size(); }
	protected int Get_Players_Limit(){ return this.players_limit; }
	protected int Get_Dealer(){ return this.dealer; }
	protected int Get_Player_on_smallBlind(){ return this.player_on_smallBlind; }
	protected int Get_Player_on_bigBlind(){ return this.player_on_bigBlind; }
	protected int Get_Player_on_decision(){ return this.player_on_decision; }
	protected int Get_bet_follow(){ return this.must_bet_to_follow;}
	protected int Get_Total_Stack(){ return this.total_stack;}
	//protected int Get_last_player_betted(){ return this.last_player_betted;}
	
	
	// Ξεκινάει το παιχνίδι στο τραπέζι
	protected void Start_Game()
	{
		for (int i=0;i<Get_Players();i++)
		{
			Players.get(i).Set_Has_Played(false);
			Players.get(i).Set_Folded(false);//Θέτουμε τους παίχτες σε unfolded
			Players.get(i).hand.clear();// Καθαρίζουμε το χέρι τους
			Players.get(i).Set_Betted(0);// Καθαρίζουμε τα πονταρίσματά τους
			Players.get(i).Set_OnDealer(false);
			Players.get(i).Set_OnSmallBLind(false);
			Players.get(i).Set_OnBigBLind(false);
			Players.get(i).Set_IsPlaying(false);
		}
		
		Random random = new Random();
		dealer = random.nextInt(Get_Players());// θέτουμε τον Dealer τυχαία
		player_on_smallBlind = (dealer+1) % Get_Players();// θέτουμε το smallBlind
		player_on_bigBlind = (player_on_smallBlind+1) % Get_Players();// θέτουμε το bigBlind
		player_on_decision = (player_on_bigBlind+1) % Get_Players();// Θέτουμε ποιος παίχτης παίζει αυτή την στιγμή
		
		Players.get(Get_Dealer()).Set_OnDealer(true);
		Players.get(Get_Player_on_smallBlind()).Set_OnSmallBLind(true);
		Players.get(Get_Player_on_bigBlind()).Set_OnBigBLind(true);
		Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
		
		
		Players.get(player_on_smallBlind).Bet(Bet.Get_SmallBLind());// Αφαιρούμε τις μάρκες από τον παίχτη στο SmallBLind
		Players.get(player_on_bigBlind).Bet(Bet.Get_BigBLind());// Αφαιρούμε τις μάρκες από τον παίχτη στο BigBLind
		Set_bet_follow(Bet.Get_BigBLind());// Οι μάρκες που πρέπει να βάλει ο καθένας για να ακολουθήσει
		Set_Total_Stack(Bet.Get_SmallBLind() + Bet.Get_BigBLind());// To συνολικό stack στο τραπέζι
		
		Initialize_Cards();// Αρχικοποιούμε τις κάρτες
		Deal_Cards();// Μοιράζουμε τα φύλλα στους παίχτες
	}
	
	// Αρχικοποιεί τις κάρτες
	protected void Initialize_Cards()
	{
		Cards.clear();
		
		for (int i=1; i<=13; i++)// Για όλα τα νούμερα (11=Βαλές, 12=Ντάμα, 13=Παπάς)
		{
			Cards.add(i + "S");// για τα σπαθιά
			Cards.add(i + "K");// για τις κούπες
			Cards.add(i + "C");// για τα καρώ
			Cards.add(i + "B");// για τα μπαστούνια
		}
	}
	
	

	// Ελέγχει εάν τελείωσε το παιχνίδι
	public boolean Finished()
	{
		for (int p=0;p<Get_Players();p++)
			if (Players.get(p).Get_Stack() == (AllPlayers * 1500))// Εάν ένας παίχτης έχει μαζέψει όλες τις μάρκες
				return true;
		
		return false;
	}
	
	public int Get_Choice(int min,int max)
	{
		int choice=-1;
		if (GUI)
		{
			System.out.print("\nChoice (" + min + "," + max + "):");
			
			while(choice == -1 || (choice < min || choice > max))
			{
				choice = Players.get(Get_Player_on_decision()).Get_Choice();
				try{Thread.sleep(300);}catch(Exception e){}
			}
			Players.get(Get_Player_on_decision()).Set_Choice(-1);
		}
		else
		{
			
			System.out.print("\nChoice (" + min + "," + max + "):");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in) );
			try{choice = Integer.parseInt(br.readLine());}catch(Exception e){}
			while(choice < min || choice > max)
			{
				System.out.print("\nChoose again:");
				try{choice = Integer.parseInt(br.readLine());}catch(Exception e){}
			}
		}
		
		return choice;
	}
	
	//----------------------------------------- PlayGame -------------------------------------------------------------
	public void PlayGame()
	{
		Bet.Check_Time();// Ελέγχουμε το ρολόι πονταρίσματος

		Player playing = Players.get(Get_Player_on_decision());// Ο παίχτης που παίζει τώρα
		
		//------------------------------------ Έλεγχος αν έχουν αυτός που παίζει τώρα έχει πάει πάσο----------------------------
		if (playing.Get_Folded()==true)//Εάν ο παίχτης έχει πάει πάσο
		{
			Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
			Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// Προχωράμε τον παίχτη που παίζει
			Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
			return;//Επιτρέφουμε από την συνάρτηση
		}
		
		//------------------------------------------- Έλεγχος αν έχουν πάει όλοι πάσο-----------------------------------------
		boolean all_folded = true;
		for (int i=0;i<Get_Players();i++)
		{
			if (Get_Player_on_decision() != i)//Εκτός από τον παίχτη που παίζει τώρα
				if (Players.get(i).Get_Folded() == false)// Εάν υπάρχει παίχτης που δεν έχει πάει πάσο
					all_folded=false;
		}
		
		if (all_folded)// Εάν έχουν πάει όλοι πάσο
		{
			playing.Set_Stack(playing.Get_Stack() + Get_Total_Stack());// Ενημερώνουμε το stack του νικητή
			Next_Round();// Προχωράμε στον επόμενο γύρο
			return;
		}
		
		
		//------------------------------------ Έλεγχος αν έχουν αυτός που παίζει τώρα έχει μηδενικό stack----------------------------
		if (playing.Get_Stack()== 0)//Εάν ο παίχτης έχει μηδενικό stack και
		{
			playing.Set_Has_Played(true);
			
			if (All_Players_Played())
				Proceed_dealing();// Μοιράζουμε τα επόμενα φύλλα
			
			Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
			Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// Προχωράμε τον παίχτη που παίζει
			Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
			return;
		}
		
		
		//------------------------------------------- Εφόσον ο παίχτης έχει επιλογή -----------------------------------------
		Print_Players_Stats();// Tύπωσε την κατάσταση του τραπεζιού
		System.out.print("\nPlayer:" + playing.Get_Name());// Εμφάνισε το όνομά του παίχτη που παίζει
		
		int has_to_bet = Get_bet_follow() - playing.Get_Betted();// Πόσα πρέπει να ποντάρει τουλάχιστον για να ακολουθήσει
		
		System.out.print("\nBet to follow "+ has_to_bet);
		System.out.println("\nMax Bet "+ Bet.Get_Max_Bet());
		
		int choice;// Η επιλογή του παίχτη
		boolean done=false;
		while(!done)
		{
			System.out.print("\n1. Check");
			System.out.print("\n2. Call " + has_to_bet);
			System.out.print("\n3. Raise");
			System.out.print("\n4. Fold");
			choice = Get_Choice(1,4);
			
			//------------------------------------------ CHECK ----------------------------------------------------
			if (choice == 1)//Εάν επιλέξει CHECK
			{
				if (has_to_bet == 0)// Εάν έχει ήδη βάλει όσα χρειάζεται (εάν δηλαδή μπορεί να κάνει check)
				{
					done = true;
				}
				else if (has_to_bet > 0)
				{
					System.out.print("\nYou cannot check!");
				}
			}
			//------------------------------------------ CALL ----------------------------------------------------
			else if (choice == 2)//Εάν επιλέξει CALL
			{
				if (has_to_bet == 0)// Εάν έχει ήδη βάλει όσα χρειάζεται (εάν δηλαδή θέλει να κάνει check)
				{
					
				}
				else if (has_to_bet >= playing.Get_Stack())// Εάν δεν έχει τόσα
				{
					System.out.print("\nYou are ALL IN!");
					Set_Total_Stack(Get_Total_Stack() + playing.Get_Stack());// Αυξάνουμε το stack του τραπεζιού
					playing.Set_Betted(playing.Get_Betted() + playing.Get_Stack());// Ενημερώνουμε το ποντάρισμα του παίχτη
					playing.Set_Stack(playing.Get_Stack() - playing.Get_Stack());// Αφαιρούμε το ποντάρισμα από το stack του παίχτη
					Bet.Set_Max_Bet(Get_Total_Stack());// Ενημερώνουμε το max bet
				}
				else if (has_to_bet < playing.Get_Stack())// Εάν έχει περισσότερα
				{
					Set_Total_Stack(Get_Total_Stack() + has_to_bet);// Αυξάνουμε το stack του τραπεζιού
					playing.Set_Betted(playing.Get_Betted() + has_to_bet);// Ενημερώνουμε το ποντάρισμα του παίχτη
					playing.Set_Stack(playing.Get_Stack() - has_to_bet);// Αφαιρούμε το ποντάρισμα από το stack του παίχτη
					Bet.Set_Max_Bet(Get_Total_Stack());// Ενημερώνουμε το max bet
				}
								
				done = true;
			}
			//------------------------------------------ RAISE ----------------------------------------------------
			else if (choice == 3)//Εάν επιλέξει RAISE
			{
				if (playing.Get_Stack() - has_to_bet < Bet.Get_BigBLind())// Εάν δεν μπορεί να κάνει raise
				{
					System.out.print("\nYou cannot raise!");
				}
				else// Εάν μπορεί να κάνει raise
				{
					for (int i=0;i<Get_Players();i++)
					{
						Players.get(i).Set_Has_Played(false);
						if (Players.get(i).Get_Folded())
							Players.get(i).Set_Has_Played(true);
					}
						
					if (playing.Get_Stack() - has_to_bet >= Bet.Get_Max_Bet())// Εάν έχει αρκετά για το max bet
						choice = Get_Choice(Bet.Get_BigBLind(), Bet.Get_Max_Bet());//  Παίρνουμε πόσα θέλει να ποντάρει
					else if (playing.Get_Stack() - has_to_bet < Bet.Get_Max_Bet())// Εάν δεν έχει αρκετά για το max bet
						choice = Get_Choice(Bet.Get_BigBLind(), (playing.Get_Stack() - has_to_bet));//  Παίρνουμε πόσα θέλει να ποντάρει
					
					Players.get(Get_Player_on_decision()).Set_Choice(-1);
					
					choice += has_to_bet;// Προσθέτουμε το call
					playing.Set_Stack(playing.Get_Stack() - choice);// Αφαιρούμε το ποντάρισμα από το stack του παίχτη
					playing.Set_Betted(playing.Get_Betted() + choice );// Ενημερώνουμε πόσα έχει ποντάρει
					Set_Total_Stack(Get_Total_Stack() + choice);// Αυξάνουμε το stack του τραπεζιού
					Bet.Set_Max_Bet(Get_Total_Stack());// Ενημερώνουμε το max bet
					Set_bet_follow(Get_bet_follow() + choice - has_to_bet);// Ενημερώνουμε το νέο bet to follow
					
					done = true;
				}
			}
			//------------------------------------------ FOLD ----------------------------------------------------
			else if (choice == 4)//Εάν επιλέξει fold
			{
				playing.Set_Folded(true);
				
				done = true;
			}
		}
		
		playing.Set_Has_Played(true);
		
		if (All_Players_Played())
			Proceed_dealing();// Μοιράζουμε τα επόμενα φύλλα
		
		Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
		Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// Προχωράμε τον παίχτη που παίζει
		Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
		
	}
	
	// Ελέγχει εάν παίξανε όλοι οι παίχτες
	private boolean All_Players_Played()
	{
		for (int i=0;i<Get_Players();i++)
			if (Players.get(i).Get_Has_Played() == false)
				return false;
		
		return true;
	}
	
	// Προχωράει με την φορά του ρολογιού αυξάνοντας κατά 1 modular players για να πάει κυκλικά 
	protected void Next_Round()
	{
		// αρχικοποιούμε τα δεδομένα των παιχτών
		for (int i=0;i<Get_Players();i++)
		{
			Players.get(i).Set_Has_Played(false);
			Players.get(i).Set_Folded(false);//Θέτουμε τους παίχτες σε unfolded
			Players.get(i).hand.clear();// Καθαρίζουμε το χέρι τους
			Players.get(i).Set_Betted(0);// Καθαρίζουμε τα πονταρίσματά τους
			Players.get(i).Set_OnDealer(false);
			Players.get(i).Set_OnSmallBLind(false);
			Players.get(i).Set_OnBigBLind(false);
			Players.get(i).Set_IsPlaying(false);
		}
		
		// Αφαιρούμε τους παίχτες με μηδενικό stack (εφόσον έχουν αποκλειστεί και δεν μπορούν να συνεχίσουν)
		boolean done = false;
		while(!done)
		{
			done = true;
			for (int i=0;i<Get_Players();i++)
			{
				if (Players.get(i).Get_Stack() == 0)
				{
					done = false;
					Players.remove(i);
					break;
				}
			}
		}

		Set_Dealer((Get_Dealer() + 1) % Get_Players());
		Set_Player_on_smallBlind( (Get_Dealer() + 1) % Get_Players() );
		Set_Player_on_bigBlind( (Get_Player_on_smallBlind() + 1) % Get_Players() );
		Set_Player_on_decision((Get_Player_on_bigBlind()+1) % Get_Players());// Θέτουμε ποιος παίχτης παίζει αυτή την στιγμή
		
		Players.get(Get_Dealer()).Set_OnDealer(true);
		Players.get(Get_Player_on_smallBlind()).Set_OnSmallBLind(true);
		Players.get(Get_Player_on_bigBlind()).Set_OnBigBLind(true);
		Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
		
		int sb = Bet.Get_SmallBLind();
		int bb = Bet.Get_BigBLind();
		
		if (Players.get(Get_Player_on_smallBlind()).Get_Stack() < Bet.Get_SmallBLind())// Eάν ο παίχτης στο smallblind δεν έχει αρκετά για αυτό
			sb = Players.get(Get_Player_on_smallBlind()).Get_Stack();
		
		if (Players.get(Get_Player_on_bigBlind()).Get_Stack() < Bet.Get_BigBLind())// Eάν ο παίχτης στο bigblind δεν έχει αρκετά για αυτό
			bb = Players.get(Get_Player_on_bigBlind()).Get_Stack();

		Players.get(Get_Player_on_smallBlind()).Bet(sb);// Αφαιρούμε τις μάρκες από τον παίχτη στο SmallBLind
		Players.get(Get_Player_on_bigBlind()).Bet(bb);// Αφαιρούμε τις μάρκες από τον παίχτη στο BigBLind
		Set_bet_follow(Bet.Get_BigBLind());// Οι μάρκες που πρέπει να βάλει ο καθένας για να ακολουθήσει
		Set_Total_Stack(sb + bb);// To συνολικό stack στο τραπέζι
		Bet.Set_Max_Bet(Get_Total_Stack());// Ενημερώνουμε το max bet
		
		Cards_Down.clear();
		Initialize_Cards();// Αρχικοποιούμε τις κάρτες
		Deal_Cards();// Μοιράζουμε τα φύλλα στους παίχτες	
	}
	
	/**
	 * Method that handles what happens each time a beting round is completed
	 */
	public abstract void Proceed_dealing();
	/**
	 * Method that prints the situation on our table, the way we want it to be shown
	 */
	public abstract void Print_Players_Stats();
	/**
	 * Method that handles the dealing of the cards for each game
	 */
	public abstract void Deal_Cards();
	/**
	 * Method that adds a player of out type in that particular game
	 */
	protected abstract void Add_Player(boolean automatic);
	/**
	 * Method that must exists to give a unique ID
	 * Just write
	 * protected int Get_ID()
	 * {
	 * return this.ID;
	 * }
	 */
	protected abstract int Get_ID();
	
}
