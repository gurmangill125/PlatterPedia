package ui;

import model.RecipeBook;
import model.Recipe;
import persistence.JsonFileHandler;

import javax.swing.*;
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

    public GuiApp() {
        UIManager.put("ToolTip.background", Color.lightGray);
        UIManager.put("ToolTip.foreground", Color.black);
        UIManager.put("ToolTip.font", new Font("SansSerif", Font.BOLD, 12));
        getContentPane().setBackground(Color.WHITE);

        addIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Plus.png");
        viewIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Cooking Book.png");
        rateIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Star Filled.png");
        deleteIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Delete.png");
        saveIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Save.png");
        loadIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Import.png");

        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Rosalia.otf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        recipeBook = new RecipeBook();
        setSize(1920, 1080);

        GradientLabel titleLabel = new GradientLabel("PlatterPedia", new Color(76, 175, 80), new Color(139, 195, 74));
        titleLabel.setFont(customFont.deriveFont(Font.BOLD)); // make font bold
        titleLabel.setForeground(Color.WHITE); // make the font color white
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(3, 3));
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel inputLabel = new JLabel("Welcome to PlatterPedia, your digital Recipe manager.");
        inputLabel.setFont(customFont.deriveFont(17f));
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        southPanel.add(inputLabel);

        inputField = new JTextField();
        southPanel.add(inputField);

        addButton = createButton(addIcon, new AddAction(), "Add recipe", new Color(76, 175, 80), new Color(139, 195, 74));
        viewButton = createButton(viewIcon, new ViewAction(), "View recipe", new Color(76, 175, 80), new Color(139, 195, 74));
        rateButton = createButton(rateIcon, new RateAction(), "Rate recipe (1 - 5 stars)", new Color(76, 175, 80), new Color(139, 195, 74));
        deleteButton = createButton(deleteIcon, new DeleteAction(), "Delete recipe", new Color(76, 175, 80), new Color(139, 195, 74));
        saveButton = createButton(saveIcon, new SaveAction(), "Save recipes", new Color(76, 175, 80), new Color(139, 195, 74));
        loadButton = createButton(loadIcon, new LoadAction(), "Load recipes", new Color(76, 175, 80), new Color(139, 195, 74));

        southPanel.add(inputField);
        southPanel.add(addButton);
        southPanel.add(viewButton);
        southPanel.add(rateButton);
        southPanel.add(deleteButton);
        southPanel.add(saveButton);
        southPanel.add(loadButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private GradientButton createButton(ImageIcon icon, ActionListener listener, String tooltip, Color color1, Color color2) {
        GradientButton button = new GradientButton(icon, listener, tooltip, color1, color2);
        button.setForeground(Color.WHITE);
        return button;
    }

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
                }
            } else {
                displayArea.setText(String.join("\n", recipeBook.listRecipeTitles()));
            }
        }
    }

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
                } catch (IllegalArgumentException ex) {
                    displayArea.setText("No recipe found with the given title.");
                }
            }
        }
    }

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
                }
            }
        }
    }

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
            }
        }
    }

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
            }
        }
    }

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

}
