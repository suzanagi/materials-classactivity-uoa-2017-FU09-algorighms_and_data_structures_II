public class RandomizedAlgorithms {

	// The value that multiply for the value N
	public static int MulN = 10;
	// The maximum value of N
	public static int MaxN = 100000;
	// How many times you'd like to try PI
	public static final int repeat = 10;
	
	/* PI Method */
	public static double PI(int N) {
		double inCircle = 0;
		for(int i = 1; i <= N; i++) {
			double x = Math.random();
			double y = Math.random();
			double d = (x - 0.5) * (x - 0.5) + (y - 0.5) * (y - 0.5);
			if(d < 0.25)
				inCircle++;
		}
		return 4 * inCircle / N;
	}
	
	/* Main Method */
	public static void main(String[] args) {
		System.out.print("N\t");
		for(int i = 1; i <= repeat; i++)
			System.out.print(i + "\t");
		System.out.println();
		for(int i = 1; i <= MaxN; i *= MulN) {
			System.out.print(i + "\t");
			for(int j = 0; j < repeat; j++)
				System.out.print(PI(i) + "\t");
			System.out.println();
		}
	}
	
}
