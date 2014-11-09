package rjm.romek.source.gen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import rjm.romek.source.model.Border;
import rjm.romek.source.model.BorderType;
import rjm.romek.source.model.Country;

public class CsvDeserializer {
	
	public Set<Country> deserialize(File csvFile) {
		BufferedReader br = null;
		String line = null;
		Set<Country> countries = new HashSet<Country>();
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)));
			while((line = br.readLine()) != null) {
				int colonPos = line.indexOf(":");
				Country country = new Country();
				country.setName(line.substring(0, colonPos));
				String [] neighbours = line.substring(colonPos+1).split(",");
				
				for(String neighbour : neighbours) {
					
					BorderType type = null;
					int bracketPos = neighbour.indexOf("(");
					
					if(bracketPos > -1) {
						if(neighbour.charAt(bracketPos+1) == 'M') {
							type = BorderType.SEA;
						} else {
							type = BorderType.LAND;
						}
						neighbour = neighbour.substring(0, bracketPos);
					} else {
						type = BorderType.UNSPECIFIED;
					}
					
					neighbour = neighbour.trim();
					
					Country neighbourCountry = new Country();
					neighbourCountry.setName(neighbour);
					country.addNeighbour(new Border(neighbourCountry, type));
				}
				
				countries.add(country);
			}
			
		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		
		return countries;
	}
	
	
	
	
}
