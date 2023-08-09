# PlatterPedia 🧑‍🍳🥘
## _A Personalized Digital Cookbook and Recipe Manager_

**PlatterPedia** is a user-friendly Java application designed to serve as a personal culinary database. It enables users to store, view, and manage their favorite recipes in an efficient manner.

### What Will the Application Do?

With PlatterPedia, users can:
- **Input new recipes:** Users can add new recipes, including details like name, list of ingredients, and cooking instructions.
- **View stored recipes:** All the recipes stored in the application can be viewed in an organized and readable format.
- **Search for recipes:** Users can search for specific recipes by name, allowing quick and easy access.
- **Remove recipes:** Any recipe that the user no longer needs can be removed from the database.

### Who Will Use It?

The primary users of this application will be:
- Home cooks looking for a convenient way to store their recipes.
- Culinary enthusiasts who love exploring and documenting various cuisines.
- Anyone who wishes to keep track of their cherished recipes in a digital format.

### Why Is This Project of Interest?

As a computer scientist with a passion for problem-solving and an interest in cooking, this project provides an exciting intersection of my professional and personal interests. It not only presents a practical application of programming principles but also caters to a real-world need of managing and preserving culinary knowledge.

The benefits of this project include:
- It's a practical application of **object-oriented design** and **data persistence**.
- It caters to a real-world need, thereby offering a tool of genuine utility for food enthusiasts.
- It is manageable for a beginner programmer such as myself, yet offers room for enhancements as skills evolve.

**PlatterPedia** ultimately offers an engaging platform to apply and advance programming skills while simultaneously contributing to the world of culinary enthusiasts.

## User Stories

The following user stories depict the core functionalities and use-cases of the **PlatterPedia** application:

1. **Adding a Recipe**: As a user, I want to be able to add a new recipe to my RecipeBook in PlatterPedia.
2. **Viewing Recipes**: As a user, I want to be able to view the list of all the recipes I have added to my RecipeBook in PlatterPedia, where each recipe is represented by its title.
3. **Viewing Recipe Details**: As a user, I want to be able to select a recipe from my RecipeBook in PlatterPedia and view its complete details.
4. **Rating a Recipe**: As a user, I want to be able to select a recipe from my RecipeBook in PlatterPedia and rate it on a scale of one to five stars based on my personal experience with it.
5. **Deleting a Recipe**: As a user, I want to be able to select a recipe from my RecipeBook in PlatterPedia and delete it if I no longer want to keep it in my collection.
6. **Save a Recipe Book**: As a user, when I select the exit option from the PlatterPedia menu, I want to be reminded to save my to-do list to file and have the option to do so or not.
7. **Load a Recipe Book**: As a user, when I start PlatterPedia, I want to be given the option to load my to-do list from file.

# Instructions for Grader
**Note: If you hover over each icon, it will provide a helpful tool tip that indicates what the button does.**

To start the application, run **"Main"** located in the **ui package.**

- You can generate the first required action related to adding Xs (Recipes) to a Y (Recipe Book) by inputting a dish into the white box and clicking the plus (+) button from the menu. This will open up a window where you add your recipe's details. Once you're done inputting the details, click the "Okay" button to successfully add your recipe.
- You can generate the second required action related to adding Xs (Recipes) to a Y (Recipe Book) by deleting Recipes from the Recipe Book by inputting the recipe you'd wish to delete into the white box and clicking the garbage bin icon, which will delete the recipe.
- You can view a recipe in your recipe book by first inputting the recipe you want to view into the white box and clicking the large book button, which will open up all the details of the recipe. Additionally, you can view all your recipes by clicking the same book icon but having nothing in your input box. 
- By default, your recipe will be unrated and given a rating of -1. In order to rate a recipe, you input the recipe into the white box and click the star icon in order to give it a rating from 1-5. Once you've inputted the rating, click "Okay" and it will successfully have rated the recipe.
- You can locate my visual component by simply running the application. On startup, there is a splashscreen that appears for 5 seconds once the application runs.
- You can save the state of my application by clicking the Save Icon, which is a large floppy disk icon. This will save all recipe's in the recipe book. 
- You can reload the state of my application by clicking the Load Icon, which is an arrow pointing left into a file, which will load your save file.

# Phase 4: Task 2

Logged events:

Wed Aug 09 01:10:59 PDT 2023

Recipe added: Cookies

Wed Aug 09 01:11:01 PDT 2023

Recipe added: Pizza

Wed Aug 09 01:11:05 PDT 2023

Recipe rated: Cookies, Rating: 4

Wed Aug 09 01:11:08 PDT 2023

Recipe rated: Pizza, Rating: 3

Wed Aug 09 01:11:10 PDT 2023

Recipe rated: Cookies, Rating: 2

Wed Aug 09 01:11:12 PDT 2023

Recipe deleted: Pizza