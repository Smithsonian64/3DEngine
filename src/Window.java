import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.security.Key;

public class Window extends JFrame {

    static int width;
    static int height;

    Engine engine;
    DrawPanel drawPanel;

    Object selectedObject;
    int objectNumber;

    Text selectedObjectLabel;

    Window() {

        width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        drawPanel = new DrawPanel(width, height);

        engine = new Engine(drawPanel);

        this.setSize(width, height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // this.setUndecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(drawPanel);

        this.setVisible(true);

        objectNumber = 0;
        try {
            selectedObject = engine.objectList.get(objectNumber);
        } catch (Exception e) {

        }

        selectedObjectLabel = engine.addText("Selected Object: ", 0, 30, 30);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    System.exit(1);
                if (e.getKeyCode() == KeyEvent.VK_R)
                    repaint();
                if (e.getKeyCode() == KeyEvent.VK_T) {
                    drawPanel.testNumber += 1;
                    // drawPanel.drawMeshes();
                    System.out.println("redrawing");
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {

                    drawPanel.drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    drawPanel.g2d = drawPanel.drawImage.createGraphics();
                    drawPanel.g2d.drawLine(100, 100, 400, 400);
                    System.out.println("line");

                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    objectNumber++;
                    if ((objectNumber) >= engine.objectList.size())
                        objectNumber = 0;
                    selectedObject = engine.objectList.get(objectNumber);
                    selectedObjectLabel.text = "Selected Object: " + selectedObject.name;

                }

                if (e.getKeyCode() == KeyEvent.VK_Z)
                    engine.wireFrame = !engine.wireFrame;

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    engine.running = !engine.running;
                }

                // Adjust camera rotation
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    engine.camera.x += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    engine.camera.x += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    engine.camera.z += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    engine.camera.z += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    engine.camera.y += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    engine.camera.y += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    engine.camera.yaw += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    engine.camera.yaw += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    engine.camera.pitch += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    engine.camera.pitch += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    engine.camera.roll += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    engine.camera.roll += -0.1;
                }

                // adjust object rotation
                if (e.getKeyCode() == KeyEvent.VK_O) {
                    engine.objectList.get(objectNumber).roll += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    engine.objectList.get(objectNumber).roll += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    engine.objectList.get(objectNumber).yaw += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_J) {
                    engine.objectList.get(objectNumber).yaw += -0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    engine.objectList.get(objectNumber).pitch += 0.1;
                }
                if (e.getKeyCode() == KeyEvent.VK_K) {
                    engine.objectList.get(objectNumber).pitch += 0.1;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

}
