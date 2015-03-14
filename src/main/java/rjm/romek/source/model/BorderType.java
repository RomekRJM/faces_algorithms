package rjm.romek.source.model;

public enum BorderType {
	LAND(1), SEA(2), UNSPECIFIED(2);
	
	transient private int distance;
	
	BorderType(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}
}
