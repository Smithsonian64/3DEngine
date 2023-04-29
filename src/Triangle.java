import java.awt.*;
import java.util.Vector;

public class Triangle {

    Vector3[] points;
    Color color;

    Triangle(Vector3 point1, Vector3 point2, Vector3 point3) {

        points = new Vector3[3];

        points[0] = new Vector3(point1);
        points[1] = new Vector3(point2);
        points[2] = new Vector3(point3);

    }

    Triangle(Triangle triangle) {

        points = new Vector3[3];

        points[0] = new Vector3();
        points[1] = new Vector3();
        points[2] = new Vector3();

        this.points[0].x = triangle.points[0].x;
        this.points[0].y = triangle.points[0].y;
        this.points[0].z = triangle.points[0].z;

        this.points[1].x = triangle.points[1].x;
        this.points[1].y = triangle.points[1].y;
        this.points[1].z = triangle.points[1].z;

        this.points[2].x = triangle.points[2].x;
        this.points[2].y = triangle.points[2].y;
        this.points[2].z = triangle.points[2].z;

    }

    public void multiplyMatrix(Matrix2D input) {
        points[0] = points[0].multiplyMatrix(input);
        points[1] = points[1].multiplyMatrix(input);
        points[2] = points[2].multiplyMatrix(input);
    }

    public void translate(double x, double y, double z) {

        points[0].x += x;
        points[0].y += y;
        points[0].z += z;

        points[1].x += x;
        points[1].y += y;
        points[1].z += z;

        points[2].x += x;
        points[2].y += y;
        points[2].z += z;

    }

}
