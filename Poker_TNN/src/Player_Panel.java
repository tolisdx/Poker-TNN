import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.border.Border;


public class Player_Panel extends JPanel
{
	/**
	 * @uml.property  name="size"
	 */
	private int size;
	/**
	 * @uml.property  name="exists"
	 */
	private boolean exists;
	/**
	 * @uml.property  name="p"
	 * @uml.associationEnd  
	 */
	private Player P;
	/**
	 * @uml.property  name="blackline"
	 * @uml.associationEnd  
	 */
	private Border blackline;
	/**
	 * @uml.property  name="cards"
	 * @uml.associationEnd  readOnly="true"
	 */
	private Card_Panel cards;
	
	Player_Panel(int size, Player P,boolean exists)
	{
		this.size = size/3;
		this.setSize(size, size);
		this.exists = exists;
		this.P = P;
		
		
		
		blackline = BorderFactory.createLineBorder(Color.black,3);
		this.setBorder(blackline);
		
	
		
		
	}
	
	Player_Panel(boolean exists)
	{
		this.exists = exists;
	}
	
	protected void paintComponent(Graphics g)
	{
		this.removeAll();
		
		super.paintComponent(g);
		
		if (exists)
		{
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			if (P.Get_IsPlaying())g.setColor(Color.BLUE);
			else g.setColor(Color.BLACK);
			g.drawString(P.Get_Name(), 10,120);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Stack:" + String.valueOf(P.Get_Stack()), 10,150);
			
			if (P.Get_Folded())		
				return;
			
			g.setColor(Color.GRAY);
			g.setFont(new Font("Courier", Font.BOLD, 15));
			if (P.Get_OnDealer())
				g.drawString("Dealer", this.getWidth()-60,this.getHeight()-20);
			if (P.Get_OnSmallBLind())
				g.drawString("SmallB", this.getWidth()-60,this.getHeight()-50);
			if (P.Get_OnBigBLind())
				g.drawString("BigB", this.getWidth()-60,this.getHeight()-50);	
			
			
			this.add( new Card_Panel(P.hand));/////////////////////////////////////////////////////
		}
	}
	
	
}
