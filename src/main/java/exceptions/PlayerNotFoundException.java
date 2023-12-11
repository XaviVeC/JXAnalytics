package exceptions;

public class PlayerNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

	public PlayerNotFoundException(String name) {
		super("User with name: "+name+" was not found"); // Error message
	}
}
