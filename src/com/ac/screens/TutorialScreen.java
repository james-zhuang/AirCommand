package com.ac.screens;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.ac.movingimages.Airplane;


public class TutorialScreen extends JPanel implements KeyListener{
		public boolean backVar;
		public boolean go = false;
		public static String className = "os";
		private Image background;
		public Airplane myPlane;
	
		public TutorialScreen(){
			super();
			setBackground(new Color(235, 225, 235));
			background = new ImageIcon("tutorialbg.jpg").getImage();
			setLayout(null);
			final JButton backButton = new JButton("");
			add(backButton);
			backButton.setLocation(10, 30);
			backButton.setSize(64, 64);
			backButton.setIcon(new ImageIcon("clickArrow.png"));
			backButton.setBorder(null);
			backButton.setContentAreaFilled(false);
			myPlane = new Airplane((new ImageIcon("redPlane.png")).getImage(), 1280/2-100, 960/2-100, 100, 100, 1280, 960, 2);


			
			backButton.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					backVar = true;
				}
			});
			backButton.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	backButton.setIcon(new ImageIcon("arrow.png"));
			    }

			    public void mouseExited(java.awt.event.MouseEvent evt) {
			    	backButton.setIcon(new ImageIcon("clickArrow.png"));
			    }
			});
		} 	
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(background, 0, 0, 1280, 960, this);

			myPlane.draw(g, this);
		}
		public void run() {
			while(go) {
				myPlane.turnConditions();
				myPlane.fly();

				repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

				}
			}
		}

		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_LEFT)
				myPlane.setLeft(true);
			else if(code == KeyEvent.VK_RIGHT)
				myPlane.setRight(true);

			if(code == KeyEvent.VK_UP)
				myPlane.setSpeed(5);
			else if(code == KeyEvent.VK_DOWN)
				myPlane.setSpeed(3);

			if(code == KeyEvent.VK_SPACE){
				myPlane.shoot();
			}
		}
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_LEFT)
				myPlane.setLeft(false);
			else if(code == KeyEvent.VK_RIGHT)
				myPlane.setRight(false);

			if(code == KeyEvent.VK_UP)
				myPlane.setSpeed(4);
			else if(code == KeyEvent.VK_DOWN)
				myPlane.setSpeed(4);
		}
		public void keyTyped(KeyEvent e) {}

		public void update(Graphics g) {

			Graphics offgc;
			Image offscreen = null;
			Rectangle box = g.getClipBounds();

			offscreen = createImage(box.width, box.height);
			offgc = offscreen.getGraphics();

			offgc.setColor(getBackground());
			offgc.fillRect(0, 0, box.width, box.height);
			offgc.setColor(getForeground());

			offgc.translate(-box.x, -box.y);
			paint(offgc);

			g.drawImage(offscreen, box.x, box.y, this);
		}
		public void presettings(Graphics g){
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
}	
