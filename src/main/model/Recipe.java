package model;

// Represents a single recipe in the system with title, details, and rating
public class Recipe {
    private String title;
    private String details;
    private int rating;

    // EFFECTS: initializes a new recipe with the given title and details, default rating is 0
    public Recipe(String title, String details) {
        this.title = title;
        this.details = details;
        this.rating = 0; // Default rating
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public int getRating() {
        return rating;
    }

    // REQUIRES: 1 =< rating =< 5
    // MODIFIES: this
    // EFFECTS: sets the rating of the recipe from 1-5 stars
    public void rate(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    // EFFECTS: returns a string representation of the recipe, its details, and rating
    @Override
    public String toString() {
        return "Title: " + this.title + "\nDetails: " + this.details + "\nRating: " + this.rating;
    }

}
