package utils;

import java.util.HashMap;
import exceptions.TournamentNotFoundException;

public class TournamentNetwork {
    private HashMap<Integer, Tournament> tournamentMap;
        //Constructor
    public TournamentNetwork() {
        tournamentMap = new HashMap<>();
    }

        // Function to add a tournament to the network
    public void addTournament(Tournament tournament) {
        tournamentMap.put(tournament.getIdTournament(), tournament);
    }

    public void deleteTournament(int tournamentId) {
        tournamentMap.remove(tournamentId);
    }

        // Function to search for a tournament by ID
    public Tournament searchById(int id) throws TournamentNotFoundException {
        if(tournamentMap.containsKey(id)){
            return tournamentMap.get(id);
        } else {
            throw new TournamentNotFoundException(id);
        }
    }

        // Function to search for a tournament by name
    public Tournament searchByName(String name) throws TournamentNotFoundException {
        for (Tournament tournament : tournamentMap.values()) {
            if (tournament.getName().equals(name)) {
                return tournament;
            }
        }
        throw new TournamentNotFoundException(name);    //Not found
    }

        // Function to retrieve all tournaments
    public HashMap<Integer, Tournament> getAllTournaments() {
        return tournamentMap;
    }
}