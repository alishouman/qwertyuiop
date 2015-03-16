package View;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;


import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;

import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ElectionResult extends JFrame {
	JPanel splitPane;
	JPanel result;
	
	public ElectionResult() {
	//	getContentPane().setLayout();
		//Create a split pane with the two scroll panes in it.
	//	Icon image = new ImageIcon( "obama.jpg" );
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		result=new JPanel();
		result.setLayout(new BorderLayout());
		// ImageIcon barIcon = new ImageIcon("barchart.gif");
		 //BufferedImage image= resizeImage(barIcon,getHeight()/4,getWidth()/4));
		 JButton barChart=new JButton(new ImageIcon(((new ImageIcon(
		            "barchart.gif").getImage()
		            .getScaledInstance(64, 64,
		                    java.awt.Image.SCALE_SMOOTH)))));
		// ImageIcon pieIcon = new ImageIcon("3d_pie_chart.jpg");
		
		
	
		 JButton pieChart=new JButton(new ImageIcon(((new ImageIcon(
		            "3d_pie_chart.jpg").getImage()
		            .getScaledInstance(64, 64,
		                    java.awt.Image.SCALE_SMOOTH)))));
		 barChart.setSize(getHeight()/4,getWidth()/4);

		pieChart.setSize(getHeight()/4,getWidth()/4);
		//JLabel label = new JLabel( "barchart.gif" );
		//barChart.add(label);
		//JScrollPane leftScrollPane = new JScrollPane();
		//JPanel rightPane= new JPanel();
		 splitPane = new JPanel();
		 splitPane.setLayout(new BorderLayout());
		//splitPane.setOneTouchExpandable(true);
		//splitPane.setDividerLocation(230);
		//leftScrollPane.getViewport().add( label );
		//Provide minimum sizes for the two components in the split pane
		//Dimension minimumSize = new Dimension(100, 50);
		//comp.setMinimumSize(minimumSize);
		//bar.setMinimumSize(minimumSize);
		
		splitPane.add(pieChart,BorderLayout.EAST);
		splitPane.add(barChart,BorderLayout.WEST);
		getContentPane().add(splitPane,BorderLayout.EAST);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(result, BorderLayout.CENTER);
		JLabel lblWelcomeToThe = new JLabel("Welcome to the election results page.");
		lblWelcomeToThe.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		toolBar.add(lblWelcomeToThe);
		pieChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyComponent comp= new MyComponent();
				/*Dimension minimumSize = new Dimension(getWidth()/2, getHeight());
				comp.setMinimumSize(minimumSize);*/
				result.removeAll();
				result.add(comp,BorderLayout.CENTER);
				result.revalidate();
			}
		});
		barChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double[] values = new double[5];
			    String[] names = new String[5];
			    values[0] = 1;
			    names[0] = "Item 1";
			 
			    values[1] = 2;
			    names[1] = "Item 2";
			 
			    values[2] = 4;
			    names[2] = "Item 3";
			    values[3] = 4;
			    names[3] = "Item 3";
			    values[4] = 4;
			    names[4] = "Item 3";
				BarChart2 bar= new BarChart2(values,names,"Result");
				/*Dimension minimumSize = new Dimension(getWidth()/2, getHeight());
				bar.setMinimumSize(minimumSize);
				bar.setMaximumSize(minimumSize);*/
				result.removeAll();
				result.add(bar,BorderLayout.CENTER);
				result.revalidate();
			}
		});
	}

	 public static BufferedImage resizeImage(final Image image, int width, int height) {
	        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        final Graphics2D graphics2D = bufferedImage.createGraphics();
	        graphics2D.setComposite(AlphaComposite.Src);
	        graphics2D.drawImage(image, 0, 0, width, height, null);
	        graphics2D.dispose();
	 
	        return bufferedImage;
	    }
public static void main (String []args){
	ElectionResult e =new  ElectionResult();
	
		e.setSize(500, 500);
        e.setLocationRelativeTo(null);
        e.setVisible(true);
	

}
}