import java.util.Date;

public class RecursiveMatrixMultiplication {

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
		C = MAT_MULT(A, B);
		long end_time = new Date().getTime();
		if(outFlag) outMatrix(C);
		System.out.println("Ellapsed time: " + (end_time - start_time) + "ms");
	}

	/* MAT-MULT Method */
	public static int[][] MAT_MULT(int[][] A, int[][] B){
		int n = A[0].length;
		int[][] C = new int[n][n];
		if(n == 1) C[0][0] = A[0][0] * B[0][0];
		else { // partition A, B, and C
			int[][] C11 = addMatrix(MAT_MULT(split(A, 0, 0), split(B, 0, 0)), MAT_MULT(split(A, 0, 1), split(B, 1, 0)));
			int[][] C12 = addMatrix(MAT_MULT(split(A, 0, 0), split(B, 0, 1)), MAT_MULT(split(A, 0, 1), split(B, 1, 1)));
			int[][] C21 = addMatrix(MAT_MULT(split(A, 1, 0), split(B, 0, 0)), MAT_MULT(split(A, 1, 1), split(B, 1, 0)));
			int[][] C22 = addMatrix(MAT_MULT(split(A, 1, 0), split(B, 0, 1)), MAT_MULT(split(A, 1, 1), split(B, 1, 1)));
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
		for(int i = 0; i < A[0].length; i++) 
			for(int j = 0; j < A[0].length; j++) 
				A[i][j] += B[i][j];
		return A;
	}
	
	/* Generate the matrix of given sizes */
	public static int[][] randomMatrix(int rows, int columns, int maxValue){
		int[][] matrix = new int[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				matrix[i][j] = (int) (Math.random() * maxValue);
		return matrix;
	}
}
