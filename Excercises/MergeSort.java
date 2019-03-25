import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MergeSort {
	
	public MergeSort() { }
	
	/* merge_sort Method */
	public ArrayList<Integer> merge_sort(ArrayList<Integer> A, int arraySize){
		
		switch(arraySize) {
		case 0:
		case 1:
			return A;
		default:
			int middle = arraySize / 2;
			// Prepare the temporary list temp
			ArrayList<Integer> temp = new ArrayList<Integer>();
			// Prepare the list left and right for merging
			ArrayList<Integer> left, right;
			
			// Add the 1 to middle elements of A to temp list
			for(int i = 0; i < middle; i++) {
				temp.add(A.get(i));
			}
			// Initialize the list left
			left = (ArrayList<Integer>) merge_sort(temp, temp.size()).clone();
			
			// Initialize the list temp
			temp.clear();
			
			// Add the middle to final elements of A to temp list
			for(int i = middle; i < arraySize; i++) {
				temp.add(A.get(i));
			}	
			// Initialize the list right
			right = (ArrayList<Integer>) merge_sort(temp, temp.size()).clone();
			
			// Return the merged array list
			return merge(left, right);
		}
	}
	
	/* merge Method */
	public ArrayList<Integer> merge(ArrayList<Integer> A, ArrayList<Integer> B){
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		while(A.size() > 0 || B.size() > 0) {
			if(A.size() > 0 && B.size() > 0) {
				if(A.get(0) <= B.get(0)) {
					result.add(A.get(0));
					A.remove(0);
				}
				else {
					result.add(B.get(0));
					B.remove(0);
				}
			}
			else if(A.size() > 0 && B.size() <= 0) {
				result.add(A.get(0));
				A.remove(0);
			}
			else if(A.size() <= 0 && B.size() > 0) {
				result.add(B.get(0));
				B.remove(0);
			}
		}
		
		return result;
	}
	
	/* Require the element of the Array from the standard input and store it in array. */
	public ArrayList<Integer> doInput(ArrayList<Integer> array) {
		// Output the message
		System.out.println("Please enter the elements of the array for merge sort. \n(Elements must be INTEGER, and must be separated by space. Input any alphabet to finish input operation.):");
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
		System.out.println("<Result of Merge Sort>");
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
		MergeSort mergeSort = new MergeSort();
		array = mergeSort.doInput(array);
		array = mergeSort.merge_sort(array, array.size());
		mergeSort.doOutput(array);
	}
}
