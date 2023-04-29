public class ProjectionMatrix extends Matrix2D {

    ProjectionMatrix() {
        super(4, 4);

        double theta = Math.PI / 2;

        double f = 1.0 / Math.tan(theta / 2);

        // double a = Window.width / Window.height;

        double a = 1;

        double zFar = 1000.0;
        double zNear = 0.1;

        double q = zFar / (zFar - zNear);

        elements[0][0] = a * f;
        elements[0][1] = 0;
        elements[0][2] = 0;
        elements[0][3] = 0;

        elements[1][0] = 0;
        elements[1][1] = f;
        elements[1][2] = 0;
        elements[1][3] = 0;

        elements[2][0] = 0;
        elements[2][1] = 0;
        elements[2][2] = q;
        elements[2][3] = 1;

        elements[3][0] = 0;
        elements[3][1] = 0;
        elements[3][2] = -zNear * q;
        elements[3][3] = 0;

    }

    ProjectionMatrix(double a, double f, double q, double zNear) {
        super(4, 4);

        elements = new double[4][4];

        elements[0][0] = a * f;
        elements[0][1] = 0;
        elements[0][2] = 0;
        elements[0][3] = 0;

        elements[1][0] = 0;
        elements[1][1] = f;
        elements[1][2] = 0;
        elements[1][3] = 0;

        elements[2][0] = 0;
        elements[2][1] = 0;
        elements[2][2] = q;
        elements[2][3] = 1;

        elements[3][0] = 0;
        elements[3][1] = 0;
        elements[3][2] = -zNear * q;
        elements[3][3] = 0;

    }

}
