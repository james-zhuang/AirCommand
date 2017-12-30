package com.ac.movingimages;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class Bullet extends MovingImage{
	private double angle;
	private int winWidth, winHeight;
	public boolean isDead = false;

	public Bullet(Image s, int x, int y, int width, int height, double angle, int wWidth, int wHeight) {
		super(s, x, y, width, height);
		this.angle = angle;
		winWidth = wWidth;
		winHeight = wHeight;
	}
	public void draw(Graphics g, ImageObserver io) 
	{
		if(!isDead){
			Graphics2D g2 = (Graphics2D)g;
			AffineTransform t = g2.getTransform();
			g2.rotate(Math.toRadians(angle), x+width/2, y+height/2);
			g.drawImage(img,x,y,width,height,io);
			g2.setTransform(t);
		}
	}
	public void fly(){
		if(x > winWidth || x < 0 || y > winHeight || y < 0)
			isDead = true;
		else{
			y -= 8*Math.cos(Math.toRadians(angle));
			x += 8*Math.sin(Math.toRadians(angle));
		}
	}
	public boolean hitPlane(Plane p){
		return p.isPointInPlane(x, y);
	}
}
