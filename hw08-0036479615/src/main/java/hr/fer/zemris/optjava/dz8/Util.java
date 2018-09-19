package hr.fer.zemris.optjava.dz8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.dz8.nn.LearningSample;

public class Util {
	
	private static final int DATA_SIZE = 600;

	public static List<LearningSample> loadData(String file, int timeDelay, int numberOfSamples) {
		List<Double> data = readDataFromFile(file);
		if (data == null) {
			return null;
		}
		
		data = normalizeData(data);
		return makeLearningSamples(data, timeDelay, numberOfSamples);
	}

	private static List<Double> readDataFromFile(String file) {
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + file),
								StandardCharsets.UTF_8))) {
			
			String line;
			List<Double> data = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				data.add(Double.parseDouble(line));
			}
			
			return data;
			
		} catch (IOException e) {
			return null;
		}
	}
	
	private static List<Double> normalizeData(List<Double> data) {
		double minValue = data.stream().min((d1, d2) -> Double.compare(d1, d2)).get();
		double maxValue = data.stream().max((d1, d2) -> Double.compare(d1, d2)).get();
		
		return data.stream()
					.map(value -> ((value - minValue) / (maxValue - minValue)) * 2.0 - 1.0)
					.collect(Collectors.toList())
					.subList(0, DATA_SIZE);
	}
	
	private static List<LearningSample> makeLearningSamples(List<Double> data, int timeDelay, int numberOfSamples) {
		if (argumentsInvalid(data, timeDelay, numberOfSamples)) {
			return null;
		}
		
		List<LearningSample> learningSamples = new ArrayList<>();
		
		for (int i = 0, size = numberOfSamples - timeDelay; i < size; i++) {
			learningSamples.add(new LearningSample(
										data.subList(i, i + timeDelay)
											.stream()
											.mapToDouble(d -> d)
											.toArray(),
										new double[]{ data.get(i + timeDelay) }));
		}
		
		return learningSamples;
	}

	private static boolean argumentsInvalid(List<Double> data, int timeDelay, int numberOfSamples) {
		if (timeDelay < 1 || timeDelay >= data.size()) {
			return true;
		}
		
		if (numberOfSamples == -1) {
			numberOfSamples = data.size();
		}
		
		if (numberOfSamples <= timeDelay) {
			return true;
		}
		
		return false;
	}
}
