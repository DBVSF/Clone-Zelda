package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {

	
	 //tudo oq tem colisão 
	protected double x;
	protected double y;
	protected double w;
	protected double h;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int w, int h, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = sprite;
	}
	public int getX() {
		return(int) this.x;
	}
	public int getY() {
		return(int) this.y;
	}
	public double getW() {
		return this.w;
	}
	public double getH() {
		return this.h;
	}
	
	public void tick() {
		
	}
	
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(),null);
		
	}
}
