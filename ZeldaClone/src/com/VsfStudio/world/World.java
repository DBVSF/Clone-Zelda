package com.VsfStudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.VsfStudio.main.Game;
import com.VsfStudios.entities.*;


public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE=16;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0,map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					if (pixelAtual == 0xFF000000 ) {
						//chão 
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFF4F7){
						//parede
						tiles[xx+(yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF0026FF) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if (pixelAtual == 0xFFFF0000) {
						//enemy
						Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
							
					}else if (pixelAtual == 0xFFFFD800) {
						//weapon
						Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));	
					}else if (pixelAtual == 0xFF808080) {
						//lifePack
						Game.entities.add(new LifePack(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN));	
					}
					
				} {
					
				}
			}
		} catch (IOException e) {
	
			e.printStackTrace();
		}
	}
	
	
	public static boolean isFree(int xNext, int yNext) {
		/*
		 * realiza o teste para verificar se as posições ao lado do personagem
		 * são paredes
		 */
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext+TILE_SIZE -1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext+TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xNext+TILE_SIZE -1) / TILE_SIZE;
		int y4 = (yNext+TILE_SIZE -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
		
	}
	
	
	public void render(Graphics g) {
		int xStart = Camera.x>>4;
		int yStart = Camera.y>>4;
		
		int xFinal = xStart + (Game.WIDTH >>4);
		int yFinal = yStart + (Game.HEIGHT>>4);
		
		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <=yFinal; yy++) {
				if (xx < 0 || yy < 0 || xx>= WIDTH || yy >= HEIGHT) 
					continue;
				 
				
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
				
			}
			
		}
	}
	
	
	
	
	
	
	
	
}
