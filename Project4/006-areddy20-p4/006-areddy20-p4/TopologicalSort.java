import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.awt.Color;
import javax.swing.JPanel;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * This is the TopologicalSort class.
 * 
 * @author Aditi Reddy.
 */
class TopologicalSort implements ThreeTenAlg {
	/**
	 * The graph the algorithm will run on.
	 */
	Graph<GraphNode, GraphEdge> graph;

	/**
	 * The priority queue of nodes for the algorithm.
	 */
	WeissPriorityQueue<GraphNode> pqueue;

	/**
	 * The sorted list of nodes for the algorithm.
	 */
	LinkedList<GraphNode> queue;

	/**
	 * Whether or not the algorithm has been started.
	 */
	private boolean started = false;

	/**
	 * The max rank that has been assigned in the current sorting.
	 */
	private int maxRank;

	/**
	 * The color when a node has "no color".
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;

	/**
	 * The color when an edge has "no color".
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;

	/**
	 * The color when a node is inactive.
	 */
	public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;

	/**
	 * The color when an edge is inactive.
	 */
	public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;

	/**
	 * The color when a node is highlighted.
	 */
	public static final Color COLOR_HIGHLIGHT = new Color(255, 204, 51);

	/**
	 * The color when a node is in warning.
	 */
	public static final Color COLOR_WARNING = new Color(255, 51, 51);

	/**
	 * {@inheritDoc}
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.DIRECTED;
	}

	/**
	 * {@inheritDoc}
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		queue = null;
		pqueue = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * {@inheritDoc}
	 */
	public void start() {
		started = true;

		// create an empty list
		queue = new LinkedList<>();

		// create an empty priority queue
		pqueue = new WeissPriorityQueue<>();

		// no nodes sorted yet
		maxRank = -1;

		for (GraphNode v : graph.getVertices()) {

			// clear rank;
			v.setRank(-1);

			// Set the cost of each node to be its degree
			v.setCost(graph.inDegree(v));

			// Set each node to be active
			// This enables the display of cost for the node
			v.setActive();

			// add node into priority queue
			pqueue.add(v);
		}

		// highlight the node with best priority
		highlightNext();

	}

