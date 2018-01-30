


import edu.rit.pj2.ObjectLoop;
import edu.rit.pj2.Task;
import edu.rit.pj2.WorkQueue;
import edu.rit.util.Instance;

/**
 * This class solves the TSP problem
 * @author Siddharth Tarey(st2476@rit.edu)
 * @author Pavan Bhat (pxb8715@rit.edu)
 *
 */
public class TSPParallev2 extends Task {

	AdjMatrix m; // adjacency matrix
	WorkQueue<NodeState> queue; // the parallel work queue
	volatile NodeState tsp = new NodeState(); // the tsp solution variable

	/**
	 * The main method
	 * @param arg0: the command line arguments
	 */
	public void main(String[] arg0) throws Exception {

		Graph g = (Graph)Instance.newInstance(arg0[0]);
		AdjMatrix m = new AdjMatrix(g.V());
		m.generateMatrix(g);
	   
		
		NodeState first = new NodeState(g.V());
		first.initcost(m);

		queue = new WorkQueue<NodeState>();
		
		for (int i = 0; i < m.Matrix.length; i++) {
			
			if (m.Matrix[first.nextnode][i] != 9999 && first.visited[i] == 0) {
				NodeState nn = new NodeState(g.V());
				nn.getCost(first, first.nextnode, i,false);
				queue.add(nn);
			}
		}
		
		parallelFor(queue).threads(THREADS_EQUALS_CORES).exec(new ObjectLoop<NodeState>() {
		
			
			/**
			 * The run metod for the parallel work queue
			 * @param : the node extracted from the work queue
			 */
			public void run(NodeState ct) throws Exception {
				
				for (int i = 0; i < m.Matrix.length; i++) {
					if (m.Matrix[ct.nextnode][i] != 9999 && ct.visited[i] == 0) {

						NodeState nn = new NodeState(g.V());
						
						nn.getCost(ct, ct.nextnode, i,true);
						
						if (nn.nextnode == 0 && nn.checkpath() && (tsp.cost == 0 || tsp.cost > nn.cost)) {
							tsp.cost = nn.cost;
							nn.prevnodes.add(nn.nextnode);
							nn.visited[nn.nextnode] =1;
							tsp.prevnodes = nn.prevnodes;
						}
						else if (nn.visited[i] == 0 && !nn.checkpath()&& (tsp.cost == 0 || tsp.cost > nn.cost)) {
							
							queue.add(nn);
							
						}
						
					}

				}
				

			}

		});

		//  display the result
		for(Integer i : tsp.prevnodes){
			
			System.out.print(i+"->");
		}

		System.out.println();
		System.out.println(tsp.cost);
		
	}

}
