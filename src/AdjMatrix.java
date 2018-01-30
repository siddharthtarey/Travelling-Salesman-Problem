
import edu.rit.util.Random;

/**
 * This class is created to contain the adjacency matrix of the Graph
 * 
 * @author Siddharth Tarey(st2476@rit.edu)
 * @author Pavan Bhat (pxb8715@rit.edu)
 *
 */
public class AdjMatrix {

	int[][] Matrix; // the adjacency matix

	/**
	 * Default constructor for this class
	 */
	AdjMatrix() {
	}

	/**
	 * Parameterized constructor to initiate the adjacency matrix
	 * 
	 * @param V:
	 *            the number of vertices, the graph must be initialized
	 */
	AdjMatrix(int V) {

		Matrix = new int[V][V];
		for (int i = 0; i < Matrix.length; i++) {
			for (int j = 0; j < Matrix.length; j++) {
				
				Matrix[i][j] = 9999;
			}
		}
	}

	/**
	 * This method generates the adjacency matrix for graph g
	 * 
	 * @param g,
	 *            the graph for which the matrix is to be created
	 */
	public void generateMatrix(Graph g) {
		Random r = new Random(98723497);
		for (int i = 0; i < g.E(); i++) {
			Edge e = new Edge();
			g.nextEdge(e); // this gets the next edge of the graph

			// set the eleemnt at the index as true
			
			int cost = r.nextInt(50);
			Matrix[e.v1][e.v2] = cost;
			
			// this is an undirected graph, therefore set the opposite direction
			// of the edge as true
			Matrix[e.v2][e.v1] = cost;
		}

	}

	

}
