package hr.fer.zemris.optjava.dz5.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

public class Util {

	public static int[][][] getData(String file) {
		
		try (BufferedReader br =
				new BufferedReader(
					new InputStreamReader(
							Util.class.getResourceAsStream("/" + file),
							StandardCharsets.UTF_8))) {
			
			int n = Integer.parseInt(br.readLine().trim());
			
			int[][] distances = readData(br, n);
			int[][] traffic = readData(br, n);
			
			int[][][] output = new int[2][n][n];
			output[0] = distances;
			output[1] = traffic;
			
			return output;
		} catch (IOException | NullPointerException | NumberFormatException e) {
			return null;
		}
	}

	private static int[][] readData(BufferedReader br, int n) throws IOException {
		int[][] data = new int[n][n];
		br.readLine();
		
		for (int i = 0; i < n; i++) {
			String line = br.readLine();
			String values[] = line.trim().split("\\s+");
			
			if (values.length != n) {
				String ln2 = br.readLine();
				String[] values2 = ln2.trim().split("\\s+");
				
				values = Stream.concat(Arrays.stream(values), Arrays.stream(values2)).toArray(String[]::new);
			}
			
			for (int j = 0; j < n; j++) {
				data[i][j] = Integer.parseInt(values[j].trim());
			}
		}
		
		return data;
	}
}
