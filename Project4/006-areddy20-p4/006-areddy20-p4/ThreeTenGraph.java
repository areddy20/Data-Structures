import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Factory;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * This is the ThreeTenGraph class.
 * 
 * @author Aditi Reddy
 */
class ThreeTenGraph implements Graph<GraphNode, GraphEdge>, DirectedGraph<GraphNode, GraphEdge> {

	/**
	 * Private static variable for max number of nodes.
	 */
	private static final int MAX_NUMBER_OF_NODES = 200;

	/**
	 * Private variable for the vertix list.
	 */
	private GraphNode[] vertexList = null;

	/**
	 * Private variable for the matrix.
	 */
	private GraphEdge[][] matrix = null;

	/**
	 * Defualt constructor for ThreeTenGraph.
	 */
	public ThreeTenGraph() {

		this.vertexList = new GraphNode[MAX_NUMBER_OF_NODES];

		this.matrix = new GraphEdge[MAX_NUMBER_OF_NODES][MAX_NUMBER_OF_NODES];

	}

	/**
	 * Method that returns a view of all edges in this graph.
	 * 
	 * @return a Collection view of all edges in this graph for output.
	 */
	public Collection<GraphEdge> getEdges() {

		LinkedList<GraphEdge> edgeListCollection = new LinkedList<>();

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			for (int j = 0; j < MAX_NUMBER_OF_NODES; j++) {

				GraphEdge edge = this.matrix[i][j];

				if (edge != null) {

					edgeListCollection.add(edge);

				}

			}

		}

