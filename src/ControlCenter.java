// Main.java

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlCenter extends JFrame implements ActionListener {


    static MenuFrame MenuF = new MenuFrame();
    static GameFrame GameF = new GameFrame();

    static PauseScreen pauseMenu = new PauseScreen();

    public ControlCenter() {
    }

    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) {
        start();
    }

    public static int var;

    private static void start() {
        MenuF.timer.start();
        MenuF.setVisible(true);

        GameF.stopTimer();
        GameF.setVisible(false);

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        Util.startSound(Globals.MenuMusic);

//        Util.stopSound(Globals.lvl1Sound);
//        Util.stopSound(Globals.lvl2Sound);
//        Util.stopSound(Globals.lvl3Sound);
    }

    public static void enterGame(int lvl){

        System.out.println("entering game >>>>>>>>>>>>>>>");
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);

        pauseMenu.timer.stop();
        pauseMenu.setVisible(false);

        GameF.startTimer(lvl);
        GameF.setVisible(true);

        var = lvl;

        Util.stopSound(Globals.MenuMusic);
    }
    public static void pauseGame() {

        GameF.stopTimer();

        pauseMenu.pausemenu.timer.start();
        MenuF.setVisible(false);

        GameF.setVisible(false);

        pauseMenu.setVisible(true);
        MenuF.menu.timer.stop();

//        Util.startSound( Globals.MenuMusic); no menu music when paused


    }

    public static void resumeGame() {
        System.out.println("resuming game");
        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);
        MenuF.setVisible(false);

        GameF.geometryDash.timer.start();
        GameF.setVisible(true);


    }

    public static void toMainMenu() {
        System.out.println("to MENU >>>>>>>>>>>>>>>>");
        GameF.setVisible(false);
        GameF.stopTimer();
        GameF.RESET();

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.menu.timer.start();
        MenuF.setVisible(true);

        Util.startSound( Globals.MenuMusic);

        Util.stopSound(Globals.lvl1Sound);
        Util.stopSound(Globals.lvl2Sound);
        Util.stopSound(Globals.lvl3Sound);
    }


}