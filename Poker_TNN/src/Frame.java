import java.awt.*;

import javax.swing.*;


public class Frame extends JFrame 
{
	/**
	 * @uml.property  name="size"
	 */
	private int size;
	/**
	 * @uml.property  name="oP"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public Option_Panel OP;
	/**
	 * @uml.property  name="pP"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Playing_Panel PP;
	
	Frame(int size, Table newTable)
	{
		this.size = size;
		this.setSize(size+100, size+100);
		
		PP = new Playing_Panel(size, newTable);
		PP.setSize(size, size);
		OP = new Option_Panel(newTable);
		                                                                              
		this.setLayout(new BorderLayout());
		this.add("Center", PP);
		this.add("South", OP);
	}
	

	
}
