package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

// Represents test cases for the Recipe class
public class RecipeTest {

    private Recipe recipe;

    // EFFECTS: sets up the testing environment before each test case
    @BeforeEach
    public void setUp() {
        recipe = new Recipe("Pancake", "Mix the ingredients and cook them on a pan.");
    }

    @Test
    public void testGetTitle() {
        assertEquals("Pancake", recipe.getTitle());
    }

    @Test
    public void testGetDetails() {
        assertEquals("Mix the ingredients and cook them on a pan.", recipe.getDetails());
    }

    @Test
    public void testGetRating_NotYetRated() {
        assertEquals(-1, recipe.getRating());
    }

    @Test
    public void testRate() {
        recipe.rate(4);
        assertEquals(4, recipe.getRating());
    }


    @Test
    public void testRate_TooHigh() {
        assertThrows(IllegalArgumentException.class, () -> recipe.rate(6));
    }

    @Test
    public void testRate_TooLow() {
        assertThrows(IllegalArgumentException.class, () -> recipe.rate(-1));
    }

    @Test
    public void testToString() {
        recipe.rate(5);
        String expected = "Title: Pancake\nDetails: Mix the ingredients and cook them on a pan.\nRating: 5";
        assertEquals(expected, recipe.toString());
    }

    @Test
    void testToJson() {
        Recipe recipe = new Recipe("TestTitle", "TestDetails");
        recipe.rate(4);

        JSONObject json = recipe.toJson();

        assertEquals("TestTitle", json.getString("title"));
        assertEquals("TestDetails", json.getString("details"));
        assertEquals(4, json.getInt("rating"));
    }

    @Test
    void testToJsonNoRating() {
        Recipe recipe = new Recipe("TestTitle", "TestDetails");

        JSONObject json = recipe.toJson();

        assertEquals("TestTitle", json.getString("title"));
        assertEquals("TestDetails", json.getString("details"));
        assertFalse(json.has("rating"));
    }

    @Test
    void testFromJson() {
        JSONObject json = new JSONObject();
        json.put("title", "TestTitle");
        json.put("details", "TestDetails");
        json.put("rating", 4);

        Recipe recipe = Recipe.fromJson(json);

        assertEquals("TestTitle", recipe.getTitle());
        assertEquals("TestDetails", recipe.getDetails());
        assertEquals(4, recipe.getRating());
    }

    @Test
    void testFromJsonNoRating() {
        JSONObject json = new JSONObject();
        json.put("title", "TestTitle");
        json.put("details", "TestDetails");

        Recipe recipe = Recipe.fromJson(json);

        assertEquals("TestTitle", recipe.getTitle());
        assertEquals("TestDetails", recipe.getDetails());
        assertFalse(recipe.hasRating());
    }

    @Test
    void testHasRating() {
        Recipe recipe = new Recipe("TestTitle", "TestDetails");
        assertFalse(recipe.hasRating());  // No rating has been set yet

        recipe.rate(4); // Set a rating
        assertTrue(recipe.hasRating()); // Now it should have a rating
    }
}
