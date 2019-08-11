package quicksort;

import java.io.PrintWriter;

/**
 * Class for Quicksort algorithm.
 * To print trace runs to a file (input >= 30) or to the terminal (input < 30), change PRINT_TRACE to true.
 * 
 * The pivot value is enclosed by '|->' and '<-|' for easier identification. 
 * For example, the pivot in array [ 58, 60, 61, 72 |-> 74 <-| 81, 88, 168, 113, 180 ] is '74'.
 * 
 * @author Cristina Padro-Juarbe
 *
 */
public class Quicksort {
	private int[] data;
	private PrintWriter pw;
	private static final boolean PRINT_TRACE = false;
	private long workNormal = 0;			// work counter for the normal pivot partitioning
	private long workMedian = 0;			// work counter for the median-of-three pivot partitioning
	
	/**
	 * Constructor of Quicksort class.
	 * This method prints the input data array to a file (if size of data > 30) or to the console (if size of data <= 30). 
	 * 
	 * @param data is an array of randomly generated integers.
	 * @param pw is the Print Writer object to write output and traces to a file.
	 */
	public Quicksort(int[] data, PrintWriter pw) {
		this.data = data;
		this.pw = pw;
		
		// no work calculated for IO operations
		// prints input data to a file
		if(this.pw != null) {
			this.pw.println("=================================== INPUT ===================================");
			this.pw.println("Data:");
			this.pw.println("[ " + this.toString(0, this.data.length) + " ]");
			this.pw.println();
			this.pw.println("=================================== OUTPUT ===================================");
			this.pw.println();
		} else {
			System.out.println("=================================== INPUT ===================================");
			System.out.println("Data:\n[ " + this.toString(0, this.data.length) + " ]\n");
			System.out.println("=================================== OUTPUT ===================================\n");
		}
		
		// work recorded for returning from the ClosestPair class constructor
		this.workNormal++;
	}
	
	/**
	 * This method sorts the input data array using the quicksort sorting algorithm. Depending on the mode of operation, the quicksort
	 * algorithm uses a normal partitioning process or the median-of-three partitioning process.
	 * 
	 * @param first is the first element in the array.
	 * @param last is the last element in the array.
	 * @param useMedianOfThree is a boolean value used to determine if the median-of-three partitioning process was selected by the user.
	 * @param message is a string to indicate if we are working on the left or right side of the data sub-array. It's only used to print trace runs.
	 */
	public void quicksort(int first, int last, boolean useMedianOfThree, String message) {
		// the sub-array has two or more elements
		if(first < last) {
			// selecting the pivot as the last element in the array
			int pivotIndex = last;
			
			// the median-of-three partitioning process was selected
			if(useMedianOfThree) {
				this.workMedian++;				// work recorded for calling the getMedianOfThree() method
				
				// obtain the index of the median (this is actually an approximate of the true median) 
				int median = getMedianOfThree(first, last);
				
				this.workMedian++;				// work recorded for calling the partition() method
				
				// partition the array using the median-of-three as pivot
				pivotIndex = partition(first, last, this.data[median]);
			} else {
				this.workNormal++; 				// work recorded for calling the partition() method
				
				// partition the array using the last element as pivot
				pivotIndex = partition(first, last, this.data[last]);
			}
			
			// work recorded for calling the quicksort() method
			this.workNormal++;
			
			// sort left sub-array
			quicksort(first, pivotIndex - 1, useMedianOfThree, "from index = " + first + " to index = " + (pivotIndex - 1) + "   (left tree)");
			
			// no work calculated for IO operations
			// prints the entire array 
			if(PRINT_TRACE) {
				if(this.pw != null) {
					this.pw.println("Data " + message); 
					this.pw.print("[ " + this.toString(first, pivotIndex));	
					printStatus(first, last, pivotIndex, pivotIndex + 1);
				} else {
					System.out.print("Data " + message + "\n[ " + this.toString(first, pivotIndex));
					printStatus(first, last, pivotIndex, pivotIndex + 1);
				}
			}
			
			// work recorded for calling the quicksort() method
			this.workNormal++;
			
			// sort right sub-array
			quicksort(pivotIndex + 1, last, useMedianOfThree, "from index = " + (pivotIndex + 1) + " to index = " + last + "   (right tree)");
			
			// no work calculated for IO operations
			// prints the entire array 
			if(PRINT_TRACE) {
				if(this.pw != null) {
					this.pw.println("Data " + message); 
					this.pw.print("[ " + this.toString(first, pivotIndex));								
					printStatus(first, last, pivotIndex, pivotIndex + 1);
				} else {
					System.out.print("Data " + message + "\n[ " + this.toString(first, pivotIndex));				
					printStatus(first, last, pivotIndex, pivotIndex + 1);
				}
			}
		}
		
		// work recorded for returning from the quicksort() method
		this.workNormal++;
	}
	
