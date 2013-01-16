import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player 
{
	/**
	 * @uml.property  name="name"
	 */
	private String Name;// �� ����� ��� ������
	private static int ID=0;// �� ID ��� ������ (����������� ��� ���� ������ ��� ����� ���������)
	/**
	 * @uml.property  name="stack"
	 */
	private int stack;// To stack ��� ������
	/**
	 * @uml.property  name="max_combination"
	 */
	private int max_combination;// O ��������� ���������� ��� ���� � �������
	/**
	 * @uml.property  name="folded"
	 */
	private boolean folded;// true ��� � ������� ����.....
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
	protected int betted;// �� ������� ��� ���� � ������� ���� ��� stack
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
	protected ArrayList<card> hand = new ArrayList<card>();// �� ���� ��� ������
	
	//Player(){}// Constructor
	
	Player(boolean automatic)// Constructor
	{
		hand.clear();
		betted = 0;// ������ ��� ���� �������� ������
		folded = false;
		onSmallBLind = false;
		onBigBlind = false;
		onDealer = false;
		isPlaying = false;
		
		ID++;// ���� ������� ���� ��������� �D
		Set_Stack(1500);// ������� �� stack 1500 ������ ��� �����
		
		if (!automatic)// E�� ��� ������ ��� ���������� ������ �������
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
		else// E�� ��� ������ ��� ���������� � �����������
		{
			// ��� ������� ��� ������ ����� ��� �� ������ "names.txt"
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
	public String Get_Name(){ return this.Name; }// ���������� �� ����� ��� ������
	public int Get_Stack(){ return this.stack; }// ���������� �� stack ��� ������
	public void Print_Cards(){ for (int i=0;i<hand.size();i++)if (hand.get(i).open)System.out.print(hand.get(i).value + " "); }// ���������� �� ����� ��� ������
	public int Get_Betted(){ return this.betted; }// ���������� ���� ���� �������� � ������� ����� �������
	public boolean Get_Folded(){ return this.folded; }// ���������� ��� ��������� fold ��� ������
	public boolean Get_OnSmallBLind(){ return this.onSmallBLind; }// 
	public boolean Get_OnBigBLind(){ return this.onBigBlind; }// 
	public boolean Get_IsPlaying(){ return this.isPlaying; }// 
	public boolean Get_OnDealer(){ return this.onDealer; }// 
	public boolean Get_Has_Played(){ return has_played; }
	public int Get_Choice()// ���������� �� choice ��� ������
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
	protected void Set_Name(String Name){ this.Name = Name; }// ����� �� ����� ��� ������
	protected void Set_Folded(boolean f){ this.folded = f; }// ����� ��� ������ folded
	protected void Set_OnSmallBLind(boolean f){ this.onSmallBLind = f; }// ����� ��� ������ 
	protected void Set_OnBigBLind(boolean f){ this.onBigBlind = f; }// ����� ��� ������ 
	protected void Set_IsPlaying(boolean f){ this.isPlaying = f; }// ����� ��� ������ 
	protected void Set_OnDealer(boolean f){ this.onDealer = f; }// ����� ��� ������ 
	protected void Set_Stack(int new_stack){ this.stack = new_stack; }// ���������� �� stack ��� ������
	protected void Set_Betted(int b){ this.betted = b; }// ���������� ���� ���� �������� � ������� ����� �������
	public void Set_Has_Played(boolean p){ this.has_played = p; }
	public void Set_Choice(int c)// ����� �� choice ��� ������
	{ 
		Out output = new Out ("choice.txt");
		output.print(c);
		output.close();
	}

	// Bet
	protected void Bet(int bet_ammount)
	{
		Set_Stack(Get_Stack() - bet_ammount);// ����������� ��� �� stack ���
		
		Set_Betted(Get_Betted() + bet_ammount);// ������������ ���� ���� �������� ����� �������
		
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
