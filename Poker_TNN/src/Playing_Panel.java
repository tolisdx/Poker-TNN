import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class Playing_Panel extends JPanel
{
	/**
	 * @uml.property  name="size"
	 */
	private int size;
	/**
	 * @uml.property  name="players"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	private Player_Panel[] Players;
	/**
	 * @uml.property  name="tP"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Table_Panel TP;
	
	Playing_Panel(int size, Table newTable)
	{
		this.size = size;
		this.setLayout(new GridLayout(3,3,0,0));
		this.setSize(size, size);
		
		
		
		Players = new Player_Panel[newTable.Get_Players()];
		
		if (newTable.Get_Players() == 2)// Αν υπάρχουν 2 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			TP = new Table_Panel(size,newTable);this.add(TP);
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
		}
		else if (newTable.Get_Players() == 3)// Αν υπάρχουν 3 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			Players[2] = new Player_Panel(size, newTable.Players.get(2), true);this.add(Players[2]);
			this.add(new Player_Panel(false));
			TP = new Table_Panel(size,newTable);this.add(TP);
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
		}
		else if (newTable.Get_Players() == 4)// Αν υπάρχουν 4 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			Players[2] = new Player_Panel(size, newTable.Players.get(2), true);this.add(Players[2]);
			this.add(new Player_Panel(false));
			TP = new Table_Panel(size,newTable);this.add(TP);
			Players[3] = new Player_Panel(size, newTable.Players.get(3), true);this.add(Players[3]);
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
		}
		else if (newTable.Get_Players() == 5)// Αν υπάρχουν 5 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			Players[2] = new Player_Panel(size, newTable.Players.get(2), true);this.add(Players[2]);
			this.add(new Player_Panel(false));
			TP = new Table_Panel(size,newTable);this.add(TP);
			Players[3] = new Player_Panel(size, newTable.Players.get(3), true);this.add(Players[3]);
			this.add(new Player_Panel(false));
			this.add(new Player_Panel(false));
			Players[4] = new Player_Panel(size, newTable.Players.get(4), true);this.add(Players[4]);
		}
		else if (newTable.Get_Players() == 6)// Αν υπάρχουν 6 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			Players[2] = new Player_Panel(size, newTable.Players.get(2), true);this.add(Players[2]);
			this.add(new Player_Panel(false));
			TP = new Table_Panel(size,newTable);this.add(TP);
			Players[3] = new Player_Panel(size, newTable.Players.get(3), true);this.add(Players[3]);
			this.add(new Player_Panel(false));
			Players[5] = new Player_Panel(size, newTable.Players.get(5), true);this.add(Players[5]);
			Players[4] = new Player_Panel(size, newTable.Players.get(4), true);this.add(Players[4]);
		}
		else if (newTable.Get_Players() == 7)// Αν υπάρχουν 7 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			Players[2] = new Player_Panel(size, newTable.Players.get(2), true);this.add(Players[2]);
			this.add(new Player_Panel(false));
			TP = new Table_Panel(size,newTable);this.add(TP);
			Players[3] = new Player_Panel(size, newTable.Players.get(3), true);this.add(Players[3]);
			Players[6] = new Player_Panel(size, newTable.Players.get(6), true);this.add(Players[6]);
			Players[5] = new Player_Panel(size, newTable.Players.get(5), true);this.add(Players[5]);
			Players[4] = new Player_Panel(size, newTable.Players.get(4), true);this.add(Players[4]);
		}
		else if (newTable.Get_Players() == 8)// Αν υπάρχουν 8 παίχτες
		{
			Players[0] = new Player_Panel(size, newTable.Players.get(0), true);this.add(Players[0]);
			Players[1] = new Player_Panel(size, newTable.Players.get(1), true);this.add(Players[1]);
			Players[2] = new Player_Panel(size, newTable.Players.get(2), true);this.add(Players[2]);
			Players[7] = new Player_Panel(size, newTable.Players.get(7), true);this.add(Players[7]);
			TP = new Table_Panel(size,newTable);this.add(TP);
			Players[3] = new Player_Panel(size, newTable.Players.get(3), true);this.add(Players[3]);
			Players[6] = new Player_Panel(size, newTable.Players.get(6), true);this.add(Players[6]);
			Players[5] = new Player_Panel(size, newTable.Players.get(5), true);this.add(Players[5]);
			Players[4] = new Player_Panel(size, newTable.Players.get(4), true);this.add(Players[4]);
		}

		this.setSize(size, size);
	}
	
	//public void paint(Graphics g)
	{
		//super.paintComponents(g);
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
		/*
		g.drawLine(size/3, 0, size/3, size);
		g.drawLine(2*size/3, 0, 2*size/3, size);
		g.drawLine(0, size/3, size, size/3);
		g.drawLine(0, 2*size/3, size, 2*size/3);
		g.drawLine(0, size, size, size);
		*/
			
		
	}

}
