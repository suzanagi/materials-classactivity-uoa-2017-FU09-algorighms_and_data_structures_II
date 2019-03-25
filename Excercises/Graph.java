import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

	// List of Nodes
	private NodeList nodes = new NodeList();
	// ArrayList of WeightedDirectedEdge
	private ArrayList<WeightedDirectedEdge> edges = new ArrayList<WeightedDirectedEdge>();
	
	/* Constructor */
	public Graph() {
		InputModule inMod = new InputModule(this.nodes, this.edges);
	}
	
	/* Returns the MinimumSpanningTree */
	public ArrayList<WeightedDirectedEdge> getMST(){
		KruskalsAlgorithm ka = new KruskalsAlgorithm();
		ArrayList<WeightedDirectedEdge> result = new ArrayList<WeightedDirectedEdge>();
		result.addAll(ka.MST_KRUSKAL(this.edges, this.nodes));
		return result;
	}
	
	/* Output the given ArrayList */
	public void outList(ArrayList<WeightedDirectedEdge> argEdge) {
		OutputModule outMod = new OutputModule(argEdge);
		outMod.output();
	}
	
	/* Main Method */
	public static void main(String[] args) {
		Graph graph = new Graph();
		System.out.println("<Edges in the Minimum Spanning Tree of this graph>");
		graph.outList(graph.getMST());
	}
	
	/* Sub class KruskalsAlgorithm */
	class KruskalsAlgorithm{
		// HashSet A for making MST
		private HashSet<WeightedDirectedEdge> A;
		// HashSet vertices for storing the set of set of nodes
		private HashSet<HashSet<Node>> setOfSetOfNode;
		/* Constructor */
		public KruskalsAlgorithm() {
			
		}
		/* MST-KRUSKAL Method */
		public HashSet<WeightedDirectedEdge> MST_KRUSKAL(ArrayList<WeightedDirectedEdge> edges, NodeList nodes) {
			// Initialize the Set A
			this.A = new HashSet<WeightedDirectedEdge>();
			// Initialize the Set setOfSetOfNode
			this.setOfSetOfNode = new HashSet<HashSet<Node>>();
			// Make set of vertices and store it to setOfSetOfNode
			for(int i = 0; i < nodes.size(); i++) this.setOfSetOfNode.add(this.MAKE_SET(nodes.get(i)));
			// Sort edges into non-decreasing order by weight
			List<WeightedDirectedEdge> sortedEdges = edges.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
			Iterator<WeightedDirectedEdge> eit = sortedEdges.iterator();
			while(eit.hasNext()) {
				WeightedDirectedEdge nextEdge = eit.next();
				if(!(this.FIND_SET(nextEdge.getOrigin()) == this.FIND_SET(nextEdge.getDestination()))) {
					A.add(nextEdge);
					this.UNION(nextEdge.getOrigin(), nextEdge.getDestination());
				}
			}
			return A;
		}
		/* UNION Method */
		public void UNION(Node u, Node v) {
			HashSet<Node> uSet = this.FIND_SET(u);
			HashSet<Node> vSet = this.FIND_SET(v);
			uSet.addAll(vSet);
			vSet.clear();
			this.setOfSetOfNode.remove(vSet);
		}
		/* FIND-SET Method */
		public HashSet<Node> FIND_SET(Node vertice){
			Iterator<HashSet<Node>> nit = this.setOfSetOfNode.iterator();
			while(nit.hasNext()) {
				HashSet<Node> nextSet = nit.next();
				if(nextSet.contains(vertice)) return nextSet;
			}
			System.err.println("Set of Node " + vertice.getName() + "isn't generated!!");
			return null;
		}
		/* MAKE-SET Method */
		public HashSet<Node> MAKE_SET(Node v){
			HashSet<Node> result = new HashSet<Node>();
			result.add(v);
			return result;
		}
	}
	
	/* Sub class DirectedEdge */
	class DirectedEdge{
		// Keep the Origin Node
		private Node origin;
		// Keep the Destination Node
		private Node destination;
		/* Constructor */
		public DirectedEdge(Node from, Node to) {
			this.origin = from;
			this.destination = to;
		}
		/* Return the Origin Node of this Edge */
		public Node getOrigin() {
			return this.origin;
		}
		/* Return the Destination Node of this Edge */
		public Node getDestination() {
			return this.destination;
		}
	}
	
	/* Sub class WeightedDirectedEdge */
	class WeightedDirectedEdge extends DirectedEdge implements Comparable<WeightedDirectedEdge>{
		// Keep the weight of this Edge
		private double weight;
		/* Constructor */
		public WeightedDirectedEdge(Node from, Node to, double weight) {
			super(from, to);
			this.weight = weight;
		}
		/* Return the value of weight */
		public double getWeight() {
			return this.weight;
		}
		@Override
		public int compareTo(WeightedDirectedEdge o) {
			return (int) (this.weight - o.weight);
		}
	}
	
	/* Sub class Node */
	class Node{
		// Keep the name of this object
		private String name;
		/* Constructor */
		public Node(String name) {
			this.name = name;
		}
		/* Return the name of this Node */
		public String getName() {
			return this.name;
		}
	}
	
	/* Sub class NodeList */
	class NodeList extends ArrayList<Node>{
		/* Override the contains method to return result by searching the name */
		public boolean contains(String name) {
			Iterator<Node> it = this.iterator();
			while(it.hasNext())
				if(it.next().getName().equals(name)) return true;
			return false;
		}
		/* Return the element designated by the name in argument */
		public Node get(String name) {
			Iterator<Node> it = this.iterator();
			while(it.hasNext()) {
				Node next = it.next();
				if(next.getName().equals(name)) return next;
			}
			return null;
		}
	}
	
	/* Sub class InputModule */
	class InputModule{
		// Keep the object of given ArrayList
		private NodeList nodes;
		private ArrayList<WeightedDirectedEdge> edges;
		private final String endWord = "End";
		
		/* Constructor */
		public InputModule(NodeList nodes, ArrayList<WeightedDirectedEdge> edges) {
			this.nodes = nodes;
			this.edges = edges;
			this.getInputOfEdge();
		}
		
		/* Operate the ArrayList of Node and Edge given in the constructor */
		public void getInputOfEdge(){
			// Prepare BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			// Output the message
			System.out.println("Please enter the information of directed edges in this graph in order of \"Origin-Node Destination-Node weight\"");
			System.out.println("Example: a b 1 (It means the directed edge from node a to node b has the weight 1.)");
			System.out.println("(Enter the string \"" + this.endWord + "\" to finish the input operation):");
			while(true) {
				// Declare the variable for storing the inputed line temporary.
				String loadLine;
				// Read a line.
				try { loadLine = br.readLine();} catch (IOException e) {e.printStackTrace();break;}
				// Store it in the array of String.
				String[] lineElement = loadLine.split(" ", 0);
				// If some element is match to the word "End", finish the input operation.
				if(lineElement[0].equals(this.endWord)) break;
				if(lineElement[1].equals(this.endWord)) break;
				if(lineElement[2].equals(this.endWord)) break;
				// If the nodeList doesn't contain the particular node, add it.
				if(!this.nodes.contains(lineElement[0])) this.nodes.add(new Node(lineElement[0]));
				if(!this.nodes.contains(lineElement[1])) this.nodes.add(new Node(lineElement[1]));
				// Generate new Edge and store it in the ArrayList
				this.edges.add(new WeightedDirectedEdge(this.nodes.get(lineElement[0]), this.nodes.get(lineElement[1]), Double.valueOf(lineElement[2])));
			}
		}
	}

	/* Sub class OutputModule */
	class OutputModule{
		// Keep the given ArrayList of Edge
		private ArrayList<WeightedDirectedEdge> outEdges;
		/* Constructor */
		public OutputModule(ArrayList<WeightedDirectedEdge> edges) {
			this.outEdges = edges;
		}
		/* Output the content of ArrayList of edges */
		public void output() {
			Iterator<WeightedDirectedEdge> eit = this.outEdges.iterator();
			while(eit.hasNext()) {
				WeightedDirectedEdge wde = eit.next();
				System.out.println("Edge from the Node " + wde.getOrigin().getName() + " to the Node " + wde.getDestination().getName() + " with weight " + wde.getWeight());
			}
		}
	}

}
