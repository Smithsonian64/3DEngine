import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class DrawPanel extends JPanel {

    BufferedImage drawImage;
    BufferedImage blankImage;
    Graphics2D g2d;

    boolean animating;

    double testNumber;
    double deltaTime;
    double deltaTimeAccumulator;
    double lastDeltaTime;


    double frameRate;
    double change;
    double speed;

    double theta;

    Vector3 cameraPosition;

    Vector3 lightDirection;

    ArrayList<Mesh> meshList;

    DrawPanel(int width, int height) {

        cameraPosition = new Vector3();
        lightDirection = new Vector3();

        testNumber = 0;

        speed = 1;

        frameRate = 60;
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

        Vector3 line1 = new Vector3();
        Vector3 line2 = new Vector3();
        Vector3 normal = new Vector3();

        Vector<Triangle> triangleSortVector = new Vector<>();

        double normalLength;

        double lightLength;

        double dotProduct;

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

            //System.out.println();

            currentTriangle = new Triangle(mesh.triangles.get(i));

            currentTriangle.points[0] = rotationMatrixX.multiplyMatrix(currentTriangle.points[0]);
            currentTriangle.points[1] = rotationMatrixX.multiplyMatrix(currentTriangle.points[1]);
            currentTriangle.points[2] = rotationMatrixX.multiplyMatrix(currentTriangle.points[2]);


            currentTriangle.points[0] = rotationMatrixZ.multiplyMatrix(currentTriangle.points[0]);
            currentTriangle.points[1] = rotationMatrixZ.multiplyMatrix(currentTriangle.points[1]);
            currentTriangle.points[2] = rotationMatrixZ.multiplyMatrix(currentTriangle.points[2]);

            currentTriangle.points[0].z += 0 + 10;
            currentTriangle.points[1].z += 0 + 10;
            currentTriangle.points[2].z += 0 + 10;

            line1.x = currentTriangle.points[1].x - currentTriangle.points[0].x;
            line1.y = currentTriangle.points[1].y - currentTriangle.points[0].y;
            line1.z = currentTriangle.points[1].z - currentTriangle.points[0].z;

            line2.x = currentTriangle.points[2].x - currentTriangle.points[0].x;
            line2.y = currentTriangle.points[2].y - currentTriangle.points[0].y;
            line2.z = currentTriangle.points[2].z - currentTriangle.points[0].z;

            normal.x = line1.y * line2.z - line1.z * line2.y;
            normal.y = line1.z * line2.x - line1.x * line2.z;
            normal.z = line1.x * line2.y - line1.y * line2.x;

            normalLength = Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);
            normal.x /= normalLength;
            normal.y /= normalLength;
            normal.z /= normalLength;

            if(normal.x * (currentTriangle.points[0].x - cameraPosition.x) +
                normal.y * (currentTriangle.points[0].y - cameraPosition.y) +
                normal.z * (currentTriangle.points[0].z - cameraPosition.z) < 0) {

                lightDirection.x = 0;
                lightDirection.y = 0;
                lightDirection.z = 1;

                lightLength = Math.sqrt(lightDirection.x * lightDirection.x + lightDirection.y * lightDirection.y +
                        lightDirection.z * lightDirection.z);

                lightDirection.x /= lightLength;
                lightDirection.y /= lightLength;
                lightDirection.z /= lightLength;

                dotProduct = normal.x * lightDirection.x + normal.y * lightDirection.y + normal.z * lightDirection.z;

                //System.out.println("RED: " + Math.round(-dotProduct * Color.WHITE.getBlue()));
                //System.out.println("GREEN: " + Math.round(-dotProduct * Color.WHITE.getBlue()));
                //System.out.println("BLUE: " + Math.round(-dotProduct * Color.WHITE.getBlue()));

                currentTriangle.color = (new Color((int) Math.round(Math.abs(dotProduct) * Color.WHITE.getRed()),
                        (int) Math.round(Math.abs(dotProduct) * Color.WHITE.getGreen()),
                        (int) Math.round(Math.abs(dotProduct) * Color.WHITE.getBlue())));

                currentTriangle.points[0] = projectionMatrix.multiplyMatrix(currentTriangle.points[0]);
                currentTriangle.points[1] = projectionMatrix.multiplyMatrix(currentTriangle.points[1]);
                currentTriangle.points[2] = projectionMatrix.multiplyMatrix(currentTriangle.points[2]);

                double addFactor = 1.0;
                double multFactor = 0.5;

                currentTriangle.points[0].x += addFactor;
                currentTriangle.points[1].x += addFactor;
                currentTriangle.points[2].x += addFactor;

                currentTriangle.points[0].y += addFactor;
                currentTriangle.points[1].y += addFactor;
                currentTriangle.points[2].y += addFactor;

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

                triangleSortVector.add(currentTriangle);



            /*int[] xPoints = new int[3];
            xPoints[0] = (int) currentTriangle.points[0].x;
            xPoints[1] = (int) currentTriangle.points[1].x;
            xPoints[2] = (int) currentTriangle.points[2].x;

            int[] yPoints = new int[3];
            yPoints[0] = (int) currentTriangle.points[0].y;
            yPoints[1] = (int) currentTriangle.points[1].y;
            yPoints[2] = (int) currentTriangle.points[2].y;



            g2d.fillPolygon(xPoints, yPoints, 3);*/

            //g2d.setColor(Color.BLACK);

                /*g2d.drawLine((int) currentTriangle.points[0].x, (int) currentTriangle.points[0].y,
                        (int) currentTriangle.points[1].x, (int) currentTriangle.points[1].y);

                g2d.drawLine((int) currentTriangle.points[1].x, (int) currentTriangle.points[1].y,
                        (int) currentTriangle.points[2].x, (int) currentTriangle.points[2].y);

                g2d.drawLine((int) currentTriangle.points[2].x, (int) currentTriangle.points[2].y,
                        (int) currentTriangle.points[0].x, (int) currentTriangle.points[0].y);*/
            }





        }

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];



        Collections.sort(triangleSortVector, (o1, o2) -> {
            double zAverage1 = (o1.points[0].z + o1.points[1].z + o1.points[2].z) / 3.0;
            double zAverage2 = (o2.points[0].z + o2.points[1].z + o2.points[2].z) / 3.0;

            int compareNumber = 0;

            if(zAverage2 - zAverage1 > 0) compareNumber = 1;
            else if( zAverage2 - zAverage1 < 0) compareNumber = -1;

            return compareNumber;
        });

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
        //System.out.println("good");



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


}
