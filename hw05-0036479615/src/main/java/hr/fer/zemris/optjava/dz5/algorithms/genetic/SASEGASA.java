package hr.fer.zemris.optjava.dz5.algorithms.genetic;

import java.util.ArrayList;
import java.util.List;

public class SASEGASA<T> extends AbstractGeneticAlgorithm<T> {
	
	private int currentSubpopulations;
	private AbstractGeneticAlgorithm<T> optAlgorithm;
	
	public SASEGASA(
			int populationSize,
			IProblem<T> problem,
			AbstractGeneticAlgorithm<T> optAlgorithm,
			double fitnessThreshold,
			int numberOfPopulations) {
		
		super(
			populationSize,
			null,
			null,
			null,
			problem,
			fitnessThreshold,
			-1);
		
		this.optAlgorithm = optAlgorithm;
		this.currentSubpopulations = numberOfPopulations;
	}
	
	@Override
	public ISolution<T> run() {
		while (currentSubpopulations >= 1) {
			iteration++;
			population = makeNewPopulation();
			
			printToStdOutput();
			
			if (foundGoodSolution()) {
				return population.getBestSolution();
			}
			
			currentSubpopulations--;
		}
		
		return population.getBestSolution();
	}
	
	@Override
	IPopulation<T> makeNewPopulation() {
		List<IPopulation<T>> subpopulations = createSubpopulations();
		List<IPopulation<T>> convergedSubpopulations = runSubpopulations(subpopulations);
		updateGlobalPopulation(convergedSubpopulations);
		
		return population;
	}

	private List<IPopulation<T>> createSubpopulations() {
		List<IPopulation<T>> subPopulations = new ArrayList<>();
		
		int chromosomesPerPopulation = population.getSize() / currentSubpopulations;
		
		for (int i = 0; i < currentSubpopulations - 1; i++) {
			IPopulation<T> nextPopulation = new Population<>();
			
			for (int j = 0; j < chromosomesPerPopulation; j++) {
				nextPopulation.add(population.getSolution(i * chromosomesPerPopulation + j));
			}
			
			subPopulations.add(nextPopulation);
		}
		
		int index = chromosomesPerPopulation * subPopulations.size();
		IPopulation<T> lastPopulation = new Population<>();
		
		while (index < population.getSize()) {
			lastPopulation.add(population.getSolution(index));
			index++;
		}
		
		subPopulations.add(lastPopulation);
		return subPopulations;
	}
	
	private List<IPopulation<T>> runSubpopulations(List<IPopulation<T>> subpopulations) {
		List<IPopulation<T>> convergedPopulations = new ArrayList<>();
		
		for (IPopulation<T> iPopulation : subpopulations) {
			optAlgorithm.setPopulation(iPopulation);
			optAlgorithm.run();
			
			convergedPopulations.add(optAlgorithm.getPopulation());
		}
		
		return convergedPopulations;
	}
	
	private void updateGlobalPopulation(List<IPopulation<T>> convergedSubpopulations) {
		population.clear();
		
		for (IPopulation<T> iPopulation : convergedSubpopulations) {
			for (ISolution<T> solution : iPopulation.getSoultions()) {
				population.add(solution);
			}
		}
	}

	@Override
	boolean printSolution(ISolution<T> solution) {
		return true;
	}
}
