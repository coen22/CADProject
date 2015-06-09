package obj;

import java.util.ArrayList;
import java.util.List;

public class Tetrahedron extends Object3D {

	private ArrayList<Vertex> vertices; 
	private ArrayList<Triangle> tris;
	
	public Tetrahedron(Triangle tri1, Triangle tri2, Triangle tri3, Triangle tri4){
		tris = new ArrayList<>();
		tris.add(tri1);
		tris.add(tri2);
		tris.add(tri3);
		tris.add(tri4);
		vertices = new ArrayList<>();
		//This is done to filter out duplicate unnecessary points
		vertices.add(tri1.getA());
		vertices.add(tri1.getB());
		vertices.add(tri1.getC());
		for(int i = 1; i < tris.size(); i++){
			if( !(tris.get(i).getA().equals(vertices.get(0))) && !(tris.get(i).getA().equals(vertices.get(1))) && !(tris.get(i).getA().equals(vertices.get(2)))){
				
				System.out.println("Adds point A");
				vertices.add(tris.get(i).getA());
				break;
			}
			else{
				//Do nothing the point is already added
			}
			if( !(tris.get(i).getB().equals(vertices.get(0))) && !(tris.get(i).getB().equals(vertices.get(1))) && !(tris.get(i).getB().equals(vertices.get(2)))){
				
				System.out.println("Adds point B");
				vertices.add(tris.get(i).getB());
				break;
			}
			else{
				//Do nothing the point is already added
			}
			if( !(tris.get(i).getC().equals(vertices.get(0))) && !(tris.get(i).getC().equals(vertices.get(1))) && !(tris.get(i).getC().equals(vertices.get(2)))){
				System.out.println("Adds point C");
				vertices.add(tris.get(i).getC());
				break;
			}
			else{
				//Do nothing the point is already added
			}
		}
	}
	
	public Tetrahedron(Vertex a, Vertex b, Vertex c, Vertex d){
		Triangle tri1 = new Triangle(a, b, c);
		Triangle tri2 = new Triangle(b, c, d);
		Triangle tri3 = new Triangle(c, d, a);
		Triangle tri4 = new Triangle(d, a, b);
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
	
	public Tetrahedron(ArrayList<Vertex> inputVertices, ArrayList<Triangle> inputTris){
		if(inputTris.size() == 4 && inputVertices.size() == 4){
			this.vertices = inputVertices;
			this.tris = inputTris;
		}
		else{
			return;
		}
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
		System.out.println(vertices.size());
		System.out.println(vertices.toString());
		double matrix[][] = new double[3][3];
		print(matrix);
		for(int i = 0; i < matrix.length; i++){
			matrix[i][0] = vertices.get(i).getX()-vertices.get(3).getX();
			System.out.println(vertices.get(i).getX() + " - " + vertices.get(3).getX());
			matrix[i][1] = vertices.get(i).getY()-vertices.get(3).getY();
			System.out.println(vertices.get(i).getY() + " - " + vertices.get(3).getY());
			matrix[i][2] = vertices.get(i).getZ()-vertices.get(3).getZ();
			System.out.println(vertices.get(i).getZ() + " - " + vertices.get(3).getZ());
		}
		print(matrix);
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
		print(matrix);
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
