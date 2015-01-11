package rjm.romek.source.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Country {
	private String flag;
	private String flagFile;
	private String name;
	private String countryDir;
	private boolean disabled;

	private Set<Border> neighbours;
	
	public Country() {
		neighbours = new LinkedHashSet<Border>();
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getFlagFile() {
		return flagFile;
	}

	public void setFlagFile(String flagFile) {
		this.flagFile = flagFile;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCountryDir() {
		return countryDir;
	}

	public void setCountryDir(String countryDir) {
		this.countryDir = countryDir;
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

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "Country [flag=" + flag + ", flagFile=" + flagFile + ", name="
				+ name + ", countryDir=" + countryDir + ", disabled="
				+ disabled + ", neighbours=" + neighbours + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((countryDir == null) ? 0 : countryDir.hashCode());
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result
				+ ((flagFile == null) ? 0 : flagFile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (countryDir == null) {
			if (other.countryDir != null)
				return false;
		} else if (!countryDir.equals(other.countryDir))
			return false;
		if (disabled != other.disabled)
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (flagFile == null) {
			if (other.flagFile != null)
				return false;
		} else if (!flagFile.equals(other.flagFile))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (neighbours == null) {
			if (other.neighbours != null)
				return false;
		} else if (!neighbours.equals(other.neighbours))
			return false;
		return true;
	}

}
