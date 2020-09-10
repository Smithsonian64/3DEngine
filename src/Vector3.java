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

    public Vector3 multiplyMatrix(Matrix2D input) {
        Vector3 output = new Vector3();

        output.x = this.x * input.elements[0][0] +
                this.y * input.elements[1][0] +
                this.z * input.elements[2][0] +
                input.elements[3][0];

        output.y = this.x * input.elements[0][1] +
                this.y * input.elements[1][1] +
                this.z * input.elements[2][1] +
                input.elements[3][1];

        output.z = this.x * input.elements[0][2] +
                this.y * input.elements[1][2] +
                this.z * input.elements[2][2] +
                input.elements[3][2];

        double w = this.x * input.elements[0][3] +
                this.y * input.elements[1][3] +
                this.z * input.elements[2][3] +
                input.elements[3][3];

        if(w != 0) {
            output.x /= w;
            output.y /= w;
            output.z /= w;
        }

        return output;
    }

    public void magnify(double value) {
        x += value;
        y += value;
        z += value;
    }

    public Vector3 add(Vector3 input) {
        Vector3 output = new Vector3();

        output.x = x + input.x;
        output.y = y + input.y;
        output.z = z + input.z;

        return output;
    }

    public Vector3 subtract(Vector3 input) {
        Vector3 output = new Vector3();

        output.x = x - input.x;
        output.y = y - input.y;
        output.z = z - input.z;

        return output;

    }

    public Vector3 crossProduct(Vector3 input) {

        Vector3 output = new Vector3();

        output.x = this.y * input.z - this.z * input.y;
        output.y = this.z * input.x - this.x * input.z;
        output.z = this.x * input.y - this.y * input.x;

        return output;
    }

    public double dotProduct(Vector3 input) {
        return this.x * input.x + this.y * input.y + this.z * input.z;
    }

    public void normalize() {
        double length = this.getVectorLength();
        this.x /= length;
        this.y /= length;
        this.z /= length;
    }

    public double getVectorLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

}
