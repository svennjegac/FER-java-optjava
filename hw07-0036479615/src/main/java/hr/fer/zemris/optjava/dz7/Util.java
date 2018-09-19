package hr.fer.zemris.optjava.dz7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<Data> parseProblem(String fileName) {
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + fileName),
								StandardCharsets.UTF_8))) {
			
			List<Data> data = new ArrayList<>();
			
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				
				String[] args = line.trim().split(":");
				args[0] = args[0].substring(1, args[0].length() - 1);
				args[1] = args[1].substring(1, args[1].length() - 1);
				
				String[] inputArgs = args[0].split(",");
				String[] outputArgs = args[1].split(",");
				
				double[] input = new double[inputArgs.length];
				for (int i = 0; i < inputArgs.length; i++) {
					input[i] = Double.parseDouble(inputArgs[i]);
				}
				
				double[] output = new double[outputArgs.length];
				for (int i = 0; i < output.length; i++) {
					output[i] = Double.parseDouble(outputArgs[i]);
				}
				
				data.add(new Data(input, output));
			}
			
			return data;
			
		} catch (IOException | NullPointerException | NumberFormatException e) {
			return null;
		}
	}
}
