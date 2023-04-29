public class Matrix2D {

    double[][] elements;
    int numrows;
    int numColumns;

    // Remember, Row THEN Column

    Matrix2D(int columns, int rows) {

        elements = new double[columns][rows];
        numrows = rows;
        numColumns = columns;

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j] = 0;
            }
        }

    }

    public Matrix2D multiplyMatrix(Matrix2D input) {

        Matrix2D output = new Matrix2D(this.numrows, input.numColumns);

        double workingNumber = 0;

        for (int i = 0; i < input.numColumns; i++) {

            for (int j = 0; j < this.numrows; j++) {

                for (int k = 0; k < this.numColumns; k++) {
                    for (int l = 0; l < input.numrows; l++) {
                        workingNumber += this.elements[k][l] * input.elements[k][l];
                    }
                }

                output.elements[i][j] = workingNumber;

            }

        }

        return output;

    }

}
