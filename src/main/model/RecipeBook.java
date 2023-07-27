package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

    // EFFECTS: returns a recipe matching the given title, throws an exception if the recipe does not exist
    //          if two titles are the same, return first one found
    public Recipe viewRecipe(String title) {
        for (Recipe recipe : this.recipes) {
            if (recipe.getTitle().equals(title)) {
                return recipe;
            }
        }
        throw new IllegalArgumentException("No recipe found with the given title.");
    }

    // EFFECTS: returns this recipe book as a JSON array
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe : this.recipes) {
            jsonArray.put(recipe.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: Returns a new recipe book from a JSON array
    public static RecipeBook fromJson(JSONArray jsonArray) {
        RecipeBook recipeBook = new RecipeBook();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            recipeBook.addRecipe(Recipe.fromJson(json));
        }
        return recipeBook;
    }

    // returns the recipe if its the recipe we are searching for
    public Recipe getRecipe(String title) {
        for (Recipe recipe : this.recipes) {  // Assuming 'recipes' is the collection of Recipe objects.
            if (recipe.getTitle().equals(title)) {
                return recipe;
            }
        }
        throw new IllegalArgumentException("No recipe found with the given title.");
    }

}
