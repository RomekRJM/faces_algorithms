package rjm.romek.source.randomizer;

import static org.testng.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import rjm.romek.source.gen.CsvDeserializer;
import rjm.romek.source.model.Country;
import rjm.romek.source.model.Properties.Path;

public class CountryRandomizerTest {
	
	private Set<Country> countries;
	private CountryRandomizer randomizer;
	
	@BeforeMethod
	public void setUp() {
		countries = new CsvDeserializer().deserialize(new File(Path.LIST_CSV));
		randomizer = new CountryRandomizer(countries);
	}

	@Test
	public void test() throws Exception {
		countries.iterator().next();
		countries.iterator().next();
		countries.iterator().next();
		Country country = countries.iterator().next();
		Country randomNeighbour = randomizer.randomNeighbour(country, 1);

		assertNotNull(country);
		assertNotNull(randomNeighbour);
		assertFalse(randomNeighbour.equals(country));
	}

	@Test
	public void testRandomNeighbour1000000() throws Exception {
		SetIndexGetter<Country> setIndexGetter = new SetIndexGetter<Country>();
		Random random = new Random();

		for (int i = 0; i < 1000000; ++i) {
			int index = random.nextInt(countries.size());
			int radius = random.nextInt(2) + 1;

			Country country = setIndexGetter.get(countries, index);
			Country randomNeighbour = randomizer.randomNeighbour(country,
					radius);

			assertNotNull(country);
			assertNotNull(randomNeighbour);
			assertFalse(randomNeighbour.equals(country), country.getName() + " points to itself!");
            assertFalse(randomNeighbour.isDisabled());
		}
	}

    @Test
    public void testRandomNeighbours1000000() throws Exception {
        SetIndexGetter<Country> setIndexGetter = new SetIndexGetter<Country>();
        Random random = new Random();

        for (int i = 0; i < 1000000; ++i) {
            int index = random.nextInt(countries.size());
            int radius = random.nextInt(2) + 1;
            int size = random.nextInt(2) + 2;

            Country country = setIndexGetter.get(countries, index);
            List<Country> randomNeighbours = randomizer.randomNeighbours(country,
                    radius, size);

            assertEquals(randomNeighbours.size(), size);
            assertContainsUniqueCountries(randomNeighbours);
            assertContainsEnabledCountries(randomNeighbours);
        }
    }

    private void assertContainsUniqueCountries(List<Country> countries) {
        Set<Country> set = new HashSet<Country>();
        set.addAll(countries);

        assertEquals(set.size(), countries.size());
    }

    private void assertContainsEnabledCountries(List<Country> countries) {
        for(Country country : countries) {
            assertFalse(country.isDisabled());
        }
    }
}
