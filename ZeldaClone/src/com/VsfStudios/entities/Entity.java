package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.VsfStudio.main.Game;
import com.VsfStudio.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage AMMO_EN = Game.spritesheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(8*16, 16, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(9*16, 16, 16, 16);
	public static BufferedImage BULLET_SHOOT = Game.spritesheet.getSprite(9*16, 0, 16, 16);
	 //tudo oq tem colisão 
	protected double x;
	protected double y;
	protected double w;
	protected double h;
	
	private BufferedImage sprite;
	
	private int maskx, masky, mWidth, mHeight;
	
	public Entity(int x, int y, int w, int h, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mHeight = h;
		this.mWidth = w;
	}
	
	public void setMask(int maskx, int masky, int mWidth, int mHeight) {
		
		this.maskx = maskx;
		this.masky = masky;
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		
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
	
	public static boolean isColidding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle (e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mWidth, e1.mHeight);
		Rectangle e2Mask = new Rectangle (e2.getX() + e2.maskx,e2.getY()+e2.masky,e1.mWidth, e2.mHeight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX()- Camera.x, this.getY()- Camera.y,null);
		
	}
}
