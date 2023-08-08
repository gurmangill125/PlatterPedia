package ui;

import model.RecipeBook;
import model.Recipe;
import persistence.JsonFileHandler;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
import org.json.JSONArray;

import java.awt.Color;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


// GuiApp is a JFrame that contains a user interface for interacting with a RecipeBook.
// It provides text fields for user input, buttons for executing actions, and a text area for displaying information to
// the user.

// CITATION: Consistently referenced the following programs when designing this class and its methods:
//           SmartHome - https://learning.edge.edx.org/course/course-v1:UBC+CPSC210+2023S2/block-v1:UBC+CPSC210+2023S2+type@sequential+block@97dffa7a4f534de695168e0d7b3d7b13/block-v1:UBC+CPSC210+2023S2+type@vertical+block@b345298bfcf04f47b2b1c0759bcc5b47
//           AlarmSystem - https://learning.edge.edx.org/course/course-v1:UBC+CPSC210+2023S2/block-v1:UBC+CPSC210+2023S2+type@sequential+block@ce6f3a154cc64f2c98be9b33d8ff11d7/block-v1:UBC+CPSC210+2023S2+type@vertical+block@da1b89f8eda74802bb08cb4518f17781
public class GuiApp extends JFrame {
    private RecipeBook recipeBook;

    private JTextArea displayArea;
    private JTextField inputField;
    private JButton addButton;
    private JButton viewButton;
    private JButton rateButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;
    private ImageIcon addIcon;
    private ImageIcon viewIcon;
    private ImageIcon rateIcon;
    private ImageIcon deleteIcon;
    private ImageIcon saveIcon;
    private ImageIcon loadIcon;

    // EFFECTS: Initializes the user interface.
    public GuiApp() {
        initUI();
    }

    // EFFECTS: Sets up UI properties, loads icons, sets up recipe book, and adds components.
    private void initUI() {
        setUpUIProperties();
        loadIcons();
        Font customFont = loadCustomFont();
        Font textFont = loadTextFont();
        setUpRecipeBook();
        addTitleLabel();
        addDisplayArea(textFont);
        JPanel southPanel = createSouthPanel(customFont);
        add(southPanel, BorderLayout.SOUTH);
        addComponents(customFont);
    }

    // EFFECTS: Sets up UI properties like tooltip color, font and background.
    private void setUpUIProperties() {
        UIManager.put("ToolTip.background", Color.lightGray);
        UIManager.put("ToolTip.foreground", Color.black);
        UIManager.put("ToolTip.font", new Font("SansSerif", Font.BOLD, 12));
        getContentPane().setBackground(Color.WHITE);
    }

    // EFFECTS: Loads in image icons from file paths
    private void loadIcons() {
        addIcon = new ImageIcon("data/resources/Plus.png");
        viewIcon = new ImageIcon("data/resources/Cooking Book.png");
        rateIcon = new ImageIcon("data/resources/Star Filled.png");
        deleteIcon = new ImageIcon("data/resources/Delete.png");
        saveIcon = new ImageIcon("data/resources/Save.png");
        loadIcon = new ImageIcon("data/resources/Import.png");
    }

    // REQUIRES: Font file to exist at the specified location.
    // MODIFIES: this
    // EFFECT: Loads custom font from a file path, returns the font.
    // CITATION: Code design was based on the following Stack Overflow post:
    //           https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
    private Font loadCustomFont() {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/resources/Rosalia.otf")).deriveFont(24f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return customFont;
    }

    // REQUIRES: Font file to exist at the specified location.
    // MODIFIES: this
    // EFFECT: Loads custom font from a file path, returns the font.
    // CITATION: Code design was based on the following Stack Overflow post:
    //           https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
    private Font loadTextFont() {
        Font textFont = null;
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/resources/Tommy.otf")).deriveFont(24f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(textFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return textFont;
    }

