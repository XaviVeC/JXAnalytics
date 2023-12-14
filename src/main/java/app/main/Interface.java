package app.main;

import javax.swing.*;

import utils.Tournament;
import utils.TournamentNetwork;

import java.awt.*;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Interface {

    private static String map = "";  // Variable to store the selected map
    
    
    private static TournamentNetwork tournamentNetwork=null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Image iconImage = Toolkit.getDefaultToolkit().getImage("Logo.png");
        frame.setIconImage(iconImage);

        // Create buttons for the main window
        JButton playerButton = new JButton("Player");
        JButton analystButton = new JButton("Analyst");

        // Add ActionListener for the player button
        playerButton.addActionListener(e -> {
            // Close the current window
            frame.dispose();

            // Open the main player window (you can replace this with your player window logic)
            openPlayerWindow();
        });

        // Add ActionListener for the analyst button
        analystButton.addActionListener(e ->
                // For now, show a simple message
                JOptionPane.showMessageDialog(frame, "Analyst button clicked!")
        );

        // Set layout and add buttons to the main window
        frame.setLayout(new GridLayout(2, 1, 10, 10));
        frame.add(playerButton);
        frame.add(analystButton);

        // Set size and make the window visible
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void openPlayerWindow() {
        JFrame playerFrame = new JFrame("Player Options");
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons for the player window
        JButton createTournamentButton = new JButton("Create Tournament");
        JButton searchUserButton = new JButton("Search User");
        JButton thirdButton = new JButton("Third");

        // Add ActionListener for the create tournament button
        createTournamentButton.addActionListener(e -> {
            
            // Show a popup to input the tournament name
            String tournamentName = JOptionPane.showInputDialog(playerFrame, "Enter Tournament Name:");

            // Check if the user clicked "Cancel"
            if (tournamentName == null) {
                // User clicked "Cancel," go back to the main player window
                playerFrame.setVisible(true);
                return;
            }

            // Open the tournament creation window
            Tournament t = openTournamentCreationWindow(playerFrame, tournamentName);
            if (tournamentNetwork==null){
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tournaments.bin"))) {
                    tournamentNetwork = (TournamentNetwork) ois.readObject();
                    tournamentNetwork.addTournament(t);
                } catch (IOException | ClassNotFoundException err) {
                    err.printStackTrace();
                }
            } else {
                tournamentNetwork.addTournament(t);
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tournaments.bin"))) {
                    oos.writeObject(tournamentNetwork);
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        });

        // Add ActionListener for the search user button
        searchUserButton.addActionListener(e ->
                // For now, show a simple message
                JOptionPane.showMessageDialog(playerFrame, "Search User button clicked!")
        );

        // Add ActionListener for the third button
        thirdButton.addActionListener(e ->
                // For now, show a simple message
                JOptionPane.showMessageDialog(playerFrame, "Third button clicked!")
        );

        // Set layout and add buttons to the player window
        playerFrame.setLayout(new GridLayout(3, 1, 10, 10));
        playerFrame.add(createTournamentButton);
        playerFrame.add(searchUserButton);
        playerFrame.add(thirdButton);

        // Set size and make the window visible
        playerFrame.setSize(300, 200);
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setVisible(true);
    }

    private static Tournament openTournamentCreationWindow(JFrame parentFrame, String tournamentName) {
        JFrame tournamentFrame = new JFrame("Tournament Creation");
        
        tournamentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create button for continue in tournament creation window
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false); // Disable initially until map is selected
        String randomTag = generateRandomTag();
        // Add ActionListener for the continue button
        continueButton.addActionListener(e -> {
            // Generate a random tag consisting of 5 letters and numbers

            // Show the random tag and decision (map) in a message
            JOptionPane.showMessageDialog(tournamentFrame, "Tournament TAG: " + randomTag + "\nSelected Map: " + map);

            // Close the tournament window and go back to the main player window
            tournamentFrame.dispose();
            parentFrame.setVisible(true);
        });

        // Create buttons with map names and icons for the second row
        JButton summonerButton = new JButton("Summoner");
        summonerButton.setIcon(new ImageIcon("icons/summoner.png"));

        JButton aramButton = new JButton("Aram");
        aramButton.setIcon(new ImageIcon("icons/aram.png"));

        JButton arenaButton = new JButton("Arena");
        arenaButton.setIcon(new ImageIcon("icons/arena.png"));

        // Add ActionListener for the three buttons in the second row
        ActionListener tournamentButtonAction = e -> {
            // Set the selected map based on the button clicked
            if (e.getSource() == summonerButton) {
                map = "Summoner";
            } else if (e.getSource() == aramButton) {
                map = "Aram";
            } else if (e.getSource() == arenaButton) {
                map = "Arena";
            }

            // Show the continue button
            continueButton.setEnabled(true);
        };
        summonerButton.addActionListener(tournamentButtonAction);
        aramButton.addActionListener(tournamentButtonAction);
        arenaButton.addActionListener(tournamentButtonAction);

        // Set layout and add components to the tournament creation window
        tournamentFrame.setLayout(new GridLayout(2, 1, 10, 10));

        // Add a panel for the first row
        JPanel firstRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        firstRowPanel.add(new JLabel("Tournament Name: " + tournamentName));
        tournamentFrame.add(firstRowPanel);

        // Add buttons to the second row
        JPanel secondRowPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        secondRowPanel.add(summonerButton);
        secondRowPanel.add(aramButton);
        secondRowPanel.add(arenaButton);
        tournamentFrame.add(secondRowPanel);

        // Add continue button to the bottom of the second row
        tournamentFrame.add(continueButton);

        // Set size and make the window visible
        tournamentFrame.setSize(400, 200);
        tournamentFrame.setLocationRelativeTo(parentFrame);
        tournamentFrame.setVisible(true);

        // Hide the parent frame (player window) while the tournament window is open
        parentFrame.setVisible(false);
        Tournament t = new Tournament(Integer.parseInt(randomTag), tournamentName, map);
        return t;
    }

    private static String generateRandomTag() {
        String characters = "0123456789";
        StringBuilder randomTag = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            randomTag.append(characters.charAt(randomIndex));
        }

        return randomTag.toString();
    }
}