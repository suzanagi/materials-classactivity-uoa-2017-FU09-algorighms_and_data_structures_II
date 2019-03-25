public class Warshall_Graph {

	// Number of Vertices in this Graph
	public final int numberOfVertices = 8;
	// Connection Matrix in this Graph(If there's a connection between two nodes, it's true. Else, false.
	private boolean[][] connectionMatrix = new boolean[numberOfVertices][numberOfVertices];
	
	/* Main Method */
	public static void main(String[] args) {
		// Declare the Graph object
		Warshall_Graph graph = new Warshall_Graph();
		// Initialize the Graph object's connection matrix by following the given graph of Problem 1
		graph.initialize(graph);
		// Prepare the dimensional array for transitive closure
		boolean[][] transitive;
		// Get the transitive closure array by processing Warshall's algorithm for the graph
		transitive = graph.WARSHALL(graph);
		// Output and check the transitive closure
		graph.outArray(transitive);
	}
	
	/* Initialize the value of connection matrix by following the given graph of Problem 1 */
	public void initialize(Warshall_Graph G) {
		for(int i = 0; i < numberOfVertices; i++)
			for(int j = 0; j < numberOfVertices; j++)
				G.connectionMatrix[i][j] = false;
		G.connectionMatrix[0][1] = true; // a-b
		G.connectionMatrix[0][4] = true; // a-e
		G.connectionMatrix[0][5] = true; // a-f
		G.connectionMatrix[0][7] = true; // a-h
		G.connectionMatrix[1][2] = true; // b-c
		G.connectionMatrix[1][6] = true; // b-g
		G.connectionMatrix[2][3] = true; // c-d
		G.connectionMatrix[2][6] = true; // c-g
		G.connectionMatrix[3][5] = true; // d-f
		G.connectionMatrix[4][2] = true; // e-c
		G.connectionMatrix[4][5] = true; // e-f
		G.connectionMatrix[5][6] = true; // f-g
		G.connectionMatrix[6][7] = true; // g-h
		G.connectionMatrix[7][6] = true; // h-g
		G.connectionMatrix[7][5] = true; // h-f
	}
	
	/* WARSHALL Method */
	public boolean[][] WARSHALL(Warshall_Graph G){
		int n = G.numberOfVertices;
		boolean[][][] t = new boolean[n][n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i == j || G.connectionMatrix[i][j])
					t[0][i][j] = true;
				else t[0][i][j] = false;
			}
		}
		for(int k = 1; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					t[k][i][j] = t[k - 1][i][j] || (t[k - 1][i][k] && t[k - 1][k][j]);
				}
			}
		}
		return t[n - 1];
	}
	
	/* Output the given dimensional array */
	public void outArray(boolean[][] matrix) {
		System.out.println("Transitive Closure G*:");
		System.out.println("  a b c d e f g h");
		for(int i = 0; i < numberOfVertices; i++) {
			switch(i){
			case 0:
				System.out.print("a ");
				break;
			case 1:
				System.out.print("b ");
				break;
			case 2:
				System.out.print("c ");
				break;
			case 3:
				System.out.print("d ");
				break;
			case 4:
				System.out.print("e ");
				break;
			case 5:
				System.out.print("f ");
				break;
			case 6:
				System.out.print("g ");
				break;
			case 7:
				System.out.print("h ");
				break;
			default:
				System.out.print("Something Wrong!");	
			}
			for(int j = 0; j < numberOfVertices; j++) {
				if(matrix[i][j]) System.out.print("1 ");
				else System.out.print("0 ");
			}
			System.out.println();
		}
	}

}
