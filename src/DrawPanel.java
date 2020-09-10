import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class DrawPanel extends JPanel {

    BufferedImage drawImage;
    BufferedImage blankImage;
    Graphics2D g2d;

    boolean animating;

    double testNumber;
    double deltaTime;
    double deltaTimeAccumulator;
    double lastDeltaTime;

    double frameRate = 240;
    double change;
    double speed;

    double theta;

    Vector3 cameraPosition;

    Vector3 lightDirection;

    Vector<Triangle> triangleSortVector = new Vector<>();

    ArrayList<Mesh> meshList;



    DrawPanel(int width, int height) {

        cameraPosition = new Vector3();
        lightDirection = new Vector3(0, 0, 1);

        testNumber = 0;

        speed = 1;

        frameRate = 240;
        theta = 0;

        meshList = new ArrayList<>();

        drawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        blankImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        g2d = drawImage.createGraphics();
        g2d.setColor(Color.YELLOW);


    }

    public void  drawMeshes() {
        drawImage = new BufferedImage(Window.width, Window.height, BufferedImage.TYPE_INT_RGB);
        g2d = drawImage.createGraphics();
        g2d.setColor(Color.WHITE);


        for(int i = 0; i < meshList.size(); i++) {
            drawMesh(meshList.get(i));
        }

        repaint();
    }

    public void drawMesh(Mesh mesh) {

        Triangle currentTriangle;
        ProjectionMatrix projectionMatrix = new ProjectionMatrix();
        Matrix2D rotationMatrixX = new Matrix2D(4, 4);
        Matrix2D rotationMatrixZ = new Matrix2D(4, 4);

        Vector3 line1;
        Vector3 line2;
        Vector3 normal;



        double dotProduct;

        double addFactor = 1.0;
        double multFactor = 0.5;

        theta += change;

        rotationMatrixX.elements[0][0] = Math.cos(theta);
        rotationMatrixX.elements[0][1] = Math.sin(theta);
        rotationMatrixX.elements[1][0] = -Math.sin(theta);
        rotationMatrixX.elements[1][1] = Math.cos(theta);
        rotationMatrixX.elements[2][2] = 1;
        rotationMatrixX.elements[3][3] = 1;

        rotationMatrixZ.elements[0][0] = 1;
        rotationMatrixZ.elements[1][1] = Math.cos(theta * 0.5);
        rotationMatrixZ.elements[1][2] = Math.sin(theta * 0.5);
        rotationMatrixZ.elements[2][1] = -Math.sin(theta * 0.5);
        rotationMatrixZ.elements[2][2] = Math.cos(theta * 0.5);
        rotationMatrixZ.elements[3][3] = 1;

        for(int i = 0; i < mesh.triangles.size(); i++) {

            currentTriangle = new Triangle(mesh.triangles.get(i));

            currentTriangle.multiplyMatrix(rotationMatrixX);
            currentTriangle.multiplyMatrix(rotationMatrixZ);

            currentTriangle.translate(0, 0, 10);

            line1 = currentTriangle.points[1].subtract(currentTriangle.points[0]);

            line2 = currentTriangle.points[2].subtract(currentTriangle.points[0]);

            normal = line1.crossProduct(line2);

            normal.normalize();

            if(normal.x * (currentTriangle.points[0].x - cameraPosition.x) +
                normal.y * (currentTriangle.points[0].y - cameraPosition.y) +
                normal.z * (currentTriangle.points[0].z - cameraPosition.z) < 0) {

                lightDirection.normalize();

                dotProduct = normal.dotProduct(lightDirection);

                currentTriangle.color = (new Color((int) Math.round(Math.abs(dotProduct) * Color.WHITE.getRed()),
                        (int) Math.round(Math.abs(dotProduct) * Color.WHITE.getGreen()),
                        (int) Math.round(Math.abs(dotProduct) * Color.WHITE.getBlue())));

                currentTriangle.multiplyMatrix(projectionMatrix);

                currentTriangle.translate(addFactor, 0, 0);
                currentTriangle.translate(0, addFactor, 0);

                compensateAspectRatio(currentTriangle, multFactor);

                triangleSortVector.add(currentTriangle);

            }

        }

        sortTriangles();

        drawTriangles();

    }

    public void animate() {

        Runnable animate = () -> {

            while(animating) {
                deltaTimeAccumulator += System.nanoTime() - lastDeltaTime;
                if(deltaTimeAccumulator / 1000000000 >= 1 / frameRate) {
                    deltaTimeAccumulator = 0;
                    change = 1/frameRate * speed;
                    drawMeshes();
                }
                lastDeltaTime = System.nanoTime();
            }
        };

        Thread run = new Thread(animate);

        run.start();
        return;
    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(drawImage, 0, 0, null);

    }

    public void compensateAspectRatio(Triangle currentTriangle, double multFactor) {
        if(Window.width > Window.height) {
            currentTriangle.points[0].x *= multFactor * Window.width;
            currentTriangle.points[0].y *= (multFactor * Window.width);
            currentTriangle.points[0].y -= (Window.width - Window.height)/2;
            currentTriangle.points[1].x *= multFactor * Window.width;
            currentTriangle.points[1].y *= (multFactor * Window.width);
            currentTriangle.points[1].y -= (Window.width - Window.height)/2;
            currentTriangle.points[2].x *= multFactor * Window.width;
            currentTriangle.points[2].y *= (multFactor * Window.width);
            currentTriangle.points[2].y -= (Window.width - Window.height)/2;
        } else if(Window.height > Window.width) {
            currentTriangle.points[0].x *= multFactor * Window.height;
            currentTriangle.points[0].x -= (Window.height - Window.width)/2;
            currentTriangle.points[0].y *= multFactor * Window.height;
            currentTriangle.points[1].x *= multFactor * Window.height;
            currentTriangle.points[1].x -= (Window.height - Window.width)/2;
            currentTriangle.points[1].y *= multFactor * Window.height;
            currentTriangle.points[2].x *= multFactor * Window.height;
            currentTriangle.points[2].x -= (Window.height - Window.width)/2;
            currentTriangle.points[2].y *= multFactor * Window.height;
        } else {
            currentTriangle.points[0].x *= multFactor * Window.width;
            currentTriangle.points[0].y *= multFactor * Window.height;
            currentTriangle.points[1].x *= multFactor * Window.width;
            currentTriangle.points[1].y *= multFactor * Window.height;
            currentTriangle.points[2].x *= multFactor * Window.width;
            currentTriangle.points[2].y *= multFactor * Window.height;
        }
    }

    public void sortTriangles() {
        Collections.sort(triangleSortVector, (o1, o2) -> {
            double zAverage1 = (o1.points[0].z + o1.points[1].z + o1.points[2].z) / 3.0;
            double zAverage2 = (o2.points[0].z + o2.points[1].z + o2.points[2].z) / 3.0;

            int compareNumber = 0;

            if(zAverage2 - zAverage1 > 0) compareNumber = 1;
            else if( zAverage2 - zAverage1 < 0) compareNumber = -1;

            return compareNumber;
        });
    }

    public void drawTriangles() {

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        for(Triangle triangle : triangleSortVector) {
            g2d.setColor(triangle.color);

            xPoints[0] = (int) triangle.points[0].x;
            xPoints[1] = (int) triangle.points[1].x;
            xPoints[2] = (int) triangle.points[2].x;


            yPoints[0] = (int) triangle.points[0].y;
            yPoints[1] = (int) triangle.points[1].y;
            yPoints[2] = (int) triangle.points[2].y;

            g2d.fillPolygon(xPoints, yPoints, 3);

        }
        triangleSortVector.clear();
    }


}
