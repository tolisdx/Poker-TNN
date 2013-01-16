
public class Card_5_Draw_Player extends Player 
{
	
	/**
	 * @uml.property  name="iD"
	 */
	private int ID;
	/**
	 * @uml.property  name="max_cards"
	 */
	private int max_cards = 5;
	
	public int Get_Max_Cards(){ return max_cards;}
	public void Set_Max_Cards(int c){ this.max_cards = c; }
	
	Card_5_Draw_Player(boolean automatic)
	{
		super(automatic);
		this.ID = Get_Player_ID();
	}
	
	public int Get_ID()
	{
		return this.ID;
	}
	
	public boolean Set_Card(String newcard)
	{
		card temp = new card();
		temp.value = newcard;
		temp.open = false;
		
		if (hand.size() < max_cards)
		{
			hand.add(temp);
			return true;
		}
		
		return false;
	}

}
