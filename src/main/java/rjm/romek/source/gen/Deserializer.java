package rjm.romek.source.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class Deserializer<T> {
	public T deserialize(File file, TypeToken typeToken) throws FileNotFoundException {
		return deserialize(new FileInputStream(file), typeToken);
	}
	
	public T deserialize(InputStream is, TypeToken typeToken) {
		T set = null;
		BufferedReader br = null;
		Gson gson = new Gson();
		
		try {
			br = new BufferedReader(new InputStreamReader(is));
			set = (T) gson.fromJson(br, typeToken.getType());
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
