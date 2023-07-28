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

import org.json.JSONArray;

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
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(75, 79, 87));
            UIManager.put("nimbusGrey", new Color(110, 113, 117));
            UIManager.put("control", new Color(110, 113, 117));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Load icons
        addIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Plus.png");
        viewIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Cooking Book.png");
        rateIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Star Filled.png");
        deleteIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Delete.png");
        saveIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Save.png");
        loadIcon = new ImageIcon("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Import.png");

        // Load custom font
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/gurmangill/Downloads/CPSC 210/project_f0y3q/data/resources/Rosalia.otf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        recipeBook = new RecipeBook();
        setSize(600, 400);
        setTitle("PlatterPedia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("PlatterPedia");
        titleLabel.setFont(customFont);
        titleLabel.setForeground(Color.black);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setForeground(Color.WHITE);
        displayArea.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(3, 2));
        southPanel.setBackground(Color.DARK_GRAY);

        inputField = new JTextField();
        addButton = createButton(addIcon, new AddAction());
        viewButton = createButton(viewIcon, new ViewAction());
        rateButton = createButton(rateIcon, new RateAction());
        deleteButton = createButton(deleteIcon, new DeleteAction());
        saveButton = createButton(saveIcon, new SaveAction());
        loadButton = createButton(loadIcon, new LoadAction());

        southPanel.add(inputField);
        southPanel.add(addButton);
        southPanel.add(viewButton);
        southPanel.add(rateButton);
        southPanel.add(deleteButton);
        southPanel.add(saveButton);
        southPanel.add(loadButton);

        add(southPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(ImageIcon icon, ActionListener listener) {
        JButton button = new JButton(icon);
        button.addActionListener(listener);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
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
            } catch (IOException ex) {
                displayArea.setText("Error reading file.");
            }
        }
    }
}
