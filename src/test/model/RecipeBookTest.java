package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents test cases for the RecipeBook class
public class RecipeBookTest {

    private RecipeBook recipeBook;
    private Recipe recipe1;
    private Recipe recipe2;

    // EFFECTS: sets up the testing environment before each test case
    @BeforeEach
    public void setUp() {
        recipeBook = new RecipeBook();
        recipe1 = new Recipe("Pancake", "Mix the ingredients and cook them on a pan.");
        recipe2 = new Recipe("Scrambled Egg", "Beat the eggs and cook them on a pan.");
    }

    @Test
    public void testAddRecipe() {
        recipeBook.addRecipe(recipe1);
        assertEquals(1, recipeBook.listRecipeTitles().size());
        assertTrue(recipeBook.listRecipeTitles().contains("Pancake"));
    }

    @Test
    public void testListRecipeTitles() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        List<String> titles = recipeBook.listRecipeTitles();
        assertTrue(titles.contains("Pancake"));
        assertTrue(titles.contains("Scrambled Egg"));
    }

    @Test
    public void testViewRecipe() {
        recipeBook.addRecipe(recipe1);
        Recipe result = recipeBook.viewRecipe("Pancake");
        assertEquals("Pancake", result.getTitle());
        assertEquals("Mix the ingredients and cook them on a pan.", result.getDetails());
    }

    @Test
    public void testViewRecipe_InvalidTitle() {
        assertThrows(IllegalArgumentException.class, () -> recipeBook.viewRecipe("Pizza"));
    }

    @Test
    public void testViewRecipe_EmptyBook() {
        assertThrows(IllegalArgumentException.class, () -> recipeBook.viewRecipe("Pancake"));
    }

    @Test
    public void testViewRecipe_MultipleRecipes() {
        recipeBook.addRecipe(recipe1); // "Pancake"
        recipeBook.addRecipe(recipe2); // "Scrambled Egg"
        Recipe result = recipeBook.viewRecipe("Scrambled Egg");
        assertEquals("Scrambled Egg", result.getTitle());
        assertEquals("Beat the eggs and cook them on a pan.", result.getDetails());
    }

    @Test
    public void testViewRecipe_DuplicateTitles() {
        Recipe recipe3 = new Recipe("Pancake", "A new method for making pancakes");
        recipeBook.addRecipe(recipe1); // "Pancake"
        recipeBook.addRecipe(recipe3); // Another "Pancake"
        Recipe result = recipeBook.viewRecipe("Pancake");
        assertEquals("Pancake", result.getTitle());
        assertEquals("Mix the ingredients and cook them on a pan.", result.getDetails());
    }

    @Test
    public void testViewRecipe_NonExistentTitle() {
        recipeBook.addRecipe(recipe1); // "Pancake"
        recipeBook.addRecipe(recipe2); // "Scrambled Egg"
        assertThrows(IllegalArgumentException.class, () -> recipeBook.viewRecipe("Pizza"));
    }

    @Test
    public void testRateRecipe() {
        recipeBook.addRecipe(recipe1);
        recipeBook.rateRecipe("Pancake", 5);
        assertEquals(5, recipeBook.viewRecipe("Pancake").getRating());
    }

    @Test
    public void testDeleteRecipe() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        recipeBook.deleteRecipe("Pancake");
        assertEquals(1, recipeBook.listRecipeTitles().size());
        assertFalse(recipeBook.listRecipeTitles().contains("Pancake"));
    }

    @Test
    public void testViewRecipe_MultipleRecipesWithSameTitle() {
        Recipe recipe3 = new Recipe("Pancake", "Different method for pancakes");
        recipeBook.addRecipe(recipe1);  // "Pancake"
        recipeBook.addRecipe(recipe3);  // Another "Pancake"
        Recipe result = recipeBook.viewRecipe("Pancake");
        assertEquals("Pancake", result.getTitle());
        assertEquals("Mix the ingredients and cook them on a pan.", result.getDetails());
    }

}
