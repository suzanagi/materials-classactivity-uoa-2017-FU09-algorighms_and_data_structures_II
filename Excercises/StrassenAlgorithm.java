import java.util.Date;

public class StrassenAlgorithm {

	// Define the max value of the element of initial matrix
	public static final int MAXVALUE = 10;
	// Set whether it outs the generated matrix and result or not
	private static boolean outFlag = false;
	
	/* Main Method */
	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]);
		if(args.length == 2 && args[1].equals("-CHECK")) outFlag = true;
		int[][] A, B, C;
		A = randomMatrix(n, n, MAXVALUE);
		B = randomMatrix(n, n, MAXVALUE);
		if(outFlag) {
			System.out.println("Initial Matrixes:");
			System.out.println("Matrix A:");
			outMatrix(A);
			System.out.println("Matrix B:");
			outMatrix(B);
			System.out.println("Result of multiplied matrix C:");
		}
		
		long start_time = new Date().getTime();
		C = STRASSEN(A, B);
		long end_time = new Date().getTime();
		if(outFlag) outMatrix(C);
		System.out.println("Ellapsed time: " + (end_time - start_time) + "ms");
	}
	
	/* STRASSEN Method */
	public static int[][] STRASSEN(int[][] A, int[][] B){
		int n = A[0].length;
		int[][] C = new int[n][n];
		if(n == 1) C[0][0] = A[0][0] * B[0][0];
		else {
			int[][][] S = new int[10][n][n];
			/* Calculate the sum matrices */
			S[0] = subtractMatrix(split(B, 0, 1), split(B, 1, 1));
			S[1] = addMatrix(split(A, 0, 0), split(A, 0, 1));
			S[2] = addMatrix(split(A, 1, 0), split(A, 1, 1));
			S[3] = subtractMatrix(split(B, 1, 0), split(B, 0, 0));
			S[4] = addMatrix(split(A, 0, 0), split(A, 1, 1));
			S[5] = addMatrix(split(B, 0, 0), split(B, 1, 1));
			S[6] = subtractMatrix(split(A, 0, 1), split(A, 1, 1));
			S[7] = addMatrix(split(B, 1, 0), split(B, 1, 1));
			S[8] = subtractMatrix(split(A, 0, 0), split(A, 1, 0));
			S[9] = addMatrix(split(B, 0, 0), split(B, 0, 1));
			/* Calculate the product matrices */
			int[][][] P = new int[7][n][n];
			P[0] = STRASSEN(split(A, 0, 0), S[0]);
			P[1] = STRASSEN(S[1], split(B, 1, 1));
			P[2] = STRASSEN(S[2], split(B, 0, 0));
			P[3] = STRASSEN(split(A, 1, 1), S[3]);
			P[4] = STRASSEN(S[4], S[5]);
			P[5] = STRASSEN(S[6], S[7]);
			P[6] = STRASSEN(S[8], S[9]);
			/* Calculate the final product sub matrices */
			int[][] C11 = addMatrix(subtractMatrix(addMatrix(P[4], P[3]), P[1]), P[5]);
			int[][] C12 = addMatrix(P[0], P[1]);
			int[][] C21 = addMatrix(P[2], P[3]);
			int[][] C22 = subtractMatrix(subtractMatrix(addMatrix(P[0], P[4]), P[2]), P[6]);
			combine(C, C11, 0, 0);
			combine(C, C12, 0, 1);
			combine(C, C21, 1, 0);
			combine(C, C22, 1, 1);
		}
		return C;
	}

	/* Split the given matrix into quarter and return the particular splitted part of a dimensional array */
	public static int[][] split(int[][] matrix, int row, int column){
		int n = matrix[0].length;
		if(row != 0 && row != 1) throw new IllegalArgumentException("Row " + row + " is undefined in the method RecursiveMatrixMultiplication.split.");
		if(column != 0 && column != 1) throw new IllegalArgumentException("Column " + column + " is undefined in the method RecursiveMatrixMultiplication.split.");
		if(n % 2 == 1) throw new IllegalArgumentException("The number fo given matrix's row and column must be even in the method RecursiveMatrixMultiplication.split.");  
		int[][] result = new int[n / 2][n / 2];
		for(int i = 0; i < n / 2; i++)
			for(int j = 0; j < n / 2; j++)
				result[i][j] = matrix[i + row * (n / 2)][j + column * (n / 2)];
		return result;
	}
	
	/* Combine the second matrix into the first matrix by the particular splitted part of a dimensional array */
	public static int[][] combine(int[][] matrix, int[][] subMatrix, int row, int column) {
		int n = matrix[0].length;
		if(row != 0 && row != 1) throw new IllegalArgumentException("Row " + row + " is undefined in the method RecursiveMatrixMultiplication.split.");
		if(column != 0 && column != 1) throw new IllegalArgumentException("Column " + column + " is undefined in the method RecursiveMatrixMultiplication.split.");
		int[][] result = matrix.clone();
		for(int i = 0; i < n / 2; i++)
			for(int j = 0; j < n / 2; j++)
				result[i + row * (n / 2)][j + column * (n / 2)] = subMatrix[i][j];
		return result;
	}
	
	/* Output the given matrix to the standard output */
	public static void outMatrix(int[][] matrix) {
		for(int i = 0; i < matrix[0].length; i++) {
			for(int j : matrix[i]) System.out.print(j + " ");
			System.out.println();
		}
	}
	
	/* Add two given matrix and returns the result */
	public static int[][] addMatrix(int[][] A, int[][] B){
		if(A[0].length != B[0].length) throw new IllegalArgumentException("The length of A and B is different in addMatrix method.");
		int[][] C = copy(A);
		for(int i = 0; i < A[0].length; i++) 
			for(int j = 0; j < A[0].length; j++) 
				C[i][j] = A[i][j] + B[i][j];
		if(C == A) System.out.println();
		return C;
	}
	
	/* Subtract second given matrix from the first matrix and returns the result */
	public static int[][] subtractMatrix(int[][] A, int[][] B){
		if(A[0].length != B[0].length) throw new IllegalArgumentException("The length of A and B is different in addMatrix method.");
		int[][] C = copy(A);
		for(int i = 0; i < A[0].length; i++)
			for(int j = 0; j < A[0].length; j++)
				C[i][j] = A[i][j] - B[i][j];
		return C;
	}
	
	/* Generate the matrix of given sizes */
	public static int[][] randomMatrix(int rows, int columns, int maxValue){
		int[][] matrix = new int[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				matrix[i][j] = (int) (Math.random() * maxValue);
		return matrix;
	}
	
	/* Copy matrix and return the copy */
	public static int[][] copy(int[][] matrix){
		int n = matrix[0].length;
		int[][] copy = new int[n][n];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				copy[i][j] = matrix[i][j];
		return copy;
	}
}
