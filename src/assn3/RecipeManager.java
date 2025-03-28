package assn3;

import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;
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
	 * Stores recipe's.
	 * Recipes are stored in alphabetical order
	 */
	private ArrayList<Recipe> data;
	
	/**
	 * The Integer stored at the same index as some recipe in `data` is the number of that recipe that is to be added to the shopping list
	 */
	private ArrayList<Integer> counts;
	

	/** 
	 * Initializes `data` & `counts`
	 */
	public RecipeManager()
	{
		data   = new ArrayList<>();
		counts = new ArrayList<>(); 
	}

	
	/**
	 * A function for storing recipe's in `data`. Iterates through the backing array to make sure an equivalent recipe
	 * doesn't already exist. If it does, it doesn't store the new recipe.
	 * 
	 * Stores values in alphabetical order
	 *
	 * @param r The recipe to store
	 */
	public void storeRecipe(Recipe r)
	{
		for (int i = 0; i < data.size(); i++)
		{
			int comparison = data.get(i).getName().compareTo(r.getName()); 
			if (comparison >= 0) 
			{
				if (comparison == 0) return; // I decided to avoid storing duplicates. 
				
				data.add(i, r); // Inserts element at current index and shifts all elements right
				counts.add(i, 0);
				return;
			}
		}
		data.add(r); // First element is added
		counts.add(0);
	}//~ storeRecipe
	
	

	/**
	 * Reads a file, attempting to populate 'data' with the recipes
	 * 
	 * @param path
	 */
	public void readRecipeFile(String relativePath)
	{
		File file = null;
		Scanner scanner = null;
		data = new ArrayList<>();
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
				storeRecipe(r);
			}//~ while hasNext() in file

			//TODO: remove this once done testing
			System.out.print(data);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (NoSuchElementException e)
		{
			e.printStackTrace();
		} finally {
			if (scanner != null)
			{
				scanner.close();
			}

		}
	}// ~readFile()

	
	
	/**
	 * @param idx The index of element in shopping list
	 * 
	 * @throws IndexOutOfBoundsException exception
	 */
	public void addToShoppingListByIdx(int idx)
	{
		Recipe r = null;
		try {
			if (idx < 0 || idx > data.size() - 1) {
				throw new IndexOutOfBoundsException();
				// TODO: do I need to set message?
			}
			counts.set(idx, counts.get(idx) + 1); // Increase count
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** TODO
	 * Writes the contents of the shopping
	 * 
	 * @param relativePath
	 */
	public void writeShoppingList(String relativePath)
	{
		
	}
	
	
	/**
	 * Test function. TODO: remove before handing in
	 */
	public void printCounts()
	{
		System.out.println(counts);
	}
	
	
	
}


