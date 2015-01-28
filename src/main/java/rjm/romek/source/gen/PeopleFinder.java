package rjm.romek.source.gen;

import java.util.Set;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Person;

public interface PeopleFinder {
	public Set<Person> findByCountry(Country country);
}
