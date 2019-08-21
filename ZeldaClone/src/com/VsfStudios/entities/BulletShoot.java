package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.VsfStudio.main.Game;
import com.VsfStudio.world.Camera;

public class BulletShoot extends Entity {
	
	private int dx, dy;
	private double spd = 0.04;
	
	private int life = 6000, curLife = 0;
	
	
	public BulletShoot(int x, int y, int w, int h, BufferedImage sprite,int dx, int dy) {
		super(x, y, w, h, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick(){
		x+=dx*spd;
		x+=dy*spd;
		curLife++;
		if (curLife == life) {
			Game.bullets.remove(this);
			return;
		}
		
	}

	public void render(Graphics g) {
	g.drawImage(Entity.BULLET_SHOOT, this.getX() - Camera.x,this.getY()- Camera.y,null);
	}
	
}
