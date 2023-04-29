public class Camera {

    double x;
    double y;
    double z;

    double roll;
    double yaw;
    double pitch;

    Matrix2D rotationMatrix;

    ProjectionMatrix projectionMatrix;

    public Camera(double x, double y, double z, double roll, double yaw, double pitch) {

        this.x = x;
        this.y = y;
        this.z = z;

        this.roll = roll;
        this.yaw = yaw;
        this.pitch = pitch;

        this.projectionMatrix = new ProjectionMatrix();

    }

    public void translate(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void rotate(double thetax, double thetay, double thetaz) {

        roll += thetaz;
        yaw += thetay;
        pitch += thetax;

    }

    public Matrix2D getRotationMatrixX(double theta) {

        Matrix2D output = new Matrix2D(4, 4);

        output.elements[0][0] = Math.cos(theta) + (1 - Math.cos(theta));
        output.elements[0][1] = 0;
        output.elements[0][2] = 0;
        output.elements[1][0] = 0;
        output.elements[1][1] = Math.cos(theta);
        output.elements[1][2] = -Math.sin(theta);
        output.elements[2][0] = 0;
        output.elements[2][1] = Math.sin(theta);
        output.elements[2][2] = Math.cos(theta);
        output.elements[3][3] = 0;

        return output;
    }

    public Matrix2D getRotationMatrixY(double theta) {

        Matrix2D output = new Matrix2D(4, 4);

        output.elements[0][0] = Math.cos(theta);
        output.elements[0][1] = 0;
        output.elements[0][2] = Math.sin(theta);
        output.elements[1][0] = 0;
        output.elements[1][1] = Math.cos(theta) + (1 - Math.cos(theta));
        output.elements[1][2] = 0;
        output.elements[2][0] = -Math.sin(theta);
        output.elements[2][1] = 0;
        output.elements[2][2] = Math.cos(theta);

        output.elements[3][3] = 0;

        return output;
    }

    public Matrix2D getRotationMatrixZ(double theta) {

        Matrix2D output = new Matrix2D(4, 4);

        output.elements[0][0] = Math.cos(theta);
        output.elements[0][1] = -Math.sin(theta);
        output.elements[0][2] = 0;
        output.elements[1][0] = Math.sin(theta);
        output.elements[1][1] = Math.cos(theta);
        output.elements[1][2] = 0;
        output.elements[2][0] = 0;
        output.elements[2][1] = 0;
        output.elements[2][2] = Math.cos(theta) + (1 - Math.cos(theta));

        output.elements[3][3] = 0;

        return output;
    }
}
