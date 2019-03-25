
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeapSort {

	/* Constructor */
	public HeapSort() { }
	
	/* HeapBottomUP Method */
	public ArrayList<Integer> heapBottomUp(ArrayList<Integer> array){
		//int heap_size = array.size();
		for(int i = array.size() / 2 - 1; i >= 0; i--) this.maxHeapify(array, i);
		return array;
	}
	
	/* MaxHeapify Method */
	public void maxHeapify(ArrayList<Integer> array, int i) {
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		int largest = -1;
		int temp;
		
		// if L child exists and is > A[i]
		if(left < array.size() && array.get(left) > array.get(i)) largest = left;
		else largest = i;
		
		if(right < array.size() && array.get(right) > array.get(largest)) largest = right;
		
		if(largest != i) {
			temp = array.get(i);
			array.set(i, array.get(largest));
			array.set(largest, temp);
			
			// heapify the subtree
			this.maxHeapify(array, largest);
		}
	}
	
	/* Require the element of the Array from the standard input and store it in array. */
	public ArrayList<Integer> doInput(ArrayList<Integer> array) {
		// Output the message
		System.out.println("Please enter the elements of the array for heap sort. \n(Elements must be INTEGER, and must be separated by space. Input any alphabet to finish input operation.):");
		// Prepare Scanner for input
		Scanner scan = new Scanner(System.in);
		
		// Loop for the input
		while(true) {
			// Read the inputed elements
			String input = scan.next();
			// Set the acceptable pattern by the regular expression. Minus value is also acceptable.
			Pattern p = Pattern.compile("^-?[0-9]*$");
			// Check whether it matches to the pattern p or not.
			Matcher m = p.matcher(input);
			// If the input is integer value, add it to the array. Else, end the loop.
			if(m.find()) array.add(Integer.valueOf(input));
			else break;
		}
		
		System.out.println("<Loaded elements in the Array>");
		this.showContent(array);
		
		return array;
	}
	
	/* Output the element of the Array */
	public void doOutput(ArrayList<Integer> array) {
		System.out.println("<Result of Heap Sort>");
		this.showContent(array);
	}
	
	/* Output the content of the Array */
	public void showContent(ArrayList<Integer> array) {
		//System.err.println("showContent is called here ");
		for(int i = 0; i < array.size(); i++) System.out.print(array.get(i) + " ");
		System.out.println();
	}
	
	/* Main Method */
	public static void main(String[] args) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		HeapSort heapSort = new HeapSort();
		array = heapSort.doInput(array);
		array = heapSort.heapBottomUp(array);
		heapSort.doOutput(array);
	}

}
