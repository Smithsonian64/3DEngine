import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

public class MeshLoader {

    public static Mesh createMesh(String fileName) {

        Vector<Vector3> vertices = new Vector<>();

        Mesh outputMesh = new Mesh();

        File file = new File(fileName);

        BufferedReader reader;

        String[] currentString;

        try {
            reader = new BufferedReader(new FileReader(
                    fileName));
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);

                currentString = line.split(" ");

                if(currentString[0].equals("v")) vertices.add(new Vector3(Double.parseDouble(currentString[1]),
                        Double.parseDouble(currentString[2]),
                        Double.parseDouble(currentString[3])));

                if(currentString[0].equals("f")) outputMesh.triangles.add(new Triangle(
                        vertices.get(Integer.parseInt(currentString[1]) - 1),
                        vertices.get(Integer.parseInt(currentString[2]) - 1),
                        vertices.get(Integer.parseInt(currentString[3]) - 1)));

                //System.out.println(Arrays.toString(currentString));

                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputMesh;

    }

}
