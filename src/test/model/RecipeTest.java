package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

    private Recipe recipe;

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
        assertEquals(0, recipe.getRating());
    }

    @Test
    public void testRate() {
        recipe.rate(4);
        assertEquals(4, recipe.getRating());
    }

    @Test
    public void testRate_TooLow() {
        assertThrows(IllegalArgumentException.class, () -> recipe.rate(0));
    }

    @Test
    public void testRate_TooHigh() {
        assertThrows(IllegalArgumentException.class, () -> recipe.rate(6));
    }

    @Test
    public void testToString() {
        recipe.rate(5);
        String expected = "Title: Pancake\nDetails: Mix the ingredients and cook them on a pan.\nRating: 5";
        assertEquals(expected, recipe.toString());
    }
}
