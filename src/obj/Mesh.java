package obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mesh extends Object3D {

    private ArrayList<Vertex> vertices;
    private ArrayList<Triangle> tris;

    public Mesh(ArrayList<Vertex> inputVertices, ArrayList<Triangle> inputFaces) {
        this.vertices = inputVertices;
        this.tris = inputFaces;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void setTris(ArrayList<Triangle> tris) {
        this.tris = tris;
    }

    public Mesh() {
        this.vertices = new ArrayList<Vertex>();
        this.tris = new ArrayList<Triangle>();
    }

    public Mesh(String Path) {
        this.vertices = new ArrayList<Vertex>();
        this.tris = new ArrayList<Triangle>();
        ReadFile reader = new ReadFile(Path);
        String[] text = new String[0];
        try {
            text = reader.OpenFile();
        } catch (IOException ex) {
            Logger.getLogger(Mesh.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String text1 : text) {
            if (text1.length() > 0) {
                if (text1.charAt(0) == 'v') {
                    text1 = text1.replaceAll("v", "");
                    text1 = text1.trim();
                    String[] tmp = text1.split(" ");
                    if (tmp.length != 3) {
                        System.out.println("Error");
                    } else {
                        vertices.add(new Vertex(Double.valueOf(tmp[0]), Double.valueOf(tmp[1]), Double.valueOf(tmp[2])));
                    }
                } else if (text1.charAt(0) == 'f') {
                    text1 = text1.replaceAll("f", "");
                    text1 = text1.trim();
                    String[] tmp = text1.split(" ");
                    if (tmp.length != 3) {
                        System.out.println("Error");
                    } else {
                        tris.add(new Triangle(vertices.get(Integer.valueOf(tmp[0]) - 1), vertices.get(Integer.valueOf(tmp[1]) - 1), vertices.get(Integer.valueOf(tmp[2]) - 1)));
                    }
                }
            } else {
                //do nothing
            }
        }
    }

    @Override
    public List<Vertex> getVerts() {
        return vertices;
    }

    @Override
    public List<Triangle> getTris() {
        return tris;
    }
}
