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
		for(int i = 0; i < tris.size(); i++){
			vertices.add(tris.get(i).getA());
			vertices.add(tris.get(i).getB());
			vertices.add(tris.get(i).getC());
		}
	}
	
	public Tetrahedron(Vertex a, Vertex b, Vertex c, Vertex d){
		Triangle tri1 = new Triangle(a, b, c);
		Triangle tri2 = new Triangle(b, c, d);
		Triangle tri3 = new Triangle(c, d, a);
		Triangle tri4 = new Triangle(d, a, b);
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
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
		//Adding the four different vertices into one arraylist without duplicates
		ArrayList <Vertex> temp = new ArrayList <Vertex>(); 
		for(int i = 0; i < this.getVerts().size(); i++){
			for(int j = 0; j < 4; j++){
				if((temp.get(j).getX() == this.getVerts().get(i).getX()) && (temp.get(j).getY() == this.getVerts().get(i).getY()) && (temp.get(j).getZ() == this.getVerts().get(i).getZ())){
				}
				else{
					System.out.println("Adds to temp");
					temp.add(this.getVerts().get(i));
					}
			}
		}
		//Putting the coordinates of these vertices into a matrix
		//Entering these into a 3x3 matrix taking away the last vertexes coordinates with every input
		System.out.println(temp.size());
		double matrix[][] = new double[3][3];
		for(int i = 0; i < matrix.length; i++){
			matrix[1][i] = temp.get(i).getX()-temp.get(3).getX();
			matrix[2][i] = temp.get(i).getY()-temp.get(3).getY();
			matrix[3][i] = temp.get(i).getZ()-temp.get(3).getZ();
		}
		print(matrix);
		//Doing matrix calculations so we get the determinant form
		double mult = 0;
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				
			}
		}
		
		
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
				System.out.println(matrix[i][j]);
			}
		}
	}
	
}
