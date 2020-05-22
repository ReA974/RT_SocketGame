import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Olivier Francou
 * @author Erwan Thibaut
 */

public class MainClient {
    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("args :" + args.length);
        if (args.length != 3) {
            System.out.println("Usage : java tea.TPClient color positionX positionY ");
            System.exit(0);
        }
        try {
            int team = Integer.parseInt(args[0]);
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            TPClient tpClient = new TPClient(team, x, y);

            tpClient.minit(tpClient.team, tpClient.x, tpClient.y); //tpClient permet de ne pas avoir qu'un seul joueur vis à vis des couleurs et donc des déplacements

            // Pour fermeture
            tpClient.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            // Create Panel back forward
            tpClient.pack();
            tpClient.setSize(1000, 1000 + 200);
            tpClient.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace(); //affiche l'erreur
        }
    }
}
