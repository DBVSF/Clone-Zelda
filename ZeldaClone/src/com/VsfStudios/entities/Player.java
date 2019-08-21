package com.VsfStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.VsfStudio.main.Game;
import com.VsfStudio.world.Camera;
import com.VsfStudio.world.World;
import com.VsfStudios.graficos.Spritesheet;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public double speed = 0.07;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	
	
	private int frames = 0, maxFrames = 550, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[]rightPlayer;
	private BufferedImage[]leftPlayer;
	private BufferedImage playerDemage;
	public int ammo = 0;
	public boolean isDemaged = false;
	private int demageFrames = 0;
	public double life=100,maxLife=100;
	private boolean hasGun = false;
	public boolean shoot = false;
	
	
	
	
	public Player(int x, int y, int w, int h, BufferedImage sprite) {
		super(x, y, w, h, sprite);
		
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDemage = Game.spritesheet.getSprite(0, 16, 16, 16);
		for (int i = 0; i < 4; i++) {
		
			rightPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 0, 16, 16);
			
		}
		
		for (int i = 0; i < 4; i++) {
			
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 16, 16, 16);
			
		}
		
		
	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		} 
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;}
		if (up && World.isFree(this.getX(),(int)(y-speed))) {
			moved = true;
			y-=speed;}
		else if(down && World.isFree(this.getX(),(int)(y+speed))) {
			moved = true;
			y+=speed;}
		if (moved) {
			
			frames++;
			if (frames == maxFrames) {
				frames = 0;	
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
			
			if (life <= 0) {
				//reseta o game
				Game.entities.clear();
				Game.enemies.clear();
				Game.entities = new ArrayList<Entity>();
				Game.enemies = new ArrayList<Enemy>();
				Game.spritesheet = new Spritesheet("/spritesheet.png");
				Game.player = new Player (0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
				Game.entities.add(Game.player);
				Game.world = new World("/map.png");
				return;
				
			}
		}
		//chama os metodos de pegar arma,ammo,lifepack
		this.checkColiddionLifePack();
		this.checkColiddionAmmo();
		this.checkColiddionGun();
		if (isDemaged) {
			this.demageFrames++;
			if (this.demageFrames == 80) {
				this.demageFrames = 0;
				isDemaged = false;
			}
		}
		if (shoot) {
			
			shoot = false;
			if (hasGun && ammo > 0 ) {
				
			
			ammo--;
			int dx = 0;
			int px = 0;
			int py = 3;
			if (dir == right_dir) {
				px = 5;
				dx = 1; 
			}else {
				px = -5;
				dx = -1;
			}
			BulletShoot bullet = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,0);
			Game.bullets.add(bullet);			
		}
		}	
		
		//clamp limita a tela apenas nos tiles do mapa, tirando a parte preta da sala
		Camera.x =Camera.clamp(this.getX()-(Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y =Camera.clamp(this.getY()-(Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkColiddionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Ammo) {
				if (Entity.isColidding(this, atual)) {
					ammo++;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkColiddionGun() {
		//pega a arma
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Weapon) {
				if (Entity.isColidding(this, atual)) {
					hasGun = true;
					Game.entities.remove(atual);
				}
			}
			
		}
	}
	
	public void checkColiddionLifePack() {
		//pega o lifepack
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof LifePack) {
				if (Entity.isColidding(this, atual)) {
					life +=10;
					Game.entities.remove(atual);
				}
			}
			
		}
	}
	
	
	
	public void render(Graphics g) {
		if (!isDemaged) {
			//faz o boneco piscar ao tomar dado
		if (dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x,this.getY()- Camera.y,null);	
			
			if (hasGun) {
				//se pegar a arma indo pra direita
				g.drawImage(Entity.GUN_RIGHT, this.getX()+4 - Camera.x,this.getY()+5- Camera.y,null);
			}
		}else if (dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
			if (hasGun) {
				//se pegar a arma indo pra esquerda
				g.drawImage(Entity.GUN_LEFT, this.getX()-4 - Camera.x,this.getY()+5- Camera.y,null);
			}
		}
		}else {
			g.drawImage(playerDemage,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		

}

}