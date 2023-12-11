package utils;

import java.util.ArrayList;
import java.util.Iterator;

import entity.*;
import exceptions.PlayerNotFoundException;

public class Tournament {
    private int idTournament;
    private String name;
    private int nPlayers;
    private ArrayList<Player> players;
    private String gameMode;

        //Constructor
    public Tournament(int id, String n, String gm){
        this.idTournament = id;
        this.name = n;
        this.players = new ArrayList<>();
        this.nPlayers = 0;
        this.gameMode = gm;
    }



            //GETTERS AND SETTERS

    public int getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(int idTournament) {
        this.idTournament = idTournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        nPlayers++;
    }
    
     // Method to delete a player from the tournament by name
     public void deletePlayer(String playerName) throws PlayerNotFoundException {
        Iterator<Player> iterator = players.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getUsername().equals(playerName)) {
                iterator.remove();
                nPlayers--;
                found = true;
            }
        }
        if(!found){ throw new PlayerNotFoundException(playerName); /*Player not found */ }
    }

    // Method to search for a player by name
    public Player searchPlayerByName(String playerName) throws PlayerNotFoundException {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getUsername().equals(playerName)) {
                return player;
            }
        }
        throw new PlayerNotFoundException(playerName); // Player not found
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    
    

}