	/**
	 * {@inheritDoc}
	 */
	public void finish() {

		// Sorting completed. Set all edges back to "no color".
		for (GraphEdge e : graph.getEdges()) {
			e.setColor(COLOR_NONE_EDGE);
		}

		// Set all sorted nodes back to "no special color".
		for (GraphNode v : graph.getVertices()) {
			if (v.color.equals(COLOR_INACTIVE_NODE))
				v.setColor(COLOR_NONE_NODE);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void cleanUpLastStep() {
		// Unused. Required by the interface.
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean setupNextStep() {

		// no more nodes, done with simulation.
		if (pqueue.size() == 0) {
			return false;
		}

		// Return true to indicate more steps to continue.
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void doNextStep() {

		// find and process next node
		GraphNode minNode = selectNext();

		// update successor info as needed
		updateSuccessorCost(minNode);

		// highlight next node with best priority
		highlightNext();

	}

	/**
	 * Method that removes the node with the best priority from the priority queue.
	 * Cost of node will also be checked.
	 * 
	 * @return min node or null for output.
	 */
	public GraphNode selectNext() {

		if (this.pqueue.isEmpty()) {

			return (null);

		}

		GraphNode minNode = pqueue.element();

		pqueue.remove();

		if (minNode.getCost() > 0) {

			minNode.setColor(COLOR_WARNING);

		}

		else {
			minNode.setRank(queue.size());

			queue.add(minNode);

			minNode.unsetActive();

			minNode.setColor(COLOR_INACTIVE_NODE);

		}

		return (minNode);

	}

	/**
	 * Method that updates the cost for all active neighbor nodes if minNode has
	 * cost of 0.
	 * edges conncecting to minNode and each active neighbor to OLOR_INACTIVE_EDGE
	 * will be set.
	 * 
	 * @param minNode for input.
	 */
	public void updateSuccessorCost(GraphNode minNode) {

		if (minNode.getCost() == 0) {

			GraphNode[] successors = graph.getSuccessors(minNode).toArray(new GraphNode[0]);

			for (int i = 0; i < successors.length; i++) {

				GraphNode successorNode = successors[i];

				int cost = successorNode.getCost();

				successorNode.setCost(cost - 1);

				GraphEdge edge = graph.findEdge(minNode, successorNode);

				if (edge != null) {

					edge.setColor(COLOR_INACTIVE_EDGE);

				}

				pqueue.update(successorNode);

			}

		}

	}

	/**
	 * Method that finds the current min node in the priority queue and changes the
	 * color of the node to be COLOR_HIGHLIGHT.
	 */
	public void highlightNext() {

		GraphNode element = null;

		if (!this.pqueue.isEmpty()) {

			element = this.pqueue.element();

		}

		if (element != null) {

			element.setColor(COLOR_HIGHLIGHT);

		}

	}

	/**
	 * Method that returns false if sorting has already started.
	 * If not started, all edges will removed.
	 * 
	 * @return true or false for output.
	 */
	public boolean simplify() {

		if (pqueue != null) {

			return (false);

		}

		LinkedList<GraphNode> nodeList = new LinkedList<>(graph.getVertices());

		for (int i = 0; i < nodeList.size() - 1; i++) {

			for (int j = i + 1; j < nodeList.size(); j++) {

				if (nodeList.get(i).getId() > nodeList.get(j).getId()) {

					GraphNode temp = nodeList.get(i);

					nodeList.set(i, nodeList.get(j));

					nodeList.set(j, temp);

				}

			}

		}

		for (int i = 0; i < nodeList.size(); i++) {

			GraphNode currentNode = nodeList.get(i);

			for (int j = i + 1; j < nodeList.size(); j++) {

				GraphNode targetNode = nodeList.get(j);

				GraphEdge edge = graph.findEdge(currentNode, targetNode);

				if (edge != null) {

					graph.removeEdge(edge);

				}

			}

		}

		return (true);

	}

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {
		ThreeTenGraph graph = new ThreeTenGraph();
		TopologicalSort topSort = new TopologicalSort();

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
			new GraphEdge(0), new GraphEdge(1), new GraphEdge(2), new GraphEdge(3), new GraphEdge(4),
			new GraphEdge(5),
			new GraphEdge(6), new GraphEdge(7), new GraphEdge(8), new GraphEdge(9), new GraphEdge(10),
			new GraphEdge(11),
			new GraphEdge(12), new GraphEdge(13), new GraphEdge(14), new GraphEdge(15), new GraphEdge(16),
			new GraphEdge(17),
			new GraphEdge(18), new GraphEdge(19), new GraphEdge(20), new GraphEdge(21), new GraphEdge(22),
			new GraphEdge(23),
			new GraphEdge(24), new GraphEdge(25), new GraphEdge(26), new GraphEdge(27), new GraphEdge(28),
			new GraphEdge(29),
			new GraphEdge(30), new GraphEdge(31), new GraphEdge(32), new GraphEdge(33), new GraphEdge(34),
			new GraphEdge(35),
			new GraphEdge(36), new GraphEdge(37), new GraphEdge(38), new GraphEdge(39), new GraphEdge(40),
			new GraphEdge(41),
			new GraphEdge(42), new GraphEdge(43), new GraphEdge(44), new GraphEdge(45), new GraphEdge(46),
			new GraphEdge(47),
			new GraphEdge(48), new GraphEdge(49), new GraphEdge(50), new GraphEdge(51), new GraphEdge(52),
			new GraphEdge(53),
			new GraphEdge(54), new GraphEdge(55), new GraphEdge(56), new GraphEdge(57), new GraphEdge(58),
			new GraphEdge(59),
			new GraphEdge(60), new GraphEdge(61), new GraphEdge(62), new GraphEdge(63), new GraphEdge(64),
			new GraphEdge(65)
		};

		graph.addVertex(nodes[0]);
		graph.addVertex(nodes[1]);

		graph.addEdge(edges[0], nodes[0], nodes[1]);

		topSort.reset(graph);
		while (topSort.step()) {
		}

		if (nodes[1].getRank() == 1 && nodes[0].getRank() == 0)
			System.out.println("pass one edge!");

		graph = new ThreeTenGraph();

		graph.addVertex(nodes[0]);
		graph.addVertex(nodes[1]);
		graph.addVertex(nodes[2]);
		graph.addVertex(nodes[3]);
		graph.addVertex(nodes[4]);
		graph.addVertex(nodes[5]);

		graph.addEdge(edges[0], nodes[5], nodes[2]);
		graph.addEdge(edges[1], nodes[5], nodes[0]);
		graph.addEdge(edges[2], nodes[4], nodes[0]);
		graph.addEdge(edges[3], nodes[4], nodes[1]);
		graph.addEdge(edges[4], nodes[2], nodes[3]);
		graph.addEdge(edges[5], nodes[3], nodes[1]);

		topSort.reset(graph);
		while (topSort.step()) {
		}

		if (nodes[4].getRank() == 0 && nodes[5].getRank() == 1 && nodes[0].getRank() == 2
				&& nodes[2].getRank() == 3 && nodes[3].getRank() == 4 && nodes[1].getRank() == 5)
			System.out.println("pass six edges!");

	}

}