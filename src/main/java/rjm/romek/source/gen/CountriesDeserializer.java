package rjm.romek.source.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rjm.romek.source.model.Country;

public class CountriesDeserializer {
	public Set<Country> deserialize(File file) {
		Set<Country> set = null;
		BufferedReader br = null;
		Gson gson = new Gson();
		
		try {
			br = new BufferedReader(new FileReader(file));
			Type setType = new TypeToken<Set<Country>>(){}.getType();
			set = gson.fromJson(br, setType);
		} catch (IOException exc) {
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		
		return set;
	}
}
