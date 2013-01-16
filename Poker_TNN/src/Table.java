import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*------------------------ ���� � class �������� ��� �������-------------------------*/
public abstract class Table 
{
	private static int ID=0;// T� ID ��� ��������� (�������� ��� ���� ������� ����������� ����������)
	/**
	 * @uml.property  name="players_limit"
	 */
	private int players_limit = 0;// T� ������������ ���� �������
	/**
	 * @uml.property  name="dealer"
	 */
	private int dealer;// ����� ����� ��� ���� ��� �������� ������
	/**
	 * @uml.property  name="player_on_smallBlind"
	 */
	private int player_on_smallBlind;// T� small Blind ��� big Blind
	/**
	 * @uml.property  name="player_on_bigBlind"
	 */
	private int player_on_bigBlind;
	/**
	 * @uml.property  name="player_on_decision"
	 */
	private int player_on_decision;// � ������� ��� ������ ����� ��� ������
	/**
	 * @uml.property  name="total_stack"
	 */
	private int total_stack;// Ti �������� � ������ ��� �������
	/**
	 * @uml.property  name="bet"
	 * @uml.associationEnd  
	 */
	protected Beting_System Bet;// �� ������� �������������
	/**
	 * @uml.property  name="must_bet_to_follow"
	 */
	private int must_bet_to_follow;// ���� ������ �� �������� ������� ��� �� �����������
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
	protected ArrayList<Player> Players = new ArrayList<Player>();// � ����� �� ���� ������� ��� ���������
	/**
	 * @uml.property  name="cards"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	protected ArrayList<String> Cards =  new ArrayList<String>();// � ����� �� ��� ���������� ������ ��� ���������
	/**
	 * @uml.property  name="cards_Down"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="card"
	 */
	protected ArrayList<card> Cards_Down = new ArrayList<card>();// � ����� �� �� ����� �����
	
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
	
	
	// �������� �� �������� ��� �������
	protected void Start_Game()
	{
		for (int i=0;i<Get_Players();i++)
		{
			Players.get(i).Set_Has_Played(false);
			Players.get(i).Set_Folded(false);//������� ���� ������� �� unfolded
			Players.get(i).hand.clear();// ����������� �� ���� ����
			Players.get(i).Set_Betted(0);// ����������� �� ������������ ����
			Players.get(i).Set_OnDealer(false);
			Players.get(i).Set_OnSmallBLind(false);
			Players.get(i).Set_OnBigBLind(false);
			Players.get(i).Set_IsPlaying(false);
		}
		
		Random random = new Random();
		dealer = random.nextInt(Get_Players());// ������� ��� Dealer ������
		player_on_smallBlind = (dealer+1) % Get_Players();// ������� �� smallBlind
		player_on_bigBlind = (player_on_smallBlind+1) % Get_Players();// ������� �� bigBlind
		player_on_decision = (player_on_bigBlind+1) % Get_Players();// ������� ����� ������� ������ ���� ��� ������
		
		Players.get(Get_Dealer()).Set_OnDealer(true);
		Players.get(Get_Player_on_smallBlind()).Set_OnSmallBLind(true);
		Players.get(Get_Player_on_bigBlind()).Set_OnBigBLind(true);
		Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
		
		
		Players.get(player_on_smallBlind).Bet(Bet.Get_SmallBLind());// ��������� ��� ������ ��� ��� ������ ��� SmallBLind
		Players.get(player_on_bigBlind).Bet(Bet.Get_BigBLind());// ��������� ��� ������ ��� ��� ������ ��� BigBLind
		Set_bet_follow(Bet.Get_BigBLind());// �� ������ ��� ������ �� ����� � ������� ��� �� �����������
		Set_Total_Stack(Bet.Get_SmallBLind() + Bet.Get_BigBLind());// To �������� stack ��� �������
		
		Initialize_Cards();// ������������� ��� ������
		Deal_Cards();// ���������� �� ����� ����� �������
	}
	
	// ����������� ��� ������
	protected void Initialize_Cards()
	{
		Cards.clear();
		
		for (int i=1; i<=13; i++)// ��� ��� �� ������� (11=�����, 12=�����, 13=�����)
		{
			Cards.add(i + "S");// ��� �� ������
			Cards.add(i + "K");// ��� ��� ������
			Cards.add(i + "C");// ��� �� ����
			Cards.add(i + "B");// ��� �� ����������
		}
	}
	
	

