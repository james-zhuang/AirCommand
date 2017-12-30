package com.ac.movingimages;

import java.awt.Image;
import java.util.ArrayList;

public abstract class Plane extends MovingImage{

	ArrayList < Bullet > bullets = new ArrayList < Bullet > ();
	protected int speed, winWidth, winHeight;
	public double angle;
	protected boolean left, right, canTurn;
	public int healthLost;
	protected long timeOfDeath,time = 0;
	public boolean isDead;
	protected Image explodeImg;
	
	public Plane(Image img, int x, int y, int width, int height) {
		super(img, x, y, width, height);
	}
	public void turn(int direction) {
		if (direction == 0) {
			if (angle <= 357) angle += 3;
			else angle = 2;
		}
		else if (direction == 1) {
			if (angle >= 0) angle -= 3;
			else angle = 357;
		}
	}
	public void fly() {
		if(!isDead){
			boolean conditions = x < 1320 && x > -50 && y < 1010 && y > -50;

			if (conditions) {
				y -= speed * Math.cos(Math.toRadians(angle));
				x += speed * Math.sin(Math.toRadians(angle));
				if (x < 0 || x > 1280 || y > 960 || y < 0) 
					canTurn = false;
				else
					canTurn = true;
			}
			else {
				canTurn = false;
				if (y <= -100) 
					angle = 180;

				else if (y >= 1060)
					angle = 0;

				if (x <= -100) 
					angle = 90;
				else if (x >= 1380) 
					angle = 270;
				if (x < 0 && y < 0) 
					angle = 135;
				else if (x < 0 && y > 960) 
					angle = 45;
				if (x > 1280 && y > 960) 
					angle = 315;
				else if (x > 1280 && y < 0) 
					angle = 225;
				y -= speed * Math.cos(Math.toRadians(angle));
				x += speed * Math.sin(Math.toRadians(angle));

			}

		}
	}
	public boolean isPointInPlane(int xPos, int yPos) {
		return contains(xPos, yPos);
	}
	public void lose() {
		isDead = true;
		timeOfDeath = System.currentTimeMillis();
	}
	public void turnConditions() {
		if (canTurn) {
			if (right) turn(0);
			if (left) turn(1);
		}
	}
}