    // MODIFIES: this
    // EFFECTS: initializes the recipe book and sets the size of the window
    private void setUpRecipeBook() {
        recipeBook = new RecipeBook();
        setSize(1920, 1080);
    }

    // REQUIRES: a customFont to be set for the UI components
    // MODIFIES: this
    // EFFECTS: adds the title label and display area, and adds the south panel with components
    private void addComponents(Font customFont) {
        addTitleLabel();
        addDisplayArea(loadTextFont());
        JPanel southPanel = createSouthPanel(customFont);
        add(southPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a title label with a gif to the UI
    private void addTitleLabel() {
        ImageIcon gifIcon = new ImageIcon("data/resources/logo.gif");

        Color color1 = new Color(76, 175, 80);
        Color color2 = new Color(139, 195, 74);

        GradientLabel gifLabel = new GradientLabel("", color1, color2);
        gifLabel.setIcon(gifIcon);
        gifLabel.setPreferredSize(new Dimension(100, 100));
        add(gifLabel, BorderLayout.NORTH);
    }


    // MODIFIES: this
    // EFFECTS: creates and adds a non-editable text area for displaying information, added to a scroll pane
    private void addDisplayArea(Font textFont) {
        displayArea = new JTextArea();
        displayArea.setFont(textFont.deriveFont(17f));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, BorderLayout.CENTER);
    }

    // REQUIRES: a customFont to be set for the UI components
    // MODIFIES: this
    // EFFECTS: creates and sets up a panel in the south layout of the UI
    private JPanel createSouthPanel(Font customFont) {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(3, 3));
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addComponentsToSouthPanel(southPanel, customFont);
        return southPanel;
    }

    // REQUIRES: a JPanel to add components and a customFont to be set for the labels
    // MODIFIES: this, southPanel
    // EFFECTS: adds components such as labels, text field and buttons to the provided panel
    private void addComponentsToSouthPanel(JPanel southPanel, Font customFont) {
        JLabel inputLabel = new JLabel("Welcome to PlatterPedia, your digital Recipe manager.");
        inputLabel.setFont(customFont.deriveFont(17f));
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        southPanel.add(inputLabel);

        inputField = new JTextField();
        southPanel.add(inputField);
        addButton = createButton(addIcon, new AddAction(), "Add recipe");
        viewButton = createButton(viewIcon, new ViewAction(), "View recipe");
        rateButton = createButton(rateIcon, new RateAction(), "Rate recipe (1 - 5 stars)");
        deleteButton = createButton(deleteIcon, new DeleteAction(), "Delete recipe");
        saveButton = createButton(saveIcon, new SaveAction(), "Save recipes");
        loadButton = createButton(loadIcon, new LoadAction(), "Load recipes");

        southPanel.add(inputField);
        southPanel.add(addButton);
        southPanel.add(viewButton);
        southPanel.add(rateButton);
        southPanel.add(deleteButton);
        southPanel.add(saveButton);
        southPanel.add(loadButton);
    }

    // REQUIRES: an icon for the button, an action listener to handle button events, a tooltip string
    // MODIFIES: this
    // EFFECTS: creates a button with specified icon, action listener and tooltip, and returns it
    private GradientButton createButton(ImageIcon icon, ActionListener listener, String tooltip) {
        Color c1 = new Color(76, 175, 80);
        Color c2 = new Color(139, 195, 74);
        GradientButton button = new GradientButton(icon, listener, tooltip, c1, c2);
        button.setForeground(Color.WHITE);
        return button;
    }

