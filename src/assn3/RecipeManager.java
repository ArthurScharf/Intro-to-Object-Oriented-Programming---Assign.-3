package assn3;

import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;


import java.util.Scanner;

/*
 * Maintains collection of recipe objects
 * 
 * Reads recipes from a file.
 * 
 * If a recipe with the same name as some other already stored recipe, is encountered, the new recipe is ignored
 * 
 * Save a "shoppinglist.txt" that stores all the ingredients the baker needs (list of recipe values)
 */
public class RecipeManager
{


	/**
	 * Reads a file, attempting to populate 'data' with the recipes
	 * 
	 * @param path
	 */
	public static ArrayList<Recipe> readRecipeFile(String relativePath)
	{
		File file = null;
		Scanner scanner = null;
		ArrayList<Recipe> data = new ArrayList<>();
		try
		{
			file = new File(relativePath);
			scanner = new Scanner(file);

			String buffer = "";
			while (scanner.hasNext())
			{				
				if (!scanner.next().equals("Recipe")) continue; // reads tokens until "Recipe" tells the program that a block for recipe is starting
				
				Recipe r = new Recipe(scanner.nextLine().trim()); // Create Recipe with just the name
				for (int i = 0; i < 5 && scanner.hasNext(); i++)
				{					
					buffer = scanner.next(); // Get's recipe element
					switch(buffer) {
					case "butter":
						r.setButter(scanner.nextFloat());
						break;
					case "eggs":
						r.setEggs(scanner.nextFloat());
						break;
					case "flour":
						r.setFlour(scanner.nextFloat());
						break;
					case "sugar":
						r.setSugar(scanner.nextFloat());
						break;
					case "yeast":
						r.setYeast(scanner.nextFloat());
						break;
					}	
				}
				storeRecipe(r, data);
			}//~ while hasNext() in file
			return data;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return new ArrayList<Recipe>();
		} catch (NoSuchElementException e)
		{
			e.printStackTrace();
			return new ArrayList<Recipe>();
		} finally {
			if (scanner != null)
			{
				scanner.close();
			}
		}
	}// ~readFile()

		
	/**
	 * A helper function for storing recipe's in `data`. Iterates through the array to make sure an equivalent recipe
	 * doesn't already exist. If it does, it doesn't store the new recipe.
	 * 
	 * Stores values in alphabetical order
	 *
	 * @param r The recipe to store
	 */
	private static void storeRecipe(Recipe r, ArrayList<Recipe> data)
	{
		for (int i = 0; i < data.size(); i++)
		{
			int comparison = data.get(i).getName().compareTo(r.getName()); 
			if (comparison >= 0) 
			{
				if (comparison == 0) return; // I decided to avoid storing duplicates. 
				
				data.add(i, r); // Inserts element at current index and shifts all elements right
				return;
			}
		}
		data.add(r); // First element is added

	}//~ storeRecipe

	

	public static void writeShoppingList(String list, String absolutePath)
	{
		File file = null;
		FileWriter writer = null;
		try {
			file = new File(absolutePath);
			writer = new FileWriter(file);
			writer.write(list);
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
	}//~ writeShoppingList
	
	
}


