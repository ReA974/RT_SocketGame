package Client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainClient {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("args :"+args.length);
        if (args.length != 4) {
            System.out.println("Usage : java Client.TPClient number color positionX positionY ");
            System.exit(0);
        }

        int number, team, x, y;
        number = Integer.parseInt(args[0]);
        team = Integer.parseInt(args[1]);
        x = Integer.parseInt(args[2]);
        y = Integer.parseInt(args[3]);

        try {
            TPClient tPClient = new TPClient(number,team,x,y);
            tPClient.minit(number, team, x, y);


            // Pour fermeture
            tPClient.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            // Create Panel back forward

            tPClient.pack();
            tPClient.setSize(1000, 1000+200);
            tPClient.setVisible(true);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