    // MODIFIES: this, recipeBook
    // EFFECTS: handles the event when the Add button is pressed. Creates a new recipe with inputted details and
    //          adds it to the recipe book.
    private class AddAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = inputField.getText();
            if (!title.trim().isEmpty()) {
                String details = JOptionPane.showInputDialog("Enter details for " + title);
                if (details != null) {
                    recipeBook.addRecipe(new Recipe(title, details));
                    inputField.setText("");
                    displayArea.setText(title + " added.");
                    playSuccessSound();
                }
            }
        }
    }

    // REQUIRES: a recipe with the specified title to exist in the recipeBook
    // MODIFIES: this
    // EFFECTS: handles the event when the View button is pressed. Displays the details of the selected recipe or
    //          a list of all recipes if no title is entered.
    private class ViewAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String title = inputField.getText();
            if (!title.trim().isEmpty()) {
                try {
                    Recipe recipe = recipeBook.getRecipe(title);
                    displayArea.setText(recipe.toString());
                } catch (IllegalArgumentException ex) {
                    displayArea.setText("No recipe found with the given title.");
                    playErrorSound();
                }
            } else {
                displayArea.setText(String.join("\n", recipeBook.listRecipeTitles()));
            }
        }
    }

    // REQUIRES: a recipe with the specified title to exist in the recipeBook, and a valid integer as input for rating
    // MODIFIES: this, recipeBook
    // EFFECTS: handles the event when the Rate button is pressed. Rates the selected recipe with user inputted rating.
    private class RateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = inputField.getText();
            if (!title.trim().isEmpty()) {
                String ratingStr = JOptionPane.showInputDialog("Enter a rating for " + title);
                try {
                    int rating = Integer.parseInt(ratingStr);
                    recipeBook.rateRecipe(title, rating);
                    inputField.setText("");
                    displayArea.setText(title + " rated.");
                    playSuccessSound();
                } catch (NumberFormatException ex) {
                    displayArea.setText("Invalid rating. Please enter a number.");
                    playErrorSound();
                } catch (IllegalArgumentException ex) {
                    displayArea.setText("No recipe found with the given title.");
                    playErrorSound();
                }
            }
        }
    }

    // REQUIRES: a recipe with the specified title to exist in the recipeBook
    // MODIFIES: this, recipeBook
    // EFFECTS: handles the event when the Delete button is pressed. Deletes the selected recipe from the recipeBook.
    private class DeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            if (!input.trim().isEmpty()) {
                try {
                    recipeBook.deleteRecipe(input);
                    inputField.setText("");
                    displayArea.setText(input + " deleted.");
                    playSuccessSound();
                } catch (IllegalArgumentException ex) {
                    displayArea.setText("No recipe found with the given title.");
                    playErrorSound();
                }
            }
        }
    }

    // MODIFIES: this, external file "myFile.json"
    // EFFECTS: handles the event when the Save button is pressed. Saves the current recipes to a json file.
    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JSONArray jsonArray = recipeBook.toJson();
                JsonFileHandler.writeJsonFile("myFile.json", jsonArray);
                displayArea.setText("Recipes saved to ./data/myFile.json");
                playSuccessSound();
            } catch (IOException ex) {
                displayArea.setText("Error writing to file.");
                playErrorSound();
            }
        }
    }

    // REQUIRES: the file with the name "myFile.json" to exist and to be readable
    // MODIFIES: this, recipeBook
    // EFFECTS: handles the event when the Load button is pressed. Loads recipes from a json file into the recipeBook.
    private class LoadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JSONArray jsonArray = JsonFileHandler.readJsonFile("myFile.json");
                recipeBook = RecipeBook.fromJson(jsonArray);
                displayArea.setText("Recipes loaded from file.");
                playSuccessSound();
            } catch (IOException ex) {
                displayArea.setText("Error reading file.");
                playErrorSound();
            }
        }
    }

    // EFFECTS: plays a success sound
    // CITATION: Some components were based on the following YouTube video from "Bro Code"
    //           https://www.youtube.com/watch?v=SyZQVJiARTQ
    private void playSuccessSound() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("data/resources/success.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: plays an error sound
    // CITATION: Some components were based on the following YouTube video from "Bro Code"
    //           https://www.youtube.com/watch?v=SyZQVJiARTQ
    private void playErrorSound() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("data/resources/error.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
