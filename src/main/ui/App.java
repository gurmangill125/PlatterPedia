package ui;

import java.util.List;
import java.util.Scanner;
import model.RecipeBook;
import model.Recipe;

import java.io.IOException;
import org.json.JSONArray;
import persistence.JsonFileHandler;


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
    // CITATION: Following code inspired by TellerApp class in TellerApp project
    public void start() {
        if (askUser("Would you like to load your recipes from file? (y/n)")) {
            try {
                JSONArray jsonArray = JsonFileHandler.readJsonFile("myFile.json");
                recipeBook = RecipeBook.fromJson(jsonArray);
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }
        }
        String action;
        do {
            printMenu();
            action = scanner.nextLine();
            handleAction(action);
        } while (!action.equals("7"));
    }

    // EFFECTS: prints the menu of the application
    private void printMenu() {
        System.out.println("\n\nPlatterPedia RecipeBook:");
        System.out.println("1. Add a recipe");
        System.out.println("2. View all recipes");
        System.out.println("3. View a recipe in detail");
        System.out.println("4. Rate a recipe");
        System.out.println("5. Delete a recipe");
        System.out.println("6. Save recipes to file");
        System.out.println("7. Exit");
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
            case "6":
                handleSaveRecipeAction();
                break;
            case "7":
                handleExitAction();
                break;
        }
    }

    // EFFECTS: prints exit message
    private void handleExitAction() {
        System.out.println("Thank you for using PlatterPedia. Goodbye!");
        System.exit(0);
    }


    // EFFECTS: asks where to save recipe and prints whether successful or not
    private void handleSaveRecipeAction() {
        if (askUser("Would you like to save your recipes to file? (y/n)")) {
            try {
                JSONArray jsonArray = recipeBook.toJson();
                JsonFileHandler.writeJsonFile("myFile.json", jsonArray);
                System.out.println("Recipes saved to ./data/myFile.json");
            } catch (IOException e) {
                System.out.println("Error writing to file.");
            }
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

    // EFFECTS: Asks the user a question and returns true if the response is "y" or "yes",
    //          false otherwise
    private boolean askUser(String question) {
        System.out.print(question);
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

}
