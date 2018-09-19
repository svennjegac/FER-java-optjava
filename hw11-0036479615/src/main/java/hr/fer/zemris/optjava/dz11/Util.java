package hr.fer.zemris.optjava.dz11;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

	public static void writeToFile(String file, int[] params) {
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(file))) {
			
			for (int i = 0; i < params.length; i++) {
				bw.write(params[i] + System.lineSeparator());
			}
			
		} catch (IOException e) {
		}
	}
	
}
