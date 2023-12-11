package exceptions;

public class TournamentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

	public TournamentNotFoundException(String name) {
		super("Tournament with name: "+name+" was not found"); // Error message
	}

    public TournamentNotFoundException(int tid) {
		super("Tournament with ID: "+tid+" was not found");
	}
}

