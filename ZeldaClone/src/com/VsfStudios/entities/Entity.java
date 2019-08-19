package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.VsfStudio.main.Game;
import com.VsfStudio.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	
	
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
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
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
		g.drawImage(sprite, this.getX()- Camera.x, this.getY()- Camera.y,null);
		
	}
}
