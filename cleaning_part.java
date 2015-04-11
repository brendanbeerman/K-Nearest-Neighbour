
//Class for gathering training and test file names and cleaning them to a better format for processing
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class cleaning_part {

	// globals
	private static String train_file_name = ""; // both obtained from user input
	private static String test_file_name = "";
	
	// getters
	public String getTrainFileName () {
		return train_file_name;
	}
	
	public String getTestFileName () {
		return test_file_name;
	}
	
	// constructor
	cleaning_part () {}
	
	// utilities
	
	/*
	 * takes in an integer depending on if the function is to get the training set or testing
	 * takes in a scanner to take in the users input
	 * the program needs the file to be entered with an extension
	 */
	public void pickFile (int count, Scanner keyboard) { 
		String current_name = "";
		if(count == 0) {
			System.out.print("\nEnter a training file name: "); //assumes the file name has the extension
		}
		else{
			System.out.print("\nEnter a test file name: "); //assumes the file name has the extension
		}
		current_name = keyboard.nextLine();
		
		File file = new File(current_name); // opens the file to check if it exists
		
		while (!file.exists()) { // file not found, user is asked to enter again
			if(count == 0) {
				System.out.print("\nFile not found\nEnter a training file name: "); //assumes the file name has the extension
			}
			else{
				System.out.print("\nFile not found\nEnter a test file name: "); //assumes the file name has the extension
			}
			current_name = keyboard.nextLine();
			
			file = new File(current_name);
		}
		
		System.out.print("\nFile was found");
		
		if(count == 0) { // saving the file name to training or testing sets
			train_file_name = current_name; 
		} else {
			test_file_name = current_name;
		}
	}
	
	public void cleanUp (int count) throws IOException {
		int divider = 3; // amount of columns to cut down the data by
		String file_name = "", cleaned_file = "";
		
		if(count == 0) { // to determine which file to open, the training or testing data
			file_name = train_file_name; 
			cleaned_file = "cleaned_train.txt";
			
		} else {
			file_name = test_file_name;
			cleaned_file = "cleaned_test.txt";
		}
		
		File file = new File(file_name); // the source file that is going to be cleaned
		Scanner original_file = new Scanner(file);
		
		File cleaned = new File(cleaned_file); // making the new file
		cleaned.createNewFile(); // used to store the cleaned data
		FileWriter clean_file = new FileWriter(cleaned, true);
		
		int end_point = 0; // the last entry that is allowed to be added
		String read_line = "", added_line = ""; // line to read, and line to append
		String []split_line; // splits the elements of the read_line up by white space(\\s+)
		
		while (original_file.hasNextLine()) { // iterates through the file
			added_line = ""; // reset the line to add
			
			read_line = original_file.nextLine(); // gets the next line
			split_line = read_line.split("\\s+"); // splits by any white space
			
			end_point = split_line.length / divider; // sets how far out the next loop should go
			
			for (int i = 1; i < end_point; i++) {
				added_line += split_line[i] + "\t";
			}
			
			added_line += "\n";
			clean_file.append(added_line);
		}
		
		original_file.close();
		clean_file.close();
		
		if(count == 0) { // sets the cleaned file names
			train_file_name = cleaned_file;
		} else {
			test_file_name = cleaned_file;
		}
	}

}
