import java.awt.*;

public class Object {

    double x;
    double y;
    double z;

    double pitch;
    double yaw;
    double roll;

    String name;

    Mesh mesh;

    TexturePaint texture;

    public Object(String name, double x, double y, double z, double pitch, double yaw, double roll, Mesh mesh) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;

        this.name = name;

        this.mesh = mesh;

    }

    public static Object newObject(String name, String modelFileName) {
        return new Object(name, 0, 0, 0, 0, 0, 0, MeshLoader.createMesh(modelFileName));
    }

    public void translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void rotate(double roll, double yaw, double pitch) {

        this.roll = roll;
        this.yaw = yaw;
        this.pitch = pitch;

    }

}
