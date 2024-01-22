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
    }

    public static void enterGame(int lvl){
        System.out.println("MY HEAD IS UNDER DA WATER"); // oh yes ofc, brain fluid gooood
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);

        pauseMenu.timer.stop();
        pauseMenu.setVisible(false);

        GameF.startTimer(lvl);
        GameF.setVisible(true);

        var = lvl;
        //GameF.geometryDash.timer.start();
    }
    public static void pauseGame() {
        pauseMenu.pausemenu.timer.start();
        MenuF.setVisible(false);

        GameF.setVisible(false);
        GameF.stopTimer();

        pauseMenu.setVisible(true);
        MenuF.menu.timer.stop();
    }

    public static void toMainMenu() {
        GameF.setVisible(false);
        GameF.stopTimer();
        GameF.RESET();

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.menu.timer.start();
        MenuF.setVisible(true);

        System.out.println("to MENU >>>>>>>>>>>>>>>>");
    }


}