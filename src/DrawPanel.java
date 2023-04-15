import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class DrawPanel extends JPanel {

    BufferedImage drawImage;
    BufferedImage blankImage;
    Graphics2D g2d;
    double testNumber;

    DrawPanel(int width, int height) {

        testNumber = 0;

    }
    @Override
    public void paint(Graphics g) {


        g.drawImage(drawImage, 0, 0, null);

        super.repaint();

    }




}
