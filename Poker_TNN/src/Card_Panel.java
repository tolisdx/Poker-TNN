import java.awt.Graphics;
import java.awt.Image;
import java.util.*;

import javax.swing.*;

public class Card_Panel extends JPanel
{
	
	/**
	 * @uml.property  name="image" multiplicity="(0 -1)" dimension="1"
	 */
	Image[] image;

	Card_Panel(ArrayList<card>  cards)
	{
		if (cards.size() > 0)
		{
			this.setSize(71 + (cards.size()-1)*20, 96);
		}
		
		image = new Image[cards.size()];
		
		for (int i=0;i<cards.size();i++)
		{
			if (cards.get(i).open)// Aν το φύλλο πρέπει να πέσει ανοιχτό
			{
				try
			    {
			      image[i] = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("cards\\" + cards.get(i).value + ".gif"), "cards\\" + cards.get(i).value + ".gif"));
			    }
			    catch (Exception e) { System.out.println("Error loading " + cards.get(i).value + ".gif"); }
			}
			else// Aν το φύλλο πρέπει να πέσει κλειστό
			{
				try
			    {
			      image[i] = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("cards\\back.gif"), "cards\\back.gif"));
			    }
			    catch (Exception e) { System.out.println("Error loading back.gif"); }
			}
		}
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int x=0,y=0;
		for (int i=0;i<image.length;i++)
		{
			if (image[i] != null)
				g.drawImage(image[i], x,y,71,96,this);
			x=x+20;
		}
	}

}
