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


    private static void start() {
        MenuF.timer.start();
        MenuF.setVisible(true);

        GameF.stopTimer();
        GameF.setVisible(false);

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.startMenuSound();

    }

    public static void enterGame(int lvl){

        System.out.println("entering game >>>>>>>>>>>>>>>");
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);

        pauseMenu.timer.stop();
        pauseMenu.setVisible(false);

        GameF.startTimer(lvl);
        GameF.setVisible(true);

        GameF.startGameSound(MenuPanel.targetLevel);

        MenuF.stopMenuSound();
    }
    public static void pauseGame() {

        GameF.stopTimer();
        GameF.setVisible(false);
        GameF.stopGameSound();

        pauseMenu.pausemenu.timer.start();
        MenuF.setVisible(false);
        pauseMenu.setVisible(true);
        MenuF.menu.timer.stop();


    }

    public static void resumeGame() {
        System.out.println("resuming game");
        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);
        MenuF.setVisible(false);

        GameF.geometryDash.timer.start();
        GameF.setVisible(true);
        GameF.startGameSound(MenuPanel.targetLevel);

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

        MenuF.startMenuSound();
        GameF.stopGameSound();
    }


}