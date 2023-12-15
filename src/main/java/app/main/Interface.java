package app.main;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.Tournament;
import utils.TournamentNetwork;

import java.awt.*;
//import java.awt.event.ActionEvent;
import com.merakianalytics.orianna.types.common.Queue;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;


import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region; 
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class Interface {

    private static String map = "";  // Variable to store the selected map
    
    
    private static TournamentNetwork tournamentNetwork;

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
        analystButton.addActionListener(e -> {
            // Create JFrame for entering player name
            JFrame playerNameFrame = new JFrame("Enter Player Name");
            playerNameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
            // Create components for player name entry
            JTextField playerNameField = new JTextField(20);
            JButton continueButton = new JButton("Continue");
        
            // Add ActionListener for continue button
            continueButton.addActionListener(actionEvent -> {
                // Get the entered player name
                String playerName = playerNameField.getText();
        
                // Close the current window
                playerNameFrame.dispose();
        
                // Open the window to display player information
                openPlayerInformationWindow(playerName);
            });
        
            // Set layout and add components to the player name entry window
            playerNameFrame.setLayout(new FlowLayout());
            playerNameFrame.add(new JLabel("Enter Player Name:"));
            playerNameFrame.add(playerNameField);
            playerNameFrame.add(continueButton);
        
            // Set size and make the window visible
            playerNameFrame.setSize(300, 130);
            playerNameFrame.setLocationRelativeTo(frame);
            playerNameFrame.setVisible(true);
        });

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
        JButton thirdButton = new JButton("Join Tournament");

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
            openTournamentCreationWindow(playerFrame, tournamentName);
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tournaments.bin", false))) {
                oos.writeObject(tournamentNetwork);
            } catch (IOException err) {
                err.printStackTrace();
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
                JOptionPane.showMessageDialog(playerFrame, "Introduce TAG to join tournament!")
        );

        // Set layout and add buttons to the player window
        playerFrame.setLayout(new GridLayout(3, 1, 10, 10));
        playerFrame.add(searchUserButton);
        playerFrame.add(createTournamentButton);

        playerFrame.add(thirdButton);

        // Set size and make the window visible
        playerFrame.setSize(300, 200);
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setVisible(true);
    }

    private static void openTournamentCreationWindow(JFrame parentFrame, String tournamentName) {
        JFrame tournamentFrame = new JFrame("Tournament Creation");
        
        tournamentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create button for continue in tournament creation window
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false); // Disable initially until map is selected
        String randomTag = generateRandomTag();
        // Add ActionListener for the continue button
        continueButton.addActionListener(e -> extracted(parentFrame, tournamentName, tournamentFrame, randomTag));

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
        tournamentFrame.setSize(700, 300);
        tournamentFrame.setLocationRelativeTo(parentFrame);
        tournamentFrame.setVisible(true);

        // Hide the parent frame (player window) while the tournament window is open
        parentFrame.setVisible(false);
        
    }

    private static void openPlayerInformationWindow(String playerName) {
        JFrame playerInfoFrame = new JFrame("Player Information");
        playerInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Orianna.setRiotAPIKey("RGAPI-52a1b023-f15c-4617-80ef-4674de59596b");
        Orianna.setDefaultRegion(Region.EUROPE_WEST);
        final Summoner summoner = Summoner.named(playerName).get();
        System.out.println(summoner.getLevel());
    
        // Create JLabels to display player information
        JLabel nameLabel = new JLabel("Player Name: " + playerName);
        JLabel lvlLabel = new JLabel("Summoner Level: " + summoner.getLevel());

        //Mastery champions

        /*final Champion leblanc = Champion.named("LeBlanc").withRegion(Region.EUROPE_WEST).get();
        final ChampionMastery cm = summoner.getChampionMastery(leblanc);*/
        //new JLabel("Mastery points " + cm.getPoints() + " and Mastery level "+ cm.getLevel() + "with LeBlanc");
        final LeagueEntry rankedFivesEntries = summoner.getLeaguePosition(Queue.RANKED_SOLO);
        JLabel elo = new JLabel("Ranked League: "+ rankedFivesEntries.getCoreData().getTier());
        JLabel div = new JLabel("Ranked division: "+rankedFivesEntries.getDivision().toString());



        // Create JButton for searching other players
        JButton searchOtherPlayerButton = new JButton("Return");
    
        // Add ActionListener for search other player button
        searchOtherPlayerButton.addActionListener(actionEvent -> {
            // Close the current window
            playerInfoFrame.dispose();
    
            // Perform actions to search other players (you can customize this logic)
            // For now, show a simple message
            //JOptionPane.showMessageDialog(null, "Return");
        });
    
        // Set layout and add components to the player information window
        playerInfoFrame.setLayout(new GridLayout(8, 1));
        playerInfoFrame.add(nameLabel);
        playerInfoFrame.add(lvlLabel);
        playerInfoFrame.add(elo);
        playerInfoFrame.add(div);
        // Add labels for other variables similarly...
        playerInfoFrame.add(searchOtherPlayerButton);
    
        // Set size and make the window visible
        playerInfoFrame.setSize(300, 150);
        playerInfoFrame.setLocationRelativeTo(null);
        playerInfoFrame.setVisible(true);
    }


    private static void extracted(JFrame parentFrame, String tournamentName, JFrame tournamentFrame, String randomTag) {
        // Show the random tag and decision (map) in a message
        JOptionPane.showMessageDialog(tournamentFrame, "Tournament TAG: " + randomTag + "\nSelected Map: " + map);
        String filePath = "tournaments.json";
        // Create a new Tournament object
        Tournament newTournament = new Tournament(tournamentName, map, Integer.parseInt(randomTag));
        try {
            // Step 1: Read the existing JSON array from the file
            JsonArray existingArray = readJsonArrayFromFile(filePath);

            // Step 2: Add the new object to the array
            JsonObject newObject = new JsonObject();
            newObject.addProperty("tournamentName", newTournament.getName());
            newObject.addProperty("map", newTournament.getGameMode());
            newObject.addProperty("tournamentTag", newTournament.getIdTournament());

            existingArray.add(newObject);

            // Step 3: Write the updated array back to the file
            writeJsonArrayToFile(filePath, existingArray);

            System.out.println("Object added successfully.");
        } catch (final IOException e) {
            e.printStackTrace();
        }
        // Close the tournament window and go back to the main player window
        tournamentFrame.dispose();
        parentFrame.setVisible(true);
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

        private static JsonArray readJsonArrayFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return jsonObject.getAsJsonArray("tournaments");
        }
    }

    private static void writeJsonArrayToFile(String filePath, JsonArray jsonArray) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("tournaments", jsonArray);

            Gson gson = new Gson();
            gson.toJson(jsonObject, writer);
        }
    }    
}