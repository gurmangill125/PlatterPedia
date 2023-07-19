package model;

import java.util.ArrayList;
import java.util.List;

// Represents a collection of recipes as a recipe book
public class RecipeBook {
    private List<Recipe> recipes;

    // EFFECTS: initializes a new, empty recipe book
    public RecipeBook() {
        this.recipes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new recipe to the recipe book
    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    // EFFECTS: returns a list of recipe titles in the recipe book
    public List<String> listRecipeTitles() {
        List<String> titles = new ArrayList<>();
        for (Recipe recipe : this.recipes) {
            titles.add(recipe.getTitle());
        }
        return titles;
    }

    // EFFECTS: returns a recipe matching the given title, throws an exception if the recipe does not exist
    public Recipe viewRecipe(String title) {
        for (Recipe recipe : this.recipes) {
            if (recipe.getTitle().equals(title)) {
                return recipe;
            }
        }
        throw new IllegalArgumentException("No recipe found with the given title.");
    }

    // REQUIRES: rating between 1 and 5 inclusive
    // MODIFIES: recipe
    // EFFECTS: sets the rating of the recipe with the given title
    public void rateRecipe(String title, int rating) {
        Recipe recipe = this.viewRecipe(title);
        recipe.rate(rating);
    }

    // MODIFIES: this
    // EFFECTS: deletes the recipe with the given title, throws an exception if the recipe does not exist
    public void deleteRecipe(String title) {
        Recipe recipe = this.viewRecipe(title);
        this.recipes.remove(recipe);
    }
}
