
import java.util.ArrayList;

/**
 * this class defines the state space node for a graph
 * @author Siddharth Tarey(st2476@rit.edu) 
 * @author Pavan Bhat(pxb8715@rit.edu)
 */


public class NodeState implements Cloneable{

	ArrayList<Integer> prevnodes; // List of previous nodes
	volatile int cost;
	int[][] reducecost;
	int nextnode;
	int[] visited;
	/**
	 * Default constructor
	 */
	NodeState() {
		prevnodes = new ArrayList<Integer>();
	}

	/**
	 * Constructer that defines the reduce cost matrix 
	 * @param V: number of vertices
	 */
	NodeState(int V) {

		prevnodes = new ArrayList<Integer>();
		reducecost = new int[V][V];
		visited = new int[V];
	}

	/**
	 * gets the cost of the root node
	 * @param a the graph 
	 */
	public void initcost(AdjMatrix a) {
		int sum = 0;
		this.reducecost = (int[][]) a.Matrix.clone();
		for (int i = 0; i < this.reducecost.length; i++) {
			int min = this.reducecost[i][0];

			for (int j = 0; j < this.reducecost.length; j++) {

				if (min > this.reducecost[i][j]) {
					min = this.reducecost[i][j];
					
				}

			}
			sum += min;
			for (int j = 0; j < this.reducecost.length; j++) {
				if (this.reducecost[i][j] != 9999) {
					this.reducecost[i][j] -= min;
				}
			}
		}

		// col min value
		for (int i = 0; i < this.reducecost.length; i++) {

			int min = this.reducecost[0][i];

			for (int j = 0; j < this.reducecost.length; j++) {
				
					if (min > this.reducecost[j][i]) {
						min = this.reducecost[j][i];
						
					}
				

			}
			sum += min;
			for (int j = 0; j < this.reducecost.length; j++) {
				if (this.reducecost[j][i] != 9999) {
					this.reducecost[j][i] -= min;
				}
			}
		}

		this.cost = sum;
		this.nextnode =0;
		//this.visited[0]=1;

	}

	/**
	 * This funnction calculaes the cost of the state space node 
	 * @param old : the parent node
	 * @param row : the row of the current node
	 * @param col: the column of the current node
	 * @param flag: determines if they are the children of the root node
	 */
	@SuppressWarnings("unchecked")
	public void getCost(NodeState old, int row, int col,boolean flag) {
		int sum = 0;

		this.reducecost = (int[][]) old.reducecost.clone();
		// row min value reduction

		for (int i = 0; i < this.reducecost.length; i++) {
			if(i == row)
				continue;
			int min = this.reducecost[i][0];

			for (int j = 0; j < this.reducecost.length; j++) {
				if ( j == col)
					continue;
					if (min > this.reducecost[i][j]) {
						min = this.reducecost[i][j];
						
					}

				
			}
			sum += min;

			for (int j = 0; j < this.reducecost.length; j++) {
				if(i == row || j ==col)
					continue;
				if ( this.reducecost[i][j] != 9999) {
					this.reducecost[i][j] -= min;
				}
			}
			min =0;
		}

		// col min value
		for (int i = 0; i < this.reducecost.length; i++) {
			if(i==col)
				continue;
			int min = this.reducecost[0][i];

			for (int j = 0; j < this.reducecost.length; j++) {
				if ( j == row) 
					continue;
					if (min > this.reducecost[j][i]) {
						min = this.reducecost[j][i];
						
					}
				

			}
			sum += min;
			for (int j = 0; j < this.reducecost.length; j++) {
				if(i == col || j == row)
					continue;
				if (this.reducecost[j][i] != 9999) {
					this.reducecost[j][i] -= min;
				}
			}
			min=0;
		}

		this.cost = sum +old.cost +old.reducecost[row][col]; 
		this.nextnode = col;
		
		for(Integer i : old.prevnodes){
			
			this.prevnodes.add(i);
		}
		
		
		this.prevnodes.add(row);
		
		this.visited = old.visited.clone();
		
		if(flag == true)
		{
			this.visited[row] = 1;
		}
		
	}

	/**
	 * Checks if the size of the TSP path is equal to the number of vertices in a graph
	 * @return true if tSP path = number of vertices
	 */
	public boolean checkpath(){
		
		if(this.prevnodes.size() == reducecost.length){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * Clones the node state object
	 */
	public Object clone() throws CloneNotSupportedException {

		//clone is created here
		NodeState r = (NodeState) super.clone();
		// deep copy is generated
		r.cost = this.cost;
		r.nextnode= this.nextnode;
		r.prevnodes = this.prevnodes;
		r.visited = this.visited;
		
		return r;

	}
	
}


