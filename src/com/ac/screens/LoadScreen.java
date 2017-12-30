package com.ac.screens;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class LoadScreen extends JPanel{
	public static String className = "ls";
	Image img;
	public LoadScreen(){
		img = new ImageIcon("loading.gif").getImage();
		setBackground(Color.BLACK);
	}
	public void paint(Graphics g){
		g.setColor(new Color(21,25,31));
		g.fillRect(0, 0, 1280, 960);
		g.drawImage(img, 240, 180, this);
	}
}
