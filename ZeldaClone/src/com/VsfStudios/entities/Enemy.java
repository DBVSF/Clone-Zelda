package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.VsfStudio.main.Game;
import com.VsfStudio.world.Camera;
import com.VsfStudio.world.World;

public class Enemy extends Entity {
	
	private double speed = 0.01;
	private int maskx =8, masky = 8, maskw = 10, maskh =10; 
	private int frames = 0, maxFrames = 500, index = 0, maxIndex = 1;
	private BufferedImage[]sprites;
	private int life = 10 ;
	private boolean isDamaged = false;
	private int demageFrames = 500,demageCurrent = 0;
	
	public Enemy(int x, int y, int w, int h, BufferedImage sprite) {
		super(x, y, w, h, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(96+16, 16, 16, 16);
				
	}
	
	
	
	public void tick () {
		
		if(this.isColiddingWithPlayer() == false ) { //< colisão do enemy com o player
			
		
		
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
		}else {
			
			if (Game.rand.nextInt(100) < 1) {
				Game.player.life-=Game.rand.nextInt(3);
				Game.player.isDemaged = true;
				
				
				
			}
			
		}
			frames++;
			if (frames == maxFrames) {
				frames = 0;	
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		
			collidingBullet();
			
			if (life <= 0) {
				destroySelf();
				return;
			}
			
			if (isDamaged) {
				this.demageCurrent++;
				if(this.demageCurrent == this.demageFrames) {
					this.demageCurrent=0;
					this.isDamaged = false;
					
				}
			}
			
		}
		
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet () {
		for (int i = 0; i <Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if (e instanceof BulletShoot) {
				
				if (Entity.isColidding(this, e)) {
					isDamaged = true;
					life--;
					Game.bullets.remove(i);
					return;
				}
			}
		}
		
	}
	
	public boolean isColiddingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX()+maskx, this.getY()+masky,maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(player);
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
		if (!isDamaged) {
			g.drawImage(sprites[index],this.getX() -Camera.x,this.getY() - Camera.y,null);
		}else {
			g.drawImage(Entity.ENEMY_DEMAGEFEEDBACK,this.getX() -Camera.x,this.getY() - Camera.y,null);
		}
		//super.render(g);
	
	}
}	
