package com.ac.screens;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ac.movingimages.Airplane;
import com.ac.movingimages.EnemyPlane;
import com.ac.movingimages.Plane;
import com.ac.objects.HealthBar;
import com.ac.objects.Server;
public class GameScreen extends JPanel implements KeyListener{
	public static String className = "gs";
	public Airplane myPlane;
	public EnemyPlane enemyPlane;
	private Image background, loseBackground, winBackground, redBackground, blueBackground;
	public HealthBar playerHealth, enemyHealth;
	public int gameState = 0;
	private int mode;
	public boolean connect = false;


	public GameScreen() throws IOException{
		setBackground(new Color(235, 225, 235));
		myPlane = new Airplane((new ImageIcon("redPlane.png")).getImage(), 1280/2-100, 960/2-100, 100, 100, 1280, 960, mode);
		enemyPlane = new EnemyPlane((new ImageIcon("bluePlane.png")).getImage(), 1280/2-100, 960/2-100, 100, 100, 1280, 960);
		background = new ImageIcon("sky.jpg").getImage();
		loseBackground = new ImageIcon("lose.jpg").getImage();
		winBackground = new ImageIcon("windat$$$.jpg").getImage();
		redBackground = new ImageIcon("redWinna.jpg").getImage();
		blueBackground = new ImageIcon("blueWinna.jpg").getImage();

		playerHealth = new HealthBar(100, myPlane);
		enemyHealth = new HealthBar(100, enemyPlane);
		try {
			Server.recieveKey(enemyPlane);
		} catch (InterruptedException e) {e.printStackTrace();}
		add(playerHealth);
		add(enemyHealth);
		addKeyListener(this);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(mode == 1){
			if(enemyPlane.isDead){
				g.drawImage(winBackground, 0, 0, 1280, 960, this);
			}
			else if(myPlane.isDead){
				g.drawImage(loseBackground, 0, 0, 1280, 960, this);
			}
			else
				g.drawImage(background, 0, 0, 1280, 960, this);
		}
		else if(mode == 2){
			if(enemyPlane.isDead){
				g.drawImage(redBackground, 0, 0, 1280, 960, this);
			}
			else if(myPlane.isDead){
				g.drawImage(blueBackground, 0, 0, 1280, 960, this);
			}
			else
				g.drawImage(background, 0, 0, 1280, 960, this);
		}

		myPlane.draw(g, this, enemyPlane);
		enemyPlane.draw(g, this, myPlane);
	}

	public void run(boolean c) {
		myPlane.setMode(mode);
		while(c) {
			myPlane.turnConditions();
			myPlane.fly();
			playerHealth.check();
			enemyPlane.turnConditions();
			enemyPlane.fly();
			enemyHealth.check();
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

		if(mode == 2){
			if (code == KeyEvent.VK_A)
				enemyPlane.setLeft(true);
			else if (code == KeyEvent.VK_D)
				enemyPlane.setRight(true);	
			if(code == KeyEvent.VK_W)
				enemyPlane.setSpeed(5);
			else if(code == KeyEvent.VK_S)
				enemyPlane.setSpeed(3);
			if(code == KeyEvent.VK_T)
				enemyPlane.shoot();
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

		if(mode == 2){
			if (code == KeyEvent.VK_A)
				enemyPlane.setLeft(false);
			else if (code == KeyEvent.VK_D)
				enemyPlane.setRight(false);	
			if(code == KeyEvent.VK_W)
				enemyPlane.setSpeed(4);
			else if(code == KeyEvent.VK_S)
				enemyPlane.setSpeed(4);
		}
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
	public void changeMode(int x){
		mode = x;
	}
	public void sendSignal(){
		try {
			myPlane.setSignal();
			connect = true;
		}
		catch (IOException e) {
			connect = false;
		}
	}
}
