package model;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook {
    private List<Recipe> recipes;

    public RecipeBook() {
        this.recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null.");
        }
        this.recipes.add(recipe);
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(this.recipes); // Return a copy for encapsulation
    }

    public void removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
    }
}
