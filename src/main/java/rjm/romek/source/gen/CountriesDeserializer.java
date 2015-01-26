package rjm.romek.source.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import com.google.gson.reflect.TypeToken;

import rjm.romek.source.model.Country;

public class CountriesDeserializer extends Deserializer<Set<Country>>{

	public Set<Country> deserialize(File file)
			throws FileNotFoundException {
		return super.deserialize(file, new TypeToken<Set<Country>>() {});
	}

	public Set<Country> deserialize(InputStream is) {
		return super.deserialize(is, new TypeToken<Set<Country>>() {});
	}
	
}
