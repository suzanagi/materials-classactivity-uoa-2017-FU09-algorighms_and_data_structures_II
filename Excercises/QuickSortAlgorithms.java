import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class QuickSortAlgorithms {

	public static final int N = 1000;
	
	/* Randomized quick sort method */
	public static void RandomizedQuicksort(ArrayList<Integer> S) {
		if(S.size() == 0) return;
		int ai = S.get((int) (Math.random() * S.size()));
		int aiNum = 0;
		ArrayList<Integer> SMinus = new ArrayList<Integer>(), SPlus = new ArrayList<Integer>();
		for(int a : S) {
			if(a < ai) SMinus.add(a);
			if(a > ai) SPlus.add(a);
			if(a == ai) aiNum++;
		}
		RandomizedQuicksort(SMinus);
		for(int i = 0; i < aiNum; i++) System.out.print(ai + " ");
		RandomizedQuicksort(SPlus);
	}
	
	/* Deterministic quick sort method */
	public static void DeterministicQuicksort(ArrayList<Integer> S) {
		if(S.size() == 0) return;
		int ai = S.get(0);
		int aiNum = 0;
		ArrayList<Integer> SMinus = new ArrayList<Integer>(), SPlus = new ArrayList<Integer>();
		for(int a : S) {
			if(a < ai) SMinus.add(a);
			if(a > ai) SPlus.add(a);
			if(a == ai) aiNum++;
		}
		RandomizedQuicksort(SMinus);
		for(int i = 0; i < aiNum; i++) System.out.print(ai + " ");
		RandomizedQuicksort(SPlus);
	}
	
	/* Main Method */
	public static void main(String[] args) {
		long randomStart, randomEnd, deterministicStart, deterministicEnd;
		ArrayList<Integer> list = randomArray(QuickSortAlgorithms.N);
		System.out.print("Original Array: ");
		for(int a : list) System.out.print(a + " ");
		System.out.println();
		System.out.print("Randomized Quick Sort: ");
		randomStart = new Date().getTime();
		RandomizedQuicksort(list);
		randomEnd = new Date().getTime();
		System.out.println();
		System.out.print("Deterministic Quick Sort: ");
		deterministicStart = new Date().getTime();
		DeterministicQuicksort(list);
		deterministicEnd = new Date().getTime();
		System.out.println();
		System.out.println("Time each quicksort version needs to sort the sequences(value of N: " + QuickSortAlgorithms.N + "):");
		System.out.println("Randomized: " + (randomEnd - randomStart) + "[ms]");
		System.out.println("Deterministic: " + (deterministicEnd - deterministicStart) + "[ms]");
	}
	
	/* Generate the random array and returns the ArrayList */
	public static ArrayList<Integer> randomArray(int numberOfElements) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		for(int i = 0; i < numberOfElements; i++)
			list.add(random.nextInt(numberOfElements));
		return list;
	}
	
}
