package hr.fer.zemris.optjava.dz3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<double[]> parseProblem(String fileName) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				Util.class.getResourceAsStream("/" + fileName), StandardCharsets.UTF_8))) {
			
			List<double[]> rows = new ArrayList<>();
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				
				line = line.trim();
				if (line.startsWith("#")) {
					continue;
				}
				
				String[] arguments = line.substring(1, line.length() - 1).split(",");
				double[] row = new double[arguments.length];
				
				for (int i = 0; i < arguments.length; i++) {
					row[i] = Double.parseDouble(arguments[i]);
				}
				
				rows.add(row);
			}
			
			return rows;
			
		} catch (IOException | NumberFormatException | NullPointerException e) {
			return null;
		}
	}
}
