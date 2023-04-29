import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {

    BufferedImage drawImage;
    BufferedImage blankImage;
    Graphics2D g2d;
    double testNumber;

    DrawPanel(int width, int height) {

        testNumber = 0;

    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(drawImage, 0, 0, null);

    }

}
