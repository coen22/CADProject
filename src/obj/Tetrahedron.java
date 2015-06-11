package obj;

import java.util.ArrayList;
import java.util.List;

public class Tetrahedron extends Object3D {

	private ArrayList<Vertex> vertices; 
	private ArrayList<Triangle> tris;
	
	public Tetrahedron(Vertex a, Vertex b, Vertex c, Vertex d){
		Triangle tri1 = new Triangle(a, b, c);
		Triangle tri2 = new Triangle(b, d, c);
		Triangle tri3 = new Triangle(c, d, a);
		Triangle tri4 = new Triangle(d, b, a);
		vertices = new ArrayList<>();
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
		tris = new ArrayList<>();
		tris.add(tri1);
		tris.add(tri2);
		tris.add(tri3);
		tris.add(tri4);
	}
	
	public double tetrahedronArea(){
		double area = 0;
		for(int i = 0; i < this.getTris().size(); i++){
			area += (this.getTris().get(i)).calcArea();
		}
		return area;
	}
	
	public double tetrahedronVolume(){
		double volume = 0;
		//The volume is 1/6 times the absolute value of 
		//the determinant which consists of the vertices
		//Putting the coordinates of these vertices into a matrix
		//Entering these into a 3x3 matrix taking away the last vertexes coordinates with every input
		double matrix[][] = new double[3][3];
		for(int i = 0; i < matrix.length; i++){
			matrix[i][0] = vertices.get(i).getX()-vertices.get(3).getX();
			matrix[i][1] = vertices.get(i).getY()-vertices.get(3).getY();
			matrix[i][2] = vertices.get(i).getZ()-vertices.get(3).getZ();
			}
		//Doing matrix calculations so we get the determinant form
		for(int i = 0; i < matrix.length; i++){
			for (int j = i + 1; j < matrix[0].length; j++) {
				double mul = 1;
				for (int k = 0; k < matrix[0].length; k++) {
					if (matrix[i][k] != 0) {
						mul = matrix[j][k]/matrix[i][k];
						break;
					}
				}
				for (int k = 0; k < matrix[0].length; k++) {
					matrix[j][k] = matrix[j][k] - (matrix[i][k] * mul);
				}
			}
		}
		double determinant = matrix[0][0]*matrix[1][1]*matrix[2][2];
		volume = Math.abs(determinant)/6;
		return volume;
	}

	@Override
	public List<Vertex> getVerts() {
		return vertices;
	}

	@Override
	public List<Triangle> getTris() {
		return tris;
	}
	public void print(double[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			System.out.println("");
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j] + " ");
			}
		}
		System.out.println("");
	}
	
}
