import javax.swing.*;
import java.awt.*;

import javax.swing.BorderFactory.*;
import javax.swing.border.Border;

public class Table_Panel extends JPanel 
{
	/**
	 * @uml.property  name="blackline"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Border blackline;

	/**
	 * @uml.property  name="image"
	 */
	Image image;
	/**
	 * @uml.property  name="size"
	 */
	int size;
	/**
	 * @uml.property  name="table"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Table table;
	
	Table_Panel(int size, Table newTable)
	{
		this.table = newTable;
		this.size = size/3;
		this.setSize(this.size, this.size);
		
		blackline = BorderFactory.createLineBorder(Color.black,5);
		this.setBorder(blackline);
		
		try
	    {
			image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("table.jpg"), "table.jpg"));
	    }
	    catch (Exception e) { System.out.println("Error loading table.jpg"); }
	}

	protected void paintComponent(Graphics g)
	{
		this.removeAll();
		super.paintComponent(g);
	    if (image != null)
	      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
	    
	    this.add( new Card_Panel(table.Cards_Down));/////////////////////////////////////////////////////
 
	    g.setFont(new Font("Arial", Font.BOLD, 17));
	    g.drawString("STACK:" + table.Get_Total_Stack(), 10, this.getHeight()-20);
	    g.setFont(new Font("Courier", Font.PLAIN, 10));
	    g.drawString("SB: " + table.Bet.Get_SmallBLind(), this.getWidth()-60, this.getHeight()-30);
	    g.drawString("BB: " + table.Bet.Get_BigBLind(), this.getWidth()-60, this.getHeight()-20);
	    if (table.Bet.Get_Ante() != 0)
	    {
	    	g.drawString("Ante: " + table.Bet.Get_Ante(), this.getWidth()-60, this.getHeight()-40);
	    }
	}
	
	
}
