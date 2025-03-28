package assn3;


/*
 * ---- EXTREMELY IMPORTANT NOTE ----
 * The instructions explicitly describe TWO classes that will be the driver for the program. Excerpts from the assignment below...
 * 
 * "Assignment3 is the driver class. It contains the main() method.  It will generate all 
 * the menus, read input from the user, and make calls to RecipeManager.  
 * Assignment3 should not directly access the recipe objects."
 * 
 * "Step 3: Create a Class named RecipeManagerTest 
 * This is the driver class.  It contains the main method.  It will also generate all the menus 
 * and manage all user inputs.  You will need:"
 * 
 * This is clearly a mistake in the instructions, as a program can have only a single entry point.
 * Given this, I've opted to follow the pattern given in previous assignments, having the
 * entry point be the class with the "test" suffix
 * 
 * ---- Description ---- 
 * Generates all the menus and manages user input
 */
public class RecipeManagerTest
{
	public static void main(String[] args)
	{
		RecipeManager manager = new RecipeManager();
		
		manager.readFile("recipelist.txt");
	}
}
