package com.VsfStudio.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String [] options = {"novo jogo", "carregar jogo", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length -1;
	
	public boolean up,down,enter;
	public boolean pause = false;
	
	public void tick () {
		if (up) {
			up = false;
			currentOption--;
			if (currentOption <0) {
				currentOption = maxOption;
			}
		}if (down) {
			down = false;
			currentOption ++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}if (enter) {
			enter = false;
			if (options[currentOption] == "novo jogo" || options[currentOption] == "continuar" ) {
				Game.gameState = "Normal";
				pause = false;
			}else if (options[currentOption] == "sair") {
				System.exit(1);
			}
			
		}
		
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD,38));
	    g.setColor(Color.white);
		g.drawString("Quaiatto Symphony",Game.WIDTH*Game.SCALE/2 -150, Game.HEIGHT*Game.SCALE/2 - 190);
		
		//opcoes de menu 
		g.setFont(new Font("Arial",Font.BOLD,20));
		if (pause == false) 
			g.drawString("Novo jogo",Game.WIDTH*Game.SCALE/2 -40, 100);
		else
			g.drawString("Resumir",Game.WIDTH*Game.SCALE/2 -30, 100);
		g.drawString("Carregar jogo",Game.WIDTH*Game.SCALE/2 -53, 140);
		g.drawString("Sair",Game.WIDTH*Game.SCALE/2 -10, 180);
		
		if (options[currentOption]== "novo jogo") {
			g.drawString(">",Game.WIDTH*Game.SCALE/2 -55, 100);
		}else if (options[currentOption]== "carregar jogo") {
			g.drawString(">",Game.WIDTH*Game.SCALE/2 -67, 140);
		}else if (options[currentOption]== "sair") {
			g.drawString(">",Game.WIDTH*Game.SCALE/2 -24, 180);
		}
	}

}
