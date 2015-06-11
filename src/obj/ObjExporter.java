package obj;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Kareem
 */
public class ObjExporter {

    String path;

    public ObjExporter(String path) {
        this.path = path;
    }

    public void export(List<Vertex> vertex,List<Triangle> tri) throws IOException {
        WriteFile w = new WriteFile(path);
        w.writeToFile("");

        w.setAppend(true);
        String tmp = "";
        for (Vertex vertex1 : vertex) {
            tmp += ("v " + vertex1.getX() + " " + vertex1.getY() + " " + vertex1.getZ() + "\n");
        }
        w.writeToFile(tmp);

        tmp = "";
        for (Triangle face : tri) {
            tmp += "f " + face.getA() + " " + face.getB() + " " + face.getC() + "\n";
        }

        w.writeToFile(tmp);
    }

}
