import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    static int width;
    static int height;

    DrawPanel drawPanel;

    Window() {

        width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        drawPanel = new DrawPanel(width, height);

        this.setSize(width, height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setUndecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(drawPanel);

        this.setVisible(true);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(1);
                if(e.getKeyCode() == KeyEvent.VK_R) repaint();
                if(e.getKeyCode() == KeyEvent.VK_T) {
                    drawPanel.testNumber += 0.1;
                    drawPanel.drawMeshes();
                    System.out.println("redrawing");
                    repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_D) {

                    drawPanel.drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    drawPanel.g2d = drawPanel.drawImage.createGraphics();
                    drawPanel.g2d.drawLine(100, 100, 400, 400);
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


}
