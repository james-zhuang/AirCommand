package com.ac.movingimages;

import java.awt.Image;
import java.awt.Rectangle;

public class MovingImage extends Rectangle{
	Image img;
	public MovingImage(Image img, int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}

}
