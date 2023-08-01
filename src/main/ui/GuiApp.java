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
        initUI();
    }

    private void initUI() {
        setUpUIProperties();
        loadIcons();
        Font customFont = loadCustomFont();
        setUpRecipeBook();
        addComponents(customFont);
    }

    private void setUpUIProperties() {
        UIManager.put("ToolTip.background", Color.lightGray);
        UIManager.put("ToolTip.foreground", Color.black);
        UIManager.put("ToolTip.font", new Font("SansSerif", Font.BOLD, 12));
        getContentPane().setBackground(Color.WHITE);
    }


    private void loadIcons() {
        addIcon = new ImageIcon("data/resources/Plus.png");
        viewIcon = new ImageIcon("data/resources/Cooking Book.png");
        rateIcon = new ImageIcon("data/resources/Star Filled.png");
        deleteIcon = new ImageIcon("data/resources/Delete.png");
        saveIcon = new ImageIcon("data/resources/Save.png");
        loadIcon = new ImageIcon("data/resources/Import.png");
    }

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

    private void setUpRecipeBook() {
        recipeBook = new RecipeBook();
        setSize(1920, 1080);
    }

    private void addComponents(Font customFont) {
        addTitleLabel(customFont);
        addDisplayArea();
        JPanel southPanel = createSouthPanel(customFont);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addTitleLabel(Font customFont) {
        GradientLabel titleLabel = new GradientLabel("PlatterPedia", new Color(76, 175, 80), new Color(139, 195, 74));
        titleLabel.setFont(customFont.deriveFont(Font.BOLD));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    }

    private void addDisplayArea() {
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createSouthPanel(Font customFont) {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(3, 3));
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addComponentsToSouthPanel(southPanel, customFont);
        return southPanel;
    }

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

    private GradientButton createButton(ImageIcon icon, ActionListener listener, String tooltip) {
        Color c1 = new Color(76, 175, 80);
        Color c2 = new Color(139, 195, 74);
        GradientButton button = new GradientButton(icon, listener, tooltip, c1, c2);
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
                    playErrorSound();
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
                    playErrorSound();
                } catch (IllegalArgumentException ex) {
                    displayArea.setText("No recipe found with the given title.");
                    playErrorSound();
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
                    playErrorSound();
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
                playErrorSound();
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
                playErrorSound();
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