	// ������� ��� �������� �� ��������
	public boolean Finished()
	{
		for (int p=0;p<Get_Players();p++)
			if (Players.get(p).Get_Stack() == (AllPlayers * 1500))// ��� ���� ������� ���� ������� ���� ��� ������
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
		Bet.Check_Time();// ��������� �� ����� �������������

		Player playing = Players.get(Get_Player_on_decision());// � ������� ��� ������ ����
		
		//------------------------------------ ������� �� ����� ����� ��� ������ ���� ���� ���� ����----------------------------
		if (playing.Get_Folded()==true)//��� � ������� ���� ���� ����
		{
			Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
			Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// ��������� ��� ������ ��� ������
			Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
			return;//����������� ��� ��� ���������
		}
		
		//------------------------------------------- ������� �� ����� ���� ���� ����-----------------------------------------
		boolean all_folded = true;
		for (int i=0;i<Get_Players();i++)
		{
			if (Get_Player_on_decision() != i)//����� ��� ��� ������ ��� ������ ����
				if (Players.get(i).Get_Folded() == false)// ��� ������� ������� ��� ��� ���� ���� ����
					all_folded=false;
		}
		
		if (all_folded)// ��� ����� ���� ���� ����
		{
			playing.Set_Stack(playing.Get_Stack() + Get_Total_Stack());// ������������ �� stack ��� ������
			Next_Round();// ��������� ���� ������� ����
			return;
		}
		
		
		//------------------------------------ ������� �� ����� ����� ��� ������ ���� ���� �������� stack----------------------------
		if (playing.Get_Stack()== 0)//��� � ������� ���� �������� stack ���
		{
			playing.Set_Has_Played(true);
			
			if (All_Players_Played())
				Proceed_dealing();// ���������� �� ������� �����
			
			Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
			Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// ��������� ��� ������ ��� ������
			Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
			return;
		}
		
		
		//------------------------------------------- ������ � ������� ���� ������� -----------------------------------------
		Print_Players_Stats();// T����� ��� ��������� ��� ���������
		System.out.print("\nPlayer:" + playing.Get_Name());// �������� �� ����� ��� ������ ��� ������
		
		int has_to_bet = Get_bet_follow() - playing.Get_Betted();// ���� ������ �� �������� ����������� ��� �� �����������
		
		System.out.print("\nBet to follow "+ has_to_bet);
		System.out.println("\nMax Bet "+ Bet.Get_Max_Bet());
		
		int choice;// � ������� ��� ������
		boolean done=false;
		while(!done)
		{
			System.out.print("\n1. Check");
			System.out.print("\n2. Call " + has_to_bet);
			System.out.print("\n3. Raise");
			System.out.print("\n4. Fold");
			choice = Get_Choice(1,4);
			
			//------------------------------------------ CHECK ----------------------------------------------------
			if (choice == 1)//��� �������� CHECK
			{
				if (has_to_bet == 0)// ��� ���� ��� ����� ��� ���������� (��� ������ ������ �� ����� check)
				{
					done = true;
				}
				else if (has_to_bet > 0)
				{
					System.out.print("\nYou cannot check!");
				}
			}
			//------------------------------------------ CALL ----------------------------------------------------
			else if (choice == 2)//��� �������� CALL
			{
				if (has_to_bet == 0)// ��� ���� ��� ����� ��� ���������� (��� ������ ����� �� ����� check)
				{
					
				}
				else if (has_to_bet >= playing.Get_Stack())// ��� ��� ���� ����
				{
					System.out.print("\nYou are ALL IN!");
					Set_Total_Stack(Get_Total_Stack() + playing.Get_Stack());// ��������� �� stack ��� ���������
					playing.Set_Betted(playing.Get_Betted() + playing.Get_Stack());// ������������ �� ���������� ��� ������
					playing.Set_Stack(playing.Get_Stack() - playing.Get_Stack());// ��������� �� ���������� ��� �� stack ��� ������
					Bet.Set_Max_Bet(Get_Total_Stack());// ������������ �� max bet
				}
				else if (has_to_bet < playing.Get_Stack())// ��� ���� �����������
				{
					Set_Total_Stack(Get_Total_Stack() + has_to_bet);// ��������� �� stack ��� ���������
					playing.Set_Betted(playing.Get_Betted() + has_to_bet);// ������������ �� ���������� ��� ������
					playing.Set_Stack(playing.Get_Stack() - has_to_bet);// ��������� �� ���������� ��� �� stack ��� ������
					Bet.Set_Max_Bet(Get_Total_Stack());// ������������ �� max bet
				}
								
				done = true;
			}
			//------------------------------------------ RAISE ----------------------------------------------------
			else if (choice == 3)//��� �������� RAISE
			{
				if (playing.Get_Stack() - has_to_bet < Bet.Get_BigBLind())// ��� ��� ������ �� ����� raise
				{
					System.out.print("\nYou cannot raise!");
				}
				else// ��� ������ �� ����� raise
				{
					for (int i=0;i<Get_Players();i++)
					{
						Players.get(i).Set_Has_Played(false);
						if (Players.get(i).Get_Folded())
							Players.get(i).Set_Has_Played(true);
					}
						
					if (playing.Get_Stack() - has_to_bet >= Bet.Get_Max_Bet())// ��� ���� ������ ��� �� max bet
						choice = Get_Choice(Bet.Get_BigBLind(), Bet.Get_Max_Bet());//  ��������� ���� ����� �� ��������
					else if (playing.Get_Stack() - has_to_bet < Bet.Get_Max_Bet())// ��� ��� ���� ������ ��� �� max bet
						choice = Get_Choice(Bet.Get_BigBLind(), (playing.Get_Stack() - has_to_bet));//  ��������� ���� ����� �� ��������
					
					Players.get(Get_Player_on_decision()).Set_Choice(-1);
					
					choice += has_to_bet;// ����������� �� call
					playing.Set_Stack(playing.Get_Stack() - choice);// ��������� �� ���������� ��� �� stack ��� ������
					playing.Set_Betted(playing.Get_Betted() + choice );// ������������ ���� ���� ��������
					Set_Total_Stack(Get_Total_Stack() + choice);// ��������� �� stack ��� ���������
					Bet.Set_Max_Bet(Get_Total_Stack());// ������������ �� max bet
					Set_bet_follow(Get_bet_follow() + choice - has_to_bet);// ������������ �� ��� bet to follow
					
					done = true;
				}
			}
			//------------------------------------------ FOLD ----------------------------------------------------
			else if (choice == 4)//��� �������� fold
			{
				playing.Set_Folded(true);
				
				done = true;
			}
		}
		
		playing.Set_Has_Played(true);
		
		if (All_Players_Played())
			Proceed_dealing();// ���������� �� ������� �����
		
		Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
		Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// ��������� ��� ������ ��� ������
		Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
		
	}
	
