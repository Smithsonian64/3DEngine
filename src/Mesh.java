
import java.util.Vector;

public class Mesh {

    Vector<Triangle> triangles;

    Mesh(Vector<Triangle> triangles) {

        this.triangles = triangles;

    }

    Mesh() {
        triangles = new Vector<>();
    }

}
