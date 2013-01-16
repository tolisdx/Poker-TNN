
public class Beting_Stack_Limit extends Beting_System 
{
	
	Beting_Stack_Limit()
	{
		super.Set_SB(5);
		super.Set_BB(10);
		super.Set_MaxB(15);
		super.Set_Double_B_Every(10);
		
		super.Set_A(10);
		super.Set_Start_A(10);
		super.Set_Double_A_Every(20);
	}
	
	public void Set_SmallBlind(int b)
	{
		super.Set_SB(5);
	}
	
	public void Set_BigBlind(int b)
	{
		super.Set_BB(b);
	}
	
	public void Set_Max_Bet(int b)
	{
		super.Set_MaxB(b);
	}
	
	public void Set_Ante(int b)
	{
		super.Set_A(b);
	}
	
	public void Set_Start_Ante(int b)
	{
		super.Set_Start_A(b);
	}
	
	public void Set_Double_Blinds_Every(int b)
	{
		super.Set_Double_B_Every(b);
	}
	
	public void Set_Double_Antes_Every(int b)
	{
		super.Set_Double_A_Every(b);
	}

}
