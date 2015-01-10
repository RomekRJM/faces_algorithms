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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((neighbour == null) ? 0 : neighbour.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Border))
			return false;
		Border other = (Border) obj;
		if (neighbour == null) {
			if (other.neighbour != null)
				return false;
		} else if (!neighbour.equals(other.neighbour))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Border [type=" + type + ", neighbour=" + neighbour + "]";
	}
	
}
