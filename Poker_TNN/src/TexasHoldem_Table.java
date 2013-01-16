import java.util.Random;


/*------------------------ ���� � class �������� ��� ������� ��� TexasHoldem-------------------------*/
public class TexasHoldem_Table extends Table 
{
	/**
	 * @uml.property  name="iD"
	 */
	private int ID;
	
	TexasHoldem_Table(int players_limit, Beting_System B, boolean G)// Constructor
	{
		GUI = G;// �� �� �������� ����� �� �������(true) � ���(false)
		this.Set_Players_Limit(players_limit);// ������� ��� ��� ���� ����� ����� �� ���������� ������� �� ���� �������
		this.ID = Get_Table_ID();// ������������ �� table ID
		Bet = B;// �� ������� �������������
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
			Players.add(new TexasHoldem_Player(automatic));
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
			
		if (Cards_Down.size() == 0)// E�� ��� ���� ��������� �� flop
		{
			Flop();// ���������� �� flop
		}
		else if (Cards_Down.size() == 3)// E�� ���� ��������� �� flop
		{
			Turn();// ���������� �� turn
		}
		else if (Cards_Down.size() == 4)// E�� ���� ��������� �� turn
		{
			River();// ���������� �� River
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
	
	// ���������� �� Flop
	public void Flop()
	{
		Random random = new Random();
		for (int i=0;i<3;i++)
		{
			int next = random.nextInt(Cards.size());
			String next_card = Cards.get(next);
			card temp = new card();
			temp.value = next_card;
			temp.open = true;
			
			Cards_Down.add(temp);
			Cards.remove(next_card);// ��������� �� ����� ��� �� ��� �������������
		}
	}
	
	// ���������� �� Turn
	public void Turn()
	{
		Random random = new Random();
		int next = random.nextInt(Cards.size());
		String next_card = Cards.get(next);
		card temp = new card();
		temp.value = next_card;
		temp.open = true;
		
		Cards_Down.add(temp);
		Cards.remove(next_card);// ��������� �� ����� ��� �� ��� �������������
	}
	
	// ���������� �� River
	public void River()
	{
		Random random = new Random();
		int next = random.nextInt(Cards.size());
		String next_card = Cards.get(next);
		card temp = new card();
		temp.value = next_card;
		temp.open = true;
		
		Cards_Down.add(temp);
		Cards.remove(next_card);// ��������� �� ����� ��� �� ��� �������������
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
