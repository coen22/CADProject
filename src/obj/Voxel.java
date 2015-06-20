package obj;

import java.util.ArrayList;

/**
 * A default voxel
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class Voxel extends Mesh {

    /**
     * Default constructor
     *
     * @param size The size of a voxel
     * @param x The x location
     * @param y The y location
     * @param z The z location
     */
    public Voxel(double size, double x, double y, double z) {
        ArrayList<Vertex> ver = new ArrayList<>();
        ArrayList<Triangle> tri = new ArrayList<>();

        ver.add(new Vertex(x - size, y - size, z - size));
        ver.add(new Vertex(x + size, y - size, z - size));
        ver.add(new Vertex(x - size, y + size, z - size));
        ver.add(new Vertex(x - size, y - size, z + size));
        ver.add(new Vertex(x + size, y - size, z + size));
        ver.add(new Vertex(x - size, y + size, z + size));
        ver.add(new Vertex(x + size, y + size, z - size));
        ver.add(new Vertex(x + size, y + size, z + size));

        tri.add(new Triangle(ver.get(0), ver.get(6), ver.get(1)));
        tri.add(new Triangle(ver.get(0), ver.get(2), ver.get(6)));
        tri.add(new Triangle(ver.get(0), ver.get(1), ver.get(4)));
        tri.add(new Triangle(ver.get(0), ver.get(4), ver.get(3)));
        tri.add(new Triangle(ver.get(0), ver.get(5), ver.get(2)));
        tri.add(new Triangle(ver.get(0), ver.get(3), ver.get(5)));
        tri.add(new Triangle(ver.get(3), ver.get(7), ver.get(5)));
        tri.add(new Triangle(ver.get(3), ver.get(4), ver.get(7)));
        tri.add(new Triangle(ver.get(7), ver.get(2), ver.get(5)));
        tri.add(new Triangle(ver.get(7), ver.get(6), ver.get(2)));
        tri.add(new Triangle(ver.get(7), ver.get(4), ver.get(1)));
        tri.add(new Triangle(ver.get(7), ver.get(1), ver.get(6)));

        setTris(tri);
        setVertices(ver);
    }
}