	// ������� ��� ������� ���� �� �������
	private boolean All_Players_Played()
	{
		for (int i=0;i<Get_Players();i++)
			if (Players.get(i).Get_Has_Played() == false)
				return false;
		
		return true;
	}
	
	// ��������� �� ��� ���� ��� �������� ���������� ���� 1 modular players ��� �� ���� ������� 
	protected void Next_Round()
	{
		// ������������� �� �������� ��� �������
		for (int i=0;i<Get_Players();i++)
		{
			Players.get(i).Set_Has_Played(false);
			Players.get(i).Set_Folded(false);//������� ���� ������� �� unfolded
			Players.get(i).hand.clear();// ����������� �� ���� ����
			Players.get(i).Set_Betted(0);// ����������� �� ������������ ����
			Players.get(i).Set_OnDealer(false);
			Players.get(i).Set_OnSmallBLind(false);
			Players.get(i).Set_OnBigBLind(false);
			Players.get(i).Set_IsPlaying(false);
		}
		
		// ��������� ���� ������� �� �������� stack (������ ����� ����������� ��� ��� ������� �� ����������)
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
		Set_Player_on_decision((Get_Player_on_bigBlind()+1) % Get_Players());// ������� ����� ������� ������ ���� ��� ������
		
		Players.get(Get_Dealer()).Set_OnDealer(true);
		Players.get(Get_Player_on_smallBlind()).Set_OnSmallBLind(true);
		Players.get(Get_Player_on_bigBlind()).Set_OnBigBLind(true);
		Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
		
		int sb = Bet.Get_SmallBLind();
		int bb = Bet.Get_BigBLind();
		
		if (Players.get(Get_Player_on_smallBlind()).Get_Stack() < Bet.Get_SmallBLind())// E�� � ������� ��� smallblind ��� ���� ������ ��� ����
			sb = Players.get(Get_Player_on_smallBlind()).Get_Stack();
		
		if (Players.get(Get_Player_on_bigBlind()).Get_Stack() < Bet.Get_BigBLind())// E�� � ������� ��� bigblind ��� ���� ������ ��� ����
			bb = Players.get(Get_Player_on_bigBlind()).Get_Stack();

		Players.get(Get_Player_on_smallBlind()).Bet(sb);// ��������� ��� ������ ��� ��� ������ ��� SmallBLind
		Players.get(Get_Player_on_bigBlind()).Bet(bb);// ��������� ��� ������ ��� ��� ������ ��� BigBLind
		Set_bet_follow(Bet.Get_BigBLind());// �� ������ ��� ������ �� ����� � ������� ��� �� �����������
		Set_Total_Stack(sb + bb);// To �������� stack ��� �������
		Bet.Set_Max_Bet(Get_Total_Stack());// ������������ �� max bet
		
		Cards_Down.clear();
		Initialize_Cards();// ������������� ��� ������
		Deal_Cards();// ���������� �� ����� ����� �������	
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
