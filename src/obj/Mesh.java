package obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Mesh extends Object3D {
	
	private ArrayList<Vertex> vertices; 
	private ArrayList<Triangle> tris;
	
	public Mesh(ArrayList<Vertex> inputVertices, ArrayList<Triangle> inputFaces){
		this.vertices = inputVertices;
		this.tris = inputFaces;
	}
	
	public Mesh(){
		this.vertices = new ArrayList<Vertex>();
		this.tris = new ArrayList<Triangle>();
	}
	
//	public Mesh(File objFile) throws FileNotFoundException{
//		
//		BufferedReader input = new BufferedReader(new FileReader(objFile));
//		
//		input.
//		
//	}
	
	
	@Override
	public List<Vertex> getVerts() {
		return vertices;
	}

	@Override
	public List<Triangle> getTris() {
		return tris;
	}
	
	

}
