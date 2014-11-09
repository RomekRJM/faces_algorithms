package rjm.romek.source.model;

public class Border {
	private BorderType type;
	private Country neighbour;
	
	public Border(Country neighbour, BorderType type) {
		this.neighbour = neighbour;
		this.type = type;
	}
	
	public BorderType getType() {
		return type;
	}
	
	public void setType(BorderType type) {
		this.type = type;
	}
	
	public Country getNeighbour() {
		return neighbour;
	}
	
	public void setNeighbour(Country neighbour) {
		this.neighbour = neighbour;
	}
	
}
