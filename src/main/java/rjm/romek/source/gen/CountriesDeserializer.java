package rjm.romek.source.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rjm.romek.source.model.Country;

public class CountriesDeserializer {
	
	public Set<Country> deserialize(File file) throws FileNotFoundException {
		return deserialize(new FileInputStream(file));
	}
	
	public Set<Country> deserialize(InputStream is) {
		Set<Country> set = null;
		BufferedReader br = null;
		Gson gson = new Gson();
		
		try {
			br = new BufferedReader(new InputStreamReader(is));
			Type setType = new TypeToken<Set<Country>>(){}.getType();
			set = gson.fromJson(br, setType);
		} catch (Exception exc) {
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
