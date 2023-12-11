package entity;

import utils.TournamentNetwork;

/**
 * Class Player
 *
 */
public class Player extends User {

		//Constructor
	public Player(String username, TournamentNetwork tNetwork) {
		super(username,tNetwork);
		
	}
	

	public int createTournament(){
		int tid = -1;
		//TODO


		return tid;
	}
	
	public int joinTournament(String username){
		int code = -1;
		//TODO

		return code;
	}

	public int joinTournament(int tid){
		int code = -1;
		//TODO

		return code;
	}

	public int searchUser(String username){
		int code = -1;
		//TODO

		return code;	
	}

	public int sendReq(String username){
		int code = -1;
		//TODO

		return code;
	}

	public int acceptReq(){
		int code = -1;
		//TODO

		return code;
	}

	public int declineReq(){
		int code = -1;
		//TODO

		return code;
	}

	public int inviteTournament(String username, int tid){
		int code = -1;
		//TODO

		return code;
	}


}
