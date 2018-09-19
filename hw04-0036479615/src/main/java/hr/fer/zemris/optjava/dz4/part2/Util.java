package hr.fer.zemris.optjava.dz4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

	public static List<Stick> parseProblem(String file) throws IOException, NullPointerException, NumberFormatException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Util.class.getResourceAsStream("/" + file), StandardCharsets.UTF_8))) {
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			String[] numbers = sb.toString().trim()
					.substring(1, sb.toString().trim().length() - 1).split(",");
			
			return Arrays
					.asList(numbers)
					.stream()
					.map(str -> new Stick(Integer.parseInt(str.trim())))
					.collect(Collectors.toList());
		}
	}
}
