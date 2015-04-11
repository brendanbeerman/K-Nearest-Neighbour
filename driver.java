// The main file for running our implementation of the KNN algorithm in java

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class driver {

	public static void main (String[] args) throws FileNotFoundException, IOException {
		// Clean up files from previous runs
		String path = new File (".").getCanonicalPath();	
		
		Files.deleteIfExists(FileSystems.getDefault().getPath(path,"cleaned_train.txt"));
		Files.deleteIfExists(FileSystems.getDefault().getPath(path,"cleaned_test.txt"));
		Files.deleteIfExists(FileSystems.getDefault().getPath(path,"output.txt"));
		
		// Variable declarations
		Scanner keyboard = new Scanner(System.in);
		String file_name = "cleaned_train.txt";
		String file_name2 = "cleaned_test.txt";
		ArrayList<node> column_main = new ArrayList<node>();
		ArrayList<node> column_data_test = new ArrayList<node>();
		ArrayList<Double> distances;
		ArrayList<Integer> indices;
		String classify;
		
		// read in the file names and clean the data
		cleaning_part stuff = new cleaning_part();
		
		stuff.pickFile(0, keyboard); // 0 for training file
		stuff.pickFile(1, keyboard); // 1 for test file
		
		stuff.cleanUp(0); // 0 for training file
		stuff.cleanUp(1); // 1 for test file
		
		// Get the number of attributes in the data
		int row_width = read_data_plus.getWidth(file_name);
		// System.out.println("\n" + row_width); // for testing
		
		// Set the classifier column to column 0 for this dataset
		int col_class =	0;
		
		// Get the k value from user
		int k = cleanin.pickKValue(keyboard);
		keyboard.close();

		// Read in the class column
		column_main = read_data_plus.readMainColumn(file_name, col_class);
		
		// Read in the test data
		column_data_test = read_data_plus.readTestColumns(file_name2);
		System.out.println("Data read");
	
		// For each row of test data, find distance between that and train data, then classify and write to file
		System.out.println("Classifying test data");
		for (int i =0; i < column_data_test.size(); i++) {
			// Get a row of data, based on the subset of the full array list
			ArrayList<node> row = new ArrayList<node>(column_data_test.subList(i*(row_width-1), i*(row_width-1)+row_width-1));
			
			// Get all the distances between this test row and all training rows
			distances = read_data_plus.readAndGetDist(file_name, col_class, row_width,row);
			
			// Find the indices of the k-smallest distances
			indices = read_data_plus.findKIndices(k, distances);
			
			// Find the majority vote of those k-smallest classifiers
			classify = read_data_plus.majorityVote(column_main,indices);
			
			// Write the result back to an output file
			read_data_plus.writeToFile(classify, row);
		}
	}

}
