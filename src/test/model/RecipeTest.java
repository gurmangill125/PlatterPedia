package model;

import model.Recipe;
import model.RecipeBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private RecipeBook recipeBook;
    private Recipe testRecipe;

    @BeforeEach
    public void setUp() {
        recipeBook = new RecipeBook();
        testRecipe = new Recipe("Test Recipe", "Test Recipe Details");
    }

    @Test
    public void testAddRecipe() {
        recipeBook.addRecipe(testRecipe);
        assertTrue(recipeBook.getRecipes().contains(testRecipe));
    }

    @Test
    public void testAddRecipe_Null() {
        assertThrows(IllegalArgumentException.class, () -> recipeBook.addRecipe(null));
    }

    @Test
    public void testRemoveRecipe() {
        recipeBook.addRecipe(testRecipe);
        recipeBook.removeRecipe(testRecipe);
        assertFalse(recipeBook.getRecipes().contains(testRecipe));
    }

    @Test
    public void testGetRecipes_Empty() {
        assertTrue(recipeBook.getRecipes().isEmpty());
    }
}
