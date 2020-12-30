package day8_2020;

public class GameCommand {
	
	public String command;
	public int argument;
	
	public GameCommand(String cmd, int arg) {
		this.command = cmd;
		this.argument = arg;
	}

	@Override
	public String toString() {
		return command + " " + argument;
	}
}
