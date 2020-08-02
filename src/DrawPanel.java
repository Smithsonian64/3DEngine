import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel {

    BufferedImage drawImage;
    BufferedImage blankImage;
    Graphics2D g2d;

    double testNumber;

    ArrayList<Mesh> meshList;

    DrawPanel(int width, int height) {

        testNumber = 0;

        meshList = new ArrayList<>();

        drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        blankImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        g2d = drawImage.createGraphics();
        g2d.setColor(Color.YELLOW);


    }

    public void  drawMeshes() {
        drawImage = new BufferedImage(Window.width, Window.height, BufferedImage.TYPE_INT_RGB);
        g2d = drawImage.createGraphics();

        for(int i = 0; i < meshList.size(); i++) {
            drawMesh(meshList.get(i));
        }
    }

    public void drawMesh(Mesh mesh) {

        Triangle currentTriangle;
        ProjectionMatrix projectionMatrix = new ProjectionMatrix();

        for(int i = 0; i < mesh.triangles.size(); i++) {
            currentTriangle = mesh.triangles.get(i);

            currentTriangle.points[0].z += 1;
            currentTriangle.points[1].z += 0;
            currentTriangle.points[2].z += 0;

            currentTriangle.points[0] = multiplyMatrix(currentTriangle.points[0], projectionMatrix);
            currentTriangle.points[1] = multiplyMatrix(currentTriangle.points[1], projectionMatrix);
            currentTriangle.points[2] = multiplyMatrix(currentTriangle.points[2], projectionMatrix);

            //scale into view

            double addFactor = 1.0;
            double multFactor = 0.5;

            currentTriangle.points[0].x += addFactor;
            currentTriangle.points[1].x += addFactor;
            currentTriangle.points[2].x += addFactor;

            currentTriangle.points[0].y += addFactor;
            currentTriangle.points[1].y += addFactor;
            currentTriangle.points[2].y += addFactor;

            currentTriangle.points[0].x *= multFactor * Window.width;
            currentTriangle.points[0].y *= multFactor * Window.height-1;
            currentTriangle.points[1].x *= multFactor * Window.width;
            currentTriangle.points[1].y *= multFactor * Window.height-1;
            currentTriangle.points[2].x *= multFactor * Window.width;
            currentTriangle.points[2].y *= multFactor * Window.height-1;

            g2d.drawLine((int) currentTriangle.points[0].x, (int) currentTriangle.points[0].y,
                    (int) currentTriangle.points[1].x, (int) currentTriangle.points[1].y);

            g2d.drawLine((int) currentTriangle.points[1].x, (int) currentTriangle.points[1].y,
                    (int) currentTriangle.points[2].x, (int) currentTriangle.points[2].y);

            g2d.drawLine((int) currentTriangle.points[2].x, (int) currentTriangle.points[2].y,
                    (int) currentTriangle.points[0].x, (int) currentTriangle.points[0].y);

        }



    }

    public Vector3 multiplyMatrix(Vector3 input, ProjectionMatrix projectionMatrix) {

        Vector3 output = new Vector3();

        output.x = input.x * projectionMatrix.elements[0][0] +
                input.y * projectionMatrix.elements[1][0] +
                input.z * projectionMatrix.elements[2][0] +
                projectionMatrix.elements[3][0];

        output.y = input.x * projectionMatrix.elements[0][1] +
                input.y * projectionMatrix.elements[1][1] +
                input.z * projectionMatrix.elements[2][1] +
                projectionMatrix.elements[3][1];

        output.x = input.x * projectionMatrix.elements[0][2] +
                input.y * projectionMatrix.elements[1][2] +
                input.z * projectionMatrix.elements[2][2] +
                projectionMatrix.elements[3][2];

        double w = input.x * projectionMatrix.elements[0][3] +
                input.y * projectionMatrix.elements[1][3] +
                input.z * projectionMatrix.elements[2][3] +
                projectionMatrix.elements[3][3];

        if(w != 0) {
            output.x /= w;
            output.y /= w;
            output.z /= w;
        }

        return output;
    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(drawImage, 0, 0, null);

    }


}
