public class Camera {

    double x;
    double y;
    double z;


    public Camera(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;


    }

    public void translate(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void rotate () {

        Matrix2D rotationMatrixX = new Matrix2D(4, 4);
        Matrix2D rotationMatrixY = new Matrix2D(4, 4);
        Matrix2D rotationMatrixZ = new Matrix2D(4, 4);




    }
}
