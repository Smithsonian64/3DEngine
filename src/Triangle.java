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

        this.points[0] = triangle.points[0];
        this.points[1] = triangle.points[1];
        this.points[2] = triangle.points[2];

    }

}
