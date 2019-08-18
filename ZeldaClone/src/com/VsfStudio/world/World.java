package com.VsfStudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.VsfStudio.main.Game;
import com.VsfStudios.entities.*;


public class World {
	
	private Tile[] tiles;
	public static int WIDTH, HEIGHT;
	
	
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
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF0026FF) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
						
						//tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					}else if (pixelAtual == 0xFFFF0000) {
						//enemy
						Game.entities.add(new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN));
						//tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);	
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
	
	public void render(Graphics g) {
		for (int xx = 0; xx < WIDTH; xx++) {
			for (int yy = 0; yy <HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
				
			}
			
		}
	}
	
	
	
	
	
	
	
	
}
