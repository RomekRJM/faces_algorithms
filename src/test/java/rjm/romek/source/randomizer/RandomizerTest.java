package rjm.romek.source.randomizer;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import rjm.romek.source.gen.CsvDeserializer;
import rjm.romek.source.model.Country;

public class RandomizerTest {

	@Test
	public void test() throws Exception {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				"src/main/resources/list.csv"));

		Randomizer randomizer = new Randomizer(countries);

		countries.iterator().next();
		countries.iterator().next();
		countries.iterator().next();
		Country country = countries.iterator().next();
		Country randomNeighbour = randomizer.randomNeighbour(country, 1);

		assertNotNull(country);
		assertNotNull(randomNeighbour);
		assertFalse(randomNeighbour.equals(country));

		//System.out.println(country.getName());
		//System.out.println(randomNeighbour.getName());
	}

	@Test
	public void test1000000() throws Exception {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				"src/main/resources/list.csv"));
		Randomizer randomizer = new Randomizer(countries);

		SetIndexGetter<Country> setIndexGetter = new SetIndexGetter<Country>();
		Random random = new Random();

		for (int i = 0; i < 1000000; ++i) {
			int index = random.nextInt(countries.size());
			int radius = random.nextInt(2) + 1;

			Country country = setIndexGetter.get(countries, index);
			//System.out.println("Picked: " + country.getName());
			Country randomNeighbour = randomizer.randomNeighbour(country,
					radius);

			assertNotNull(country);
			assertNotNull(randomNeighbour);

			if (randomNeighbour.equals(country)) {
				int a = 0;
			}
			/*System.out.println(country.getName() + " is in " + radius
					+ " radius to " + randomNeighbour.getName() + "\n\n\n");*/
			assertFalse(country.getName() + " points to itself!",
					randomNeighbour.equals(country));

		}
	}
}