	/**
	 * This method uses a pivot to split the array in two sub-arrays. Values less or equal to the pivot are swapped to the left sub-array,
	 * and values greater than the pivot are swapped to the right sub-array.
	 * 
	 * @param first is the first element in the array.
	 * @param last is the last element in the array.
	 * @param pivot the value used to split the array in two sub-arrays
	 * @return the pivot index
	 */
	private int partition(int first, int last, int pivot) {
		int pivotIndex = first - 1;
		
		for(int index = first; index < last; index++) {			
			this.workNormal++;				// work recorded for each comparison in the partition() method
			if(this.data[index] <= pivot) {
				pivotIndex++;
				
				this.workNormal++;			// work recorded for calling the swap() method
				swap(pivotIndex, index);
			}
			this.workNormal++;				// work recorded for every loop iteration in the loop
		}
		this.workNormal++;					// work recorded for breaking from the loop
			
		this.workNormal++;					// work recorded for calling the swap() method  
		swap(pivotIndex + 1, last);			
		
		// no work calculated for IO operations
		// prints the current sub-array 
		if(PRINT_TRACE) {
			if(this.pw != null) {
				this.pw.println("Pivot = " + this.data[pivotIndex + 1] + ", PartitionIndex = " + (pivotIndex + 1));
				this.pw.println("Data after partition:");	
				this.pw.print("[ " + this.toString(first, pivotIndex + 1));	
				printStatus(first, last, pivotIndex + 1, pivotIndex + 2);
			} else {	
				System.out.println("Pivot = " + this.data[pivotIndex + 1] + ", PartitionIndex = " + (pivotIndex + 1));
				System.out.print("Data after partition:\n[ " + this.toString(first, pivotIndex + 1));
				printStatus(first, last, pivotIndex + 1, pivotIndex + 2);
			}
		}
		
		this.workNormal++;					// work recorded for returning from the partition() method		
		return pivotIndex + 1;				
	}
	
	/**
	 * This method determines the median-of-three index and places the array element with the median-of-three index at the end of the array.
	 * 
	 * @param first is the first element in the array.
	 * @param last is the last element in the array.
	 * @return median-of-three index, which is used as pivot.
	 */
	private int getMedianOfThree(int first, int last) {
		// calculate index of element in the middle of the array
		int middle = (first + last)/2;
		
		this.workMedian++;						// work recorded for each comparison in the getMedianOfThree() method
		
		// place the greatest value at the beginning of the array
		if(this.data[middle] > this.data[first]) {
			this.workMedian++;					// work recorded for calling the swap() method
			swap(middle, first);
		}
		
		this.workMedian++;						// work recorded for each comparison in the getMedianOfThree() method
		
		// place the greatest value at the beginning of the array
		if(this.data[last] > this.data[first]) {
			this.workMedian++;					// work recorded for calling the swap() method
			swap(last, first);
		}
		
		this.workMedian++;						// work recorded for each comparison in the getMedianOfThree() method
		
		// place the greatest value at the end of the array
		if(this.data[middle] > this.data[last]) {
			this.workMedian++;					// work recorded for calling the swap() method
			swap(middle, last);
		}
		
		this.workMedian++;			// work recorded for returning from the getMedianOfThree() method
		return last;				// return the index of the median-of-three
	}	
	
	/**
	 * Swaps two elements in the array.
	 * 
	 * @param value1 is an element in the array.
	 * @param value2 is an element in the array.
	 */
	private void swap(int value1, int value2) {
		int temp = this.data[value1];
		this.data[value1] = this.data[value2];
		this.data[value2] = temp;		
		this.workNormal++;					// work recorded for returning from the swap() method		
	}
	
	/**
	 * This method return the string value of the sub-array starting from index = first to index = last.
	 * 
	 * @param first is the first element in the array string value.
	 * @param last is the last element in the array string value.
	 * @return the string value of the sub-array starting from index = first to index = last.
	 */
	public String toString(int first, int last) {
		String result = "";
		
		for(int i = first; i < last - 1; i++) {
			result += this.data[i] + ", ";

			if((i + 1) % 35 == 0) {
				result += "\r\n";
			}
		}
		
		if(first == last) {
			result += this.data[last];
		} else {
			result += this.data[last - 1];
		}
		
		return result;
	}
	
	/**
	 * This method is used to print trace runs to I/O.
	 * The pivot value is enclosed by '|->' and '<-|' for easier identification. 
	 * For example, the pivot in array [ 58, 60, 61, 72 |-> 74 <-| 81, 88, 168, 113, 180 ] is '74'.
	 * 
	 * @param first is the first element in the array.
	 * @param last is the last element in the array.
	 * @param pivotIndexA is the pivot index
	 * @param pivotIndexB is the pivot index + 1
	 */
	private void printStatus(int first, int last, int pivotIndexA, int pivotIndexB) {	
		if(this.pw != null) {		
			if(pivotIndexA > first) {
				this.pw.print(" |-> " + this.toString(pivotIndexA, pivotIndexB));
			} 
			
			if(pivotIndexB <= last) {
				this.pw.println(" <-| " + this.toString(pivotIndexB, last + 1) + " ]");
				this.pw.println();
			} else {
				this.pw.println(" ]");
				this.pw.println();
			}
		} else {
			if(pivotIndexA > first) {
				System.out.print(" |-> " + this.toString(pivotIndexA, pivotIndexB));
			}
			
			if(pivotIndexB <= last) {
				System.out.println(" <-| " + this.toString(pivotIndexB, last + 1) + " ]\n");
			} else {
				System.out.println(" ]\n");
			}
		}
	}

	/**
	 * This method returns the work done by the quicksort algorithm when using normal partitioning.
	 * 
	 * @return work done by the quicksort algorithm when using normal partitioning.
	 */
	public long getNormalWork() {
		return ++this.workNormal;			// work recorded for returning from the getNormalWork() method
	}

	/**
	 * This method returns the work done by the quicksort algorithm when using median-of-three partitioning.
	 * The workMedian counter increments when performing work to obtain the median-of-three index only.  
	 * The rest of the work done in quicksort(), partition(), and swap() methods are recorded by the workNormal counter.
	 * Thus, we use the work recorded by the workMedian in combination with the work recorded by workNormal to 
	 * obtain the total work done by the quicksort algorithm when using median-of-three partitioning.
	 * 
	 * @return work done by the quicksort algorithm when using using median-of-three partitioning.
	 */
	public long getMedianWork() {
		return ++this.workMedian + this.workNormal;		// work recorded for returning from the getMedianWork() method
	}
	
}
