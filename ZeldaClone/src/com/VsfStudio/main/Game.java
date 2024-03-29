package com.VsfStudio.main;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.VsfStudio.world.World;
import com.VsfStudios.entities.BulletShoot;
import com.VsfStudios.entities.Enemy;
import com.VsfStudios.entities.Entity;
import com.VsfStudios.entities.Player;
import com.VsfStudios.graficos.Spritesheet;
import com.VsfStudios.graficos.UI;;

public class Game extends Canvas implements Runnable, KeyListener {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	 private Thread thread;
	 private boolean isRunning = true;
	 public static final int WIDTH = 240;
	 public static final int HEIGHT = 160;
	 public static int SCALE = 3;
	 private BufferedImage image;
	 private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	 private boolean restartGame = false;
	
	 public static List<Entity> entities;
	 public static List<Enemy> enemies;
	 public static List<BulletShoot> bullets;
	 public static Spritesheet spritesheet; 
	 public static World world;
	 public static Player player;
	 public static Random rand;
	 public UI ui;
	 public static String gameState = "Menu"; 
	 private boolean showMessageGameOver = true;
	 private int framesGameOver = 0; 
	 public Menu menu;
	

	 
	public Game () {
		Sound.musicBackground.loop();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		ui = new UI();
		rand = new Random();
		image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList <BulletShoot>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player (0,0,16,16,spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/level1.png");
		
		menu = new Menu();
		
		
	}
	 public void initFrame(){
	        
	        frame = new JFrame("Quaiatto Symphony");
	        frame.add(this);
	        //nao poder mudar as dimens�es 
	        frame.setResizable(false);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        //para a execu��o clicando no X do JFrame
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
	        //parar caso d� erro
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
		//loop avan�ado para 60 frames por segundo
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        requestFocus();
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
		if (gameState == "Normal") {
			this.restartGame = false;
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		
		//avan�a o jogo 
		if(enemies.size() == 0){
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL = 1;
			}
			String newWorld = "level" + CUR_LEVEL + ".png";
			World.restarGame(newWorld);
		}
	    }else if(gameState == "GAME_OVER") {
	    	//anima��o de morte
	    	this.framesGameOver++;
	    	if (this.framesGameOver == 350) {
				this.framesGameOver = 0;
				if (this.showMessageGameOver) 
					this.showMessageGameOver = false;
					else
						this.showMessageGameOver= true;
				
			}
	    	if (restartGame) {
	    		this.restartGame = false;
	    		this.gameState = "Normal";
	    		CUR_LEVEL = 1;
	    		String newWorld = "level" + CUR_LEVEL + ".png";
				World.restarGame(newWorld);
			}
	    }else if (gameState == "Menu") {
	    	menu.tick();
	    }
	}
	
	
	
	public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color( 0,0,0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        world.render(g);

        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setColor(Color.white);
        g.drawString("",19, 19);
        g.setColor(Color.MAGENTA);   
        for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
        for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
        
        ui.render(g);
        g.dispose();
        g = bs.getDrawGraphics();
        //preenchendo o background
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        
        g.fillRect(590, 58,65, 20);
		g.setColor(Color.red);
        
        g.setColor(Color.white);
    
		g.setFont(new Font("arial", Font.BOLD,20));
        g.drawString("VSF: " + player.ammo, 590, 75);
        if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			
				g.setFont(new Font("arial", Font.BOLD,20));
				g.setColor(Color.white);
		        g.drawString("� MERDA, JAKE PAN�A LHE SURROU",(WIDTH*SCALE) /3 , (HEIGHT*SCALE)/2 );
		        g.setColor(Color.white);
		        if(showMessageGameOver)
		        	g.drawString(">pega no meu e retorne para a batalha<",(WIDTH*SCALE) /3 , (HEIGHT*SCALE)/2 + 40 );
		}else if (gameState == "Menu") {
			menu.render(g);
		}
        bs.show();
    }
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			//andar para a direita
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			//andar para a esquerda
			player.left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			//ANDAR PARA CIMA
			player.up = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			//ANDAR PARA BAIXO
			player.down = true;
			
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			//andar para a direita
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			//andar para a esquerda
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			//ANDAR PARA CIMA
			player.up = false;
			if(gameState == "Menu") {
				menu.up = true;
			}
		}
	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			//ANDAR PARA BAIXO
			player.down = false;
			
			if(gameState == "Menu") {
				menu.down = true;
			}
		}if (e.getKeyCode()== KeyEvent.VK_X) {
			Sound.atackSound.play();
			player.shoot = true;
			
		}
		if (e.getKeyCode()== KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if (gameState =="Menu") {
				menu.enter = true;
			}
		}
		if (e.getKeyCode()== KeyEvent.VK_ESCAPE) {
			gameState = "Menu";
			menu.pause = true; 
		} 
		}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	
	}
}
