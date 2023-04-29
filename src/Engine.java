import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.linear.RealVector;

public class Engine {

    ArrayList<Mesh> meshList;
    ArrayList<Object> objectList;
    ArrayList<Text> textList;
    BufferedImage blankImage;
    BufferedImage drawImage;
    Graphics2D g2d;
    double speed;
    double framerate;
    boolean running;

    boolean wireFrame;
    double deltaTimeAccumulator;
    double lastDeltaTime;
    Vector<Triangle> triangleSortVector = new Vector<>();

    double change;
    double theta;

    Vector3 lightDirection;

    Camera camera;

    DrawPanel drawPanel;

    public Engine(DrawPanel drawPanel) {

        camera = new Camera(0, 0, 0, 0, 0, 0);

        speed = 1;
        framerate = 60;
        running = true;

        meshList = new ArrayList<>();
        objectList = new ArrayList<>();
        textList = new ArrayList<>();

        drawImage = new BufferedImage(Window.width, Window.height, BufferedImage.TYPE_INT_RGB);
        blankImage = new BufferedImage(Window.width, Window.height, BufferedImage.TYPE_INT_RGB);
        g2d = drawImage.createGraphics();
        g2d.setColor(Color.YELLOW);

        this.drawPanel = drawPanel;
        this.drawPanel.drawImage = drawImage;

        lightDirection = new Vector3(0, 0, 1);

        speed = 1;
        theta = 0;

        wireFrame = true;
    }

    public void run() {

        Runnable run = () -> {
            while (true) {

                deltaTimeAccumulator += System.nanoTime() - lastDeltaTime;


                if (deltaTimeAccumulator / 1000000000 >= 1 / framerate) {


                    System.out.println(System.nanoTime() - lastDeltaTime + "\r");
                    deltaTimeAccumulator = 0;
                    change = 1 / framerate * speed;
                    if (!running)
                        continue;

                    drawMeshes();

                }


                lastDeltaTime = System.nanoTime();

            }
        };

        Thread t1 = new Thread(run);
        t1.start();
    }

    public void drawMeshes() {
        drawImage = new BufferedImage(Window.width, Window.height, BufferedImage.TYPE_INT_RGB);
        drawPanel.drawImage = drawImage;
        g2d = drawImage.createGraphics();
        // g2d.setColor(Color.WHITE);

        for (Object object : objectList) {
            drawMesh(object, object.mesh);
            g2d.setPaint(object.texture);
        }

        Font newFont;
        for (Text text : textList) {
            newFont = g2d.getFont().deriveFont(text.size);
            g2d.setFont(newFont);
            g2d.setColor(Color.WHITE);
            g2d.drawString(text.text, text.x, text.y);
        }

        // drawPanel.paint(g2d);
    }

    public void drawMesh(Object object, Mesh mesh) {

        Triangle currentTriangle;
        Matrix2D rotationMatrixX = new Matrix2D(4, 4);
        Matrix2D rotationMatrixY = new Matrix2D(4, 4);
        Matrix2D rotationMatrixZ = new Matrix2D(4, 4);

        Vector3 line1;
        Vector3 line2;
        Vector3 normal;

        double dotProduct;

        double addFactor = 1.0;
        double multFactor = 0.5;

        theta += change;

        rotationMatrixX.elements[0][0] = Math.cos(object.roll);
        rotationMatrixX.elements[0][1] = Math.sin(object.roll);
        rotationMatrixX.elements[1][0] = -Math.sin(object.roll);
        rotationMatrixX.elements[1][1] = Math.cos(object.roll);
        rotationMatrixX.elements[2][2] = 1;
        rotationMatrixX.elements[3][3] = 1;

        rotationMatrixY.elements[0][0] = Math.cos(object.yaw);
        rotationMatrixY.elements[0][1] = 0;
        rotationMatrixY.elements[0][2] = Math.sin(object.yaw);
        rotationMatrixY.elements[1][0] = 0;
        rotationMatrixY.elements[1][1] = 1;
        rotationMatrixY.elements[1][2] = 0;
        rotationMatrixY.elements[2][0] = -Math.sin(object.yaw);
        rotationMatrixY.elements[2][1] = 0;
        rotationMatrixY.elements[2][2] = Math.cos(object.yaw);
        rotationMatrixY.elements[3][3] = 1;

        rotationMatrixZ.elements[0][0] = 1;
        rotationMatrixZ.elements[1][1] = Math.cos(object.pitch);
        rotationMatrixZ.elements[1][2] = Math.sin(object.pitch);
        rotationMatrixZ.elements[2][1] = -Math.sin(object.pitch);
        rotationMatrixZ.elements[2][2] = Math.cos(object.pitch);
        rotationMatrixZ.elements[3][3] = 1;

        for (int i = 0; i < mesh.triangles.size(); i++) {

            currentTriangle = new Triangle(mesh.triangles.get(i));

            // vvvvThese must remain in the order they are, remember, matrices are non
            // commutativevvv

            // object rotation
            currentTriangle.multiplyMatrix(rotationMatrixX);
            currentTriangle.multiplyMatrix(rotationMatrixY);
            currentTriangle.multiplyMatrix(rotationMatrixZ);

            // object translation
            currentTriangle.translate(object.x, object.y, object.z);

            // camera translation
            currentTriangle.translate(-camera.x, -camera.y, -camera.z);

            // camera rotation
            currentTriangle.multiplyMatrix(camera.getRotationMatrixX(camera.pitch));
            currentTriangle.multiplyMatrix(camera.getRotationMatrixY(camera.yaw));
            currentTriangle.multiplyMatrix(camera.getRotationMatrixZ(camera.roll));

            line1 = currentTriangle.points[1].subtract(currentTriangle.points[0]);

            line2 = currentTriangle.points[2].subtract(currentTriangle.points[0]);

            normal = line1.crossProduct(line2);

            normal.normalize();

            if (normal.x * (currentTriangle.points[0].x - 0) + normal.y * (currentTriangle.points[0].y - 0) + normal.z * (currentTriangle.points[0].z - 0) < 0) {

                lightDirection.normalize();

                dotProduct = normal.dotProduct(lightDirection);

                currentTriangle.color = (new Color((int) Math.round(Math.abs(dotProduct) * Color.WHITE.getRed()), (int) Math.round(Math.abs(dotProduct) * Color.WHITE.getGreen()),
                        (int) Math.round(Math.abs(dotProduct) * Color.WHITE.getBlue())));

                currentTriangle.multiplyMatrix(camera.projectionMatrix);

                currentTriangle.translate(addFactor, 0, 0);
                currentTriangle.translate(0, addFactor, 0);

                compensateAspectRatio(currentTriangle, multFactor);

                triangleSortVector.add(currentTriangle);

            }

        }

        sortTriangles();

        drawTriangles();

        drawPanel.repaint();
    }



