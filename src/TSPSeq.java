import java.util.ArrayList;
import java.util.PriorityQueue;

import edu.rit.pj2.Job;
import edu.rit.util.Instance;

public class TSPSeq{

	
	public static void main(String[] args) {
		Graph g1 = null;
		boolean flag = false;
		PriorityQueue<NodeState> pqueue = new PriorityQueue<NodeState>();
		ArrayList<NodeState> path = new ArrayList<NodeState>();
		// check if the number of arguments is equal to 1
		if (args.length != 1) {

			invalidarguments();
			
		}
		try {

			g1 = (Graph) Instance.newInstance(args[0]);

		} catch (Exception e) {

			System.out.println("invalid argument");
			System.exit(0);
		}
		AdjMatrix graph = new AdjMatrix(g1.V());
		graph.generateMatrix(g1);
		NodeState first = new NodeState();
		// get starting node
		for (int i = 0; i < graph.Matrix.length; i++) {
			
			for (int j = 0; j < graph.Matrix[i].length; j++) {
				
				if(graph.Matrix[i][j] != 0){
					
					first.cost = graph.Matrix[i][j];
					
					break;
				}
			}
			
		}
		
		// best first search
		
		
		
		
	}

	private static void invalidarguments() {
		System.out.println("invalid number of arguments");
		System.exit(0);
	}
}
