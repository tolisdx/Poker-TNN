
import java.util.Random;


/*------------------------ ���� � class �������� ��� ������� ��� Card_5_Draw-------------------------*/
public class Card_5_Draw_Table extends Table 
{
	/**
	 * @uml.property  name="iD"
	 */
	private int ID;
	/**
	 * @uml.property  name="changed_cards"
	 */
	private boolean changed_cards;
	
	Card_5_Draw_Table(int players_limit, Beting_System B, boolean G)// Constructor
	{
		GUI = G;// �� �� �������� ����� �� �������(true) � ���(false)
		this.Set_Players_Limit(players_limit);// ������� ��� ��� ���� ����� ����� �� ���������� ������� �� ���� �������
		this.ID = Get_Table_ID();// ������������ �� table ID
		Bet = B;// �� ������� �������������
		changed_cards = false;
	}
	
	// Get functions

	
	// Set functions
	//�������� ��� ������ ����� �������
	public void Deal_Cards()
	{
		Random random = new Random();
		for (int p=0;p<Get_Players();p++)
		{
			while(true)
			{
				boolean done = false;
				int next = random.nextInt(Cards.size());
				String next_card = Cards.get(next);

				if (Players.get(p).Set_Card(next_card) == false)// ��� ��� ������ �� ����� ���� ����� � �������
					done = true;// ��������� ���� ������� ������
				else// ��� ������ �� ����� ���� ����� � �������
					Cards.remove(next_card);// ��������� �� ����� ��� �� ��� �������������
				if (done)
					break;
			}
		}
	}

	
	//��������� ��� ��������� ������ ��� ��������
	public void Add_Player(boolean automatic)
	{
		AllPlayers++;
		
		if (Get_Players() < Get_Players_Limit())// A� ������� ����� ��� ����� ������
			Players.add(new Card_5_Draw_Player(automatic));
		else// ������
			System.out.println("No other player can enter the table ID:" + Get_ID());
	}
	
	public int Get_ID()
	{
		return this.ID;
	}
	
	
	public void Proceed_dealing()
	{
		for (int i=0;i<Get_Players();i++)
		{
			Players.get(i).Set_Has_Played(false);
			if (Players.get(i).Get_Folded())
				Players.get(i).Set_Has_Played(true);
		}
			
		if (!changed_cards)// E�� ��� ����� ������� ��� ������ ����
		{
			Change_Cards();
			changed_cards = true;
		}
		else//������
		{
			for (int i=0;i<Get_Players();i++)
			{
				for (int j=0;j<Players.get(i).Get_Max_Cards();j++)
				{
					Players.get(i).hand.get(j).open = true;// A�������� �� ����� ��� �� ����� ����� ��������
				}
			}
			
			if (GUI)F.repaint();
			
		}
	}
	
	public void Change_Cards()
	{
		for (int i=0;i<Get_Players();i++)
		{
			if (GUI)F.repaint();
			Players.get(Get_Player_on_decision()).Set_IsPlaying(false);
			Set_Player_on_decision((Get_Player_on_decision() + 1) % Get_Players());// ��������� ��� ������ ��� ������
			Players.get(Get_Player_on_decision()).Set_IsPlaying(true);
			Player playing = Players.get(Get_Player_on_decision());// � ������� ��� ������ ����
			
			System.out.print(playing.Get_Name() + " how many cards you want to remove");
			if (GUI)F.OP.tx3.setVisible(true);
			int choose = Get_Choice(1,5);
			if (choose==5)// �� ����� �� �� ������� ���
			{
				playing.hand.clear();
				if (GUI)F.repaint();
			}
			else// �� ��� ����� �� �� ������� ���
			{
				System.out.print("Change " + choose + " cards");
				for (int j=0;j<choose;j++)
				{
					if (GUI)F.OP.tx3.setVisible(true);
					int what = Get_Choice(1,playing.hand.size());
					playing.hand.remove(playing.hand.get(what-1));
					
					if (GUI)F.repaint();
				}
			}
		}
				
		Deal_Cards();// ���������� ���� ����� 5 ������ ���� ������
	}
	
		
	
	// ������� �� �������� ��� �������
	public void Print_Players_Stats()
	{
		System.out.println("Table ID:" + Get_ID());
		for (int i=0;i<Get_Players();i++)
		{
			System.out.print("\nID:" + Players.get(i).Get_ID() + " Name:" + Players.get(i).Get_Name() + " Stack:" + Players.get(i).Get_Stack() + " ");
			Players.get(i).Print_Cards();
			if (Get_Dealer() == i)
				System.out.print(" Dealer ");
			if (Get_Player_on_smallBlind() == i)
				System.out.print(" smallBlind ");
			if (Get_Player_on_bigBlind() == i)
				System.out.print(" bigBlind ");
			if (Get_Player_on_decision() == i)
				System.out.print(" playing ");
			if (Players.get(i).Get_Folded())
				System.out.print(" Folded ");
			if (Players.get(i).Get_Has_Played())
				System.out.print(" HAS PLAYED ");
			
			System.out.print(" Betted: " + Players.get(i).Get_Betted());
		}
		
		System.out.println("\nStack on table:" + Get_Total_Stack());
		
		System.out.print(" Table: ");
		for (int i=0;i<Cards_Down.size();i++)
		{
			System.out.print(Cards_Down.get(i).value + " ");
		}
		System.out.println();
			
		
	}
	
	
}
