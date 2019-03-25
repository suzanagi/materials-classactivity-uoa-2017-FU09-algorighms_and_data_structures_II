import java.util.ArrayList;
import java.util.Iterator;

public class LinearCongruentialGenerator {

	/* RAND1 Method */
	public static ArrayList<Integer> RAND1(int n) {	// n - Number of random integers
		int x = 53402397;	// Seed
		ArrayList<Integer> rand_seq = new ArrayList<Integer>();	// Empty list
		for(int i = 1; i <= n; i++) {
			x = 65539 * x + 125654;
			if(x < 0) {	// Check for overflow
				x += 2147483647;		// +(M-1)
				x += 1;		
			}
			rand_seq.add(x);
		}
		return rand_seq;
	}
	
	/* RAND2 Method */
	public static ArrayList<Integer> RAND2(int n){	// n - Number of random integers
		int x = 1;	// Seed
		int A = 48271, M = 2147483647;
		int Q = M / A, R = M % A;
		ArrayList<Integer> rand_seq = new ArrayList<Integer>();
		for(int i = 1; i <= n; i++) {
			x = A * (x % Q) - R * (x / Q);
			if(x < 0)
				x += M;
			rand_seq.add(x);
		}
		return rand_seq;
	}
	
	/* RAND3 Method */
	public static ArrayList<Integer> RAND3(int n){	// n - Number of random integers
		int x = 1, next = 0;
		ArrayList<Integer> A = RAND2(55);
		ArrayList<Integer> rand_seq = new ArrayList<Integer>();	// Empty list
		for(int i = 1; i <= n; i++) {
			int j = (next + 31) % 55;
			x = A.get(j) - A.get(next);
			if(x < 0) {
				x += 2147483647; x+= 1;
			}
			A.set(next, x);
			next = (next + 1) % 55;
			rand_seq.add(x);
		}
		return rand_seq;
	}
	
	/* Main Method */
	public static void main(String[] args) {
		System.out.println("<Problem 2-a, 5 random numbers>");
		testRand();
		System.out.println("\n<Problem 2-b, Histogram of each random method>");
		testHistogram();
	}
	
	/* Return the sample double array list */
	public static ArrayList<Double> sample(){
		ArrayList<Double> sample = new ArrayList<Double>();
		sample.add(0.35);
		sample.add(0.25);
		sample.add(0.32);
		sample.add(0.28);
		sample.add(0.21);
		return sample;
	}
	
	/* Test the each random method(It corresponds to the Problem2, a).) */
	public static void testRand() {
		int testNum = 5;
		System.out.print("Rand1: ");
		OutputModule.outSeq(RAND1(testNum));
		System.out.print("Rand2: ");
		OutputModule.outSeq(RAND2(testNum));
		System.out.print("Rand3: ");
		OutputModule.outSeq(RAND3(testNum));
	}
	
	/* Test the each random method, store into histogram, and out(It corresponds to the Problem2, b).) */
	public static void testHistogram() {
		OutputModule.outDistributionHead();
		Histogram histogram;
		// Part of the method RAND1
		System.out.println("Rand1");
		histogram = new Histogram(10);
		histogram.append(fall(RAND1(10)));
		histogram.printDistribution();
		histogram = new Histogram(1000);
		histogram.append(fall(RAND1(1000)));
		histogram.printDistribution();
		histogram = new Histogram(1000000);
		histogram.append(fall(RAND1(1000000)));
		histogram.printDistribution();
		// Part of the method RAND2
		System.out.println("Rand2");
		histogram = new Histogram(10);
		histogram.append(fall(RAND2(10)));
		histogram.printDistribution();
		histogram = new Histogram(1000);
		histogram.append(fall(RAND2(1000)));
		histogram.printDistribution();
		histogram = new Histogram(1000000);
		histogram.append(fall(RAND2(1000000)));
		histogram.printDistribution();
		// Part of the method RAND3
		System.out.println("Rand3");
		histogram = new Histogram(10);
		histogram.append(fall(RAND3(10)));
		histogram.printDistribution();
		histogram = new Histogram(1000);
		histogram.append(fall(RAND3(1000)));
		histogram.printDistribution();
		histogram = new Histogram(1000000);
		histogram.append(fall(RAND3(1000000)));
		histogram.printDistribution();
	}
	
	/* Make the elements in the double array list falling into the range 0 to 1 */
	public static ArrayList<Double> fall(ArrayList<Integer> list) {
		ArrayList<Double> fallen = new ArrayList<Double>();
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) fallen.add((double)(it.next()) / (double)Integer.MAX_VALUE);
		return fallen;
	}
	
	/* Sub class Histogram that contains the sections 0 to 1 devided by 10 (like 0.0~0.1, 0.1~0.2) */
	private static class Histogram extends ArrayList<ArrayList<Double>> {
		// The total number of elements added to this histogram
		private int elements = 0;
		// The number of sections in this histogram
		private final int sections = 10;
		/* Constructor(Argument n is the number of sections) */
		public Histogram(int n) {
			for(int i = 0; i < this.sections; i++) this.add(new ArrayList<Double>());
		}
		/* Add the given element to the appropriate section */
		private void add(double d) {
			elements++;
			if(d == 1.0) this.get(sections - 1).add(d);
			else this.get((int) (d / 0.1)).add(d);
		}
		/* Append the given Double list to this Histogram */
		public void append(ArrayList<Double> list) {
			for(double d : list) {
				if(d < 0.0 || 1.0 < d) throw new IllegalArgumentException("double value less than 0 or larger than 1 is given to the Histgram.add method.");
				this.add(d);
			}
		}
		/* Print the number distribution in % for 10 intervals of this Histogram */
		public void printDistribution() {
			System.out.print(this.elements + "\t");
			for(int i = 0; i < sections; i++)
				System.out.printf("%.2f%%\t", (((double)this.get(i).size() / (double)this.elements) * 100));
//				System.out.print((((double)this.get(i).size() / (double)this.elements) * 100) + "%\t");
			System.out.println();
		}
	}
	
	/* Sub class OutputModule */
	public static class OutputModule {
		public static void outSeq(ArrayList<Integer> list) {
			for(int i : list) System.out.print(i + " ");
			System.out.println();
		}
		public static void outDistributionHead() {
			System.out.print("N\t");
			for(double d = 0.0; d < 0.9; d += 0.1) 
				System.out.printf("%.1f-%.1f\t", d, d+0.1);
			System.out.println();
		}
	}

}

