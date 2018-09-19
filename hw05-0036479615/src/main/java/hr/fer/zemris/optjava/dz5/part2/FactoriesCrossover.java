package hr.fer.zemris.optjava.dz5.part2;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.algorithms.genetic.ICrossoverOperator;
import hr.fer.zemris.optjava.dz5.algorithms.genetic.ISolution;

public class FactoriesCrossover implements ICrossoverOperator<int[]> {

	private Random rand = new Random(System.currentTimeMillis());
	
	@Override
	public ISolution<int[]> crossover(ISolution<int[]> solution1, ISolution<int[]> solution2) {
		int[] rep1 = solution1.getRepresentation();
		int[] rep2 = solution2.getRepresentation();
		
		int index1 = rand.nextInt(rep1.length);
		int index2;
		
		while (true) {
			index2 = rand.nextInt(rep1.length);
			
			if (index2 != index1) {
				break;
			}
		}
		
		if (index2 < index1) {
			int tmp = index1;
			index1 = index2;
			index2 = tmp;
		}
		
		int[] childRep = makeCrossover(rep1, rep2, index1, index2);
		return new FactoriesLocationsSolution(childRep, 0d, 0d);
	}

	private int[] makeCrossover(int[] rep1, int[] rep2, int index1, int index2) {
		int[] childRep = new int[rep1.length];
		
		for (int i = 0; i < rep1.length; i++) {
			childRep[i] = -1;
		}
		
		
		for (int i = index1; i <= index2; i++) {
			childRep[i] = rep1[i];
		}
		
		int index = 0;
		for (int i = 0; i < childRep.length; i++) {
			if (childRep[i] != -1) {
				continue;
			}
			
			while (true) {
				boolean updated = false;
				
				for (int j = 0; j < childRep.length; j++) {
					if (childRep[j] == rep2[index]) {
						index++;
						updated = true;
						break;
					}
				}
				
				if (updated) {
					continue;
				}
				
				break;
			}
			
			childRep[i] = rep2[index];
		}
		
		return childRep;
	}
}
