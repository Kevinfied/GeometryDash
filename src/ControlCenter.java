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
        MenuF.menu.timer.start();
        MenuF.setVisible(true);

        GameF.stopTimer();
        GameF.setVisible(false);

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        System.out.println(pauseMenu.timer.isRunning());
    }

    public static void enterGame(int lvl){
        System.out.println("player x >>>>>>   " + GameFrame.geometryDash.player.getVX());


        System.out.println("entering game >>>>>>>>>>>>>>>");
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);

        pauseMenu.timer.stop();
        pauseMenu.setVisible(false);

        GameF.startTimer(lvl);
        GameF.setVisible(true);

        var = lvl;

        System.out.println(pauseMenu.timer.isRunning());
        //GameF.geometryDash.timer.start();
    }
    public static void pauseGame() {
        pauseMenu.pausemenu.timer.start();
        MenuF.setVisible(false);

        GameF.setVisible(false);
        GameF.stopTimer();

        pauseMenu.setVisible(true);
        MenuF.menu.timer.stop();

        System.out.println(pauseMenu.timer.isRunning());
    }

    public static void toMainMenu() {
        GameF.setVisible(false);
        GameF.stopTimer();
        GameF.RESET();

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.menu.timer.start();
        MenuF.levelMenu.timer.stop();
        MenuF.setVisible(true);


        System.out.println(pauseMenu.timer.isRunning());
        System.out.println("to MENU >>>>>>>>>>>>>>>>");
    }

    public static void toLevelMenu() {
        GameF.setVisible(false);
        GameF.stopTimer();
        GameF.RESET();

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.levelMenu.timer.start();
        MenuF.menu.timer.stop();
        MenuF.setVisible(true);


        System.out.println(pauseMenu.timer.isRunning());
        System.out.println("to LevelMenu >>>>>>>>>>>>>>>>");
    }


}