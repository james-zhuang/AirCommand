package com.ac.objects;

import java.awt.Color;

import javax.swing.JProgressBar;

import com.ac.movingimages.Airplane;
import com.ac.movingimages.Plane;

public class HealthBar extends JProgressBar{
	private int maxHealth;
	private int healthLost;
	private Plane p;
	
	public HealthBar(int max, Plane p){
		super(0, max);
		maxHealth = max;
		healthLost = 0;
		this.p = p;
		
		setStringPainted(true);
		setString((maxHealth - healthLost) + " / " + maxHealth);
		setForeground(Color.RED);
		setBackground(Color.WHITE);
		setValue(maxHealth - healthLost);
	}
	public void loseHealth(int x){
		if(healthLost + x <= maxHealth)
			healthLost += x;
		else
			healthLost = maxHealth;
		
		setString((maxHealth - healthLost) + " / " + maxHealth);
		setValue(maxHealth - healthLost);
		repaint();
		
		if(healthLost == maxHealth){
			p.lose();
		}
	}
	public void check(){
		loseHealth(p.healthLost - healthLost);
	}
}
