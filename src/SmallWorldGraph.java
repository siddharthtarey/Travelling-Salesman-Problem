import edu.rit.util.Random;
import edu.rit.util.Set;
import java.util.NoSuchElementException;

/**
 * Class SmallWorldGraph encapsulates a random undirected graph generated using
 * the Watts-Strogatz procedure. Starting from a K-regular graph, each edge is
 * rewired with probability P.
 *
 * @author  Alan Kaminsky
 * @version 12-Oct-2016
 */
public class SmallWorldGraph
	implements Graph
	{

	private int V;              // Number of vertices
	private int K;              // Start with a K-regular graph
	private double P;           // Edge rewiring probability
	private Random prng;        // Pseudorandom number generator
	private int a, j;           // Indexes for generating edges
	private Set<Integer> edges; // Set of generated edges

	/**
	 * Construct a new small-world graph.
	 *
	 * @param  V     Number of vertices, 2 &le; V &le; 65536.
	 * @param  K     Start with a K-regular graph, K &ge; 1.
	 * @param  P     Edge rewiring probability, 0.0 &le; P &le; 1.0.
	 * @param  seed  Random seed.
	 *
	 * @exception  IllegalArgumentException
	 *     (unchecked exception) Thrown if V, K, or P is out of range.
	 */
	public SmallWorldGraph
		(int V,
		 int K,
		 double P,
		 long seed)
		{
		// Verify preconditions.
		this.V = V;
		this.K = K;
		this.P = P;
		if (V < 2 || V > 65536)
			throw new IllegalArgumentException (String.format
				("SmallWorldGraph(): V = %d illegal", V));
		if (K < 1)
			throw new IllegalArgumentException (String.format
				("SmallWorldGraph(): K = %d illegal", K));
		if (P < 0.0 || P > 1.0)
			throw new IllegalArgumentException (String.format
				("SmallWorldGraph(): P = %.3f illegal", P));

		// Initialize fields.
		prng = new Random (seed);
		a = 0;
		j = 1;
		edges = new Set<Integer>();
		}

	/**
	 * Returns the number of vertices in this graph, V.
	 */
	public int V()
		{
		return V;
		}

	/**
	 * Returns the number of edges in this graph, E.
	 */
	public int E()
		{
		return V*K;
		}

	/**
	 * Obtain the next edge in this graph. This method must be called
	 * repeatedly, E times, to obtain all the edges. Each time this method is
	 * called, it stores, in the v1 and v2 fields of object e, the vertices
	 * connected by the next edge. Each vertex is in the range 0 .. V-1.
	 *
	 * @param  edge  Edge object in which to store the vertices.
	 *
	 * @exception  NoSuchElementException
	 *     (unchecked exception) Thrown if this method is called more than E
	 *     times.
	 */
	public void nextEdge
		(Edge e)
		{
		// Verify preconditions.
		if (a == V)
			throw new NoSuchElementException();

		// Generate the current edge (a,b).
		int b = (a + j) % V;
		if (prng.nextDouble() < P)
			{
			// Rewire edge to (a,c).
			int c;
			do
				c = prng.nextInt (V);
			while (c == a || c == b || edges.contains (edgeFor (a, c)));
			b = c;
			}
		e.v1 = a;
		e.v2 = b;
		edges.add (edgeFor (a, b));

		// Go to the next edge.
		++ j;
		if (j > K)
			{
			j = 1;
			++ a;
			}
		}

	/**
	 * Returns an Integer object representing edge (x,y).
	 */
	private static Integer edgeFor
		(int x,
		 int y)
		{
		if (x < y)
			return new Integer ((x << 16) | y);
		else
			return new Integer ((y << 16) | x);
		}

	}
