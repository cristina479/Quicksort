package quicksort;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class for the Quicksort program
 * 
 * @author Cristina Padro-Juarbe
 *
 */
public class Main {
	
	final static int MAX_LENGTH_ARRAY = 30;		
	final static int BOUND = 200;				// Upper bound integer for generating integer random numbers
	final static String filepath = "src/quicksort/QuickSort";
	static PrintWriter printWriter = null;
	static int workMain = 0;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an integer size for the input array: ");
		int size = in.nextInt();
		
		// From the problem statement in the programming assignment, the input array needs to be at least of size 3.
		while(size < 3) {
			System.out.println("The input array length must be of at least 3.");
			System.out.println("\nEnter an integer size for the input array: ");
			size = in.nextInt();
		}
		
		// get the mode (0 - Normal partition, 1 - Median Of Three partitioning, 2 - Comparing both partitions) from the user
		System.out.println("\nEnter '0' to use normal partitioning, '1' to use median-of-three partitioning, or '2' to use both in sequence (for comparing both partition proceses): ");				
		int mode = in.nextInt();
		
		// get the mode (0 - Normal partition, 1 - Median Of Three partitioning, 2 - Comparing both partitions) from the user
		while(mode < 0 || mode > 2) {
			System.out.println("Valid inputs are (0 - Normal partition, 1 - Median Of Three partitioning, 2 - Both partitions in sequence).");
			System.out.println("\nEnter '0' to use normal partitioning, '1' to use median-of-three partitioning, or '2' to use both in sequence (for comparing both partition proceses): ");
			mode = in.nextInt();
		}
		in.close();

		// helper method
		run(size, mode);
		System.out.println("\nDone!!\n");
		
	}

	/**
	 * This is a helper method that creates a random input array of varying size, and calls a 
	 * second helper method with different parameter values depending on the mode of operation.
	 * 
	 * @param size the length of the input array.
	 * @param mode is the mode of operation of the Quicksort program. Mode 0 - Normal partition, Mode 1 - Median Of Three partitioning, Mode 2 - Comparing both partitions. 
	 */
	private static void run(int size, int mode) {
		Random rand = new Random();
		int[] data1 = new int[size];
		int[] data2 = null;		
		boolean trace_run_console = size <= MAX_LENGTH_ARRAY ? true : false;
		
		if(mode == 2) {
			data2 = new int[size];
		}
			
		// generating the input array
		for (int i = 0; i < size; i++) {
			data1[i] = rand.nextInt(BOUND);
			if(data2 != null) {
				data2[i] = data1[i];
			}
		}
		
		switch(mode) {
		case 0:
			// Normal Partition
			System.out.println("\nYou selected the normal partition process.\n");	
			System.out.println("Starting work. Please wait...\n");
			sort(data1, size, false, trace_run_console, "Normal Partitioning");
			
			break;
		case 1:
			// Median-Of-Three Partition
			System.out.println("\nYou selected the Median-Of-Three partition process.\n");
			System.out.println("Starting work. Please wait...\n");
			sort(data1, size, true, trace_run_console, "Median-Of-Three Partitioning");	
			
			break;
		case 2:
			// Comparison mode
			System.out.println("\nYou selected mode 2 (comparison mode). The input array will be first sorted using the normal partition process.");
			System.out.println("Afterwards, the same unordered input array will be sorted using the median-of-three partition process.\n");
			System.out.println("Starting work. Please wait...\n");
			
			// sort the input array using normal partitioning
			System.out.println("===Normal Partitioning===\n");
			sort(data1, size, false, trace_run_console, "Normal Partitioning");
			
			// sort a copy of the input array using median-of-three partitioning
			System.out.println("\n===Median-Of-Three Partitioning===\n");
			sort(data2, size, true, trace_run_console, "Median-Of-Three Partitioning");
			
			break;  
		default:
			System.out.println("\nInvalid mode.");
		}
	}

	/**
	 * This is a helper method that calls the quicksort() sorting algorithm, and prints the program trace runs and results to I/O.
	 * 
	 * @param data the input array.
	 * @param size the length of the input array.
	 * @param useMedianOfThree a boolean value used to determine if the median-of-three partitioning process was selected by the user. 
	 * @param printTraces a boolean value used to determine if trace runs should be printed to the console or a file.
	 * @param partitionProcess the name of the partition process selected.
	 */
	private static void sort(int[] data, int size, boolean useMedianOfThree, boolean printTraces, String partitionProcess) {
		// print output to a file
		if (!printTraces) {
			try {
				printWriter = new PrintWriter(new FileWriter(filepath + " (" + partitionProcess + ")/n=" + size + ".txt"));
				
				workMain++;		// work recorded for calling the Quicksort constructor class
				Quicksort qs = new Quicksort(data, printWriter);
				
				workMain++;		// work recorded for calling the quicksort() method
				qs.quicksort(0, data.length - 1, useMedianOfThree, "");
				
				printWriter.println();
				printWriter.println("RESULT Data:\n[ " + qs.toString(0, data.length) + " ]");	
				printWriter.println("==============================================================================");
				printWriter.println();
				printWriter.print("Total work done using " + partitionProcess.toLowerCase() + ": ");
				
				// printing the work done by the partition processes
				if(!useMedianOfThree) {
					printWriter.println(workMain + qs.getNormalWork() + 1);
				} else {
					printWriter.println(workMain + qs.getMedianWork() + 1);
				}

				// used for debugging
				System.out.println("Is the input array sorted? " + isSorted(data));
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null)
					printWriter.close();
			}
		} else {
			workMain++;			// work recorded for calling the Quicksort constructor class
			Quicksort qs = new Quicksort(data, null);
			
			workMain++;			// work recorded for calling the quicksort() method
			qs.quicksort(0, data.length - 1, useMedianOfThree, "");
			
			System.out.println("\nRESULT Data:\n[ " + qs.toString(0, data.length) + " ]");	
			System.out.println("==============================================================================\n");
			System.out.print("Total work done using " + partitionProcess.toLowerCase() + ": ");
			
			// printing the work done by the partition processes
			if(!useMedianOfThree) {
				System.out.println(workMain + qs.getNormalWork() + 1);
			} else {
				System.out.println(workMain + qs.getMedianWork() + 1);
			}
			
			// used for debugging
			System.out.println("Is the input array sorted? " + isSorted(data));
		}
	}

	/**
	 * This is a method used for debugging. 
	 * It determines if the array is sorted in increasing order.
	 * 
	 * @param array is the resulting array after sorting the input array with quicksort().
	 * @return true if array is sorted, false otherwise.
	 */
	private static boolean isSorted(int[] array) {
		for(int i = 1; i < array.length; i++) {
			if(array[i - 1] > array[i]) {
				return false;
			}
		}
		return true;
	}

}
