package hr.fer.zemris.optjava.dz6.algorithms.ant;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;


public class AntAlgorithm<T> implements IOptAlgorithm<T> {

	private static final int STAGNATION_THRESH = 1000;
	private static final int PHEROMONES_DRAWING_INTERVAL = 300;
	private static final double MAX_WEIGHT = 0.65;
	
	private ExecutorService es;
	private ICanvas<T> canvas;
	private List<IAnt<T>> ants;
	private IAnt<T> bestAnt;
	private IAntSupplier<T> antSupplier;
	private ISolutionCreator<T> solutionCreator;
	
	private Map<Edge, EdgeData> pheromoneEdges;
	private IProblem<T> problem;
	
	private int maxIterations;
	private double tauMax;
	private double tauMin;
	private double ro;
	private double a;
	
	public AntAlgorithm(ICanvas<T> canvas, IProblem<T> problem, IAntSupplier<T> antSupplier, ISolutionCreator<T> solutionCreator,
			double tauMin, double ro, double a, int numberOfAnts, int maxIterations, ISolution<T> bestSolution) {
		
		this.canvas = canvas;
		this.problem = problem;
		this.antSupplier = antSupplier;
		this.solutionCreator = solutionCreator;
		
		this.maxIterations = maxIterations;
		this.tauMin = tauMin;
		this.ro = ro;
		this.a = a;
		
		bestAnt = antSupplier.newAnt(bestSolution, problem.getRandomStartPoint());
		tauMax = 1d / (ro * bestAnt.getValue());
		
		initAnts(numberOfAnts);
		initPheromoneEdges();
		
		solutionCreator.setProblem(problem);
		solutionCreator.setPheromoneEdges(pheromoneEdges);
	}

	private void initAnts(int numberOfAnts) {
		ants = new ArrayList<>();
		
		for (int i = 0; i < numberOfAnts; i++) {
			ants.add(antSupplier.newAnt(null, problem.getRandomStartPoint()));
		}
	}
	
	private void initPheromoneEdges() {
		pheromoneEdges = new HashMap<>();
		
		for (Point p1 : problem) {
			for (Point p2 : problem) {
				
				if (p1.getId() < p2.getId()) {
					pheromoneEdges.put(new Edge(p1.getId(), p2.getId()), new EdgeData(p1, p2, tauMax));
				}
			}
		}
	}

	@Override
	public ISolution<T> run() {
		try  {
			es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			showCanvas();
			int iter = 0;
			Counter bestChanged = new Counter();
			
			while (iter < maxIterations) {
				walkAnts();
				decayPheromones();
				dropPheromones(getChoosenAnts(bestChanged), iter);
				
				drawPheromones(iter);
				printBestSolution(iter);
				iter++;
				
				if (stagnationOccured(bestChanged)) {
					updateMaxMinTau();
					bestChanged.reset();
					resetPheromoneEdges();
					drawPheromones(0);
				}
			}
			
			return bestAnt.getSolution();
			
		} catch (NullPointerException e) {
			return bestAnt.getSolution();
		} finally {
			es.shutdown();
		}
	}

	private void showCanvas() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					canvas.showCanvas();
				}
			});
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}
	}
	
	private void drawBestSolution() {
		canvas.drawSolution(bestAnt.getSolution());
	}
	
	private void drawPheromones(int iter) {
		if (iter % PHEROMONES_DRAWING_INTERVAL == 0) {
			canvas.drawPheromones(pheromoneEdges);
		}
	}

	private void walkAnts() {
		List<Future<Void>> jobs = new ArrayList<>();
		
		for (IAnt<T> iAnt : ants) {
			jobs.add(es.submit(new AntCreationJob(iAnt)));
		}
		
		for (Future<Void> future : jobs) {
			try {
				future.get();
			} catch (CancellationException | InterruptedException | ExecutionException e) {
			}
		}
	}
	
	public void decayPheromones() {
		pheromoneEdges.entrySet().forEach(entry -> {
			EdgeData data = entry.getValue();
			double newValue = data.getPheromoneValue() * (1 - ro);
			
			if (newValue > tauMin) {
				data.setPheromoneValue(newValue);
			}
		});
	}

	private List<IAnt<T>> getChoosenAnts(Counter bestChanged) {
		List<IAnt<T>> choosenAnts = ants
										.stream()
										.sorted((a1, a2) -> Double.compare(a2.getFitness(), a1.getFitness()))
										.limit(1)
										.collect(Collectors.toList());
		
		if (bestAnt.getFitness() >= choosenAnts.get(0).getFitness()) {
			choosenAnts.add(bestAnt);
		} else {
			bestChanged.reset();
			bestAnt = choosenAnts.get(0).copy();
			choosenAnts.add(bestAnt);
			updateMaxMinTau();
			drawBestSolution();
		}
		
		bestChanged.increment();
		return choosenAnts;
	}

	public void dropPheromones(List<IAnt<T>> ants, int iter) {
		for (int i = 0; i < 2; i++) {
			IAnt<T> ant = ants.get(i);
			
			for (Edge edge : ant) {
				EdgeData edgeData = pheromoneEdges.get(edge);
				double oldValue = edgeData.getPheromoneValue();
				double newValue = oldValue + (1d / problem.getValue(ant.getSolution())) * weight(i, iter);
				
				if (newValue <= tauMax) {
					edgeData.setPheromoneValue(newValue);
				}
			}
		}
	}
	
	private double weight(int i, int iter) {
		double weight;
		
		if (i == 0) {
			weight = (double) (maxIterations - iter) / maxIterations / 2;
		} else {
			weight = iter / maxIterations + 0.5;
		}
		
		if (weight > MAX_WEIGHT) {
			weight = MAX_WEIGHT;
		} else if (weight < 1 - MAX_WEIGHT) {
			weight = 1 - MAX_WEIGHT;
		}
		
		return weight;
	}

	private void printBestSolution(int iter) {
		System.out.println(iter + " -> " + bestAnt.getValue());
	}
	
	private boolean stagnationOccured(Counter bestChanged) {
		return bestChanged.getCounter() >= STAGNATION_THRESH;
	}

	private void updateMaxMinTau() {
		tauMax = 1d / (ro * bestAnt.getValue());
		tauMin = tauMin / a;
	}
	
	private void resetPheromoneEdges() {
		pheromoneEdges
					.values()
					.forEach(edgeData -> edgeData.setPheromoneValue(tauMax));
	}
	
	private class AntCreationJob implements Callable<Void> {

		private IAnt<T> ant;
		
		public AntCreationJob(IAnt<T> ant) {
			this.ant = ant;
		}

		@Override
		public Void call() {
			ant.setSolution(solutionCreator.createSolution(ant.getStartPoint()));
			ant.setFitness(problem.getFitness(ant.getSolution()));
			ant.setValue(problem.getValue(ant.getSolution()));
			return null;
		}
	}
	
	private static class Counter {
		
		private int counter = 0;
	
		public void increment() {
			counter++;
		}
		
		public int getCounter() {
			return counter;
		}
		
		public void reset() {
			counter = 0;
		}
	}
}