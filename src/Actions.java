import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Actions extends JPanel {
    public static void main(String[] args) {

        Window window1 = new Window();

        Vector3 point1 = new Vector3(0, 0, 0);
        Vector3 point2 = new Vector3(0, 1, 0);
        Vector3 point3 = new Vector3(1, 1, 0);
        Vector3 point4 = new Vector3(1, 0, 0);
        Vector3 point5 = new Vector3(1, 0, 1);
        Vector3 point6 = new Vector3(1, 1, 1);
        Vector3 point7 = new Vector3(0, 1, 1);
        Vector3 point8 = new Vector3(0, 0, 1);

        Vector<Triangle> triangles = new Vector<>();

        triangles.add(new Triangle(point1, point2, point3));
        triangles.add(new Triangle(point1, point3, point4));
        triangles.add(new Triangle(point4, point3, point6));
        triangles.add(new Triangle(point4, point6, point5));
        triangles.add(new Triangle(point5, point6, point7));
        triangles.add(new Triangle(point5, point7, point8));
        triangles.add(new Triangle(point8, point7, point2));
        triangles.add(new Triangle(point8, point2, point1));
        triangles.add(new Triangle(point2, point7, point6));
        triangles.add(new Triangle(point2, point6, point3));
        triangles.add(new Triangle(point8, point1, point4));
        triangles.add(new Triangle(point8, point4, point5));

        Mesh cube = new Mesh(triangles);

        //Mesh bunny = MeshLoader.createMesh("C:\\Users\\Smith\\Documents\\3DEngine\\bunny.obj");

        //Mesh man = MeshLoader.createMesh("C:\\Users\\Smith\\Documents\\3DEngine\\Male.obj");

        Mesh teapot = MeshLoader.createMesh("UtahTeapot.obj");

        //window1.drawPanel.meshList.add(cube);
        window1.engine.meshList.add(teapot);
        //window1.engine.drawMeshes();

        //MeshLoader.createMesh("C:\\Users\\Smith\\Documents\\3DEngine\\UtahTeapot.obj");

        //window1.drawPanel.drawMeshes();

        //window1.engine.run();
        window1.engine.run();
    }





}
