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
        System.out.println("MY HEAD IS UNDER DA WATER");
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);
        pauseMenu.setVisible(false);
        GameF.setVisible(true);
        GameF.geometryDash.timer.start();
    }
    public static void pauseGame() {
        pauseMenu.pausemenu.timer.start();
        GameF.geometryDash.timer.stop();
        MenuF.setVisible(false);
        GameF.setVisible(false);
        pauseMenu.setVisible(true);
//        GameF.setVisible(false);
        MenuF.menu.timer.stop();
    }

    public static void toMainMenu() {
        GameF.setVisible(false);
        GameF.geometryDash.timer.stop();
        GameF.geometryDash.resetPlayer();
        pauseMenu.pausemenu.timer.stop();
        MenuF.menu.timer.start();
        MenuF.setVisible(true);
        pauseMenu.setVisible(false);
        System.out.println("to MENU >>>>>>>>>>>>>>>>");
    }


}