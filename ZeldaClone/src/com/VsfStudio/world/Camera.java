package com.VsfStudio.world;

public class Camera {
	
	public static int x = 0;
	public static int y = 0;
	//clamp limita a tela apenas nos tiles do mapa, tirando a parte preta da sala
	 public static int clamp(int xAtual, int xMin, int xMax) {
		 if (xAtual < xMin) {
			xAtual = xMin;
		}if(xAtual > xMax) {
			xAtual = xMax;
		}
	 return xAtual;
	
	

}
}