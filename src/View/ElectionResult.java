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
import java.util.ArrayList;

public class ElectionResult extends JFrame {
	JPanel splitPane;
	JPanel result;
	
	public ElectionResult(final int[]values,final String[] names) {

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

		 JButton barChart=new JButton(new ImageIcon(((new ImageIcon(
		            "barchart.gif").getImage()
		            .getScaledInstance(64, 64,
		                    java.awt.Image.SCALE_SMOOTH)))));
		
		
	
		 JButton pieChart=new JButton(new ImageIcon(((new ImageIcon(
		            "3d_pie_chart.jpg").getImage()
		            .getScaledInstance(64, 64,
		                    java.awt.Image.SCALE_SMOOTH)))));
		 barChart.setSize(getHeight()/4,getWidth()/4);

		pieChart.setSize(getHeight()/4,getWidth()/4);
		
		 splitPane = new JPanel();
		 splitPane.setLayout(new BorderLayout());
	
		
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
				MyComponent comp= new MyComponent(values,names);
			
				result.removeAll();
				result.add(comp,BorderLayout.CENTER);
				result.revalidate();
			}
		});
		barChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BarChart2 bar= new BarChart2(values,names,"Result");
		
				result.removeAll();
				result.add(bar,BorderLayout.CENTER);
				result.revalidate();
			}
		});
	}

	

}