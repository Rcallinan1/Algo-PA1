package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** Sorts integers from command line using insertion sort and mergesort algorithms <p>
 * 
 * Wentworth Institute of Technology <br>
 * COMP 2350 <br>
 * Lab Assignment 1 solution <br>
 * <p>
 * 
 * @author      Memo Ergezer
 * @author      Frank Kreimendahl
 * @author      Ryan Callinan
 * @version     %I%, %G%
 * @since       1.0
 */

public class LAB1 {


	
	/**
	 * Implementation of insertionSort as given in week 1 lecture. <br>
	 * Temp is the key <br>
	 * We step through the array and look for the proper place to insert the key <br>
	 * Elements up the key are assumed to be sorted. <br>
	 * <p> Steps:
	 * <ul>
	 * <li> Copy the key
	 * <li> Copy elements UP until we find where the key goes
	 * <li> We put [insert] the key
	 * <li> Repeat for j+1
	 * </ul>
	 * <p>
	 * Java, C++ implementation: <br>
	 * http://www.algolist.net/Algorithms/Sorting/Insertion_sort
	 * <p>
	 * Expected Costs: <br>
	 * Time: O(n^2) <br>
	 * Space: O(1)
	 *
	 * @param a	Array to be sorted
	 * @return sorted array
	 */
	public static int[] insertionSort(int[] a) {
			int i, j, newValue;
			for (i = 1; i < a.length; i++) {
				newValue = a[i];
				j = i;
				while (j > 0 && a[j - 1] > newValue) {
					a[j] = a[j - 1];
					j--;
				}
				a[j] = newValue;
			}
		return a;
	}

	/**
	 * Calls sorting function with additional parameters.
	 * @param a1 Array to be sorted
	 * @return sorted array
	 */
	public static int[] mergeSort(int[] a1) {
        int[] tmp = new int[a1.length];
        return mergeSorter(a1, tmp,  0,  a1.length - 1);
    }

	/**
	 * Identifies the center of the array <br>
	 * Floor increases each time the array is sorted <br>
	 * Ceiling decreases each recursive loop until equal to floor <br>
	 * @param a1 Array to be stored
	 * @param tmp Temp array to store the
	 * @param floor Acts as a lower boundary count
	 * @param ceiling Acts as a count for the total size of the array
	 * @return The sorted array or Null for recursive loops
	 */
    private static int[] mergeSorter(int [ ] a1, int [ ] tmp, int floor, int ceiling) {
        if(a1.length == 1){
            return a1; //If array is only one number then it is complete
        }
        if( floor < ceiling )
        {
            int center = (floor + ceiling) / 2;
            mergeSorter(a1, tmp, floor, center); //Sort the left side of the array
            mergeSorter(a1, tmp, center + 1, ceiling); //Sort the right side of the array
            return merge(a1, tmp, floor, center + 1, ceiling); //Merge the two sides together
        }
        return new int[0];
    }


	/**
	 * Checks if the current floor is greater than the current ceiling <br>
     * If so set the ceiling to the current index <br>
     * Lastly copy over the remaining index ack into the array <br>
	 * @param a1 The original array to be sorted
	 * @param tmp Holds the new values of the newly sorted array
	 * @param floor Identifies the first index of the to be sorted array
	 * @param ceiling Identifies the last index of the to be sorted array
	 * @param arrayEnd Identifies the end of the array
	 * @return - The merged array
	 */
	private static int[] merge(int[] a1, int[] tmp, int floor, int ceiling, int arrayEnd) {
        int floorEnd = ceiling - 1; //Sets the max index for the floor
        int index = floor; //Sets the starting index for tmp array
        int num = arrayEnd - floor + 1; // Identifies the amount of numbers held in the to be sorted array
        while(floor <= floorEnd && ceiling <= arrayEnd) {
			if (a1[floor] <= a1[ceiling]) { //If a lower index is greater or equal to higher index
				tmp[index++] = a1[floor++];
			} else {
				tmp[index++] = a1[ceiling++];
			}
		}
        while(floor <= floorEnd) {
			tmp[index++] = a1[floor++];
		}
        while(ceiling <= arrayEnd) {
			tmp[index++] = a1[ceiling++];
		}
        for(int i = 0; i < num; i++, arrayEnd--) { // Copy over the remaining index
			a1[arrayEnd] = tmp[arrayEnd];
		}
		return tmp;
	}



	/********************************************
	 *
	 * Do NOT modify anything below
	 *
	 ********************************************/

	public final static int MAX_INPUT = 524287;
	public final static int MIN_INPUT = 0;


	/* Implementation note: The "system" sorting algorithm is a Dual-Pivot Quicksort
	 * by Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch.
	 *  This algorithm offers O(n log(n)) performance on many data
	 *  sets that cause other quicksorts to degrade to quadratic performance,
	 *  and is typically faster than traditional (one-pivot) Quicksort implementations.
	 */

	public static int[] systemSort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	// read ints from a Scanner
	// returns an array of the ints read
	private static int[] getInts(Scanner s) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		while (s.hasNextInt()) {
			int i = s.nextInt();
			if ((i <= MAX_INPUT) && (i >= MIN_INPUT))
				a.add(i);
		}

		return toIntArray(a);
	}

	// copies an ArrayList of Integer to an array of int
	private static int[] toIntArray(ArrayList<Integer> a) {
		int[] ret = new int[a.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use ([i]nsertion, [m]erge, or [s]ystem): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the integers that you would like sorted, followed by a non-integer character: ");
		int[] unsorted_values = getInts(s);
		int[] sorted_values = {};

		s.close();

		switch (algo) {
		case 'm':
			sorted_values = mergeSort(unsorted_values);
			break;
		case 'i':
			sorted_values = insertionSort(unsorted_values);
			break;
		case 's':
			sorted_values = systemSort(unsorted_values);
			break;
		default:
			System.out.println("Invalid sorting algorithm");
			System.exit(0);
			break;
		}

		System.out.println(Arrays.toString(sorted_values));
	}

}
