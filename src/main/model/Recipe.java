package model;

import org.json.JSONObject;

// Represents a single recipe in the system with title, details, and rating
public class Recipe {
    private String title;
    private String details;
    private static final int NO_RATING = 0;
    private int rating;

    // EFFECTS: initializes a new recipe with the given title and details, default rating is 0
    public Recipe(String title, String details) {
        this.title = title;
        this.details = details;
        this.rating = NO_RATING; // Default rating
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public boolean hasRating() {
        return this.rating != NO_RATING;
    }

    public int getRating() {
        return rating;
    }

    // REQUIRES: 1 =< rating =< 5
    // MODIFIES: this
    // EFFECTS: sets the rating of the recipe from 1-5 stars
    public void rate(int rating) {
        if (rating != NO_RATING && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    // The following code is inspired from the Account class in the TellerApp project:
    // EFFECTS: returns a string representation of the recipe, its details, and rating
    @Override
    public String toString() {
        return "Title: " + this.title + "\nDetails: " + this.details + "\nRating: " + this.rating;
    }

    // EFFECTS: returns this recipe as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("details", details);
        if (hasRating()) {
            json.put("rating", rating);
        }
        return json;
    }

    // EFFECTS: Returns a new recipe from a JSON object
    public static Recipe fromJson(JSONObject json) {
        String title = json.getString("title");
        String details = json.getString("details");
        Recipe recipe = new Recipe(title, details);
        if (json.has("rating")) {
            int rating = json.getInt("rating");
            recipe.rate(rating);
        }
        return recipe;
    }

}
