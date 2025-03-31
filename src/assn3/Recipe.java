/*
 * Author: Arthur Scharf
 * Date: March 31, 2025
 * File: RecipeManager.java
 * Description: For assignment 3. An assignmetn meant to test our error handling, and file/read write comprehension
 */






/**
 * A container class designed to store the name and ingredients of a recipe.
 */
package assn3;

public class Recipe
{
	
	/**
	 * Name of the recipe
	 */
	private String name;
	
	/*
	 * float representations of recipe values
	 */
	private float butter;
	private float eggs; // NOTE: While eggs are discrete, recipe files store them as floats
	private float flour;
	private float sugar;
	private float yeast;
	
	
	public Recipe()
	{
		this("DEFAULT");
	}
	
	public Recipe(String name)
	{
		// The ternary operator is preventing empty names from resulting in nameless recipes. Names are the recipe IDs
		this(name.isEmpty() ? "DEFAULT" : name, 0.f, 0.f, 0.f, 0.f, 0.f);	
	}
	
	
	public Recipe(String name, float butter, float eggs, float flour, float sugar, float yeast)
	{
		setName(name);
		setButter(butter);
		setEggs(eggs);
		setFlour(flour);
		setSugar(sugar);
		setYeast(yeast);
	}

	
	/**
	 * Returns the recipe in its string format.
	 * Shows the name, and the amount of each ingredient required for the recipe
	 */
	public String toString()
	{
		return String.format(
				" -- %s --\n"
				+ "butter: %.1f\n"
				+ "eggs:  %.1f\n"
				+ "flour: %.1f\n"
				+ "sugar: %.1f\n"
				+ "yeast: %.1f\n", getName(), getButter(), getEggs(), getFlour(), getSugar(), getYeast());
	}
	
	// ---- ----------------- ---- //
	// ---- Getters & Setters ---- //
	// ---- ----------------- ---- //
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the butter
	 */
	public float getButter()
	{
		return butter;
	}

	/**
	 * @param butter the butter to set
	 */
	public void setButter(float butter)
	{
		this.butter = (butter > 0) ? butter : 0.f;
	}

	/**
	 * @return the eggs
	 */
	public float getEggs()
	{
		return eggs;
	}

	/**
	 * @param eggs the eggs to set
	 */
	public void setEggs(float eggs)
	{
		this.eggs = (eggs > 0) ? eggs : 0.f;
	}

	/**
	 * @return the flour
	 */
	public float getFlour()
	{
		return flour;
	}

	/**
	 * @param flour the flour to set
	 */
	public void setFlour(float flour)
	{
		this.flour = (flour > 0) ? flour : 0.f;
	}

	/**
	 * @return the sugar
	 */
	public float getSugar()
	{
		return sugar;
	}

	/**
	 * @param sugar the sugar to set
	 */
	public void setSugar(float sugar)
	{
		this.sugar = (sugar > 0) ? sugar : 0.f;
	}

	/**
	 * @return the yeast
	 */
	public float getYeast()
	{
		return yeast;
	}

	/**
	 * @param yeast the yeast to set
	 */
	public void setYeast(float yeast)
	{
		this.yeast = (yeast > 0) ? yeast : 0.f;
	}
	
	

}//~Recipe
