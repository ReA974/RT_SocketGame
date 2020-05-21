package Client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


public class TPCanvas extends Canvas {

	int size =1000;
	int nbPosition = 10;
	byte [] etat;




	
	Color [] color = {Color.black,Color.blue,Color.red, Color.green, Color.yellow}; 
	// 0 black
	// 1 blue
	// 2 red
	// 3 green (bloqué blue)
	// 4 yellow (bloqué red);
	
	public TPCanvas(byte [] pEtat)
	{
		this.etat = pEtat;
	}
		
	public void paint(Graphics win)
	{
		System.out.println("Paint");
		paintCarte(win);
		drawEtat(win);
	}
	
	public Dimension getMinimumSize() {
	    return new Dimension(size, size); 
	 
	  }

	public void paintCarte(Graphics win)
	{
		int h,w;
		int larg = 10;
		h = getSize().height;
		w = getSize().width;
		win.drawRect(0,0, size-1, size-1);	// Draw border
		for (int i=1; i < 10; i++)
		{
			// Dessin
			win.setColor(Color.black);
			win.drawLine(i*size/nbPosition, 0, i*size/nbPosition, size);
			win.drawLine(0, i*size/nbPosition, size, i*size/nbPosition);
		}
		



	}

	public void drawEtat(Graphics win)
	{
		for (int i=0;i<10*10;i++)
		{
			if (etat[2*i]!=0)
			{
				//System.out.println("Joueur "+etat[2*i]+ " X "+i%10+" Y "+i/10);
				drawPlayer(win,i%10,i/10, etat[2*i+1]);
			}
		}
	}
	
	public void drawPlayer(Graphics win, int x, int y, byte type)
	{
		 win.setColor(color[type]);
		 win.fillOval ((x*size/nbPosition)+1,(y*size/nbPosition)+1, size/nbPosition-1, size/nbPosition-1);
	}
	
}
