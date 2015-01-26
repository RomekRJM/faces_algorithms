package rjm.romek.source.model;

public class Person {
	private String name;
	private String fileUuid;
	
	public Person(String name, String fileUuid) {
		this.name = name;
		this.fileUuid = fileUuid;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFileUuid() {
		return fileUuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileUuid == null) ? 0 : fileUuid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		if (fileUuid == null) {
			if (other.fileUuid != null)
				return false;
		} else if (!fileUuid.equals(other.fileUuid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