    public void sortTriangles() {
        Collections.sort(triangleSortVector, (o1, o2) -> {
            double zAverage1 = (o1.points[0].z + o1.points[1].z + o1.points[2].z) / 3.0;
            double zAverage2 = (o2.points[0].z + o2.points[1].z + o2.points[2].z) / 3.0;

            int compareNumber = 0;

            if (zAverage2 - zAverage1 > 0)
                compareNumber = -1;
            else if (zAverage2 - zAverage1 < 0)
                compareNumber = 1;

            return compareNumber;
        });
    }

    public void drawTriangles() {

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        for (Triangle triangle : triangleSortVector) {
            g2d.setColor(triangle.color);

            if (triangle.points[0].z < 1 || triangle.points[1].z < 1 || triangle.points[2].z < 1)
                continue;

            xPoints[0] = (int) triangle.points[0].x;
            xPoints[1] = (int) triangle.points[1].x;
            xPoints[2] = (int) triangle.points[2].x;

            yPoints[0] = (int) triangle.points[0].y;
            yPoints[1] = (int) triangle.points[1].y;
            yPoints[2] = (int) triangle.points[2].y;

            if (wireFrame)
                g2d.drawPolygon(xPoints, yPoints, 3);
            else {
                g2d.fillPolygon(xPoints, yPoints, 3);

            }
        }
        triangleSortVector.clear();
    }

    public void addObject(Object object) {
        this.objectList.add(object);
    }

    public Text addText(String text, float x, float y, float size) {

        Text output = new Text(text, x, y, size);
        textList.add(output);
        return output;

    }

    public void compensateAspectRatio(Triangle currentTriangle, double multFactor) {
        if (Window.width > Window.height) {
            currentTriangle.points[0].x *= multFactor * Window.width;
            currentTriangle.points[0].y *= (multFactor * Window.width);
            currentTriangle.points[0].y -= (Window.width - Window.height) / 2;
            currentTriangle.points[1].x *= multFactor * Window.width;
            currentTriangle.points[1].y *= (multFactor * Window.width);
            currentTriangle.points[1].y -= (Window.width - Window.height) / 2;
            currentTriangle.points[2].x *= multFactor * Window.width;
            currentTriangle.points[2].y *= (multFactor * Window.width);
            currentTriangle.points[2].y -= (Window.width - Window.height) / 2;
        } else if (Window.height > Window.width) {
            currentTriangle.points[0].x *= multFactor * Window.height;
            currentTriangle.points[0].x -= (Window.height - Window.width) / 2;
            currentTriangle.points[0].y *= multFactor * Window.height;
            currentTriangle.points[1].x *= multFactor * Window.height;
            currentTriangle.points[1].x -= (Window.height - Window.width) / 2;
            currentTriangle.points[1].y *= multFactor * Window.height;
            currentTriangle.points[2].x *= multFactor * Window.height;
            currentTriangle.points[2].x -= (Window.height - Window.width) / 2;
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

}