		return (edgeListCollection);

	}

	/**
	 * Method that returns a view of all vertices in this graph.
	 * 
	 * @return a Collection view of all vertices in this graph for output.
	 */
	public Collection<GraphNode> getVertices() {

		LinkedList<GraphNode> nodeListCollection = new LinkedList<>();

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphNode vertex = vertexList[i];

			if (vertex != null) {

				nodeListCollection.add(vertex);

			}

		}

		return (nodeListCollection);

	}

	/**
	 * Method that returns the number of edges in this graph.
	 * 
	 * @return the number of edges in this graph for output.
	 */
	public int getEdgeCount() {

		return (this.getEdges().size());

	}

	/**
	 * Method that returns the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph for output.
	 */
	public int getVertexCount() {

		return (this.getVertices().size());

	}

	/**
	 * Method that returns true if this graph's vertex collection contains vertex.
	 * 
	 * @param vertex the vertex whose presence is being queried for input.
	 * @return true iff this graph contains a vertex vertex for output.
	 */
	public boolean containsVertex(GraphNode vertex) {

		if (vertex.id < 0 || vertex.id >= MAX_NUMBER_OF_NODES) {

			return (false);
		}

		if (vertexList[vertex.id] != null) {

			return (true);

		}

		return (false);

	}

	/**
	 * Method that returns a Collection view of the incoming edges incident to
	 * vertex in this graph.
	 * 
	 * @param vertex the vertex whose incoming edges are to be returned for input.
	 * @return a Collection view of the incoming edges incident to vertex in this
	 *         graph for output.
	 */
	public Collection<GraphEdge> getInEdges(GraphNode vertex) {

		LinkedList<GraphEdge> edgeListCollection = new LinkedList<>();

		if (containsVertex(vertex) == false) {

			return (edgeListCollection);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphEdge edge = matrix[i][vertex.id];

			if (edge != null) {

				edgeListCollection.add(edge);

			}

		}

		return (edgeListCollection);

	}

	/**
	 * Method that returns a Collection view of the outgoing edges incident to
	 * vertex in this graph.
	 * 
	 * @param vertex the vertex whose outgoing edges are to be returned for input.
	 * @return a Collection view of the outgoing edges incident to vertex in this
	 *         graph for output.
	 */
	public Collection<GraphEdge> getOutEdges(GraphNode vertex) {

		LinkedList<GraphEdge> edgeListCollection = new LinkedList<>();

		if (containsVertex(vertex) == false) {

			return (edgeListCollection);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphEdge edge = matrix[vertex.id][i];

			if (edge != null) {

				edgeListCollection.add(edge);

			}

		}

		return (edgeListCollection);

	}

	/**
	 * Method that returns the number of incoming edges incident to vertex.
	 * 
	 * @param vertex the vertex whose indegree is to be calculated for input.
	 * @return the number of incoming edges incident to vertex for output.
	 */
	public int inDegree(GraphNode vertex) {

		if (containsVertex(vertex) == false) {

			return (0);

		}

		return (getInEdges(vertex).size());

	}

	/**
	 * Method that returns the number of outgoing edges incident to vertex.
	 * 
	 * @param vertex the vertex whose outdegree is to be calculated for input.
	 * @return the number of outgoing edges incident to vertex for output.
	 */
	public int outDegree(GraphNode vertex) {

		if (containsVertex(vertex) == false) {

			return 0;

		}

		return (getOutEdges(vertex).size());

	}

	/**
	 * Method that returns a Collection view of the predecessors of vertex in this
	 * graph.
	 * 
	 * @param vertex the vertex whose predecessors are to be returned for input.
	 * @return a Collection view of the predecessors of vertex in this graph for
	 *         output.
	 */
	public Collection<GraphNode> getPredecessors(GraphNode vertex) {

		LinkedList<GraphNode> nodeListCollection = new LinkedList<>();

		if (containsVertex(vertex) == false) {

			return (nodeListCollection);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphEdge edge = matrix[i][vertex.id];

			if (edge != null) {

				nodeListCollection.add(vertexList[i]);

			}

		}

		return (nodeListCollection);

	}

	/**
	 * Method that returns a Collection view of the successors of vertex in this
	 * graph.
	 * 
	 * @param vertex the vertex whose predecessors are to be returned for input.
	 * @return a Collection view of the successors of vertex in this graph for
	 *         output.
	 */
	public Collection<GraphNode> getSuccessors(GraphNode vertex) {

		LinkedList<GraphNode> nodeListCollection = new LinkedList<>();

		if (containsVertex(vertex) == false) {

			return (nodeListCollection);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphEdge edge = matrix[vertex.id][i];

			if (edge != null) {

				nodeListCollection.add(vertexList[i]);

			}

		}

		return (nodeListCollection);

	}

	/**
	 * Method that returns true if v1 is a predecessor of v2 in this graph.
	 * 
	 * @param v1 the first vertex to be queried for input.
	 * @param v2 the second vertex to be queried for input.
	 * @return true if v1 is a predecessor of v2, and false otherwise for output.
	 */
	public boolean isPredecessor(GraphNode v1, GraphNode v2) {

		if (containsVertex(v1) == false || containsVertex(v2) == false) {

			return (false);
		}

		if (matrix[v2.id][v1.id] != null) {

			return (true);
		}

		return (false);

	}

	/**
	 * Method that returns true if v1 is a successor of v2 in this graph.
	 * 
	 * @param v1 the first vertex to be queried for input.
	 * @param v2 the second vertex to be queried for input.
	 * @return true if v1 is a successor of v2, and false otherwise for output.
	 */
	public boolean isSuccessor(GraphNode v1, GraphNode v2) {

		if (containsVertex(v1) == false || containsVertex(v2) == false) {

			return (false);

		}

		if (matrix[v1.id][v2.id] != null) {

			return (true);

		}

		return (false);

	}

	/**
	 * Method that returns the collection of vertices which are connected to
	 * vertexvia any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then it will be included
	 * in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned for input.
	 * @return the collection of vertices which are connected to vertex, or null if
	 *         vertex is not present for output.
	 */
	public Collection<GraphNode> getNeighbors(GraphNode vertex) {

		LinkedList<GraphNode> neighborsCollection = new LinkedList<>();

		if (containsVertex(vertex) == false) {

			return (neighborsCollection);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphEdge edge = matrix[vertex.id][i];

			if (edge != null) {

				neighborsCollection.add(vertexList[i]);

			}

			else {

				edge = matrix[i][vertex.id];

				if (edge != null) {

					neighborsCollection.add(vertexList[i]);

				}

			}

		}

		return (neighborsCollection);

	}

	/**
	 * Method that returns the number of vertices that are adjacent to vertex.
	 * 
	 * @param vertex the vertex whose neighbor count is to be returned for input.
	 * @return the number of neighboring vertices for output.
	 */
	public int getNeighborCount(GraphNode vertex) {

		return (getNeighbors(vertex).size());

	}

	/**
	 * Method that returns the source if directedEdge is a directed edge in this
	 * graph, otherwise returns null.
	 * The source of a directed edge d is defined to be the vertex for which d is an
	 * outgoing edge.
	 * directedEdge is guaranteed to be a directed edge if its EdgeType is
	 * DIRECTED.
	 * 
	 * @param directedEdge for input.
	 * @return the source of directedEdge if it is a directed edge in this graph,
	 *         or null otherwise for output.
	 */
	public GraphNode getSource(GraphEdge directedEdge) {

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			for (int j = 0; j < MAX_NUMBER_OF_NODES; j++) {

				GraphEdge edge = matrix[i][j];

				if (edge == directedEdge) {

					return (vertexList[i]);

				}

			}

		}

		return (null);

	}

	/**
	 * Method that returns the destination if directedEdge is a directed edge in
	 * this graph, otherwise returns null.
	 * The destination of a directed edge d is defined to be the vertex incident to
	 * d for which d is an incoming edge.
	 * directedEdge is guaranteed to be a directed edge if its EdgeType is
	 * DIRECTED.
	 * 
	 * @param directedEdge for input.
	 * @return the destination of directedEdge if it is a directed edge in this
	 *         graph, or null otherwise for output.
	 */
	public GraphNode getDest(GraphEdge directedEdge) {

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			for (int j = 0; j < MAX_NUMBER_OF_NODES; j++) {

				GraphEdge edge = matrix[i][j];

				if (edge == directedEdge) {

					return (vertexList[j]);

				}

			}

		}

		return (null);

	}

	/**
	 * Method that returns an edge that connects v1 to v2.
	 * If this edge is not uniquely defined any of these edges may be returned.
	 * 
	 * @param v1 for input.
	 * @param v2 for input.
	 * @return an edge that connects v1 to v2, or null if no such edge exists for
	 *         output.
	 */
	public GraphEdge findEdge(GraphNode v1, GraphNode v2) {

		if (containsVertex(v1) == false || containsVertex(v2) == false) {

			return (null);

		}

		return (matrix[v1.id][v2.id]);

	}

	/**
	 * Method that returns true if vertex and edge are incident to each other.
	 * 
	 * @param vertex for input.
	 * @param edge   for input.
	 * @return true if vertex and edge are incident to each other for output.
	 */
	public boolean isIncident(GraphNode vertex, GraphEdge edge) {

		if (containsVertex(vertex) == false) {

			return (false);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			GraphEdge tempEdge = matrix[vertex.id][i];

			if (tempEdge == edge) {

				return (true);

			}

			tempEdge = matrix[i][vertex.id];

			if (tempEdge == edge) {

				return (true);

			}

		}

		return (false);

	}

	/**
	 * Method that adds edge e to this graph such that it connects vertex v1 to v2.
	 * If this graph does not contain v1, v2, or both, vertices may be added to the
	 * graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of e will be the
	 * default for this graph.
	 * 
	 * @param e  the edge to be added for input.
	 * @param v1 the first vertex to be connected for input.
	 * @param v2 the second vertex to be connected for input.
	 * @return true if the add is successful, false otherwise for output.
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {

		if (containsVertex(v1) == false || containsVertex(v2) == false) {

			throw new IllegalArgumentException();

		}

		this.matrix[v1.id][v2.id] = e;

		return (true);

	}

	/**
	 * Method that adds vertex to this graph and fails if vertex is null or already
	 * in the graph.
	 * 
	 * @param vertex the vertex to add for input.
	 * @return true if the add is successful, and false otherwise for output.
	 * @throws IllegalArgumentException if vertex is null.
	 */
	public boolean addVertex(GraphNode vertex) {

		if (vertex == null) {

			throw new IllegalArgumentException();

		}

		if (vertex.id < 0 || vertex.id >= MAX_NUMBER_OF_NODES) {

			throw new IllegalArgumentException();
		}

		this.vertexList[vertex.id] = vertex;

		return (true);

	}

	/**
	 * Method that removes edge from this graph and fails if edge is null, or is
	 * otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove for input.
	 * @return true if the removal is successful, false otherwise for output.
	 */
	public boolean removeEdge(GraphEdge edge) {

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			for (int j = 0; j < MAX_NUMBER_OF_NODES; j++) {

				GraphEdge tempEdge = matrix[i][j];

				if (tempEdge == edge) {

					matrix[i][j] = null;

					return (true);

				}

			}

		}

		return (false);

	}

	/**
	 * Method that removes vertex from this graph and may remove any edges e
	 * incident to vertex.
	 * 
	 * @param vertex the vertex to remove for input
	 * @return true if the removal is successful, false otherwise for output.
	 */
	public boolean removeVertex(GraphNode vertex) {

		if (containsVertex(vertex) == false) {

			return (false);

		}

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			this.matrix[vertex.id][i] = null;

			this.matrix[i][vertex.id] = null;

		}

		this.vertexList[vertex.id] = null;

		return (true);

	}

	/**
	 * Method that returns a string of the depth first traversal of the graph.
	 * 
	 * @return a string representation of the depth first traversal, or an empty
	 *         string if the graph is empty for output.
	 */
	public String depthFirstTraversal() {

		StringBuilder strBuilder = new StringBuilder();

		boolean visitedNodes[] = new boolean[MAX_NUMBER_OF_NODES];

		for (int i = 0; i < MAX_NUMBER_OF_NODES; i++) {

			if (this.vertexList[i] != null && visitedNodes[i] == false) {

				helperRecursiveTraversal(i, visitedNodes, strBuilder);

			}

		}

		return (strBuilder.toString());

	}

	/**
	 * Private method to help with traversals.
	 * 
	 * @param currentNode  for input.
	 * @param visitedNodes for input.
	 * @param strBuilder   for input.
	 */
	private void helperRecursiveTraversal(int currentNode, boolean[] visitedNodes, StringBuilder strBuilder) {

		visitedNodes[currentNode] = true;

		strBuilder.append(strBuilder.toString().isEmpty() ? "" : " ");

		strBuilder.append(currentNode);

		int i = 0;

		while (i < MAX_NUMBER_OF_NODES) {

			if (matrix[currentNode][i] != null) {

				if (!visitedNodes[i]) {

					helperRecursiveTraversal(i, visitedNodes, strBuilder);

				}

			}

			i++;

		}

	}

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {

		GraphNode[] nodes = {
			new GraphNode(0),
			new GraphNode(1),
			new GraphNode(2),
			new GraphNode(3),
			new GraphNode(4),
			new GraphNode(5),
			new GraphNode(6),
			new GraphNode(7),
			new GraphNode(8),
			new GraphNode(9)
		};

		GraphEdge[] edges = {
			new GraphEdge(0),
			new GraphEdge(1),
			new GraphEdge(2),
			new GraphEdge(3),				
			new GraphEdge(4),
			new GraphEdge(5),
			new GraphEdge(6),
			new GraphEdge(7)
		};

		ThreeTenGraph graph = new ThreeTenGraph();
		for (GraphNode n : nodes) {
			graph.addVertex(n);
		}

		graph.addEdge(edges[0], nodes[0], nodes[1]);
		graph.addEdge(edges[1], nodes[1], nodes[2]);
		graph.addEdge(edges[2], nodes[3], nodes[6]);
		graph.addEdge(edges[3], nodes[6], nodes[7]);
		graph.addEdge(edges[4], nodes[8], nodes[9]);
		graph.addEdge(edges[5], nodes[9], nodes[0]);
		graph.addEdge(edges[6], nodes[2], nodes[7]);
		graph.addEdge(edges[7], nodes[1], nodes[8]);

		if (graph.getVertexCount() == 10 && graph.getEdgeCount() == 8) {
			System.out.println("Yay 1");
		}

		if (graph.inDegree(nodes[0]) == 1 && graph.outDegree(nodes[1]) == 2) {
			System.out.println("Yay 2");
		}

		if (graph.toString().trim().equals("0 1 2 7 8 9 3 6 4 5")) {
			System.out.println("Yay 3");
		}

	}

	/**
	 * Returns the number of edges incident to vertex.
	 * Special cases of interest:
	 * <ul>
	 * <li/>Incident self-loops are counted once.
	 * <li>If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li>If the graph is directed, then the value returned will be
	 * the sum of this vertex's indegree (the number of edges whose
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 * 
	 * <p>Equivalent to getIncidentEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(GraphNode vertex) {
		return inDegree(vertex) + outDegree(vertex);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(GraphNode v1, GraphNode v2) {
		return (findEdge(v1, v2) != null || findEdge(v2, v1) != null);
	}

	/**
	 * Returns the endpoints of edge as a Pair.
	 * 
	 * @param edge the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of edge
	 */
	public Pair<GraphNode> getEndpoints(GraphEdge edge) {

		if (edge == null)
			return null;

		GraphNode v1 = getSource(edge);
		if (v1 == null)
			return null;

		GraphNode v2 = getDest(edge);
		if (v2 == null)
			return null;

		return new Pair<>(v1, v2);
	}

	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return the collection of edges which are connected to vertex,
	 *         or null if vertex is not present
	 */
	public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.addAll(getInEdges(vertex));
		ret.addAll(getOutEdges(vertex));
		return ret;
	}

	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this
	 * collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may
	 * or may
	 * not be distinct). Implementations for those graph types may provide alternate
	 * methods
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned
	 * @return the collection of vertices which are connected to edge,
	 *         or null if edge is not present
	 */
	public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		LinkedList<GraphNode> ret = new LinkedList<>();
		ret.add(p.getFirst());
		ret.add(p.getSecond());
		return ret;
	}

	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * 
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(GraphEdge edge) {
		return (getEndpoints(edge) != null);
	}

	/**
	 * Returns the collection of edges in this graph which are of type edgeType.
	 * 
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edgeType, or
	 *         null if the graph does not accept edges of this type
	 * @see EdgeType
	 */
	public Collection<GraphEdge> getEdges(EdgeType edgeType) {
		if (edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of edges of type edgeType in this graph.
	 * 
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edgeType in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if (edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * 
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(GraphNode vertex) {
		return inDegree(vertex);
	}

	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * 
	 * @param vertex the vertex whose successor count is to be returned
	 * @return the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(GraphNode vertex) {
		return outDegree(vertex);
	}

	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * 
	 * @param vertex the vertex to be queried
	 * @param edge   the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
		if (getSource(edge).equals(vertex)) {
			return getDest(edge);
		} else if (getDest(edge).equals(vertex)) {
			return getSource(edge);
		} else
			return null;
	}

	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting
	 * v1 to v2), any of these edges
	 * may be returned. findEdgeSet(v1, v2) may be
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>
	 * Returns an empty collection if either v1 or v2 are not present in this graph.
	 * 
	 * <b>Note</b>: for purposes of this method, v1 is only considered to be
	 * connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if
	 * u is incident to both v1 and v2.)
	 * @param v1 for input.
	 * @param v2 for input.
	 * 
	 * @return a collection containing all edges that connect v1 to v2,
	 *         or null if either vertex is not present
	 * @see Hypergraph#findEdge(Object, Object)
	 */
	public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
		GraphEdge edge = findEdge(v1, v2);
		if (edge == null) {
			return null;
		}

		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.add(edge);
		return ret;

	}

	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * 
	 * @param vertex the vertex to be queried
	 * @param edge   the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(GraphNode vertex, GraphEdge edge) {
		return getSource(edge).equals(vertex);
	}

	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * 
	 * @param vertex the vertex to be queried
	 * @param edge   the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(GraphNode vertex, GraphEdge edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Adds edge e to this graph such that it connects
	 * vertex v1 to v2.
	 * Equivalent to addEdge.
	 * If this graph does not contain v1, v2,
	 * or both, implementations may choose to either silently add
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If edgeType is not legal for this graph, this method will
	 * throw IllegalArgumentException.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * 
	 * @param e        the edge to be added
	 * @param v1       the first vertex to be connected
	 * @param v2       the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
		// NOTE: Only directed edges allowed

		if (edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}

		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge for input.
	 * @param vertices for input.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null,
	 *                                  or if a different vertex set in this graph
	 *                                  is already connected by edge,
	 *                                  or if vertices are not a legal vertex set
	 *                                  for edge
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
		if (edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		GraphNode[] vs = (GraphNode[]) vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edgeType.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edgeType is not legal for this graph
	 * </ul>
	 * 
	 * @param edge for input.
	 * @param vertices for input.
	 * @param edgeType for input.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null,
	 *                                  or if a different vertex set in this graph
	 *                                  is already connected by edge,
	 *                                  or if vertices are not a legal vertex set
	 *                                  for edge
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
		if (edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		GraphNode[] vs = (GraphNode[]) vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}

	// ********************************************************************************
	// DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
	// ********************************************************************************

	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * 
	 * @param <V> for use of generics.
	 * @param <E> for use of generics.
	 * @return instance of graph type for output.
	 */

	public static <V, E> Factory<Graph<GraphNode, GraphEdge>> getFactory() {
		return new Factory<Graph<GraphNode, GraphEdge>>() {
			@SuppressWarnings("unchecked")
			public Graph<GraphNode, GraphEdge> create() {
				return (Graph<GraphNode, GraphEdge>) new ThreeTenGraph();
			}
		};
	}

	/**
	 * Returns the edge type of edge in this graph.
	 * 
	 * @param edge for input.
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(GraphEdge edge) {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted).
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * 
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(GraphEdge edge) {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return depthFirstTraversal();
	}

}
