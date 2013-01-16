import java.util.Random;


/*------------------------ Αυτή η class υλοποιεί ένα τραπέζι για TexasHoldem-------------------------*/
public class TexasHoldem_Table extends Table 
{
	/**
	 * @uml.property  name="iD"
	 */
	private int ID;
	
	TexasHoldem_Table(int players_limit, Beting_System B, boolean G)// Constructor
	{
		GUI = G;// Αν το παιχνίδι είναι με γραφικό(true) ή όχι(false)
		this.Set_Players_Limit(players_limit);// θέτουμε από την αρχή πόσοι είναι οι επιτρεπτοί παίχτες σε κάθε τραπέζι
		this.ID = Get_Table_ID();// Ενημερώνεται το table ID
		Bet = B;// Το σύστημα πονταρίσματος
	}
	
	// Get functions

	
	// Set functions
	//Μοιράζει τις κάρτες στους παίχτες
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

				if (Players.get(p).Set_Card(next_card) == false)// Εάν δεν μπορεί να πάρει άλλα φύλλα ο παίχτης
					done = true;// Προχωράμε στον επόμενο παίχτη
				else// Εάν μπορεί να πάρει άλλα φύλλα ο παίχτης
					Cards.remove(next_card);// Αφαιρούμε το φύλλο για να μην ξαναμοιραστεί
				if (done)
					break;
			}
		}
	}

	
	//Προσθέτει τον κατάλληλο παίχτη στο παιχνίδι
	public void Add_Player(boolean automatic)
	{
		AllPlayers++;
		
		if (Get_Players() < Get_Players_Limit())// Aν υπάρχει χώρος για άλλον παίχτη
			Players.add(new TexasHoldem_Player(automatic));
		else// Αλλιώς
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
			
		if (Cards_Down.size() == 0)// Eάν δεν έχει μοιραστεί το flop
		{
			Flop();// Μοιράζουμε το flop
		}
		else if (Cards_Down.size() == 3)// Eάν έχει μοιραστεί το flop
		{
			Turn();// Μοιράζουμε το turn
		}
		else if (Cards_Down.size() == 4)// Eάν έχει μοιραστεί το turn
		{
			River();// Μοιράζουμε το River
		}
		else//ΑΛλιώς
		{
			for (int i=0;i<Get_Players();i++)
			{
				for (int j=0;j<Players.get(i).Get_Max_Cards();j++)
				{
					Players.get(i).hand.get(j).open = true;// Aνοίγουμε τα φύλλα για να δούμε ποιος κερδίζει
				}
			}
			
			if (GUI)F.repaint();
			
		}
	}
	
	// Μοιράζουμε το Flop
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
			Cards.remove(next_card);// Αφαιρούμε το φύλλο για να μην ξαναμοιραστεί
		}
	}
	
	// Μοιράζουμε το Turn
	public void Turn()
	{
		Random random = new Random();
		int next = random.nextInt(Cards.size());
		String next_card = Cards.get(next);
		card temp = new card();
		temp.value = next_card;
		temp.open = true;
		
		Cards_Down.add(temp);
		Cards.remove(next_card);// Αφαιρούμε το φύλλο για να μην ξαναμοιραστεί
	}
	
	// Μοιράζουμε το River
	public void River()
	{
		Random random = new Random();
		int next = random.nextInt(Cards.size());
		String next_card = Cards.get(next);
		card temp = new card();
		temp.value = next_card;
		temp.open = true;
		
		Cards_Down.add(temp);
		Cards.remove(next_card);// Αφαιρούμε το φύλλο για να μην ξαναμοιραστεί
	}
	
	
	
	// Τυπώνει τα στοιχεία των παιχτών
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
