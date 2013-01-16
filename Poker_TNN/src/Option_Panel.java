import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Option_Panel extends JPanel implements ActionListener
{
	/**
	 * @uml.property  name="check"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton check;
	/**
	 * @uml.property  name="call"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton call;
	/**
	 * @uml.property  name="raise"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton raise;
	/**
	 * @uml.property  name="fold"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton fold;
	/**
	 * @uml.property  name="tx1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public JTextField tx1;
	/**
	 * @uml.property  name="tx2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public JTextField tx2;
	/**
	 * @uml.property  name="tx3"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public JTextField tx3;
	/**
	 * @uml.property  name="tx4"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	public JTextField tx4;
	/**
	 * @uml.property  name="table"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Table table;
	
	Option_Panel(Table newTable)
	{
		this.table = newTable;
		this.setLayout(new GridLayout(2,5,10,10));
		
		check = new JButton("Check");
		call = new JButton("Call");
		raise = new JButton("Raise");
		fold = new JButton("Fold");
		
		tx1 = new JTextField(10);
		tx2 = new JTextField(10);
		tx3 = new JTextField(10);
		tx4 = new JTextField(10);
		
		tx1.setVisible(false);
		tx2.setVisible(false);
		tx3.setVisible(false);
		tx4.setVisible(false);
		
		tx3.addKeyListener
	      (new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	           int key = e.getKeyCode();
	           if (key == KeyEvent.VK_ENTER) {
	              Toolkit.getDefaultToolkit().beep();   
	              	if (!tx3.getText().isEmpty())
	              	{
		              	Out output = new Out ("choice.txt");
		  				output.print(tx3.getText());
		  				output.close();
		  				tx3.setText("");
		  				tx3.setVisible(false);
	              	}
	              }
	           }
	         }
	      );
	    
		
		check.addActionListener(this);
		call.addActionListener(this);
		raise.addActionListener(this);
		fold.addActionListener(this);
		
		this.add(tx1);
		this.add(tx2);
		this.add(tx3);
		this.add(tx4);
		this.add(check);
		this.add(call);
		this.add(raise);
		this.add(fold);

	}
	
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == check)
		{
			Out output = new Out ("choice.txt");
			output.print("1");
			output.close();
		}
		else if (e.getSource() == call)
		{
			Out output = new Out ("choice.txt");
			output.print("2");
			output.close();
		}
		else if (e.getSource() == raise)
		{
			tx3.setVisible(true);
			Out output = new Out ("choice.txt");
			output.print("3");
			output.close();

		}
		else if (e.getSource() == fold)
		{
			Out output = new Out ("choice.txt");
			output.print("4");
			output.close();
		}
	}

}
