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
        MenuF.menu.timer.start();
        MenuF.setVisible(true);
        GameF.geometryDash.timer.stop();
        pauseMenu.pausemenu.timer.stop();
        GameF.setVisible(false);
        pauseMenu.setVisible(false);
    }

    public static void enterGame(){
        System.out.println("MY HEAD IS UNDER DA WATER"); // oh yes ofc, brain fluid gooood
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);

        pauseMenu.timer.stop();
        pauseMenu.setVisible(false);

        GameF.setVisible(true);
        GameF.geometryDash.timer.start();
    }
    public static void pauseGame() {
        pauseMenu.pausemenu.timer.start();
        MenuF.setVisible(false);

        GameF.geometryDash.timer.stop();
        GameF.setVisible(false);

        pauseMenu.setVisible(true);
        MenuF.menu.timer.stop();
    }

    public static void toMainMenu() {
        GameF.setVisible(false);
        GameF.geometryDash.timer.stop();
        GameF.geometryDash.resetPlayer();

        pauseMenu.pausemenu.timer.stop();
        pauseMenu.setVisible(false);

        MenuF.menu.timer.start();
        MenuF.setVisible(true);

        System.out.println("to MENU >>>>>>>>>>>>>>>>");
    }


}