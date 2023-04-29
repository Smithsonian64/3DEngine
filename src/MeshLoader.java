import java.io.*;
import java.util.Vector;

public class MeshLoader {

    public static Mesh createMesh(String fileName) {

        Vector<Vector3> vertices = new Vector<>();

        Mesh outputMesh = new Mesh();

        File file = new File(fileName);

        BufferedReader reader;

        String[] currentString;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {

                currentString = line.split("\s+");

                if (currentString[0].equals("mtllib")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("usemtl")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("o")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("g")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("s")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("#")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("v"))
                    vertices.add(new Vector3(Double.parseDouble(currentString[1]), Double.parseDouble(currentString[2]), Double.parseDouble(currentString[3])));

                if (currentString[0].equals("f")) {
                    for (int i = 0; i + 3 < currentString.length; i++) {
                        outputMesh.triangles.add(new Triangle(vertices.get(Integer.parseInt(currentString[1].split("/")[0]) - 1),
                                vertices.get(Integer.parseInt(currentString[i + 2].split("/")[0]) - 1), vertices.get(Integer.parseInt(currentString[i + 3].split("/")[0]) - 1)));
                    }
                }

                if (currentString[0].equals("l")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("vt")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("vn")) {
                    line = reader.readLine();
                    continue;
                }

                if (currentString[0].equals("vp")) {
                    line = reader.readLine();
                    continue;
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputMesh;

    }

}
