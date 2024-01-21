// Main.java

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlCenter extends JFrame implements ActionListener {


    static MenuFrame MenuF = new MenuFrame();
    static GameFrame GameF = new GameFrame();

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
        GameF.setVisible(false);
    }

    public static void enterGame(){
        MenuF.menu.timer.stop();
        MenuF.setVisible(false);
        GameF.setVisible(true);
        GameF.geometryDash.timer.start();
    }


    public static void toMenu() {
        GameF.setVisible(false);
        GameF.geometryDash.timer.stop();
        MenuF.menu.timer.start();
        MenuF.setVisible(true);
        System.out.println("to MENU >>>>>>>>>>>>>>>>");
    }


    public static void toPauseMenu() {}

}