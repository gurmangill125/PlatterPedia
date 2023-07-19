package ui;

import java.util.List;
import java.util.Scanner;
import model.RecipeBook;
import model.Recipe;

// Represents the user interface of the application.
public class App {
    private RecipeBook recipeBook;
    private Scanner scanner;

    // EFFECTS: initializes a new App with a new, empty RecipeBook and a new Scanner
    public App() {
        recipeBook = new RecipeBook();
        scanner = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: starts the application, provides a menu for user to interact with the app
    public void start() {
        String action;
        do {
            printMenu();
            action = scanner.nextLine();
            handleAction(action);
        } while (!action.equals("6"));

        System.out.println("Thank you for using PlatterPedia. Goodbye!");
    }

    // EFFECTS: prints the menu of the application
    private void printMenu() {
        System.out.println("\n\nPlatterPedia RecipeBook:");
        System.out.println("1. Add a recipe");
        System.out.println("2. View all recipes");
        System.out.println("3. View a recipe in detail");
        System.out.println("4. Rate a recipe");
        System.out.println("5. Delete a recipe");
        System.out.println("6. Exit");
        System.out.print("Choose an action: ");
    }

    // MODIFIES: this
    // EFFECTS: handles user action based on the input and calls corresponding methods
    private void handleAction(String action) {
        switch (action) {
            case "1":
                addRecipe();
                break;
            case "2":
                viewAllRecipes();
                break;
            case "3":
                viewRecipeDetail();
                break;
            case "4":
                rateRecipe();
                break;
            case "5":
                deleteRecipe();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new recipe to the recipe book
    private void addRecipe() {
        System.out.print("Enter recipe title: ");
        String title = scanner.nextLine();
        System.out.print("Enter recipe details: ");
        String details = scanner.nextLine();

        Recipe recipe = new Recipe(title, details);
        recipeBook.addRecipe(recipe);
    }

    // EFFECTS: prints all recipes in the recipe book or tells you that it is empty
    private void viewAllRecipes() {
        List<String> titles = recipeBook.listRecipeTitles();

        if (titles.isEmpty()) {
            System.out.println("No recipes in your collection yet.");
        } else {
            System.out.println("Your recipes:");
            titles.forEach(System.out::println);
        }
    }

    // EFFECTS: prints the details of the recipe with the given title or whether no recipe has the
    //          given the title
    private void viewRecipeDetail() {
        System.out.print("Enter recipe title: ");
        String title = scanner.nextLine();

        try {
            Recipe recipe = recipeBook.viewRecipe(title);
            System.out.println(recipe);
        } catch (IllegalArgumentException e) {
            System.out.println("No recipe found with the given title.");
        }
    }

    // REQUIRES: rating between 1 and 5 inclusive
    // MODIFIES: recipe
    // EFFECTS: sets the rating for the recipe with the given title
    private void rateRecipe() {
        System.out.print("Enter recipe title: ");
        String title = scanner.nextLine();
        System.out.print("Rate the recipe (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        try {
            recipeBook.rateRecipe(title, rating);
        } catch (IllegalArgumentException e) {
            System.out.println("No recipe found with the given title or invalid rating provided.");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the recipe with the given title
    private void deleteRecipe() {
        System.out.print("Enter recipe title: ");
        String title = scanner.nextLine();

        try {
            recipeBook.deleteRecipe(title);
            System.out.println("Recipe deleted.");
        } catch (IllegalArgumentException e) {
            System.out.println("No recipe found with the given title.");
        }
    }
}
