package clueGame;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private boolean isComputer;
	private boolean isHuman;
	
	public Player(String name, String color, boolean isComputer) {
		this.name = name;
		this.color = color;
		this.isComputer = isComputer;
		this.isHuman = !isComputer;
	}
	
	public abstract void updateHand(Card card);
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}

	public boolean isComputer() {
		return isComputer;
	}

	public boolean isHuman() {
		return isHuman;
	}
}
