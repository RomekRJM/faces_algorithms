package rjm.romek.source.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Set;

import rjm.romek.source.model.Country;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class Serializer<T> {
	
	public void serialize(File file, T object) throws FileNotFoundException {
		serialize(new FileOutputStream(file), object);
	}
	
	public void serialize(OutputStream os, T object) {
		Gson gson = new Gson();
		Type type = new TypeToken<T>(){}.getType();
		String json = gson.toJson(object, type);
		
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
