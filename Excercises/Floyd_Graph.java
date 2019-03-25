public class Floyd_Graph {
	
	// Number of vertices in the graph
	public static final int numberOfVertices = 8;
	// Define the value of Infinity in this program
	public static final int INFINITY = Integer.MAX_VALUE / 2;
	
	/* Main Method */
	public static void main(String[] args) {
		int[][] shortestPaths = FLOYD(adjacencyMatrixOfProblem2());
		outArray(shortestPaths);
	}
	
	/* FLOYD Method */
	public static int[][] FLOYD(int[][] W){
		// Given weight matrix W, returns APSP matrix D (n)
		int n = Floyd_Graph.numberOfVertices;
		int[][][] D = new int[n][n][n];
		D[0] = W;
		for(int k = 1; k < n; k++) 
			for(int i = 0; i < n; i++) 
				for(int j = 0; j < n; j++) 
					D[k][i][j] = Floyd_Graph.min(D[k - 1][i][j], D[k - 1][i][k] + D[k - 1][k][j]);	
		return D[n - 1];
	}
	
	/* Return the weight matrix W created by following the weight adjacency matrix in Problem 2 */
	public static int[][] adjacencyMatrixOfProblem2(){
		int[][] W = new int[Floyd_Graph.numberOfVertices][Floyd_Graph.numberOfVertices];
		for(int i = 0; i < Floyd_Graph.numberOfVertices; i++) {
			for(int j = 0; j < Floyd_Graph.numberOfVertices; j++) {
				if(i == j) W[i][j] = 0;
				else W[i][j] = Floyd_Graph.INFINITY;
			}
		}
		W[0][1] = 48; // a-b:48
		W[0][3] = 8;  // a-d:8
		W[0][4] = 20; // a-e:20
		W[0][6] = 20; // a-g:20
		W[1][2] = 24; // b-c:24
		W[1][4] = 9;  // b-e:9
		W[1][6] = 76; // b-g:76
		W[1][7] = 29; // b-h:29
		W[2][0] = 97; // c-a:97
		W[2][6] = 18; // c-g:18
		W[2][7] = 1;  // c-h:1
		W[3][1] = 52; // d-b:52
		W[3][2] = 34; // d-c:34
		W[3][4] = 29; // d-e:29
		W[4][5] = 10; // e-f:10
		W[5][1] = 10; // f-b:10
		W[5][2] = 85; // f-c:85
		W[5][3] = 43; // f-d:43
		W[5][6] = 41; // f-g:41
		W[5][7] = 29; // f-h:29
		W[6][3] = 76; // g-d:76
		W[6][4] = 38; // g-e:38
		W[7][0] = 28; // h-a:28
		W[7][1] = 42; // h-b:42
		W[7][3] = 77; // h-d:77
		W[7][4] = 21; // h-e:21
		W[7][6] = 11; // h-g:11
		return W;
	}
	
	/* min Method that returns the minimum value of two values given */
	public static int min(int a, int b) {
		if(a < b) return a;
		else return b;
	}
	
	/* Output the given dimensional array */
	public static void outArray(int[][] matrix) {
		System.out.println("All pairs shortest paths calculated by Floyd's algorithm:");
		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
		for(int i = 0; i < numberOfVertices; i++) {
			switch(i){
			case 0:
				System.out.print("a\t");
				break;
			case 1:
				System.out.print("b\t");
				break;
			case 2:
				System.out.print("c\t");
				break;
			case 3:
				System.out.print("d\t");
				break;
			case 4:
				System.out.print("e\t");
				break;
			case 5:
				System.out.print("f\t");
				break;
			case 6:
				System.out.print("g\t");
				break;
			case 7:
				System.out.print("h\t");
				break;
			default:
				System.out.print("Something Wrong!");	
			}
			for(int j = 0; j < numberOfVertices; j++) {
				if(matrix[i][j] == Floyd_Graph.INFINITY) System.out.print("∞\t");
				else System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

}
