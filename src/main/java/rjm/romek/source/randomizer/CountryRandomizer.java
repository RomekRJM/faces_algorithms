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
		return randomNeighbour(startPoint, null, radius);
	}
	
	private Country randomNeighbour(Country startPoint, Country current, int radius) {
		
		if(current != null && (current.getBorders() == null || current.getBorders().isEmpty())) {
			return randomCountry(startPoint);
		}

		Border border = null;
		
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
	
	private Country randomCountry(Country excluded) {
		int tries = MAX_RANDOM_TRIES;

		Country randomCountry = null;
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
		Border randomBorder = null;
		
		do {
			int index = random.nextInt(borders.size());
			randomBorder = setIndexGetter.get(borders, index);
			--tries;
		} while (tries >= 0 && StringUtils.equals(randomBorder.getNeighbourName(), excluded.getName()));
		
		return randomBorder;
	}

    private Country findByName(String name) {
        for(Country country : countries) {
            if(StringUtils.equals(country.getName(), name)) {
                return country;
            }
        }

        return null;
    }
}
