import java.util.Vector;

public class Actions {

    public static void main(String[] args) {

        Window window1 = new Window();

        Vector3 point1 = new Vector3(0, 0, 0);
        Vector3 point2 = new Vector3(0, 1, 0);
        Vector3 point3 = new Vector3(1, 1, 0);
        Vector3 point4 = new Vector3(1, 0, 0);
        Vector3 point5 = new Vector3(0, 0, 1);
        Vector3 point6 = new Vector3(0, 1, 1);
        Vector3 point7 = new Vector3(1, 1, 1);
        Vector3 point8 = new Vector3(1, 0, 1);

        Vector<Triangle> triangles = new Vector<>();

        triangles.add(new Triangle(point1, point2, point3));
        triangles.add(new Triangle(point3, point4, point1));
        triangles.add(new Triangle(point4, point3, point7));
        triangles.add(new Triangle(point7, point8, point4));
        triangles.add(new Triangle(point8, point7, point6));
        triangles.add(new Triangle(point6, point5, point8));
        triangles.add(new Triangle(point5, point6, point2));
        triangles.add(new Triangle(point2, point1, point5));
        triangles.add(new Triangle(point2, point6, point7));
        triangles.add(new Triangle(point7, point3, point2));
        triangles.add(new Triangle(point1, point5, point8));
        triangles.add(new Triangle(point8, point4, point1));

        Mesh cube = new Mesh(triangles);

        window1.drawPanel.meshList.add(cube);
        window1.drawPanel.drawMeshes();

        //window1.drawPanel.drawMeshes();


    }



}
