public class Matrix2D {

    double[][] elements;

    //Remember, Row THEN Column

    Matrix2D(int x, int y) {

        elements = new double[x][y];

        for(int i = 0; i < elements.length; i++) {
            for(int j = 0; j < elements[i].length; j++) {
                elements[i][j] = 0;
            }
        }

    }

    public Vector3 multiplyMatrix(Vector3 input) {

        Vector3 output = new Vector3();

        output.x = input.x * this.elements[0][0] +
                input.y * this.elements[1][0] +
                input.z * this.elements[2][0] +
                this.elements[3][0];

        output.y = input.x * this.elements[0][1] +
                input.y * this.elements[1][1] +
                input.z * this.elements[2][1] +
                this.elements[3][1];

        output.z = input.x * this.elements[0][2] +
                input.y * this.elements[1][2] +
                input.z * this.elements[2][2] +
                this.elements[3][2];

        double w = input.x * this.elements[0][3] +
                input.y * this.elements[1][3] +
                input.z * this.elements[2][3] +
                this.elements[3][3];

        if(w != 0) {
            output.x /= w;
            output.y /= w;
            output.z /= w;
        }

        return output;
    }

}