package rjm.romek.source.randomizer;

import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rjm.romek.source.model.Border;
import rjm.romek.source.model.Country;

public class Randomizer {
	
	Logger logger = LoggerFactory.getLogger(Randomizer.class);
	
	private Set<Country> countries;
	private Random random;
	private static final int MAX_RANDOM_TRIES = 10;
	
	public Randomizer(Set<Country> countries) {
		this.countries = countries;
		this.random = new Random();
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
		current = border.getNeighbour();
		if(radius <= 0) {
			if(!startPoint.equals(current)) {
				logger.debug(startPoint.getName() + " not equals to " + ((current!=null) ? current.getName() : null));
				return current;
			} else {
				logger.debug(startPoint.getName() + " equals to " + ((current!=null) ? current.getName() : null) + ". Picking randomly.");
				return randomCountry(startPoint);
			}			
		} else {
			logger.debug("Going to: " + current.getName() + ", radius: " + radius );
			return randomNeighbour(startPoint, current, radius);			
		}
	}
	
	private Country randomCountry(Country excluded) {
		int tries = MAX_RANDOM_TRIES;
		logger.debug("Going to pick random country for: " + excluded.getName());
		Country randomCountry = null;
		SetIndexGetter<Country> setIndexGetter = new SetIndexGetter<Country>();
		
		do {
			int index = random.nextInt(countries.size());
			randomCountry = setIndexGetter.get(countries, index);
			--tries;
		} while (tries > 0 && randomCountry.equals(excluded));
		
		if(tries < 0) {
			logger.debug("random country exceeded");
		}
		
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
		} while (tries >= 0 && randomBorder.getNeighbour().equals(excluded));
		
		if(tries < 0) {
			logger.debug("random border exceeded");
		}
		
		return randomBorder;
	}
}
