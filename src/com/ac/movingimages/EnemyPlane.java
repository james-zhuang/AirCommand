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

public class EnemyPlane extends Plane{

	public EnemyPlane(Image s, int x, int y, int width, int height, int wWidth, int wHeight) {
		super(s, x, y, width, height);
		healthLost = 0;
		speed = 4;
		canTurn = true;
		winWidth = wWidth;
		winHeight = wHeight;
		isDead = false;
		explodeImg = new ImageIcon("explosion.png").getImage();
	}
public void draw(Graphics g, ImageObserver io, Airplane myPlane) {
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
						if(b.hitPlane(myPlane)){
							myPlane.healthLost += 5;
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
	// 0 is true 1 is false
	public void setLeft(boolean t) {
		left = t;
	}
	// 2 is true 3 is false
	public void setRight(boolean t) {
		right = t;
	}
	
	public void shoot() {
		if (System.currentTimeMillis() - time >= 225) {
			bullets.add(new Bullet((new ImageIcon("bullet.png")).getImage(), (int)(x + width / 2 + -10 + 60 * Math.sin(Math.toRadians(angle))), (int)(y + height / 2 - 10 - 60 * Math.cos(Math.toRadians(angle))), 20, 20, angle, winWidth, winHeight));
			time = System.currentTimeMillis();
		}
	}
	public void setSpeed(int x){
		speed = x;
	}
	/*
	 * lP lR left	0 1
	 * rP rR right  2 3
	 * uP uR up 	6 5
	 * dP dR down   7 5
	 *   s   space   4
	 */
	public void doAction(int read) {
		if(read == 0){
			setLeft(true);
		}
		else if(read == 1){
			setLeft(false);
		}
		else if(read == 2){
			setRight(true);
		}
		else if(read == 3){
			setRight(false);
		}
		else if(read == 4){
			shoot();
		}
		else if(read == 5){
			setSpeed(4);
		}
		else if(read == 6){
			setSpeed(5);
		}
		else if(read == 7){
			setSpeed(3);
		}
		
	}
}