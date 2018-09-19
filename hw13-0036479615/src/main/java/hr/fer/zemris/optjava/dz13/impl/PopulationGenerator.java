package hr.fer.zemris.optjava.dz13.impl;

import hr.fer.zemris.optjava.dz13.genetic.IPopulation;
import hr.fer.zemris.optjava.dz13.genetic.IPopulationGenerator;
import hr.fer.zemris.optjava.dz13.genetic.IProblem;
import hr.fer.zemris.optjava.dz13.genetic.ISolution;
import hr.fer.zemris.optjava.dz13.genetic.Population;
import hr.fer.zemris.optjava.dz13.nodes.NodeUtil;
import hr.fer.zemris.optjava.dz13.nodes.Tree;

public class PopulationGenerator implements IPopulationGenerator<Tree> {

	private int depth;
	private int populationSize;
	private IProblem<Tree> problem;
	
	public PopulationGenerator(int depth, int populationSize, IProblem<Tree> problem) {
		this.depth = depth;
		this.populationSize = populationSize;
		this.problem = problem;
	}

	@Override
	public IPopulation<Tree> generatePopulation() {
		int differentDepths = depth - 1;
		double eachDepthPercentage = 1.0 / differentDepths;
		int eachDepthEachKindTreeNumber = (int) (eachDepthPercentage * populationSize / 2);
	
		IPopulation<Tree> population = new Population<>();
		
		for (int i = depth; i >= 2; i--) {
			for (int j = 0; j < eachDepthEachKindTreeNumber; j++) {
				population.add(new TreeSolution(NodeUtil.createFullTree(i), 0, 0));
				population.add(new TreeSolution(NodeUtil.createGrowTree(i), 0, 0));
			}
		}
		
		for (ISolution<Tree> solution : population) {
			solution.setFitness(problem.getFitness(solution));
			solution.setValue(problem.getValue(solution));
		}
		
		return population;
	}
}
