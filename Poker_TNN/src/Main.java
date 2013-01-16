import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JFrame;

public class Main 
{
	static int choice;

	public static void main(String[] args) 
	{		
		//Αρχικοποιούμε το αρχείο σε -1
		Out output = new Out ("choice.txt");
		output.print(-1);
		output.close();
		
		System.out.print("Choose game");
		System.out.print("\n1. Texas Holdem");
		System.out.print("\n2. 5 Card Draw");
		System.out.print("\n3. 7 Card Stud");
		int game = 3;//Get_Choice(1,3);
		
		//Επιλέγουμε τον πλήθος των παιχτών
		System.out.print("How many players(2-8)");
		int players = 7;// Get_Choice(2,8);
		
		//Επιλέγουμε το σύστημα πονταρίσματος
		System.out.print("Choose beting system");
		System.out.print("\n1. Stack Limit");
		Beting_System Bet;
		choice = 1;// Get_Choice(1,1);
		if (choice==1)
			Bet = new Beting_Stack_Limit();
		else
			Bet = new Beting_Stack_Limit();
		
		//Επιλέγουμε τον τύπο παιχνιδιού
		System.out.print("Choose playstyle");
		System.out.print("\n1. Console");
		System.out.print("\n2. Window");
		boolean gui;
		choice = 2;// Get_Choice(1,2);/////////////////////////////////////
		
		if (choice == 1)
			gui = false;
		else
			gui = true;
		
		
		Table newTable;
		//Δημιουργούμε το τραπέζι
		if (game == 1)
			newTable = new TexasHoldem_Table(players,Bet,gui);
		else if (game == 2)
			 newTable = new Card_5_Draw_Table(players,Bet,gui);
		else if (game == 3)
			 newTable = new Card_7_Stud_Table(players,Bet,gui);
		else
			 newTable = new TexasHoldem_Table(players,Bet,gui);
		
		//Προσθέτουμε τους παίχτες
		for (int i=0;i<players;i++)
			newTable.Add_Player(true);
		
		// Aν έχουμε γραφικό παιχνίδι
		if (gui)
		{
			//Αρχικοποιούμε το παράθυρο
			int size=600;
			newTable.F = new Frame(size, newTable);
			newTable.F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			newTable.F.setResizable(false);
			newTable.F.setVisible(true);
		}
		
		//Ξεκινάμε το παιχνίδι
		newTable.Start_Game();
		
		
		while(!newTable.Finished())//Όσο το παιχνίδι δεν έχει τελειώσει 
		{
			newTable.PlayGame();//Παίξε
			if (gui)// Aν έχουμε γραφικό παιχνίδι
				newTable.F.repaint();// Ενημέρωσε το γραφικό
		}
		
	}
	
	public static int Get_Choice(int min,int max)
	{
		int choice=-1;
		
		System.out.print("\nChoice (" + min + "," + max + "):");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in) );
		try{choice = Integer.parseInt(br.readLine());}catch(Exception e){}
		while(choice < min || choice > max)
		{
			System.out.print("\nChoose again:");
			try{choice = Integer.parseInt(br.readLine());}catch(Exception e){}
		}
		
		return choice;
	}
}
