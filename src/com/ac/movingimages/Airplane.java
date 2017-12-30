package com.ac.movingimages;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.ac.objects.Client;

public class Airplane extends Plane{

	private int mode;
	public String otherIP = "localhost";//172.16.240.202";"



	public Airplane(Image s, int x, int y, int width, int height, int wWidth, int wHeight, int mode) {
		super(s, x, y, width, height);
		healthLost = 0;
		speed = 4;
		canTurn = true;
		winWidth = wWidth;
		winHeight = wHeight;
		isDead = false;
		explodeImg = new ImageIcon("explosion.png").getImage();
		this.mode = mode;
	}

	public void draw(Graphics g, ImageObserver io, EnemyPlane enemyPlane) {
		if(!isDead){
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform t = g2.getTransform();
			g2.rotate(Math.toRadians(angle), x + width / 2, y + height / 2);
			g.drawImage(img, x, y, width, height, io);
			g2.setTransform(t);
			try{
				for (Bullet b: bullets) {
					if(b.isDead)
						bullets.remove(b);
					else{
						b.draw(g, io);
						b.fly();
						if(b.hitPlane(enemyPlane)){
							enemyPlane.healthLost += 5;
							bullets.remove(b);
						}
					}
				}
			}catch(ConcurrentModificationException e){}
		}else{
			if(timeOfDeath - System.currentTimeMillis() < 3000)
				g.drawImage(explodeImg, x, y, width, height, io);
		}
	}
	public void draw(Graphics g, ImageObserver io) {
		if(!isDead){
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform t = g2.getTransform();
			g2.rotate(Math.toRadians(angle), x + width / 2, y + height / 2);
			g.drawImage(img, x, y, width, height, io);
			g2.setTransform(t);
			try{
				for (Bullet b: bullets) {
					if(b.isDead)
						bullets.remove(b);
					else{
						b.draw(g, io);
						b.fly();
					}
				}
			}catch(ConcurrentModificationException e){}
		}else{
			if(timeOfDeath - System.currentTimeMillis() < 3000)
				g.drawImage(explodeImg, x, y, width, height, io);
		}
	}
	// 0 is true 1 is false
	public void setLeft(boolean t) {
		if(mode == 1){
			try {
				if(t)
					Client.sendKey(0, otherIP);
				else

					Client.sendKey(1, otherIP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		left = t;
	}
	// 2 is true 3 is false
	public void setRight(boolean t) {
		if(mode == 1){
			try {
				if(t)
					Client.sendKey(2, otherIP);
				else

					Client.sendKey(3, otherIP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		right = t;
	}
	public void shoot() {
		if (System.currentTimeMillis() - time >= 225) {
			if(mode == 1){
				try {
					Client.sendKey(4, otherIP);
					System.out.println("Sent     4");
				} catch (IOException e){e.printStackTrace();}
			}
			bullets.add(new Bullet((new ImageIcon("bullet.png")).getImage(), (int)(x + width / 2 + -10 + 60 * Math.sin(Math.toRadians(angle))), (int)(y + height / 2 - 10 - 60 * Math.cos(Math.toRadians(angle))), 20, 20, angle, winWidth, winHeight));
			time = System.currentTimeMillis();
		}
	}
	public void setSpeed(int x){
		if(mode == 1){
			try {
				if(x == 4)
					Client.sendKey(5, otherIP);
				else if(x == 5)
					Client.sendKey(6, otherIP);
				else if(x == 3)
					Client.sendKey(7, otherIP);
			} catch (IOException e) {e.printStackTrace();}
		}
		speed = x;
	}
	public void setMode(int x){
		mode = x;
	}

	public void setSignal() throws IOException {
		Client.sendKey(42, otherIP);
	}
}