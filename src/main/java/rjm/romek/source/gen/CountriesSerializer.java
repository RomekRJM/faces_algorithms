package rjm.romek.source.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

import rjm.romek.source.model.Country;

import com.google.gson.Gson;

public class CountriesSerializer {
	
	public void serialize(File file, Set<Country> countries) throws FileNotFoundException {
		serialize(new FileOutputStream(file), countries);
	}
	
	public void serialize(OutputStream os, Set<Country> countries) {
		Gson gson = new Gson();
		String json = gson.toJson(countries);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(json);
		} catch (IOException exc) {
			return;
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
