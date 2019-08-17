package com.VsfStudio.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	 private Thread thread;
	 private boolean isRunning = true;
	 private final int WIDTH = 240;
	 private final int HEIGHT = 160;
	 private final int SCALE = 3;
	 private BufferedImage image;
	 /*
	 private Spritesheet sheet;
	 private BufferedImage[]player;
	 private int frames = 0;
	 private int maxFrames = 1110;
	 private int curAnimation = 0,maxAnimation=3;
	 */
	
	public Game () {
		
		/*sheet = new Spritesheet("/spritesheet.png");
		player = new BufferedImage[4];
		player[0] = sheet.getSprite(0, 0, 16, 16);
		player[1] = sheet.getSprite(16, 0, 16, 16);
		player[2] = sheet.getSprite(32, 0, 16, 16);
		player[3] = sheet.getSprite(48, 0, 16, 16);
		*/
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		

		
	}
	 public void initFrame(){
	        
	        frame = new JFrame("Game#1");
	        frame.add(this);
	        //nao poder mudar as dimensões 
	        frame.setResizable(false);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        //para a execução clicando no X do JFrame
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //executou apareceu
	        frame.setVisible(true);
	        
	    }
	 public synchronized void start (){
	        
	        thread = new Thread(this);
	        thread.start();
	        isRunning = true;
	        
	    }
	 public synchronized void stop (){
	        
	        //parar caso dê erro
	        isRunning = false;
	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	    }
	  public static void main(String[] args) {
	        Game game = new Game();
	        game.start();
	        
	    }
	    

	
	public void run() {
		// TODO Auto-generated method stub
		//loop avançado para 60 frames por segundo
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        
        while(isRunning){
            
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            
            if (delta >= 1) {
                tick();
                render();
            }
            
        }
        stop();
	}
		
	public void tick(){
		/*
		frames++;
		if(frames > maxFrames) {
			frames = 0;
			curAnimation++;
			
			if(curAnimation >= maxAnimation) {
				curAnimation = 0;
			}
		}
		*/ 
		 
		 
	    }
	
	
	
	
	public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        //cor do backgroud
        
        
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setColor(Color.white);
        g.drawString("vsffffffffffffffffffffffff",19, 19);
        g.setColor(Color.MAGENTA);
        
        //render game
        //Graphics2D g2 = (Graphics2D) g ;
        
        //g2.drawImage(player[curAnimation], 90, 90, null);
        
        g.dispose();
        g = bs.getDrawGraphics();
        //preenchendo o background
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }
    
	
	 
	 
	
	
	
	
	
	
	

}
