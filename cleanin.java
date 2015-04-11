
//Contains utility function to pick the K value
import java.util.Scanner;

public class cleanin {
	public static int pickKValue (Scanner keyboard) {
		int result;
		
		System.out.print("\nEnter a k value: ");
		result = keyboard.nextInt();
		
		while (result <= 0) {
			System.out.print("\nEnter a number above 0.");
			System.out.print("\nEnter a k value: ");
			result = keyboard.nextInt();			
		}
		
		return result;
	}

}
