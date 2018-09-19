package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriSatSolver {
	
	private static final String COMMENT_SIGN = "c";
	private static final String STOP_SIGN = "%";
	private static final String DEFINITION_SIGN = "p";
	
	private static final Integer SAT_N = 3;
	private static Integer VARIABLES = -1;
	
	private static final Map<Integer, Algorithm> ALGORITHMS;
	
	static {
		ALGORITHMS = new HashMap<>();
		ALGORITHMS.put(1, new AlgorithmOne());
		ALGORITHMS.put(2, new AlgorithmTwo());
		ALGORITHMS.put(3, new AlgorithmThree());
		ALGORITHMS.put(4, new AlgorithmFour());
		ALGORITHMS.put(5, new AlgorithmFive());
		ALGORITHMS.put(6, new AlgorithmSix());
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Krivi broj argumenata.");
			return;
		}
		
		
		int algorithmIndex = 0;
		String path = null;
		
		try {
			algorithmIndex = Integer.parseInt(args[0]);
			path = args[1];
		} catch (NumberFormatException | InvalidPathException e) {
			System.out.println("Argumenti nisu valjani.");
			return;
		}
		
		Clause[] clauses = parseClauses(path);
		if (clauses == null) {
			System.out.println("Ne mogu otvoriti datoteku.");
			return;
		}
		
		SATFormula satFormula = new SATFormula(VARIABLES, clauses);
		Algorithm algorithm = ALGORITHMS.get(algorithmIndex);
		if (algorithm == null) {
			System.out.println("Indeks algoritma nije valjan.");
			return;
		}
		algorithm.setSATFormula(satFormula);
		
		algorithm.runAlgorithm();
		if (algorithm.foundSolution()) {
			System.out.println("Zadovoljivo: " + algorithm.getSolution());
		} else {
			System.out.println("Algoritam nije pronašao rješenje.");
		}
	}

	private static Clause[] parseClauses(String path) {
		List<Clause> clauses = new ArrayList<>();
		InputStream is = TriSatSolver.class.getResourceAsStream("/" + path);
		if (is == null) {
			return null;
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				
				//special lines handling
				if (line.startsWith(COMMENT_SIGN)) {
					continue;
				} else if (line.startsWith(STOP_SIGN)) {
					break;
				} else if (line.startsWith(DEFINITION_SIGN)) {
					String[] defArgs = line.split("\\s+");
					
					VARIABLES = Integer.parseInt(defArgs[2]);
					continue;
				}
				
				//clause line parsing
				String[] vars = line.split("\\s+");
				int[] indexes = new int[SAT_N];
				
				for (int i = 0; i < SAT_N; i++) {
					int var = Integer.parseInt(vars[i]);
					
					if (var == 0) {
						break;
					}
					
					indexes[i] = var;
				}
				
				clauses.add(new Clause(indexes));
			}
			
			return clauses.toArray(new Clause[clauses.size()]);
		} catch (IOException e) {
			return null;
		}
	}
}
