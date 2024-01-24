// ControlCenter.java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// THIS IS THE MAIN THIS IS THE MAIN
/*
control all frames( MenuFrame, PauseScreen and GameFrame) and their panels.
-Daisy
 */

public class ControlCenter extends JFrame implements ActionListener {
    //control different frame, panels, their timers

    //3 frames:
    static MenuFrame MenuF = new MenuFrame();
    static GameFrame GameF = new GameFrame();

    static PauseScreen pauseMenu = new PauseScreen();

    public ControlCenter() {}

    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) {
        start();
    }


    //show menu, play menu sound
    private static void start() {
        MenuF.timer.start();
        MenuF.setVisible(true);

        GameF.stopTimer();
        GameF.setVisible(false);

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.startMenuSound();

    }

    //show game, play game music
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

    //show pause screen, no sound played
    public static void pauseGame() {

        GameF.stopTimer();
        GameF.setVisible(false);
        GameF.stopGameSound();

        pauseMenu.pausemenu.timer.start();
        MenuF.setVisible(false);
        pauseMenu.setVisible(true);
        MenuF.menu.timer.stop();


    }

    //back to game, play game music
    public static void resumeGame() {
        System.out.println("resuming game");
        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);
        MenuF.setVisible(false);

        GameF.geometryDash.timer.start();
        GameF.setVisible(true);
        GameF.startGameSound(MenuPanel.targetLevel);

    }

    //show menu, play menu music
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