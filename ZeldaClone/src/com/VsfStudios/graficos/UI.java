package com.VsfStudios.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.VsfStudio.main.Game;


public class UI {

	
	public void render (Graphics g) {
		//barrinha de vida 
		g.setColor(Color.red);
		g.fillRect(180, 8,50, 8);
		g.setColor(Color.green);
		g.fillRect(180, 8,(int)((Game.player.life/Game.player.maxLife)*50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,8));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife,190,15);
	
	}
}
