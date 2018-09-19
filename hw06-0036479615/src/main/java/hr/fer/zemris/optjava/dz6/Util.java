package hr.fer.zemris.optjava.dz6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.optjava.dz6.algorithms.ant.IProblem;
import hr.fer.zemris.optjava.dz6.algorithms.ant.ISolution;
import hr.fer.zemris.optjava.dz6.algorithms.ant.Point;

public class Util {
	
	private static final String STOP_SIGN_1 = "DISPLAY_DATA_SECTION";
	private static final String STOP_SIGN_2 = "NODE_COORD_SECTION";
	private static final String EOF = "EOF";
	private static final String OPT_TOUR = "TOUR_SECTION";
	
	private static final String TSP_EXT = ".tsp";
	private static final String OPT_EXT = ".opt.tour";
	
	public static List<Point> getCoordinates(String fileName) {
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + fileName), StandardCharsets.UTF_8))) {
			
			skipUnimportantLines(br);
			return getCoordinates(br);
			
		} catch (IOException | NumberFormatException | NullPointerException e) {
			return null;
		}
	}

	private static void skipUnimportantLines(BufferedReader br) throws IOException {
		String line;
		
		while (true) {
			line = br.readLine().trim();
			
			if (line.equals(STOP_SIGN_1) || line.equals(STOP_SIGN_2)) {
				break;
			}
		}
	}
	
	private static List<Point> getCoordinates(BufferedReader br) throws IOException {
		String line;
		List<Point> coords = new ArrayList<>();
		
		while ((line = br.readLine()) != null && !line.trim().equals(EOF)) {
			line = line.trim();
			String[] args = line.split("\\s+");
			
			Point point = new Point(
								Double.parseDouble(args[1].trim()),
								Double.parseDouble(args[2].trim()),
								Integer.parseInt(args[0]) - 1);
			
			coords.add(point);
		}
		
		return coords;
	}
	
	public static double[][] getDistances(List<Point> coords) {
		double[][] distances = new double[coords.size()][coords.size()];
		
		for (int i = 0, size = coords.size(); i < size; i++) {
			for (int j = 0; j < size; j++) {
				Point coord1 = coords.get(i);
				Point coord2 = coords.get(j);
				
				distances[i][j] = Math.sqrt(
										Math.pow(coord1.getX() - coord2.getX(), 2) +
										Math.pow(coord1.getY() - coord2.getY(), 2));
			}
		}
		
		return distances;
	}
	
	public static ISolution<int[]> createInitialSolution(IProblem<int[]> problem) {
		List<Integer> ints = new ArrayList<>();
		for (int i = 0; i < problem.getSize(); i++) {
			ints.add(i);
		}
		
		Collections.shuffle(ints);
		
		TSPSolution tspSol = new TSPSolution(ints.stream().mapToInt(a -> a).toArray(), 0d, 0d);
		tspSol.setFitness(problem.getFitness(tspSol));
		tspSol.setValue(problem.getValue(tspSol));
		
		return tspSol;
	}

	public static ISolution<int[]> getBestSolution(String fileName) {
		fileName = fileName.replaceAll(TSP_EXT, OPT_EXT);
		
		try (BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								Util.class.getResourceAsStream("/" + fileName), StandardCharsets.UTF_8))) {
			
			while (!br.readLine().trim().equals(OPT_TOUR));
			int[] bestVals = getBestValues(br);
			return new TSPSolution(bestVals, 0d, 0d);
			
		} catch (IOException | NumberFormatException | NullPointerException e) {
			return null;
		}
	}

	private static int[] getBestValues(BufferedReader br) throws NumberFormatException, IOException {
		String line;
		List<Integer> ints = new ArrayList<>();
		
		while ((line = br.readLine()) != null && !line.trim().equals("-1")) {
			line = line.trim();
			
			String[] args = line.split("\\s+");
			for (String arg : args) {
				ints.add(Integer.parseInt(arg) - 1);
			}
		}
		
		return ints.stream().mapToInt(a -> a).toArray();
	}
}
