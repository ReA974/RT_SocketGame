package Client;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;



public class TPClient extends Frame {

	byte [] etat = new byte [2*10*10];
	int team;
	int x;
	int y;
	int port = 2000;
	Socket socket = null;
	InputStream in;
	DataOutputStream out;
	TPPanel tpPanel;
	TPCanvas tpCanvas;
	Timer timer;




	/** Constructeur */
	public TPClient(int number,int team, int x, int y)
	{
		setLayout(new BorderLayout());
		tpPanel = new TPPanel(this);
		add("North", tpPanel);
		tpCanvas = new TPCanvas(this.etat);
		add("Center", tpCanvas);

		int index = ( (y * 10) + x ) * 2;

		tpCanvas.etat[index] = (byte) number; // number > 0 = joueur et 0 = pas de joueur
		tpCanvas.etat[index+1] = (byte) team; // 1 = equipe blue et 2 = equipe rouge
		
		timer = new Timer();
		timer.schedule ( new MyTimerTask (  ) , 500,500) ;

	}
	
	/** Action vers droit */
	public synchronized void droit()
	{
		System.out.println("Droit");
		try{
			tpCanvas.repaint();
			throw new IOException();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	/** Action vers gauche */
	public synchronized void gauche()
	{
		System.out.println("Gauche");
		try{
			tpCanvas.repaint();
			throw new IOException();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	/** Action vers gauche */
	public synchronized void haut()
	{
		System.out.println("Haut");
		try{
			tpCanvas.repaint();
			throw new IOException();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	
	}
	/** Action vers bas */
	public synchronized void bas ()
	{
		System.out.println("Bas");
		try{
			tpCanvas.repaint();
			throw new IOException();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	/** Pour rafraichir la situation */
	public synchronized void refresh ()
	{
		try {
			throw new IOException();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		tpCanvas.repaint();
	}
	/** Pour recevoir l'Etat */
	public void receiveEtat()
	{
		try{
			throw new IOException();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	/** Initialisations */
	public void minit(int number, int pteam, int px, int py)
	{
		try{
			throw new IOException();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	public String etat()
	{
		String result = new String();
		return result;
	}

	/** Pour rafraichir */
	class MyTimerTask extends TimerTask{
		
		public void run ()
		{
			System.out.println("refresh");
          		refresh();
		}
	}
	
}
