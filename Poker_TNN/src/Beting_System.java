import java.util.Date;


public abstract class Beting_System 
{
	/**
	 * @uml.property  name="smallblind"
	 */
	private int smallblind;
	/**
	 * @uml.property  name="bigblind"
	 */
	private int bigblind;
	/**
	 * @uml.property  name="max_bet"
	 */
	private int max_bet;
	/**
	 * @uml.property  name="ante"
	 */
	private int ante;
	/**
	 * @uml.property  name="now"
	 */
	private Date now;
	/**
	 * @uml.property  name="clock_for_blinds"
	 */
	private long clock_for_blinds;// Σε δευτερόλεπτα
	/**
	 * @uml.property  name="clock_for_start_antes"
	 */
	private long clock_for_start_antes;
	/**
	 * @uml.property  name="clock_for_antes"
	 */
	private long clock_for_antes;
	/**
	 * @uml.property  name="double_Blinds_every"
	 */
	private int double_Blinds_every;// Σε δευτερόλεπτα
	/**
	 * @uml.property  name="double_antes_every"
	 */
	private int double_antes_every;// Σε δευτερόλεπτα
	/**
	 * @uml.property  name="started_antes"
	 */
	private boolean started_antes;
	
	Beting_System ()
	{
		started_antes = false;
		now = new Date();
		clock_for_blinds = now.getTime();
		clock_for_antes = now.getTime();
	}
	// Έλεγχος του χρόνου για διπλασιασμό blinds και antes
	public void Check_Time()
	{
		now = new Date();
		//System.out.println("\nNow:" + now.getTime() + " before:" + clock_for_blinds);
		if ((now.getTime() - clock_for_blinds) >= (1000*double_Blinds_every))
		{
			clock_for_blinds = now.getTime();
			Double_Blinds();
		}
		
		if (started_antes == false)
		{
			if ((now.getTime() - clock_for_antes) >= (1000*clock_for_start_antes))
			{
				started_antes = true;
				clock_for_antes = now.getTime();
			}
		}
		else
		{
			if ((now.getTime() - clock_for_antes) >= (1000*double_antes_every))
			{
				clock_for_antes = now.getTime();
				Double_Ante();
			}
		}
	}
	
	// Set functions
	public void Double_Blinds(){ smallblind *= 2; bigblind *= 2; }
	public void Double_Ante(){ ante *= 2; }
	protected void Set_MaxB( int b ){ this.max_bet = b; }
	protected void Set_SB(int b){ this.smallblind = b; }
	protected void Set_BB(int b){ this.bigblind = b; }
	protected void Set_A(int b){ this.ante = b; }
	public void Set_Double_B_Every(int b){ this.double_Blinds_every = b; }
	public void Set_Double_A_Every(int b){ this.double_antes_every = b; }
	public void Set_Start_A(int a){ this.clock_for_start_antes = a; }
	
	// Get functions
	public int Get_Ante(){ if (started_antes)return ante;else return 0; }
	public int Get_SmallBLind(){ return smallblind; }
	public int Get_BigBLind(){ return bigblind; }
	public int Get_Max_Bet(){ return this.max_bet; }
	
	/**
	 * Method that sets the SmallBlind
	 */
	public abstract void Set_SmallBlind(int b);
	/**
	 * Method that sets the BigBlind
	 */
	public abstract void Set_BigBlind(int b);
	/**
	 * Method that sets the maxBet
	 */
	public abstract void Set_Max_Bet(int b);
	/**
	 * Method that sets the ante amount
	 */
	public abstract void Set_Ante(int b);
	/**
	 * Method that sets the time in seconds that the blinds should be doubled
	 */
	public abstract void Set_Double_Blinds_Every(int b);
	/**
	 * Method that sets the time in seconds that the ante should be doubled
	 */
	public abstract void Set_Double_Antes_Every(int b);
	/**
	 * Method that sets the time in seconds that the ante should start
	 */
	public abstract void Set_Start_Ante(int b);
	

}
