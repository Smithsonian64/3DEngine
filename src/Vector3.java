import java.util.Vector;

public class Vector3 {

    double x;
    double y;
    double z;

    Vector3(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    Vector3(Vector3 vector3) {

        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;

    }

    Vector3() {

        this.x = 0;
        this.y = 0;
        this.z = 0;

    }

}
