package rjm.romek.source.gen;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import rjm.romek.source.model.Border;
import rjm.romek.source.model.BorderType;
import rjm.romek.source.model.Country;

public class CsvDeserializer {
	
	public Set<Country> deserialize(File csvFile) {
		BufferedReader br = null;
		String line;
		Set<Country> countries = new HashSet<Country>();
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), System.getProperty("file.encoding")));
			while((line = br.readLine()) != null) {
				int colonPos = line.indexOf(":");
                int firstBracketPos = line.indexOf("(");
                int secondBracketPos = line.indexOf(")");
				Country country = new Country();
				country.setName(line.substring(0, firstBracketPos));
                String substingAfterColon = line.substring(colonPos+1);
                country.setContinent(line.substring(firstBracketPos + 1, secondBracketPos));
				String [] neighbours;

                if(StringUtils.isNotBlank(substingAfterColon)) {
                    neighbours = substingAfterColon.split(",");
                } else {
                    neighbours = new String[0];
                }
				
				for(String neighbour : neighbours) {
					
					BorderType type;
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
					country.addNeighbour(new Border(neighbourCountry.getName(), type));
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
