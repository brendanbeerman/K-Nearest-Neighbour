
//A file of data scanning functions and core untility KNN algorithm functions
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class read_data_plus {

	public static int getWidth (String fn) throws FileNotFoundException { // takes in the file name
		String line = "";
		String []split_line;
		 
		File file = new File(fn); // open the file and its scanner
		Scanner scan_file = new Scanner(file);
		
		line = scan_file.nextLine();
		split_line = line.split("\\s+"); // splits the line by any white space
		
		scan_file.close();
		
		return split_line.length; // returns the amount of attributes
	}
	
	/*
	 * Takes in training file fn, classifier column col number, row width rw, and a row of data (ArrayList) newp
	 *returns list of distances between newp and training rows
	*/ 
	public static ArrayList<Double> readAndGetDist (String fn, int col, int rw, ArrayList<node> newp) throws FileNotFoundException {
		File file = new File(fn); 
		Scanner scan_file;
		
		String line = ""; // used to break up the current line
		String []split_line;
		
		ArrayList<node> cd = new ArrayList<node>();
		ArrayList<Double> distances = new ArrayList<Double>();
		
		int height = 0; // the y coordinate, i is the x coordinate
		
		node current;
		scan_file = new Scanner(file);
		
		while (scan_file.hasNextLine()) { // For all rows in the file
			line = scan_file.nextLine(); // Scan in a row of training data
			
			for (int i=0 ; i < rw; i++) {

				current = new node();

				split_line = line.split("\\s+");
				
				current.setCoordinates(i, height);
				current.setValue(split_line[i]);
				
				if(i!=col){ //ignore the classifier column
					cd.add(current);
				}
			}
			
			distances.add(findDistance(cd,newp,rw)); //once all values from the row are read, find distance between newp and that row, and save
			cd.clear();
			height++;
		}

		scan_file.close();
		
		return distances;
	}

	public static ArrayList<node> readMainColumn (String fn, int r) throws FileNotFoundException {
		ArrayList<node> cm = new ArrayList<node>();
		File file = new File(fn); 
		Scanner scan_file = new Scanner(file);
		
		String line = ""; // used to break up the current line
		String []split_line;
		
		int height = 0; // the y coordinate, i is the x coordinate
		
		node current;
		
		while (scan_file.hasNextLine()) {
			current = new node();
			
			line = scan_file.nextLine();
			split_line = line.split("\\s+");
			
			current.setCoordinates(r, height);
			current.setValue(split_line[r]);
			
			cm.add(current);
			
			height++;
		}

		scan_file.close();
		return cm;
	}
	
	// Function that was used for testing
	public static void testPrinting (ArrayList<node> a) {
		node current = new node();
		
		for (int i = 0; i < a.size(); i++) {
			current = a.get(i);
			System.out.print(current);
		}
		
		System.exit(0);
	}
	
	//Read in test data file
	public static ArrayList<node> readTestColumns (String fn) throws FileNotFoundException {
		ArrayList<node> cd_test = new ArrayList<node>();
		
		File file = new File(fn);
		Scanner scan_file = new Scanner(file);

		int height = 0;
		
		String line = "";
		String []split_line;
		
		node current;
		
		while (scan_file.hasNextLine()) { // Read in each element by row, save to the array list
			line = scan_file.nextLine();
			split_line = line.split("\\s+");
			
			for (int i = 1; i < split_line.length; i++) {
				current = new node();
				current.setValue(split_line[i]);
				current.setCoordinates(i, height);
				
				cd_test.add(current);
			}
			
			height++;
		}
		
		scan_file.close();
		return cd_test;
	}
	
	// Find the distance between a point (row of data) p1 and the trained row of data l1
	public static double findDistance (ArrayList<node> l1, ArrayList<node> p1, int rw) {
		double sum_of_squares = 0;
		double p1val;
		double l1val;
		double distance;

		for(int i=0; i<rw-1; i++){ // for each attribute of the point
			// Parse the strings to double values
			p1val = Double.valueOf(p1.get(i).getValue()).longValue();
			l1val = Double.valueOf(l1.get(i).getValue()).longValue();
			
			// Find the difference and square it
			sum_of_squares += Math.pow(p1val - l1val,2);
		}
		
		distance = Math.sqrt(sum_of_squares); // square root the sums of squares to get the euclidean distance
	
		return distance;
	}
	
	// Find the indices of the k rows where the smallest distance values are found
	public static ArrayList<Integer> findKIndices (int k, ArrayList<Double> l1) {
		int index;
		ArrayList<Integer> smallIndices = new ArrayList<Integer>();
		
		//find the smallest in the list, then remove it and repeat until k values found
		for(int i=0; i<k; i++){
			index = l1.indexOf(Collections.min(l1));
			smallIndices.add(index);
			l1.set(index, Double.MAX_VALUE);
		}

		return smallIndices;
	}
	
	
	// Given the indices of the k smallest distances, find their classes, and then find the majority
	public static String majorityVote (ArrayList<node> classes, ArrayList<Integer> ind) {
		String value;
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		
		for(int i = 0; i<ind.size(); i++){ // Build a hashmap to keep counts
			value = classes.get(ind.get(i)).getValue();
			
			if (!counts.containsKey(value)) {
				counts.put(value, 1);
			} else {
				counts.put(value, counts.get(value)+1);
			}
		}

		String classify = "";
		
		int maxValue=(Collections.max(counts.values())); //Find the key where the max value is found 
        for (Map.Entry<String, Integer> entry : counts.entrySet()) { 
            if (entry.getValue()==maxValue) {
                classify =  entry.getKey();
            }
        }
        
        return classify;
	}
	
	// Writes/appends results (classifier + data used to classify) to output.txt
	public static void writeToFile(String cl, ArrayList<node> restOfRow) throws IOException {
		FileWriter writer = new FileWriter("output.txt", true); 
		
		//Write the classifier
		writer.append(cl+" ");
		
		//Write the rest of the data
		for(node anode: restOfRow) {
			String str = anode.getValue();
			writer.append(str+" ");
		}
		
		//Finish with a newline
		writer.append("\n");
		writer.close();
	}
	
}
	