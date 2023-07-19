package model;

public class Recipe {
    private String title;
    private String details;
    private int rating;

    public Recipe(String title, String details) {
        if (title == null || title.isEmpty() || details == null || details.isEmpty()) {
            throw new IllegalArgumentException("Title and details cannot be null or empty.");
        }

        this.title = title;
        this.details = details;
        this.rating = 0;  // Recipe is initially not rated
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

    public void rate(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nDetails: " + details + "\nRating: " + (rating == 0 ? "Not yet rated" : rating);
    }
}
