package entity;

import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import exceptions.*;
import utils.Tournament;
import utils.TournamentNetwork;

/**
 * Super User containing common routines between Player and Game Analyst
 *
 */
public class User {
	// ATRIBUTOS
	protected String username;
	protected TournamentNetwork currentTournamentNetwork;

		//Constructor
	public User(String username, TournamentNetwork tNetwork) {
		this.username = username;
		currentTournamentNetwork = tNetwork;
	}

	public String visualizePlayerStats(String username){
		String stats="";
		final Summoner summoner = Summoner.named(username).withRegion(Region.EUROPE_WEST).get();
		summoner.getChampionMasteries().toString();
		return stats;
	}


	public String visualizeTournamentStats(String tid){
		String stats="";
		/*try{
			
		} catch (TournamentNotFoundException e) {
			
		}*/
		return stats;
	}

		//Getters
	public String getUsername() {
		return username;
	}


}
