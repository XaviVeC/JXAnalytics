package app.main;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.common.Season;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import utils.Tournament;

public class Main { //TODO Prolly delete and make only GUI no console app
    public static void main(String[] args) {
        Orianna.setRiotAPIKey("RGAPI-52a1b023-f15c-4617-80ef-4674de59596b");
        Orianna.setDefaultRegion(Region.EUROPE_WEST);
        final Summoner summoner = Summoner.named("ElXavioMK09").get();
        System.out.println(summoner.getLevel());


        String filePath = "tournaments.json";

        // Create a new Tournament object
        Tournament newTournament = new Tournament("NewTournament", "NewMap", 12345);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
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