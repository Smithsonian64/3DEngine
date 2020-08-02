import java.util.Vector;

public class Triangle {

    Vector3[] points;

    Triangle(Vector3 point1, Vector3 point2, Vector3 point3) {

        points = new Vector3[3];

        points[0] = point1;
        points[1] = point2;
        points[2] = point3;

    }

}
