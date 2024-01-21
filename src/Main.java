//// Main.java
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//
//public class Main extends JFrame implements ActionListener {
//
//    static JFrame menu, game;
//    public Main() {
//        super("Geometry Dash");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
//        add(geometryDash);
////        pack();
//        setVisible(true);
//
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        geometryDash.move();
//        geometryDash.repaint();
//    }
//    public void control(ActionEvent e) {
//        setVisible(false);
//    }
//
//    public static void main(String[] args) {
//
//        Main geometryDash = new Main();
//        createFrames();
//    }
//
//    private static void createFrames() {
//        GamePanel geometryDash = new GamePanel();
//        MenuFrame menu = new MenuFrame();
//    }
//}