import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// TODO: Auto-generated Javadoc

/**
 * The Class GameView.
 * @author Vaibhav V. Jadhav
 * @version 2.0
 * @since November 06, 2019
 */
public class GameView extends JFrame {
    
    /** The j buttons. */
    private JButton[][] jButtons = new JButton[3][3];
    
    /** The text area. */
    private JTextArea textArea;
    
    /** The player name text. */
    private JTextField playerText, playerNameText;

    /**
     * Instantiates a new game view.
     */
    public GameView() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        createChildPanels(mainPanel, constraints);
        setSize(600, 400);
        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates the child panels.
     *
     * @param mainPanel the main panel
     * @param constraints the constraints
     */
    public void createChildPanels(JPanel mainPanel, GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = 0;
        populateGrid(mainPanel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        populateTextArea(mainPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        playerText = new JTextField();
        populateLabelTextField(mainPanel, constraints, "Player: ", " ", 30, 20, playerText);
        constraints.gridx = 0;
        constraints.gridy = 2;
        playerNameText = new JTextField();
        populateLabelTextField(mainPanel, constraints, "User Name: ", " ", 150, 20, playerNameText);
    }

    /**
     * Populate grid.
     *
     * @param mainPanel the main panel
     * @param constraints the constraints
     */
    public void populateGrid(JPanel mainPanel, GridBagConstraints constraints) {
        JPanel gridPanel = new JPanel();
        gridPanel.setSize(500, 400);
        gridPanel.setLayout(new GridLayout(3, 3, 5, 5));
        for(int i = 0; i < jButtons.length; i++) {
            for(int j = 0; j < jButtons.length; j++ ) {
                jButtons[i][j] = new JButton();
                jButtons[i][j].setPreferredSize(new Dimension(60, 60));
                initializeViews(gridPanel, jButtons[i][j]);
            }
        }
        mainPanel.add(gridPanel, constraints);
    }

    /**
     * Populate text area.
     *
     * @param mainPanel the main panel
     * @param constraints the constraints
     */
    public void populateTextArea(JPanel mainPanel, GridBagConstraints constraints) {
        JPanel textPanel = new JPanel(new BorderLayout(5, 5));
        textPanel.setSize(200, 200);
        JLabel label = new JLabel("Message Window: ");
        textArea = new JTextArea("Game is not yet started", 5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(true);
        textPanel.add(label, BorderLayout.NORTH);
        textPanel.add(textArea);
        mainPanel.add(textPanel, constraints);
    }

    /**
     * Populate label text field.
     *
     * @param mainPanel the main panel
     * @param constraints the constraints
     * @param labelName the label name
     * @param textName the text name
     * @param width the width
     * @param height the height
     * @param textField the text field
     */
    public void populateLabelTextField(JPanel mainPanel, GridBagConstraints constraints,
                                       String labelName, String textName, int width, int height, JTextField textField) {
        JPanel textPanel = new JPanel();
        textPanel.setSize(100, 100);
        JLabel label = new JLabel(labelName);
        textField.setText(textName);
        textField.setPreferredSize(new Dimension(width, height));
        textPanel.add(label);
        textPanel.add(textField);
        mainPanel.add(textPanel, constraints);
    }

    /**
     * Initialize views.
     *
     * @param panel the panel
     * @param button the button
     */
    public void initializeViews(JPanel panel, JButton button) {
        panel.add(button);
    }

    /**
     * Gets the players name.
     *
     * @param message the message
     * @return the players name
     */
    public String getPlayersName(String message) {
        String name = JOptionPane.showInputDialog(message);
        return name;
    }

    /**
     * Creates the message dialog.
     *
     * @param message the message
     */
    public void createMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
        dispose();
    }

    /**
     * Creates the action listener.
     *
     * @param listener the listener
     */
    public void createActionListener(ActionListener listener) {
        for(int i = 0; i < jButtons.length; i++) {
            for(int j = 0; j < jButtons.length; j++ ) {
                jButtons[i][j].addActionListener(listener);
            }
        }
    }

    /**
     * Adds the mark on view.
     *
     * @param source the source
     * @param mark the mark
     */
    public void addMarkOnView(int row, int col, char mark) {
        jButtons[row][col].setText(String.valueOf(mark));
    }

    /**
     * Gets the position of buttons.
     *
     * @param source the source
     * @param mark the mark
     * @return the position of buttons
     */
    public int[] getPositionOfButtons(JButton source) {
        for(int i = 0; i < jButtons.length; i++) {
            for(int j = 0; j < jButtons.length; j++ ) {
                if(source == jButtons[i][j]) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Sets the text area.
     *
     * @param textArea the new text area
     */
    public void setTextArea(String textArea) {
        this.textArea.setText(textArea);
    }

    /**
     * Sets the player text.
     *
     * @param playerText the new player text
     */
    public void setPlayerText(String playerText) {
        this.playerText.setText(playerText);
    }

    /**
     * Sets the player name text.
     *
     * @param playerNameText the new player name text
     */
    public void setPlayerNameText(String playerNameText) {
        this.playerNameText.setText(playerNameText);
    }
}
