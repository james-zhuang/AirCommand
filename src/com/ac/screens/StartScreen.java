package com.ac.screens;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class StartScreen extends JPanel{
	public boolean helpVar, playGame, userVar, settingVar;
	public static String className = "ss";
	public Image background;
	
	public StartScreen(){
		setLayout(null);
		final JButton startButton = new JButton();
		startButton.setIcon(new ImageIcon("Start.png"));
		startButton.setBorder(null);
		startButton.setContentAreaFilled(false);
		startButton.setSize(271, 147);
		startButton.setLocation(527 ,325);
		
		final JButton tutButton = new JButton("");
		tutButton.setIcon(new ImageIcon("Tutorial.png"));
		tutButton.setBorder(null);
		tutButton.setContentAreaFilled(false);
		tutButton.setSize(193,105);
		tutButton.setLocation(565, 520);

		
		background = new ImageIcon("Background.jpg").getImage();


		add(startButton);
		add(tutButton);
		
		tutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyFrame.infoScreen(); 
			}
		});
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playGame = true;
			}
		});
		startButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	startButton.setIcon(new ImageIcon("StartH.png"));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	startButton.setIcon(new ImageIcon("Start.png"));
		    }
		});
		tutButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	tutButton.setIcon(new ImageIcon("TutorialH.png"));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	tutButton.setIcon(new ImageIcon("Tutorial.png"));
		    }
		});
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, 1280, 960, this);
	}
}	
