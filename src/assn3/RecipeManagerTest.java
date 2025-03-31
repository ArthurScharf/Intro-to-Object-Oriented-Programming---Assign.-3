/*
 * Author: Arthur Scharf
 * Date: March 31, 2025
 * File: RecipeManagerTest.java
 * Description: For assignment 3. An assignmetn meant to test our error handling, and file/read write comprehension
 */







package assn3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;

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
 * I've opted to follow the pattern given in previous assignments, having the
 * entry point be the class with the "test" suffix
 * 
 * ---- Description ---- 
 * Generates all the menus and manages user input
 */
public class RecipeManagerTest
{

	/**
	 * Stores recipe's. Recipes are stored in alphabetical order
	 */
	private static ArrayList<Recipe> recipes;

	/**
	 * The Integer stored at the same index as some recipe in `recipes` is the
	 * number of that recipe that is to be added to the shopping list
	 */
	private static ArrayList<Integer> counts;

	public static void main(String[] args)
	{
		// -- Initializing state of program -- //
		recipes = RecipeManager.readRecipeFile("recipelist.txt");
		counts = new ArrayList<Integer>();
		for (Recipe r : recipes)
			counts.add(0); // We want counts for each recipe

		System.out.println("Welcome to Arthur Scharf's Recipe Manager.\n");

		showChoiceMenu(); 
		
		// -- Input Loop -- //
		Scanner scanner = new Scanner(System.in);
		int choice = -1;
		while (choice != 4)
		{
			choice = -1;
			System.out.println("Make a Choice: ");
			try {
				choice = scanner.nextInt();
				switch (choice)
				{
				case (0): // Next Loop or bad input
					showChoiceMenu();
					break;
				case (1): // Print Bread Options
					String str = "\n---- Available Recipes ----\n";
					for (int i = 0; i < recipes.size(); i++)
					{
						str += (i + 1) + ". " + recipes.get(i).getName() + "\n";
					}
					System.out.println(str);
					break;
				case (2): // Add bread to shopping list
					int breadIdx = -1;
					int breadCount = 0;
					showBreadMenu();
					System.out.println("\nWhich bread would you like?: ");
					// -- Getting User Input -- //
					breadIdx = scanner.nextInt();
					if (breadIdx > recipes.size())
						throw new InputMismatchException(); // TODO: Do I set any messages in the exception?
					System.out.println("\nHow many: ");
					breadCount = scanner.nextInt();
					if (breadCount < 0)
						throw new InputMismatchException();
					// -- Adding to shopping list -- //
					addToShoppingListByIdx(breadIdx, breadCount);
					
					break;
				case (3): // Print shopping list
					String list = shoppingListAsString();
					System.out.println(list);
					System.out.println("Save shopping list? [Y\\y]: ");
					if (scanner.next().toLowerCase().startsWith("y"))
					{
						RecipeManager.writeShoppingList(list, "shoppinglist.txt");
					}
					break;
				case (4):
					continue; // Goes to evaluate loop, which exits loop
				default: // Same as above. Good habit to have default though
					break;
				}// ~ switch on `choice`
			} catch (InputMismatchException e) {
				System.out.println("WARNING: Invalid value given as input\n");
				choice = -1;
				scanner.nextLine(); // Consumes invalid input
			} catch (NoSuchElementException e) {
				// Error called if input stream is exhausted. 
				e.printStackTrace();
				scanner.close();
				return;
			} catch (IllegalStateException e) {
				// Error only called if scanner is already closed
				e.printStackTrace(); 
				return;
			}
		} // ~ input loop
		scanner.close();
		System.out.println("Exiting...");
	}

	/**
	 * This method can remove recipes from the shopping list by receiving negative
	 * values. There can never be a negative number associated with a recipe in this
	 * way
	 * 
	 * @param idx         The index of element in shopping list
	 * 
	 * @param numberToAdd The number of instances of the bread at idx to add to the
	 *                    shopping list
	 * 
	 * @throws IndexOutOfBoundsException exception
	 */
	public static void addToShoppingListByIdx(int idx, int numberToAdd)
	{
		Recipe r = null;
		try
		{
			if (idx < 0 || idx > recipes.size() - 1)
			{
				throw new IndexOutOfBoundsException();
				// TODO: do I need to set message?
			}
			if (counts.get(idx) + numberToAdd < 0)
			{
				counts.set(idx, 0); // makes sure subtractions doesn't make the count less than 0
			} else
			{
				counts.set(idx, counts.get(idx) + numberToAdd); // Increase count
			}

		} catch (IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}// ~ addToShoppingListByIdx

	/**
	 * @return A formatted menu of bread, with each respective bread's index
	 *         displayed
	 */
	public static void showBreadMenu()
	{
		String str = "\n---- Available Recipes ----\n";
		for (int i = 0; i < recipes.size(); i++)
		{
			str += (i + 1) + ". " + recipes.get(i).getName() + "\n";
		}
		System.out.println(str);
	}

	/**
	 * Helper function meant to keep clean the primary loop. Shows the menu of
	 * choices the user can make
	 */
	public static void showChoiceMenu()
	{
		System.out.println("1. Show available recipes\n" + "2. Create shopping list\n" + "3. Print shopping list\n"
				+ "4. Quit program\n" + "0. Reprint this menu\n");
	}

	/**
	 * Returns a string representation of the shopping list
	 */
	private static String shoppingListAsString()
	{

		String str = "";
		// -- Writing to File -- //
		/*
		 * Iterate through `counts`. Each non-zero indicates that the recipe with the
		 * same index as that non-zero should be added to the shopping list, with the
		 * multiplicity being the value of the non-zero count. Each non-zero inclusion
		 * will add it's ingredient values to the totals, which will be written
		 * afterwards
		 */
		float butter = 0.f;
		int eggs = 0;
		float flour = 0.f;
		float sugar = 0.f;
		float yeast = 0.f;
		// Aggregating the sum of all needed ingredients
		for (int i = 0; i < counts.size(); i++)
		{
			if (counts.get(i) > 0)
			{
				// NOTE: Recipe never allows negative values for ingredients so it's safe to
				// just add
				Recipe r = recipes.get(i);
				butter += r.getButter();
				eggs += r.getEggs();
				flour += r.getFlour();
				sugar += r.getSugar();
				yeast += r.getYeast();
				str += counts.get(i) + " : " + r.getName() + "\n";
			}
		}
		str += "\n -- Ingredients -- \n" + butter + " grams of butter\n" + eggs + " egg(s)\n" + flour
				+ " grams of flour\n" + sugar + " grams of sugar\n" + yeast + " grams of yeast\n";
		return str;
	}
}
