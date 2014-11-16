package rjm.romek.source.model;
import java.util.HashSet;
import java.util.Set;

public class Country {
	private String flag;
	private String name;
	private String photoFolder;

	private Set<Border> neighbours;
	
	public Country() {
		neighbours = new HashSet<Border>();
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhotoFolder() {
		return photoFolder;
	}
	
	public void setPhotoFolder(String photoFolder) {
		this.photoFolder = photoFolder;
	}
	
	public Set<Border> getBorders() {
		return neighbours;
	}
	
	public void setNeighbours(Set<Border> neighbours) {
		this.neighbours = neighbours;
	}
	
	public void addNeighbour(Border neighbour) {
		neighbours.add(neighbour);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photoFolder == null) ? 0 : photoFolder.hashCode());
		result = prime * result
				+ ((neighbours == null) ? 0 : neighbours.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Country))
			return false;
		Country other = (Country) obj;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photoFolder == null) {
			if (other.photoFolder != null)
				return false;
		} else if (!photoFolder.equals(other.photoFolder))
			return false;
		if (neighbours == null) {
			if (other.neighbours != null)
				return false;
		} else if (!neighbours.equals(other.neighbours))
			return false;
		return true;
	}
}
