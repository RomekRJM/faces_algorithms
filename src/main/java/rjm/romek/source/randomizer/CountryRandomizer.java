package rjm.romek.source.randomizer;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import rjm.romek.source.model.Border;
import rjm.romek.source.model.Country;

public class CountryRandomizer {
	
	private Set<Country> countries;
	private Random random;
	private static final int MAX_RANDOM_TRIES = 10;
	
	public CountryRandomizer(Set<Country> countries) {
		this.countries = countries;
		this.random = new Random();
	}

    public List<Country> randomCountriesFromDifferentContinents(Country startPoint, int size) {
        List<Country> neighbours = new ArrayList<Country>(size);
        int tries = MAX_RANDOM_TRIES;

        do {
            Country next = randomCountry();
            if(!neighbours.contains(next) && !StringUtils.equals(startPoint.getContinent(), next.getContinent())) {
                neighbours.add(next);
            } else {
                --tries;
            }

            if(tries == 0) {
                tries = MAX_RANDOM_TRIES;
            }
        } while (neighbours.size() < size);

        return neighbours;
    }

    public Country randomCountry() {
        return randomCountry(null);
    }

    public List<Country> randomNeighbours(Country startPoint, int radius, int size) {
        List<Country> neighbours = new ArrayList<Country>(size);
        int tries = MAX_RANDOM_TRIES;

        do {
            Country neighbour = randomNeighbour(startPoint, radius);
            if(!neighbours.contains(neighbour)) {
                neighbours.add(neighbour);
            } else {
                --tries;
            }

            if(tries == 0) {
                radius += 1;
                tries = MAX_RANDOM_TRIES;
            }
        } while (neighbours.size() < size);

        return neighbours;
    }
	
	public Country randomNeighbour(Country startPoint, int radius) {
		return randomNeighbour(startPoint, startPoint, radius);
	}
	
	private Country randomNeighbour(Country startPoint, Country current, int radius) {
		
		if(current != null && (current.getBorders() == null || current.getBorders().isEmpty())) {
			return randomCountry(startPoint);
		}

		Border border;
		
		if(current != null) {
			border = randomBorder(current, startPoint);
		} else {
			border = randomBorder(startPoint, startPoint);
		}
		
		radius -= border.getType().getDistance();
		current = findByName(border.getNeighbourName());
		if(radius <= 0) {
			if(!startPoint.equals(current)) {
				return current;
			} else {
				return randomCountry(startPoint);
			}			
		} else {
			return randomNeighbour(startPoint, current, radius);			
		}
	}

    private Country findByName(String name) {
        Country found = null;
        for(Country country : countries) {
            if(StringUtils.equals(country.getName(), name)) {
                found = country;
                break;
            }
        }

        return found;
    }

	private Country randomCountry(Country excluded) {
		int tries = MAX_RANDOM_TRIES;

		Country randomCountry;
		SetIndexGetter<Country> setIndexGetter = new SetIndexGetter<Country>();

		do {
			int index = random.nextInt(countries.size());
			randomCountry = setIndexGetter.get(countries, index);
			--tries;
		} while (tries > 0 && randomCountry.equals(excluded));

		return randomCountry;
	}

	private Border randomBorder(Country country, Country excluded) {
		int tries = MAX_RANDOM_TRIES;
		Set<Border> borders = country.getBorders();
		SetIndexGetter<Border> setIndexGetter = new SetIndexGetter<Border>();
		Border randomBorder;

		do {
			int index = random.nextInt(borders.size());
			randomBorder = setIndexGetter.get(borders, index);
			--tries;
		} while (tries >= 0 && StringUtils.equals(randomBorder.getNeighbourName(), excluded.getName()));

		return randomBorder;
	}
}
