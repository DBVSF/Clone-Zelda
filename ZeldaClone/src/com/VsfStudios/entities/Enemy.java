package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.VsfStudio.main.Game;
import com.VsfStudio.world.Camera;
import com.VsfStudio.world.World;

public class Enemy extends Entity {
	
	private double speed = 0.007;
	
	private int maskx =8, masky = 8, maskw = 10, maskh =10; 
	
	private int frames = 0, maxFrames = 550, index = 0, maxIndex = 1;
	
	private BufferedImage[]sprites;
	
	public Enemy(int x, int y, int w, int h, BufferedImage sprite) {
		super(x, y, w, h, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(96+16, 16, 16, 16);
				
	}
	
	public void tick () {
		if ((int)x < Game.player.getX()&& World.isFree((int)(x+speed),this.getY())
				&& !isColidding((int)(x+speed),this.getY())) {
			x+=speed;
		}else if ((int)x > Game.player.getX()&& World.isFree((int)(x-speed),this.getY())
				&& !isColidding((int)(x-speed),this.getY())) {
			x-=speed;
		}if ((int)y < Game.player.getY()&& World.isFree(this.getX(),(int)(y+speed))
				&& !isColidding(this.getX(),(int)(y+speed))) {
			y+=speed;
		}else if ((int)y > Game.player.getY()&& World.isFree(this.getX(),(int)(y-speed))
				&& !isColidding(this.getX(),(int)(y-speed))) {
			y-=speed;
		}
			frames++;
			if (frames == maxFrames) {
				frames = 0;	
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		
		
		
	}
	
	public boolean isColidding (int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext+maskx, ynext+masky,maskw, maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) 
				continue;
				Rectangle targetEnemy = new Rectangle(e.getX()+maskx,e.getY()+masky,maskw, maskh);
				if (enemyCurrent.intersects(targetEnemy)) {
					return true;
					
				
			}
	}
		return false;	
}
	public void render(Graphics g) {
		//super.render(g);
		g.drawImage(sprites[index],this.getX() -Camera.x,this.getY() - Camera.y,null);
	}
}	
